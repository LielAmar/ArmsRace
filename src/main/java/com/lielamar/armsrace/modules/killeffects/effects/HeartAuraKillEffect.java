package com.lielamar.armsrace.modules.killeffects.effects;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.lielamar.armsrace.Main;
import com.lielamar.armsrace.modules.killeffects.KillEffect;

public class HeartAuraKillEffect implements KillEffect {

	@Override
	public void playKillEffect(Main main, Location loc, Player victim, Player killer) {
		Random rnd = new Random();

		new BukkitRunnable() {

			int counter = 40;

			@Override
			public void run() {
				if (counter <= 0) {
					this.cancel();
					return;
				}
				for (Player pl : Bukkit.getOnlinePlayers()) {
					main.getPacketHandler().getNMSHandler().sendParticle(pl, "HEART", new Location(loc.getWorld(), loc.getX() + rnd.nextDouble(), loc.getY() + 1.5 + rnd.nextDouble(), loc.getZ() + rnd.nextDouble()), null, 5);
					main.getPacketHandler().getNMSHandler().sendParticle(pl, "HEART", new Location(loc.getWorld(), loc.getX() + rnd.nextDouble(), loc.getY() + 1.5 + rnd.nextDouble(), loc.getZ() + rnd.nextDouble()), null, 5);
					main.getPacketHandler().getNMSHandler().sendParticle(pl, "HEART", new Location(loc.getWorld(), loc.getX() + rnd.nextDouble(), loc.getY() + 1.5 + rnd.nextDouble(), loc.getZ() + rnd.nextDouble()), null, 5);
				}
				counter = counter - 4;
			}
		}.runTaskTimer(main, 4L, 4L);
	}
}
