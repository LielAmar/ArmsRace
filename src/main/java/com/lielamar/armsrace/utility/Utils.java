package com.lielamar.armsrace.utility;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import com.lielamar.armsrace.Main;

public class Utils {

	/**
	 * Clears a player
	 *
	 * @param main             Main class instance
	 * @param p                Player to clear
	 * @param health           Health
	 * @param hunger           Hunger
	 * @param maxHealth        Max health
	 * @param gamemode         Gamemode
	 * @param spawnProtection  Seconds of spawnProtection
	 */
	public static void clearPlayer(Main main, Player p, int health, int hunger, double maxHealth, GameMode gamemode, int spawnProtection) {
		p.getActivePotionEffects().clear();
		p.setWalkSpeed(0.2f);
		p.setFlySpeed(0.1f);
		p.setFlying(false);
		p.setAllowFlight(false);
		p.setMaxHealth(maxHealth);
		p.setHealth(health);
		p.setFoodLevel(hunger);
		p.setGameMode(gamemode);
		p.setExp(0);
		p.setLevel(0);

		Bukkit.getScheduler().runTask(main, new Runnable() {
			@Override
			public void run() {
				p.setFireTicks(0);
			}
		});

		main.getPlayerManager().updateSpawnProtection(p);
	}

	/**
	 * Fixes decimal point to be only 2 digits
	 */
	public static double fixDecimal(double d) {
		BigDecimal bd = BigDecimal.valueOf(d);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
}
