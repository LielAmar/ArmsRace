package com.lielamar.armsrace.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;

import com.lielamar.armsrace.Main;

public class OnDurabilityChange implements Listener {

	private Main main;
	
	public OnDurabilityChange(Main main) {
		this.main = main;
	}

	@EventHandler
	public void onDurabilityChange(PlayerItemDamageEvent e) {
		Player p = e.getPlayer();
		if(main.getPlayerManager().getPlayer(p).getCurrentMap() != null)
			e.setCancelled(true);
	}
}
