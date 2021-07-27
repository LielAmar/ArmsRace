package com.lielamar.armsrace.modules.killeffects.effects;

import com.cryptomorin.xseries.XSound;
import com.lielamar.armsrace.modules.killeffects.KillEffect;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Squid;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.lielamar.armsrace.Main;

public class SquidMissileKillEffect implements KillEffect {

	@Override
	public void playKillEffect(Main main, Location loc, Player victim, Player killer) {
		Squid squid = (Squid) loc.getWorld().spawnEntity(loc, EntityType.SQUID);
		squid.setNoDamageTicks(1000);

		new BukkitRunnable() {
			int counter = 100;

			@Override
			public void run() {
				counter = counter-5;
				if(counter <= 0) {
					squid.remove();
					squid.getWorld().playSound(squid.getLocation(), XSound.ENTITY_FIREWORK_ROCKET_LARGE_BLAST_FAR.parseSound(), 1.0F, 4.0F);
					this.cancel();
					return;
				}
				squid.setVelocity(new Vector(0, 0.3, 0));
				squid.getWorld().playSound(squid.getLocation(), XSound.ENTITY_ITEM_PICKUP.parseSound(), 1.0F, 4.0F);

				if(counter%10 == 0) {
					for(Player pl : Bukkit.getOnlinePlayers()) {
						main.getPacketHandler().getNMSHandler().sendParticle(pl, "FLAME", squid.getLocation(), null, 1);
					}
				}
			}
		}.runTaskTimer(main, 2, 2);
	}
}
