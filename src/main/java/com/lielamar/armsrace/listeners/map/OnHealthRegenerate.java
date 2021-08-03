package com.lielamar.armsrace.listeners.map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

import com.lielamar.armsrace.Main;

public class OnHealthRegenerate implements Listener {

	private Main main;
	
	public OnHealthRegenerate(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onHealth(EntityRegainHealthEvent e) {
		if(!(e.getEntity() instanceof Player)) return;
		Player p = (Player)e.getEntity();
		
		if(main.getPlayerManager().getPlayer(p).getCurrentMap() == null) return;
		
		if(!main.getPlayerManager().getPlayer(p).getCurrentMap().isPlayersRegen())
			e.setCancelled(true);
	}
}
