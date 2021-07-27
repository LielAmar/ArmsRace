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

public class CookieFountainKillEffect implements KillEffect {

	@Override
	public void playKillEffect(Main main, Location loc, Player victim, Player killer) {
		Random rnd = new Random();
		Location spawnLoc = loc.add(0, 2, 0);

		new BukkitRunnable() {
			int counter = 30;
			ItemStack cookie = new ItemStack(Material.COOKIE);
			ItemMeta meta;

			@Override
			public void run() {
				if(counter <= 0) {
					this.cancel();
					return;
				}

				meta = cookie.getItemMeta();
				meta.setDisplayName(rnd.nextInt(10000)+1 + "");
				cookie.setItemMeta(meta);
				Entity ent = spawnLoc.getWorld().dropItemNaturally(spawnLoc, cookie);
				main.getKillEffectsManager().addEntity(ent);

				new BukkitRunnable() {
					@Override
					public void run() {
						ent.remove();
					}
				}.runTaskLater(main, 40L);
				counter = counter-2;
			}
		}.runTaskTimer(main, 2L, 2L);
	}
}
