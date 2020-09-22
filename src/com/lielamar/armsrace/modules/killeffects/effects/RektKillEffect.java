package com.lielamar.armsrace.modules.killeffects.effects;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.lielamar.armsrace.Main;
import com.lielamar.armsrace.modules.killeffects.KillEffect;

public class RektKillEffect implements KillEffect {

	@Override
	public void playKillEffect(Main main, Location loc, Player victim, Player killer) {
//		Block b = loc.add(0, 1, 0).getBlock();
//		Material oldType = b.getType();
//		b.setType(Material.TORCH);
//		b.setData((byte)2, true);
		
		ArmorStand as = (ArmorStand) loc.getWorld().spawnEntity(loc.add(0, -0.5, 0), EntityType.ARMOR_STAND);
		
		as.setVisible(false);
		as.setGravity(false);
		as.setCustomNameVisible(true);
		as.setCustomName(ChatColor.GRAY + killer.getName() + ChatColor.YELLOW + " has #rekt " + ChatColor.GRAY + victim.getName() + ChatColor.YELLOW + " here");
		
		new BukkitRunnable() {
			@Override
			public void run() {
				as.remove();
			}
		}.runTaskLater(main, 100L);
	}
}
