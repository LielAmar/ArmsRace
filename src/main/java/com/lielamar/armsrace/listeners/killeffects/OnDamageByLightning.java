package com.lielamar.armsrace.listeners.killeffects;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.lielamar.armsrace.Main;
import com.lielamar.armsrace.modules.CustomPlayer;

public class OnDamageByLightning implements Listener {

	private Main main;
	
	public OnDamageByLightning(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onLightning(EntityDamageEvent e) {
		if(!(e.getEntity() instanceof Player)) return;
		if(e.getCause() != DamageCause.LIGHTNING) return;
		Player p = (Player)e.getEntity();
		
		CustomPlayer cp = main.getPlayerManager().getPlayer(p);
		if(cp.getCurrentMap() != null)
			e.setCancelled(true);
	}
}
