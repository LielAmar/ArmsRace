package com.lielamar.armsrace.modules.killeffects.effects;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import com.lielamar.armsrace.Main;
import com.lielamar.armsrace.modules.killeffects.KillEffect;

public class PinataKillEffect implements KillEffect {

	@Override
	public void playKillEffect(Main main, Location loc, Player victim, Player killer) {
		Random rnd = new Random();
		Location spawnLoc = loc.add(0, 2, 0);
		
		for(int i = 0; i < 14; i++) {
			if(rnd.nextBoolean()) {
				ItemStack item = new ItemStack(Material.INK_SACK, 1, (byte)i);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName("1");
				item.setItemMeta(meta);
				
				Entity ent = spawnLoc.getWorld().dropItemNaturally(spawnLoc, item);
				main.getKillEffectsManager().addEntity(ent);
				
				new BukkitRunnable() {
					@Override
					public void run() {
						ent.remove();
					}
				}.runTaskLater(main, 40L);
			}
		}

		for(int i = 0; i < 14; i++) {
			if(rnd.nextBoolean()) {
				ItemStack item = new ItemStack(Material.INK_SACK, 1, (byte)i);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName("1");
				item.setItemMeta(meta);
				
				Entity ent = spawnLoc.getWorld().dropItemNaturally(spawnLoc, item);
				main.getKillEffectsManager().addEntity(ent);
				
				new BukkitRunnable() {
					@Override
					public void run() {
						ent.remove();
					}
				}.runTaskLater(main, 40L);
			}
		}
	}
}
