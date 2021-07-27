package com.lielamar.armsrace.listeners.permap;

import com.lielamar.armsrace.utility.Utils;
import com.lielamar.armsrace.utility.packets.PacketVersion;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.lielamar.armsrace.Main;
import com.lielamar.armsrace.modules.CustomPlayer;
import com.lielamar.armsrace.modules.map.Map;
import com.lielamar.armsrace.modules.map.Pickup;
import com.lielamar.armsrace.modules.map.PickupType;

public class OnPickup implements Listener {

	private Main main;

	public OnPickup(Main main) {
		this.main = main;
	}

	@EventHandler
	public void onPickup(PlayerPickupItemEvent e) {
		Player p = e.getPlayer();

		CustomPlayer cp = main.getPlayerManager().getPlayer(p);
		Map map = cp.getCurrentMap();

		if(map == null) {
			for(Map tmpMap : main.getGameManager().getMapManager().getMaps().values()) {
				for(Pickup pickup : tmpMap.getPickups()) {
					if(pickup.getPickup().getItemStack().getType() == e.getItem().getItemStack().getType()
							&& e.getItem().getItemStack().hasItemMeta() && e.getItem().getItemStack().getItemMeta().hasDisplayName()
							&& e.getItem().getItemStack().getItemMeta().getDisplayName().equalsIgnoreCase(tmpMap.getName())) {
						e.setCancelled(true);
					}
				}
			}
			return;
		}

		e.setCancelled(true);

		PacketVersion nmsHandler = main.getPacketHandler().getNMSHandler();
		for(Pickup pickup : map.getPickups()) {
			if(pickup.getPickup().getItemStack().getType() == e.getItem().getItemStack().getType()
					&& e.getItem().getItemStack().hasItemMeta() && e.getItem().getItemStack().getItemMeta().hasDisplayName()
					&& e.getItem().getItemStack().getItemMeta().getDisplayName().equalsIgnoreCase(map.getName())) {

				if(pickup.getType() == PickupType.HEALTH) {
					double health = p.getHealth()+map.getHealthPerPickup();
					if(health > 20)
						health = 20.0;
					p.setHealth(health);
					nmsHandler.sendActionBar(p, main.getMessages().healthPickedUp(map.getHealthPerPickup()));
				} else if(pickup.getType() == PickupType.DOUBLE_DAMAGE) {
					main.getPlayerManager().addDoubleDamage(p);

					new BukkitRunnable() {
						double time = map.getDoubleDamageDuration();

						@Override
						public void run() {
							time = time-0.1;
							if(time <= 0) {
								main.getPlayerManager().removeOneTap(p);
								nmsHandler.sendActionBar(p, main.getMessages().doubleDamageEnded());
								this.cancel();
								return;
							}
							nmsHandler.sendActionBar(p, main.getMessages().doubleDamagePickedUp(Utils.fixDecimal(time)));
						}
					}.runTaskTimer(main, 2, 2);
				} else if(pickup.getType() == PickupType.COINS) {
					if(map.getCoinsPerPickup() > 0) {
						cp.giveCoins(map.getCoinsPerPickup());
						nmsHandler.sendActionBar(p, main.getMessages().coinsPickedUp(map.getCoinsPerPickup()));
					}
				} else if(pickup.getType() == PickupType.SPEED) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, (int) map.getSpeedDuration()*20, map.getSpeedLevel()-1));

					new BukkitRunnable() {
						double time = map.getSpeedDuration();

						@Override
						public void run() {
							time = time-0.1;
							if(time <= 0) {
								this.cancel();
								return;
							}
							nmsHandler.sendActionBar(p, main.getMessages().speedPickedUp(Utils.fixDecimal(time), map.getSpeedLevel()));
						}
					}.runTaskTimer(main, 2, 2);
				} else if(pickup.getType() == PickupType.RESISTANCE) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, (int) map.getResistanceDuration()*20, map.getResistanceLevel()-1));

					new BukkitRunnable() {
						double time = map.getResistanceDuration();

						@Override
						public void run() {
							time = time-0.1;
							if(time <= 0) {
								this.cancel();
								return;
							}
							nmsHandler.sendActionBar(p, main.getMessages().resistancePickedUp(Utils.fixDecimal(time), map.getResistanceLevel()));
						}
					}.runTaskTimer(main, 2, 2);
				} else if(pickup.getType() == PickupType.TIER_UP) {
					int counter = 0;
					while(counter < map.getTiersUpAmount()) {
						cp.setCurrentTierId(cp.getCurrentTierId()+1);
						if(cp.getCurrentTierId() >= map.getTiers().length)
							cp.setCurrentTierId(map.getTiers().length-1);
						else
							cp.getPlayer().sendMessage(main.getMessages().youWerePromoted(cp.getCurrentTierId()));
						cp.setCurrentTier(map.getTiers()[cp.getCurrentTierId()]);

						if(!cp.containsHightier(map.getName()) || cp.getCurrentTierId() > cp.getHightier(map.getName()))
							cp.setHightier(map.getName(), cp.getCurrentTierId());

						counter++;
					}

					nmsHandler.sendActionBar(p, main.getMessages().tiersupPickedUp(map.getTiersUpAmount()));
				} else if(pickup.getType() == PickupType.ONE_TAP) {
					main.getPlayerManager().addOneTap(p);

					new BukkitRunnable() {
						double time = map.getOneTapDuration();

						@Override
						public void run() {
							time = time-0.1;
							if(time <= 0) {
								main.getPlayerManager().removeOneTap(p);
								nmsHandler.sendActionBar(p, main.getMessages().onetapEnded());
								this.cancel();
								return;
							}
							nmsHandler.sendActionBar(p, main.getMessages().onetapPickedUp(Utils.fixDecimal(time)));
						}
					}.runTaskTimer(main, 2, 2);
				}

				try {
					//Minecraft 1.9
					p.getWorld().playSound(p.getLocation(),Sound.valueOf("ENTITY_PLAYER_LEVELUP"), 1.0f, 1.0f);
				} catch (Exception exc) {
					//Minecraft 1.8.8
					p.getWorld().playSound(p.getLocation(),Sound.valueOf("LEVEL_UP"), 1.0f, 1.0f);
				}
				e.getItem().remove();
			}
		}
	}
}