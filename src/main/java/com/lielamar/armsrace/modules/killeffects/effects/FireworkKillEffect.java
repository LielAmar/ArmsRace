package com.lielamar.armsrace.modules.killeffects.effects;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

import com.lielamar.armsrace.Main;
import com.lielamar.armsrace.modules.killeffects.KillEffect;

public class FireworkKillEffect implements KillEffect {

	@Override	
	public void playKillEffect(Main main, Location loc, Player victim, Player killer) {
		Firework fw = (Firework)loc.getWorld().spawnEntity(loc.add(0, 1, 0), EntityType.FIREWORK);
		FireworkMeta meta = fw.getFireworkMeta();
		
		FireworkEffect.Builder fwB = FireworkEffect.builder();
        fwB.flicker(true);
        fwB.trail(true);
        fwB.with(FireworkEffect.Type.BALL);
        fwB.withColor(Color.ORANGE);
        fwB.withFade(Color.WHITE);
        FireworkEffect fe = fwB.build();
        meta.addEffect(fe);
        fw.setFireworkMeta(meta);

        new BukkitRunnable() {
        
        	@Override
        	public void run() {
        		fw.detonate();
        	}
        }.runTaskLater(main, 2L);
	}
}
