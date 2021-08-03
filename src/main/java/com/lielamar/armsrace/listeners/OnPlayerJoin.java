package com.lielamar.armsrace.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.lielamar.armsrace.Main;
import com.lielamar.armsrace.modules.CustomPlayer;

public class OnPlayerJoin implements Listener {

	private Main main;
	
	public OnPlayerJoin(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		CustomPlayer cp = main.getPlayerManager().addPlayer(p);
		main.getScoreboardManager().addPlayer(cp);
		
		if(this.main.getSettingsManager().getSpawn() != null) 
			p.getPlayer().teleport(this.main.getSettingsManager().getSpawn());
	}
}
