package com.lielamar.armsrace.listeners.killeffects;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

import com.lielamar.armsrace.Main;

public class OnPinataPickup implements Listener {

	private Main main;
	
	public OnPinataPickup(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onPickup(PlayerPickupItemEvent e) {
		if(!(e.getItem().getItemStack().getType() == Material.INK_SACK)) return;
		
		if(main.getKillEffectsManager().containsEntity(e.getItem()))
			e.setCancelled(true);
	}
}
