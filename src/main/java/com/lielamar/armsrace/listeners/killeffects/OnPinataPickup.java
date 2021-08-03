package com.lielamar.armsrace.listeners.killeffects;

import org.bukkit.Bukkit;
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
		String name = e.getItem().getType().name();
		String version = Bukkit.getVersion();

		if (version.contains("1.8") || version.contains("1.9") || version.contains("1.10") || version.contains("1.11") || version.contains("1.12")) {
			if (!(e.getItem().getItemStack().getType() == Material.valueOf("INK_SACK"))) return;
		} else {
			if (!(name.contains("DYE"))) {
				return;
			}
				if (main.getKillEffectsManager().containsEntity(e.getItem()))
					e.setCancelled(true);
			}
		}
	}
