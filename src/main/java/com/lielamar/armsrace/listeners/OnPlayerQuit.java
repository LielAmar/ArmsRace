package com.lielamar.armsrace.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.lielamar.armsrace.Main;
import com.lielamar.armsrace.modules.CustomPlayer;

public class OnPlayerQuit implements Listener {

	private Main main;

	public OnPlayerQuit(Main main) {
		this.main = main;
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		CustomPlayer cp = main.getPlayerManager().getPlayer(p);
		cp.save();
		main.getScoreboardManager().removePlayer(cp);
	}
}
