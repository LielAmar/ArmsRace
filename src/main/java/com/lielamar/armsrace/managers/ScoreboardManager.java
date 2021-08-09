package com.lielamar.armsrace.managers;

import com.lielamar.armsrace.Main;
import com.lielamar.armsrace.modules.CustomPlayer;
import com.lielamar.armsrace.modules.CustomScoreboard;
import com.lielamar.armsrace.modules.map.Map;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.Collections;
import java.util.List;

public class ScoreboardManager {

    private Main main;
    private CustomScoreboard customScoreboard;

    public ScoreboardManager(Main main) {
        this.main = main;
        load();
        start();
    }

    public CustomScoreboard getCustomScoreboard() {
        return customScoreboard;
    }

    public void load() {
        this.customScoreboard = new CustomScoreboard(false, null, null, null);

        if (main.getConfig().contains("Scoreboard")) {
            customScoreboard.enabled = main.getConfig().getBoolean("Scoreboard.Enabled");
            customScoreboard.title = main.getConfig().getString("Scoreboard.Title");
            customScoreboard.footer = main.getConfig().getString("Scoreboard.Footer");

            List<String> lines = main.getConfig().getStringList("Scoreboard.Lines");
            Collections.reverse(lines);

            customScoreboard.lines = lines.toArray(new String[0]);
        }
    }

    public void start() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(main, () -> main.getPlayerManager().getPlayers().values().forEach(this::updatePlayer), 0L, 20L);
    }

    public void addPlayer(CustomPlayer customPlayer) {
        Player player = customPlayer.getPlayer();
        Map currentMap = customPlayer.getCurrentMap();
        CustomScoreboard customScoreboard = currentMap != null ? currentMap.getCustomScoreboard() : this.customScoreboard;
        String title = customScoreboard.title;

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("ArmsRace", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', title.replaceAll("%mapname%", customPlayer.getCurrentMap().getName())));

        Score footer = objective.getScore(ChatColor.translateAlternateColorCodes('&', customScoreboard.footer));
        footer.setScore(0);

        int score = 1;
        for (String line : customScoreboard.lines) {
            Team team;
            String entry;

            if (line.contains("%mapname%")) {
                team = scoreboard.registerNewTeam("mapName");
                entry = ChatColor.translateAlternateColorCodes('&', line.replaceAll("%mapname%", ""));
                team.addEntry(entry);
                team.setSuffix(customPlayer.getCurrentMap().getName());
            } else if (line.contains("%name%")) {
                team = scoreboard.registerNewTeam("name");
                entry = ChatColor.translateAlternateColorCodes('&', line.replaceAll("%name%", ""));
                team.addEntry(entry);
                team.setSuffix(player.getName());
            } else if (line.contains("%displayname%")) {
                team = scoreboard.registerNewTeam("displayname");
                entry = ChatColor.translateAlternateColorCodes('&', line.replaceAll("%displayname%", ""));
                team.addEntry(entry);
                team.setSuffix(player.getDisplayName());
            } else if (line.contains("%kills%")) {
                team = scoreboard.registerNewTeam("kills");
                entry = ChatColor.translateAlternateColorCodes('&', line.replaceAll("%kills%", ""));
                team.addEntry(entry);
                team.setSuffix("" + customPlayer.getKills(customPlayer.getCurrentMap().getName()));
            } else if (line.contains("%deaths%")) {
                team = scoreboard.registerNewTeam("deaths");
                entry = ChatColor.translateAlternateColorCodes('&', line.replaceAll("%deaths%", ""));
                team.addEntry(entry);
                team.setSuffix("" + customPlayer.getDeaths(customPlayer.getCurrentMap().getName()));
            } else if (line.contains("%coins%")) {
                team = scoreboard.registerNewTeam("coins");
                entry = ChatColor.translateAlternateColorCodes('&', line.replaceAll("%coins%", ""));
                team.addEntry(entry);
                team.setSuffix("" + customPlayer.getCoins());
            } else if (line.contains("%sumkills%")) {
                team = scoreboard.registerNewTeam("sumkills");
                entry = ChatColor.translateAlternateColorCodes('&', line.replaceAll("%sumkills%", ""));
                team.addEntry(entry);
                team.setSuffix("" + customPlayer.getKills());
            } else if (line.contains("%sumdeaths%")) {
                team = scoreboard.registerNewTeam("sumdeaths");
                entry = ChatColor.translateAlternateColorCodes('&', line.replaceAll("%sumdeaths%", ""));
                team.addEntry(entry);
                team.setSuffix("" + customPlayer.getDeaths());
            } else if (line.contains("%ks%")) {
                team = scoreboard.registerNewTeam("ks");
                entry = ChatColor.translateAlternateColorCodes('&', line.replaceAll("%ks%", ""));
                team.addEntry(entry);
                team.setSuffix("" + customPlayer.getKillStreak());
            } else if (line.contains("%topks%")) {
                team = scoreboard.registerNewTeam("topks");
                entry = ChatColor.translateAlternateColorCodes('&', line.replaceAll("%topks%", ""));
                team.addEntry(entry);

                if (customPlayer.getCurrentMap().getHighestKillstreak() == null)
                    team.setSuffix("none");
                else
                    team.setSuffix(customPlayer.getCurrentMap().getHighestKillstreak().getPlayer().getName());
            } else if (line.contains("%tier%")) {
                team = scoreboard.registerNewTeam("tier");
                entry = ChatColor.translateAlternateColorCodes('&', line.replaceAll("%tier%", ""));
                team.addEntry(entry);
                team.setSuffix("" + customPlayer.getCurrentTierId());
            } else if (line.contains("%toptier%")) {
                team = scoreboard.registerNewTeam("toptier");
                entry = ChatColor.translateAlternateColorCodes('&', line.replaceAll("%toptier%", ""));
                team.addEntry(entry);

                if (customPlayer.getCurrentMap().getHighestTier() == null)
                    team.setSuffix("none");
                else
                    team.setSuffix(customPlayer.getCurrentMap().getHighestTier().getPlayer().getName());
            } else {
                Score s = objective.getScore(line);
                s.setScore(score);
                score++;
                continue;
            }

            objective.getScore(entry).setScore(score);
            score++;
        }

        customPlayer.getPlayer().setScoreboard(scoreboard);
    }

    public void removePlayer(CustomPlayer cp) {
        Player p = cp.getPlayer();
        p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
    }

    public void updatePlayer(CustomPlayer cp) {
        Scoreboard scoreboard = cp.getPlayer().getScoreboard();

        if (scoreboard.getTeam("mapName") != null)
            scoreboard.getTeam("mapName").setSuffix(cp.getCurrentMap().getName());

        if (scoreboard.getTeam("name") != null)
            scoreboard.getTeam("name").setSuffix(cp.getPlayer().getName());

        if (scoreboard.getTeam("displayname") != null)
            scoreboard.getTeam("displayname").setSuffix(cp.getPlayer().getDisplayName());

        if (scoreboard.getTeam("kills") != null)
            scoreboard.getTeam("kills").setSuffix("" + cp.getKills(cp.getCurrentMap().getName()));

        if (scoreboard.getTeam("deaths") != null)
            scoreboard.getTeam("deaths").setSuffix("" + cp.getDeaths(cp.getCurrentMap().getName()));

        if (scoreboard.getTeam("coins") != null)
            scoreboard.getTeam("coins").setSuffix("" + cp.getCoins());

        if (scoreboard.getTeam("sumkills") != null)
            scoreboard.getTeam("sumkills").setSuffix("" + cp.getKills());

        if (scoreboard.getTeam("sumdeaths") != null)
            scoreboard.getTeam("sumdeaths").setSuffix("" + cp.getDeaths());

        if (scoreboard.getTeam("ks") != null)
            scoreboard.getTeam("ks").setSuffix("" + cp.getKillStreak());

        if (scoreboard.getTeam("topks") != null) {
            if (cp.getCurrentMap().getHighestKillstreak() == null)
                scoreboard.getTeam("topks").setSuffix("none");
            else
                scoreboard.getTeam("topks").setSuffix(cp.getCurrentMap().getHighestKillstreak().getPlayer().getName());
        }

        if (scoreboard.getTeam("tier") != null)
            scoreboard.getTeam("tier").setSuffix("" + cp.getCurrentTierId());

        if (scoreboard.getTeam("topplayertier") != null) {
            if (cp.getCurrentMap().getHighestTier() == null)
                scoreboard.getTeam("topplayertier").setSuffix("none");
            else
                scoreboard.getTeam("topplayertier").setSuffix(cp.getCurrentMap().getHighestTier().getPlayer().getName());
        }
    }
}
