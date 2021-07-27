package com.lielamar.armsrace.managers;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.lielamar.armsrace.Main;
import com.lielamar.armsrace.modules.CustomPlayer;
import com.lielamar.armsrace.modules.CustomScoreboard;

import net.md_5.bungee.api.ChatColor;

public class ScoreboardManager {

	private Main main;
	private CustomScoreboard sb;

	public ScoreboardManager(Main main) {
		this.main = main;
		load();
		start();
	}

	public CustomScoreboard getSb() {
		return sb;
	}

	public void load() {
		this.sb = new CustomScoreboard(false, null, null, null);
		if(main.getConfig().contains("Scoreboard")) {
			boolean enabled = main.getConfig().getBoolean("Scoreboard.Enabled");
			this.sb.setEnabled(enabled);

			String title = main.getConfig().getString("Scoreboard.Title");
			this.sb.setTitle(title);

			String footer = main.getConfig().getString("Scoreboard.Footer");
			this.sb.setFooter(footer);

			List<String> configList = main.getConfig().getStringList("Scoreboard.Lines");
			String[] lines = new String[configList.size()];
			for(int i = 0; i < lines.length; i++)
				lines[i] = configList.get(i);

			String[] fixed = new String[lines.length];
			int temp = 0;
			for (int i = lines.length-1; i > 0; i--) {
				fixed[temp] = lines[i];
				temp++;
			}
			this.sb.setLines(fixed);
		}
	}

	public void start() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(main, new Runnable() {
			@Override
			public void run() {
				if(Bukkit.getOnlinePlayers().size() == 0) return;

				for(CustomPlayer pl : main.getPlayerManager().getPlayers().values()) {
					if(pl == null) continue;
					updatePlayer(pl);
				}
			}
		}, 0L, 20L);
	}

	public void addPlayer(CustomPlayer cp) {
		Player p = cp.getPlayer();

		Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective obj = scoreboard.registerNewObjective("ArmsRace", "dummy");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);

		CustomScoreboard csb = this.sb;
		String title = csb.getTitle();

		if(cp.getCurrentMap() != null) {
			csb = cp.getCurrentMap().getSb();
			title = csb.getTitle().replace("%mapname%", cp.getCurrentMap().getName());
		}

		obj.setDisplayName(ChatColor.translateAlternateColorCodes('&', csb.getTitle().replaceAll("%mapname%", title)));

		Score footer = obj.getScore(ChatColor.translateAlternateColorCodes('&', csb.getFooter()));
		footer.setScore(0);

		int score = 1;
		for(String line : csb.getLines()) {
			Team team = null;
			String entry = null;

			if(line == null) continue;
			else if(line.contains("%mapname%")) {
				team = scoreboard.registerNewTeam("mapName");
				entry = ChatColor.translateAlternateColorCodes('&', line.replaceAll("%mapname%", ""));
				team.addEntry(entry);
				team.setSuffix(cp.getCurrentMap().getName());
			} else if(line.contains("%name%")) {
				team = scoreboard.registerNewTeam("name");
				entry = ChatColor.translateAlternateColorCodes('&', line.replaceAll("%name%", ""));
				team.addEntry(entry);
				team.setSuffix(p.getName());
			} else if(line.contains("%displayname%")) {
				team = scoreboard.registerNewTeam("displayname");
				entry = ChatColor.translateAlternateColorCodes('&', line.replaceAll("%displayname%", ""));
				team.addEntry(entry);
				team.setSuffix(p.getDisplayName());
			} else if(line.contains("%kills%")) {
				team = scoreboard.registerNewTeam("kills");
				entry = ChatColor.translateAlternateColorCodes('&', line.replaceAll("%kills%", ""));
				team.addEntry(entry);
				team.setSuffix("" + cp.getKills(cp.getCurrentMap().getName()));
			} else if(line.contains("%deaths%")) {
				team = scoreboard.registerNewTeam("deaths");
				entry = ChatColor.translateAlternateColorCodes('&', line.replaceAll("%deaths%", ""));
				team.addEntry(entry);
				team.setSuffix("" + cp.getDeaths(cp.getCurrentMap().getName()));
			} else if(line.contains("%coins%")) {
				team = scoreboard.registerNewTeam("coins");
				entry = ChatColor.translateAlternateColorCodes('&', line.replaceAll("%coins%", ""));
				team.addEntry(entry);
				team.setSuffix("" + cp.getCoins());
			} else if(line.contains("%sumkills%")) {
				team = scoreboard.registerNewTeam("sumkills");
				entry = ChatColor.translateAlternateColorCodes('&', line.replaceAll("%sumkills%", ""));
				team.addEntry(entry);
				team.setSuffix("" + cp.getKills());
			} else if(line.contains("%sumdeaths%")) {
				team = scoreboard.registerNewTeam("sumdeaths");
				entry = ChatColor.translateAlternateColorCodes('&', line.replaceAll("%sumdeaths%", ""));
				team.addEntry(entry);
				team.setSuffix("" + cp.getDeaths());
			} else if(line.contains("%ks%")) {
				team = scoreboard.registerNewTeam("ks");
				entry = ChatColor.translateAlternateColorCodes('&', line.replaceAll("%ks%", ""));
				team.addEntry(entry);
				team.setSuffix("" + cp.getKillstreak());
			} else if(line.contains("%topks%")) {
				team = scoreboard.registerNewTeam("topks");
				entry = ChatColor.translateAlternateColorCodes('&', line.replaceAll("%topks%", ""));
				team.addEntry(entry);

				if(cp.getCurrentMap().getHighestKillstreak() == null)
					team.setSuffix("none");
				else
					team.setSuffix(cp.getCurrentMap().getHighestKillstreak().getPlayer().getName());
			} else if(line.contains("%tier%")) {
				team = scoreboard.registerNewTeam("tier");
				entry = ChatColor.translateAlternateColorCodes('&', line.replaceAll("%tier%", ""));
				team.addEntry(entry);
				team.setSuffix("" + cp.getCurrentTierId());
			} else if(line.contains("%toptier%")) {
				team = scoreboard.registerNewTeam("toptier");
				entry = ChatColor.translateAlternateColorCodes('&', line.replaceAll("%toptier%", ""));
				team.addEntry(entry);

				if(cp.getCurrentMap().getHighestTier() == null)
					team.setSuffix("none");
				else
					team.setSuffix(cp.getCurrentMap().getHighestTier().getPlayer().getName());
			} else {
				Score s = obj.getScore(line);
				s.setScore(score);
				score++;
				continue;
			}

			if(team == null || entry == null) continue;
			obj.getScore(entry).setScore(score);
			score++;
		}

		cp.getPlayer().setScoreboard(scoreboard);
	}

	public void removePlayer(CustomPlayer cp) {
		Player p = cp.getPlayer();
		p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
	}

	public void updatePlayer(CustomPlayer cp) {
		Scoreboard scoreboard = cp.getPlayer().getScoreboard();

		if(scoreboard.getTeam("mapName") != null)
			scoreboard.getTeam("mapName").setSuffix(cp.getCurrentMap().getName());

		if(scoreboard.getTeam("name") != null)
			scoreboard.getTeam("name").setSuffix(cp.getPlayer().getName());

		if(scoreboard.getTeam("displayname") != null)
			scoreboard.getTeam("displayname").setSuffix(cp.getPlayer().getDisplayName());

		if(scoreboard.getTeam("kills") != null)
			scoreboard.getTeam("kills").setSuffix("" + cp.getKills(cp.getCurrentMap().getName()));

		if(scoreboard.getTeam("deaths") != null)
			scoreboard.getTeam("deaths").setSuffix("" + cp.getDeaths(cp.getCurrentMap().getName()));

		if(scoreboard.getTeam("coins") != null)
			scoreboard.getTeam("coins").setSuffix("" + cp.getCoins());

		if(scoreboard.getTeam("sumkills") != null)
			scoreboard.getTeam("sumkills").setSuffix("" + cp.getKills());

		if(scoreboard.getTeam("sumdeaths") != null)
			scoreboard.getTeam("sumdeaths").setSuffix("" + cp.getDeaths());

		if(scoreboard.getTeam("ks") != null)
			scoreboard.getTeam("ks").setSuffix("" + cp.getKillstreak());

		if(scoreboard.getTeam("topks") != null) {
			if(cp.getCurrentMap().getHighestKillstreak() == null)
				scoreboard.getTeam("topks").setSuffix("none");
			else
				scoreboard.getTeam("topks").setSuffix(cp.getCurrentMap().getHighestKillstreak().getPlayer().getName());
		}

		if(scoreboard.getTeam("tier") != null)
			scoreboard.getTeam("tier").setSuffix("" + cp.getCurrentTierId());

		if(scoreboard.getTeam("topplayertier") != null) {
			if(cp.getCurrentMap().getHighestTier() == null)
				scoreboard.getTeam("topplayertier").setSuffix("none");
			else
				scoreboard.getTeam("topplayertier").setSuffix(cp.getCurrentMap().getHighestTier().getPlayer().getName());
		}
	}
}
