package com.lielamar.armsrace.listeners.trails;

import com.lielamar.armsrace.utility.packets.PacketVersion;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.lielamar.armsrace.Main;
import com.lielamar.armsrace.modules.CustomPlayer;

public class OnProjectileShoot implements Listener {

	private Main main;

	public OnProjectileShoot(Main main) {
		this.main = main;
	}

	@EventHandler
	public void onProjectile(ProjectileLaunchEvent e) {
		Projectile proj = e.getEntity();
		if(!(proj.getShooter() instanceof Player)) return;

		Player p = (Player) proj.getShooter();
		CustomPlayer cp = main.getPlayerManager().getPlayer(p);

		if(cp.getCurrentMap() == null) return;

		String trail = cp.getCurrentTrail();
		if(trail.length() < 0 || trail.equalsIgnoreCase("reset")) return;

		PacketVersion pv = main.getPacketHandler().getNMSHandler();
		if(pv == null) return;

		new BukkitRunnable() {
			String strippedTrail = ChatColor.stripColor(trail);

			@Override
			public void run() {
				if(e.getEntity() == null) this.cancel();
				if(e.getEntity().isDead()) this.cancel();
				if(e.getEntity().isOnGround()) this.cancel();

				for(Player pl : Bukkit.getOnlinePlayers()) {
					pv.sendParticle(pl, strippedTrail, e.getEntity().getLocation(), cp.getCurrentTrailData(), main.getSettingsManager().getTrailsAmount());
				}
			}
		}.runTaskTimerAsynchronously(main, 0L, main.getSettingsManager().getTrailsSpeed());
	}

	@EventHandler
	public void onProjectileHit(EntityDamageByEntityEvent e) {
		if(!(e.getEntity() instanceof LivingEntity)) return;
		if(!(e.getDamager() instanceof Projectile)) return;

		e.getDamager().remove();
	}
}
