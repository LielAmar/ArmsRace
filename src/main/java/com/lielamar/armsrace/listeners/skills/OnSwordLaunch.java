package com.lielamar.armsrace.listeners.skills;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.lielamar.armsrace.Main;
import com.lielamar.armsrace.modules.CustomPlayer;

public class OnSwordLaunch implements Listener {

	private final Main main;

	public OnSwordLaunch(Main main) {
		this.main = main;
	}

	@EventHandler
	public void onRightClick(PlayerInteractEvent e) {
		if (e.getAction() != Action.RIGHT_CLICK_AIR) return;

		Player p = e.getPlayer();
		CustomPlayer cp = main.getPlayerManager().getPlayer(p);
		if (cp.getCurrentMap() == null)
			return;

		if (cp.getSkillLevel("SWORD_LAUNCH") < 1) return;

		if (p.getItemInHand().getType() != Material.DIAMOND_SWORD
				&& p.getItemInHand().getType() != Material.IRON_SWORD
				&& p.getItemInHand().getType() != Material.STONE_SWORD
				&& p.getItemInHand().getType() != XMaterial.GOLDEN_SWORD.parseMaterial()
				&& p.getItemInHand().getType() != XMaterial.WOODEN_SWORD.parseMaterial())
			return;

		if (cp.getSwordLaunch() == -1 || (System.currentTimeMillis() - cp.getSwordLaunch()) / 1000 > main.getSettingsManager().getSwordLaunchCooldown().get(cp.getSkillLevel("SWORD_LAUNCH"))) {
			cp.setSwordLaunch(System.currentTimeMillis());
			launchSword(p);
		} else {
			double seconds = main.getSettingsManager().getSwordLaunchCooldown().get(cp.getSkillLevel("SWORD_LAUNCH")) - ((System.currentTimeMillis() - cp.getSwordLaunch()) / 1000);
			p.sendMessage(main.getMessages().youAreStillInCooldown((int) seconds));
		}
	}

	/**
	 * Launches the held sword of a player
	 *
	 * @param p Launcher of the sword
	 */
	public void launchSword(Player p) {
		ArmorStand as = (ArmorStand) p.getLocation().getWorld().spawnEntity(p.getLocation(), EntityType.ARMOR_STAND);
		as.setVisible(false);
		int slot = p.getInventory().getHeldItemSlot();
		as.setItemInHand(p.getItemInHand());
		as.setCanPickupItems(false);
		as.setGravity(false);
		p.setItemInHand(null);
		main.getPlayerManager().updateSwordLaunchAs(p, as);

		Location current = p.getLocation();
		Location eye = p.getTargetBlock(null, 200).getLocation();

		new BukkitRunnable() {

			int counter = 0;
			double x = current.getX();
			double y = current.getY() + 1;
			double z = current.getZ();
			double xDiff = (eye.getX() - x) / 75;
			double yDiff = (eye.getY() - y) / 75;
			double zDiff = (eye.getZ() - z) / 75;

			@Override
			public void run() {
				if (counter >= 75) {
					this.cancel();
					p.getInventory().setItem(slot, as.getItemInHand());
					as.remove();
					main.getPlayerManager().removeSwordLaunchAs(p);
				} else {
					Location newLoc = new Location(current.getWorld(), x, y, z);
					as.teleport(newLoc);
					for (Entity ent : as.getNearbyEntities(0.1, 0.1, 0.1)) {
						if (ent instanceof LivingEntity) {
							if (ent instanceof Player) {
								if (ent == p) continue;
								CustomPlayer cpKiller = main.getPlayerManager().getPlayer(p);
								CustomPlayer cpVic = main.getPlayerManager().getPlayer((Player) ent);

								if (cpVic.getCurrentMap() == null && cpKiller.getCurrentMap() == null)
									continue;
								if (cpVic.getCurrentMap() == null && cpKiller.getCurrentMap() != null
										|| cpVic.getCurrentMap() != null && cpKiller.getCurrentMap() == null)
									continue;
								if (cpVic.getCurrentMap() != cpKiller.getCurrentMap())
									continue;


								int multiplier = 1;
								if (main.getPlayerManager().containsOneTap(p)) {
									((LivingEntity) ent).damage(ent.getMaxFireTicks(), p);
								} else if (main.getPlayerManager().containsDoubleDamage(p)) {
									multiplier = 2;
								} else if (as.getItemInHand().getType() == XMaterial.WOODEN_SWORD.parseMaterial())
									((LivingEntity) ent).damage(4 * multiplier, p);
								else if (as.getItemInHand().getType() == XMaterial.GOLDEN_SWORD.parseMaterial())
									((LivingEntity) ent).damage(4 * multiplier, p);
								else if (as.getItemInHand().getType() == Material.STONE_SWORD)
									((LivingEntity) ent).damage(5 * multiplier, p);
								else if (as.getItemInHand().getType() == Material.IRON_SWORD)
									((LivingEntity) ent).damage(6 * multiplier, p);
								else if (as.getItemInHand().getType() == Material.DIAMOND_SWORD)
									((LivingEntity) ent).damage(7 * multiplier, p);
							}
						}
					}

					x += xDiff;
					y += yDiff;
					z += zDiff;
					counter++;
				}
			}
		}.runTaskTimer(main, 0L, 1L);
	}
}
