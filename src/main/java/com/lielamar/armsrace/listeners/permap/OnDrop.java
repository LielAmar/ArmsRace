package com.lielamar.armsrace.listeners.permap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import com.lielamar.armsrace.Main;

public class OnDrop implements Listener {

	private Main main;

	public OnDrop(Main main) {
		this.main = main;
	}

	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		Player p = e.getPlayer();

		if(main.getPlayerManager().getPlayer(p).getCurrentMap() == null) return;
		e.setCancelled(true);
	}
}