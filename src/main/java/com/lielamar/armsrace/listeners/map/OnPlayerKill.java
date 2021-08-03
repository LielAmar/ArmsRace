package com.lielamar.armsrace.listeners.map;

import java.util.Random;

import com.cryptomorin.xseries.XSound;
import com.lielamar.armsrace.listeners.custom.LeaveReason;
import com.lielamar.armsrace.listeners.custom.PlayerTierDownEvent;
import com.lielamar.armsrace.listeners.custom.PlayerTierUpEvent;
import com.lielamar.armsrace.utility.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.lielamar.armsrace.Main;
import com.lielamar.armsrace.modules.CustomPlayer;
import com.lielamar.armsrace.modules.killeffects.KillEffect;
import com.lielamar.armsrace.modules.map.Map;

public class OnPlayerKill implements Listener {

	private Main main;
	
	public OnPlayerKill(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onDeath(EntityDamageEvent e) {
		if(!(e.getEntity() instanceof Player)) return;
		CustomPlayer cpVic = main.getPlayerManager().getPlayer((Player)e.getEntity());
		
		if(e.getCause() != DamageCause.ENTITY_ATTACK) {
			if(cpVic.getPlayer().getHealth()-e.getDamage() <= 0) {
				if(cpVic.isLeftMap()) {
					e.setDamage(0);
					cpVic.setLeftMap(false);
					
					if(cpVic.getCurrentMap() != null) {
						cpVic.getPlayer().spigot().respawn();
					}
					return;
				}
				
				e.setDamage(0);
				handleKill(cpVic, null);
				return;
			}
		}
	}
	
	@EventHandler
	public void onDeath(EntityDamageByEntityEvent e) {
		if(!(e.getEntity() instanceof Player)) return;
		
		CustomPlayer cpVic = main.getPlayerManager().getPlayer((Player)e.getEntity());
		
		if(cpVic.getCurrentMap() == null) return;
		
		if(e.getCause() == DamageCause.ENTITY_ATTACK || e.getCause() == DamageCause.PROJECTILE) {
			CustomPlayer cpKiller = null;
			if(e.getDamager() instanceof Projectile) {
				if(((Projectile)e.getDamager()).getShooter() instanceof Player)
					cpKiller = main.getPlayerManager().getPlayer((Player)((Projectile)e.getDamager()).getShooter());
			}
			
			if(e.getDamager() instanceof Player)
				cpKiller = main.getPlayerManager().getPlayer((Player)e.getDamager());
			
			if(cpKiller != null) {
				if(cpVic.getCurrentMap() == null && cpKiller.getCurrentMap() == null) {
					return;
				}
				
				if(cpVic.getCurrentMap() == null && cpKiller.getCurrentMap() != null
						|| cpVic.getCurrentMap() != null && cpKiller.getCurrentMap() == null) {
					e.setCancelled(true);
					cpKiller.getPlayer().sendMessage(main.getMessages().cantHitPlayersOutsideMap());
					return;
				}
				
				if(cpVic.getCurrentMap() != cpKiller.getCurrentMap()) {
					e.setCancelled(true);
					cpKiller.getPlayer().sendMessage(main.getMessages().cantHitPlayersDifferentMap());
					return;
				}
				
				main.getCombatLogManager().addCombatLog(cpKiller.getPlayer());
				main.getCombatLogManager().addCombatLog(cpVic.getPlayer());
				
				if(cpVic.getPlayer().getHealth()-e.getDamage() <= 0) {
					e.setDamage(0);
					handleKill(cpVic, cpKiller);
					main.getCombatLogManager().removeCombatLog(cpVic.getPlayer());
					return;
				} else {
					cpVic.setLastDamager(cpKiller.getPlayer());
					if(cpKiller.getSkillLevel("HEAD_SHOT") > 0) {
						Random rnd = new Random();
						int chance = rnd.nextInt(100-1+1)+1;
						if(chance <= main.getSettingsManager().getHeadShotRate().get(cpKiller.getSkillLevel("HEAD_SHOT"))) {
							handleKill(cpVic, cpKiller);
							main.getPacketHandler().getNMSHandler().sendActionBar(cpKiller.getPlayer(), ChatColor.GREEN + "Head Shotted " + cpVic.getPlayer().getName());
							return;
						}
					}
					if(main.getPlayerManager().containsOneTap(cpKiller.getPlayer())) {
						handleKill(cpVic, cpKiller);
						return;
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		
		CustomPlayer cp = main.getPlayerManager().getPlayer(p);
		Map map = cp.getCurrentMap();
		if(map != null) {
			if(main.getCombatLogManager().isCombatLog(p)) {
				CustomPlayer cpKiller = main.getPlayerManager().getPlayer(cp.getLastDamager());
				if(cpKiller != null) {
					handleKill(cp, cpKiller);
				}
			}
			map.removePlayer(p, LeaveReason.QUIT_GAME);
		}
	}
	
	/**
	 * Handles the kill event
	 * 
	 * @param cpVic       A  {@link CustomPlayer} instance of the victim
	 * @param cpKiller    A  {@link CustomPlayer} instance of the killer
	 */
	public void handleKill(CustomPlayer cpVic, CustomPlayer cpKiller) {
		Map map = cpVic.getCurrentMap();
		
		Location deathLocation = cpVic.getPlayer().getLocation();
		
		if(map == null) return;
		
		// Kill event (Victim)
		cpVic.getPlayer().teleport(map.getRandomLocation().getLoc());
		cpVic.addDeath(map.getName());
		cpVic.getPlayer().setFireTicks(0);
		cpVic.setKillstreak(0);
		
		Utils.clearPlayer(main, cpVic.getPlayer(), map.getHealthOnJoin(), map.getHungerOnJoin(), cpVic.getPlayer().getMaxHealth(), map.getGamemode(), map.getSpawnProtection());
		
		int maxHealth = 20;
		if(cpVic.getSkillLevel("EXTRA_HEALTH") > 0)
			maxHealth = 20+main.getSettingsManager().getExtraHealthAmount().get(cpVic.getSkillLevel("EXTRA_HEALTH"));
		cpVic.getPlayer().setMaxHealth(maxHealth);
		
		cpVic.setCurrentTierId(cpVic.getCurrentTierId()-1);
		if(cpVic.getCurrentTierId() < 0)
			cpVic.setCurrentTierId(0);
		else
			cpVic.getPlayer().sendMessage(main.getMessages().youWereDemoted(cpVic.getCurrentTierId()));
		cpVic.setCurrentTier(map.getTiers()[cpVic.getCurrentTierId()]);
		
		if(cpVic.getSkillLevel("SPAWN_RESISTANCE") > 0)
			cpVic.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, main.getSettingsManager().getSpawnResistanceDuration().get(cpVic.getSkillLevel("SPAWN_RESISTANCE"))*20, 0));
		
		if(cpVic.getSkillLevel("SPAWN_GAPPLE") > 0) {
			Random rnd = new Random();
			int chance = rnd.nextInt(100-1+1)+1;
			if(chance <= main.getSettingsManager().getSpawnGappleRate().get(cpVic.getSkillLevel("SPAWN_GAPPLE")))
				cpVic.getPlayer().getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE));
		}
		
		main.getPacketHandler().getNMSHandler().sendActionBar(cpVic.getPlayer(), main.getMessages().death());
		cpVic.getPlayer().playSound(cpVic.getPlayer().getLocation(), XSound.BLOCK_FIRE_AMBIENT.parseSound(), 10, 2);
		
		// Killer handler
		if(cpKiller == null) {
			if(cpVic.getLastDamager() != null) {
				cpKiller = main.getPlayerManager().getPlayer(cpVic.getLastDamager());
				if(cpVic.getCurrentMap() == null && cpKiller.getCurrentMap() == null
					|| cpVic.getCurrentMap() == null && cpKiller.getCurrentMap() != null
					|| cpVic.getCurrentMap() != null && cpKiller.getCurrentMap() == null
					|| cpVic.getCurrentMap() != cpKiller.getCurrentMap()) {
					
					cpVic.setLastDamager(null);
					
					PlayerTierDownEvent tde = new PlayerTierDownEvent(cpVic.getPlayer(), cpVic, null, null, map, cpVic.getCurrentTier(), cpVic.getCurrentTierId());
					Bukkit.getPluginManager().callEvent(tde);
					return;
				}
			} else {
				cpVic.setLastDamager(null);
				return;
			}
		}
		cpVic.setLastDamager(null);
		
		KillEffect ke = main.getKillEffectsManager().getKillEffect(cpKiller.getCurrentKillEffect());
		if(ke != null) {
			ke.playKillEffect(main, deathLocation, cpVic.getPlayer(), cpKiller.getPlayer());
		}
		
		PlayerTierDownEvent tde = new PlayerTierDownEvent(cpVic.getPlayer(), cpVic, cpKiller.getPlayer(), cpKiller, map, cpVic.getCurrentTier(), cpVic.getCurrentTierId());
		Bukkit.getPluginManager().callEvent(tde);
			
		// Kill event (Killer)
		maxHealth = 20;
		if(cpKiller.getSkillLevel("EXTRA_HEALTH") > 0) {
			maxHealth = 20+main.getSettingsManager().getExtraHealthAmount().get(cpKiller.getSkillLevel("EXTRA_HEALTH"));
			cpKiller.getPlayer().setMaxHealth(maxHealth);
		}
		
		double newHealth = cpKiller.getPlayer().getHealth() + map.getHealthOnKill();
		if(newHealth > cpKiller.getPlayer().getMaxHealth())
			newHealth = cpKiller.getPlayer().getMaxHealth();
		cpKiller.getPlayer().setHealth(newHealth);
		cpKiller.addKill(map.getName());
		cpKiller.setKillstreak(cpKiller.getKillstreak() + 1);
		
		// Amount of tiers to skip
		int counter = 1;
		if(cpKiller.getSkillLevel("SKIP_TIER") > 0) {
			Random rnd = new Random();
			int chance = rnd.nextInt(100-1+1)+1;
			if(chance <= main.getSettingsManager().getSkipTierRate().get(cpKiller.getSkillLevel("SKIP_TIER")))
				counter++;
		}
		if(map.isDoubleTiersEvent())
			counter = counter*2;
		
		while (counter > 0) {
			cpKiller.setCurrentTierId(cpKiller.getCurrentTierId() + 1);
			if (cpKiller.getCurrentTierId() >= map.getTiers().length)
				cpKiller.setCurrentTierId(map.getTiers().length - 1);
			else
				cpKiller.getPlayer().sendMessage(main.getMessages().youWerePromoted(cpKiller.getCurrentTierId()));
			cpKiller.setCurrentTier(map.getTiers()[cpKiller.getCurrentTierId()]);
			counter--;
		}
		
		if (!cpKiller.containsHightier(map.getName())
				|| cpKiller.getCurrentTierId() > cpKiller.getHightier(map.getName()))
			cpKiller.setHightier(map.getName(), cpKiller.getCurrentTierId());

		main.getPacketHandler().getNMSHandler().sendActionBar(cpKiller.getPlayer(),
				main.getMessages().kill(cpVic.getPlayer().getName()));
		cpKiller.getPlayer().playSound(cpKiller.getPlayer().getLocation(), XSound.ENTITY_ARROW_HIT_PLAYER.parseSound(), 10, 2);

		
		double coins = map.getCoinsPerKill();
		if(cpKiller.getSkillLevel("EXTRA_GOLD") > 0)
			coins+=main.getSettingsManager().getExtraGoldAmount().get(cpKiller.getSkillLevel("EXTRA_GOLD"));
		if(coins > 0) {
			cpKiller.giveCoins(coins);
			cpKiller.getPlayer().sendMessage(main.getMessages().youReceivedCoinsForKilling(coins, cpVic.getPlayer().getName()));
		}

		PlayerTierUpEvent tue = new PlayerTierUpEvent(cpKiller.getPlayer(), cpKiller, cpVic.getPlayer(), cpVic, map,
				cpKiller.getCurrentTier(), cpKiller.getCurrentTierId());
		Bukkit.getPluginManager().callEvent(tue);
	}
}
