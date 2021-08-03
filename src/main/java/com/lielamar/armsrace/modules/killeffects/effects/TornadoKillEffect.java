package com.lielamar.armsrace.modules.killeffects.effects;

import com.lielamar.armsrace.modules.killeffects.KillEffect;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.lielamar.armsrace.Main;

public class TornadoKillEffect implements KillEffect {

	@Override
	public void playKillEffect(Main main, Location loc, Player victim, Player killer) {
		Location mid = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ());
		
		new BukkitRunnable() {
		
			int counter = 4;
			
			@Override
			public void run() {
				if(counter <= 0) {
					this.cancel();
				}

				double radius = 0.005;
				double y = mid.getY();
				
				for(double i = 0; i < 50; i+=0.1) {
					double x = radius*Math.sin(i);
					double z = radius*Math.cos(i);
					
					Location tmp = null;
					if(counter%2 == 0)
						tmp = new Location(loc.getWorld(), loc.getX()+x, y, loc.getZ()+z);
					else
						tmp = new Location(loc.getWorld(), loc.getX()+z, y, loc.getZ()+x);
					
					for(Player pl : Bukkit.getOnlinePlayers()) {
						main.getPacketHandler().getNMSHandler().sendParticle(pl, "FIREWORKS_SPARK", tmp, null, 0);
					}
					
					if(radius < 2.5)
						radius += 0.005;
					y += 0.01;
				}
				for(Player pl : Bukkit.getOnlinePlayers()) {
					main.getPacketHandler().getNMSHandler().sendParticle(pl, "SMOKE_LARGE", loc, null, 0);
				}
				
				counter--;
			}
			
		}.runTaskTimer(main, 10L, 10L);
	}
}
