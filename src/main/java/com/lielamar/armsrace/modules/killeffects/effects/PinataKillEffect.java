package com.lielamar.armsrace.modules.killeffects.effects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
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

	private final Random random = new Random();

	@Override
	public void playKillEffect(Main main, Location loc, Player victim, Player killer) {
		Random rnd = new Random();
		Location spawnLoc = loc.add(0, 2, 0);

		for(int i = 0; i < 14; i++) {
			if(rnd.nextBoolean()) {

				List<String> newMaterials = new ArrayList<>(Arrays.asList("WHITE_STAINED_GLASS_PANE", "ORANGE_STAINED_GLASS_PANE", "MAGENTA_STAINED_GLASS_PANE", "LIGHT_BLUE_STAINED_GLASS_PANE", "YELLOW_STAINED_GLASS_PANE", "LIME_STAINED_GLASS_PANE", "PINK_STAINED_GLASS_PANE", "GRAY_STAINED_GLASS_PANE", "LIGHT_GRAY_STAINED_GLASS_PANE", "CYAN_STAINED_GLASS_PANE", "PURPLE_STAINED_GLASS_PANE", "BLUE_STAINED_GLASS_PANE", "BROWN_STAINED_GLASS_PANE", "GREEN_STAINED_GLASS_PANE", "RED_STAINED_GLASS_PANE", "BLUE_STAINED_GLASS_PANE"));
				List<Integer> legacyDurabilities = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 9, 10, 11, 12, 13,14, 15));
				ItemStack item;
				String version = Bukkit.getVersion();
				if (version.contains("1.8") || version.contains("1.9") || version.contains("1.10") || version.contains("1.11") || version.contains("1.12")) {
					item = new ItemStack(Material.valueOf("INK_SACK"), 1, (byte) random.nextInt(legacyDurabilities.size()));
				} else {
					item = new ItemStack(Material.valueOf(newMaterials.get(random.nextInt(newMaterials.size()))), 1);
				}
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
				List<String> newMaterials = new ArrayList<>(Arrays.asList("WHITE_STAINED_GLASS_PANE", "ORANGE_STAINED_GLASS_PANE", "MAGENTA_STAINED_GLASS_PANE", "LIGHT_BLUE_STAINED_GLASS_PANE", "YELLOW_STAINED_GLASS_PANE", "LIME_STAINED_GLASS_PANE", "PINK_STAINED_GLASS_PANE", "GRAY_STAINED_GLASS_PANE", "LIGHT_GRAY_STAINED_GLASS_PANE", "CYAN_STAINED_GLASS_PANE", "PURPLE_STAINED_GLASS_PANE", "BLUE_STAINED_GLASS_PANE", "BROWN_STAINED_GLASS_PANE", "GREEN_STAINED_GLASS_PANE", "RED_STAINED_GLASS_PANE", "BLUE_STAINED_GLASS_PANE"));
				List<Integer> legacyDurabilities = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 9, 10, 11, 12, 13,14, 15));
				ItemStack item;
				String version = Bukkit.getVersion();
				if (version.contains("1.8") || version.contains("1.9") || version.contains("1.10") || version.contains("1.11") || version.contains("1.12")) {
					item = new ItemStack(Material.valueOf("INK_SACK"), 1, (byte) random.nextInt(legacyDurabilities.size()));
				} else {
					item = new ItemStack(Material.valueOf(newMaterials.get(random.nextInt(newMaterials.size()))), 1);
				}
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
