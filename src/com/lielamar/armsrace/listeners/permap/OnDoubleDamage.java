package com.lielamar.armsrace.listeners.permap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.lielamar.armsrace.Main;
import com.lielamar.armsrace.modules.CustomPlayer;

public class OnDoubleDamage implements Listener {

	private Main main;
	
	public OnDoubleDamage(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void DoubleDamage(EntityDamageByEntityEvent e) {
		if(!(e.getEntity() instanceof Player)) return;
		if(!(e.getDamager() instanceof Player)) return;
	
		Player p = (Player)e.getEntity();
		CustomPlayer cp = main.getPlayerManager().getPlayer(p);
		CustomPlayer cDamager = main.getPlayerManager().getPlayer((Player)e.getDamager());
		
		if(cp.getCurrentMap() == null || cDamager.getCurrentMap() == null) return;
		if(cp.getCurrentMap() != cDamager.getCurrentMap()) return;
		
		if(main.getPlayerManager().containsDoubleDamage(cDamager.getPlayer())) {
			e.setDamage(e.getDamage()*2);
		}
	}
}
