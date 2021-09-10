package com.lielamar.armsrace.modules.killeffects.effects;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.lielamar.armsrace.Main;
import com.lielamar.armsrace.modules.killeffects.KillEffect;

public class BurningShoesKillEffect implements KillEffect {

	@Override
	public void playKillEffect(Main main, Location loc, Player victim, Player killer) {
		new BukkitRunnable() {
			int counter = 30;

			@Override
			public void run() {
				if (counter <= 0) {
					this.cancel();
					return;
				}
				for (Player pl : Bukkit.getOnlinePlayers()) {
					main.getNmsHandler().sendParticle(pl, "FLAME", killer.getLocation(), null, 5);
					main.getNmsHandler().sendParticle(pl, "FLAME", killer.getLocation(), null, 5);
					main.getNmsHandler().sendParticle(pl, "FLAME", killer.getLocation(), null, 5);
				}
				counter = counter - 1;
			}
		}.runTaskTimer(main, 1L, 1L);
	}
}
