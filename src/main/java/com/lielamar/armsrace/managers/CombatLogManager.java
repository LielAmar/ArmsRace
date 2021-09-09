package com.lielamar.armsrace.managers;

import com.lielamar.armsrace.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.Map.Entry;

public class CombatLogManager {

    private final Main main;

    private final int combatLogDuration;
    private Map<UUID, Long> players;

    public CombatLogManager(Main main) {
        this.main = main;
        this.combatLogDuration = this.main.getConfig().getInt("CombatLogDuration");
        if (this.combatLogDuration <= 0) return;

        this.players = new LinkedHashMap<>();

        new BukkitRunnable() {
            @Override
            public void run() {
                long current = System.currentTimeMillis();
                List<UUID> removeList = new LinkedList<>();
                for (Entry<UUID, Long> pair : players.entrySet()) {
                    if (((current - pair.getValue()) / 1000) > combatLogDuration) {
                        removeList.add(pair.getKey());

                        Player player = Bukkit.getPlayer(pair.getKey());
                        if (player != null) {
                            player.sendMessage(main.getMessages().noLongerInCombat());
                        }
                    }
                }
                removeList.forEach(players::remove);
            }
        }.runTaskTimer(main, 5L, 5L);
    }

    public int getDuration() {
        return combatLogDuration;
    }

    public void addCombatLog(Player p) {
        if (!players.containsKey(p.getUniqueId()))
            p.sendMessage(main.getMessages().youAreNowInCombat());

        players.put(p.getUniqueId(), System.currentTimeMillis());
    }

    public void removeCombatLog(Player p) {
        if (players.containsKey(p.getUniqueId()))
            players.remove(p.getUniqueId());
    }

    public boolean isCombatLog(Player p) {
        return players.containsKey(p.getUniqueId());
    }

    public int getCombatLog(Player p) {
        return (int) ((System.currentTimeMillis() - players.get(p.getUniqueId())) / 1000);
    }
}
