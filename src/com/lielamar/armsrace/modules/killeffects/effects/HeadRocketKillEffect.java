package com.lielamar.armsrace.modules.killeffects.effects;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import com.lielamar.armsrace.Main;
import com.lielamar.armsrace.modules.killeffects.KillEffect;

public class HeadRocketKillEffect implements KillEffect {

	@Override	
	public void playKillEffect(Main main, Location loc, Player victim, Player killer) {
		ArmorStand as = (ArmorStand) loc.getWorld().spawnEntity(loc.add(0, -1, 0), EntityType.ARMOR_STAND);
		as.setVisible(false);
		
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (byte)SkullType.PLAYER.ordinal());
		SkullMeta meta = (SkullMeta) skull.getItemMeta();
		meta.setOwner(victim.getName());
		skull.setItemMeta(meta);
		as.setHelmet(skull);
		
		as.getWorld().playSound(as.getLocation(), Sound.FIREWORK_LAUNCH, 1.0F, 4.0F);
		
		new BukkitRunnable() {
			int counter = 50;
			
			@Override
			public void run() {
				counter = counter-1;
				if(counter <= 0) {
					as.getWorld().playSound(as.getLocation(), Sound.FIREWORK_BLAST, 1.0F, 4.0F);
					as.remove();
					this.cancel();
					return;
				}
				
				as.setVelocity(new Vector(0, 0.3, 0));
				as.setHeadPose(new EulerAngle(0, Math.toRadians(counter*4), 0));
				if(counter%2 == 0) {
					for(Player pl : Bukkit.getOnlinePlayers()) {
						main.getPacketHandler().getNMSHandler().sendParticle(pl, "EXPLOSION_NORMAL", as.getLocation(), null, 1);
					}
				}
			}
		}.runTaskTimer(main, 1, 1);
	}
}
