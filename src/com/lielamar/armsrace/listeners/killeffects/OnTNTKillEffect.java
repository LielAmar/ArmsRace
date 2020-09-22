package com.lielamar.armsrace.listeners.killeffects;

import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import com.lielamar.armsrace.Main;

public class OnTNTKillEffect implements Listener {

	private Main main;
	
	public OnTNTKillEffect(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onTNT(EntityExplodeEvent e) {
		if(!(e.getEntity() instanceof TNTPrimed)) return;
		
		if(main.getKillEffectsManager().containsEntity(e.getEntity()))
			e.blockList().clear();
	}
	
	@EventHandler
	public void onDamageTNT(EntityDamageByEntityEvent e) {
		if(!(e.getDamager() instanceof TNTPrimed)) return;
		
		if(main.getKillEffectsManager().containsEntity(e.getDamager())) {
			e.setCancelled(true);
		}
	}
}
