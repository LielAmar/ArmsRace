package com.lielamar.armsrace.managers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.lielamar.armsrace.Main;

public class CombatLogManager {

	private Main main;
	
	private int combatLogDuration;
	private Map<UUID, Long> players;
	
	public CombatLogManager(Main main) {
		this.main = main;
		this.combatLogDuration = this.main.getConfig().getInt("CombatLogDuration");
		if(this.combatLogDuration <= 0) return;
		
		this.players = new HashMap<UUID, Long>();

		new BukkitRunnable() {

			@Override
			public void run() {
				if(players.size() == 0) return;
				
				long current = System.currentTimeMillis();
				
			    Iterator<Entry<UUID, Long>> it = players.entrySet().iterator();
			    while (it.hasNext()) {
			        Map.Entry<UUID, Long> pair = (Map.Entry<UUID, Long>)it.next();
					if(((current-pair.getValue())/1000) > combatLogDuration) {
						players.remove(pair.getKey());
						Bukkit.getPlayer(pair.getKey()).sendMessage(main.getMessages().noLongerInCombat());
					}
			    }
			}
			
		}.runTaskTimer(main, 5L, 5L);
	}
	
	public int getDuration() {
		return combatLogDuration;
	}
	
	public void addCombatLog(Player p) {
		if(!players.containsKey(p.getUniqueId()))
			p.sendMessage(main.getMessages().youAreNowInCombat());
		
		players.put(p.getUniqueId(), System.currentTimeMillis());
	}
	
	public void removeCombatLog(Player p) {
		if(players.containsKey(p.getUniqueId()))
			players.remove(p.getUniqueId());
	}
	
	public boolean isCombatLog(Player p) {
		return players.containsKey(p.getUniqueId());
	}
	
	public int getCombatLog(Player p) {
		return (int) ((System.currentTimeMillis()-players.get(p.getUniqueId()))/1000);
	}
}
