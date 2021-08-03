package com.lielamar.armsrace.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.lielamar.armsrace.Main;
import com.lielamar.armsrace.modules.CustomPlayer;

public class OnPlayerDeath implements Listener {

	private Main main;

	public OnPlayerDeath(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		if(!(e.getEntity() instanceof Player)) return;

		CustomPlayer cpVic = main.getPlayerManager().getPlayer(e.getEntity());
		if(cpVic.isLeftMap()) {
			e.setDeathMessage(null);
			cpVic.setLeftMap(false);
			cpVic.setKillstreak(0);
		}
		
		if(cpVic.getCurrentMap() != null) {
			cpVic.getPlayer().spigot().respawn();
		}
	}
}
