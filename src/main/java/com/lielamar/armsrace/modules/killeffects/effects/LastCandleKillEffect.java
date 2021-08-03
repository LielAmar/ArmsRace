package com.lielamar.armsrace.modules.killeffects.effects;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.lielamar.armsrace.Main;
import com.lielamar.armsrace.modules.killeffects.KillEffect;

public class LastCandleKillEffect implements KillEffect {

	@Override
	public void playKillEffect(Main main, Location loc, Player victim, Player killer) {
//		Block b = loc.add(0, 1, 0).getBlock();
//		Material oldType = b.getType();
//		b.setType(Material.TORCH);
//		b.setData((byte)2, true);
		
		ArmorStand as = (ArmorStand) loc.getWorld().spawnEntity(loc.add(0, -0.3, 0), EntityType.ARMOR_STAND);
		ArmorStand as2 = (ArmorStand) loc.getWorld().spawnEntity(loc.add(0, -0.3, 0), EntityType.ARMOR_STAND);
		
		as.setVisible(false);
		as.setGravity(false);
		as.setCustomNameVisible(true);
		as.setCustomName(ChatColor.GRAY + killer.getName() + ChatColor.YELLOW + "'s candle");
		
		as2.setVisible(false);
		as2.setGravity(false);
		as2.setCustomNameVisible(true);
		as2.setCustomName(ChatColor.YELLOW + "for " + ChatColor.GRAY + victim.getName());
		
		new BukkitRunnable() {
			@Override
			public void run() {
				as.remove();
				as2.remove();
//				if(oldType != Material.DEAD_BUSH)
//					b.setType(oldType);
//				else
//					b.setType(Material.AIR);
			}
		}.runTaskLater(main, 100L);
	}
}
