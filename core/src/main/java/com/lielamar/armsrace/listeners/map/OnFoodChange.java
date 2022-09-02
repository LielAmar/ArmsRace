package com.lielamar.armsrace.listeners.map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import com.lielamar.armsrace.Main;

public class OnFoodChange implements Listener {

	private final Main main;

	public OnFoodChange(Main main) {
		this.main = main;
	}

	@EventHandler
	public void onFood(FoodLevelChangeEvent e) {
		Player p = (Player) e.getEntity();

		if (main.getPlayerManager().getPlayer(p).getCurrentMap() == null) return;

		if (!main.getPlayerManager().getPlayer(p).getCurrentMap().isFoodLevelChange()) {
			e.setCancelled(true);
		}
	}
}
