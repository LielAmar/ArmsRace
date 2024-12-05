package com.lielamar.armsrace.listeners.trails;

import com.cryptomorin.xseries.reflection.XReflection;
import com.lielamar.armsrace.Main;
import com.lielamar.armsrace.modules.CustomPlayer;
import com.lielamar.armsrace.utility.ParticleUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class OnProjectileShoot implements Listener {

	private final Main main;

	public OnProjectileShoot(Main main) {
		this.main = main;
	}

	@EventHandler
	public void onProjectile(ProjectileLaunchEvent e) {
		Projectile proj = e.getEntity();
		if (!(proj.getShooter() instanceof Player)) return;

		Player p = (Player) proj.getShooter();
		CustomPlayer cp = main.getPlayerManager().getPlayer(p);

		if (cp.getCurrentMap() == null) return;

		String trail = cp.getCurrentTrail();
		if (trail.equalsIgnoreCase("reset")) return;

		new BukkitRunnable() {
			final String strippedTrail = ChatColor.stripColor(trail);

			@Override
			public void run() {
				if (e.getEntity().isDead()) this.cancel();
				if (e.getEntity().isOnGround()) this.cancel();

				for (Player pl : Bukkit.getOnlinePlayers()) {
					if (trail.equalsIgnoreCase("redstone")) {
						if (XReflection.supports(9)) {
							p.spawnParticle(Particle.DUST, e.getEntity().getLocation(), 10, 0, 0, 0, new Particle.DustOptions(Color.RED, 1));
						} else {
							p.spawnParticle(Particle.valueOf("REDSTONE"), e.getEntity().getLocation(), 10, 0, 0, 0, new Particle.DustOptions(Color.RED, 1));
						}
					} else {
						if (trail.equalsIgnoreCase("reset")) {
							return;
						}
						ParticleUtils.sendParticle(pl, strippedTrail, e.getEntity().getLocation(), cp.getCurrentTrailData(), main.getSettingsManager().getTrailsAmount());
					}
				}
			}
		}.runTaskTimerAsynchronously(main, 0L, main.getSettingsManager().getTrailsSpeed());
	}

	@EventHandler
	public void onProjectileHit(EntityDamageByEntityEvent e) {
		if (!(e.getEntity() instanceof LivingEntity)) return;
		if (!(e.getDamager() instanceof Projectile)) return;

		e.getDamager().remove();
	}
}