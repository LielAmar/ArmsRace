package com.lielamar.armsrace.listeners.permap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import com.lielamar.armsrace.Main;
import com.lielamar.armsrace.modules.CustomPlayer;

public class OnSpawnProtection implements Listener {

	private Main main;
	
	public OnSpawnProtection(Main main) {
		this.main = main;
	}

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if(!(e.getEntity() instanceof Player)) return;
	
		Player p = (Player)e.getEntity();
		CustomPlayer cpVic = main.getPlayerManager().getPlayer(p);
		if(cpVic.getCurrentMap() == null) {
			if(main.getPlayerManager().getSpawnProtection(p) == -1)
				main.getPlayerManager().removeSpawnProtection(p);
			return;
		}
		
		if(main.getPlayerManager().getSpawnProtection(cpVic.getPlayer()) == -1) return;
		if((System.currentTimeMillis()-main.getPlayerManager().getSpawnProtection(p))/1000 < cpVic.getCurrentMap().getSpawnProtection()) e.setCancelled(true);
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if(!(e.getEntity() instanceof Player)) return;
		if(!(e.getDamager() instanceof Player)) return;
	
		Player p = (Player)e.getEntity();
		CustomPlayer cp = main.getPlayerManager().getPlayer(p);
		CustomPlayer cDamager = main.getPlayerManager().getPlayer((Player)e.getDamager());
		
		if(cp.getCurrentMap() == null) {
			if(main.getPlayerManager().getSpawnProtection(p) == -1)
				main.getPlayerManager().removeSpawnProtection(p);
			return;
		}
		
		if(cDamager.getCurrentMap() == null) {
			if(main.getPlayerManager().getSpawnProtection(cDamager.getPlayer()) == -1)
				main.getPlayerManager().removeSpawnProtection(cDamager.getPlayer());
			return;
		}
		
		if(main.getPlayerManager().getSpawnProtection(cDamager.getPlayer()) == -1) return;
		if((System.currentTimeMillis()-main.getPlayerManager().getSpawnProtection(cDamager.getPlayer()))/1000 < cDamager.getCurrentMap().getSpawnProtection()) e.setCancelled(true);
	}
}
