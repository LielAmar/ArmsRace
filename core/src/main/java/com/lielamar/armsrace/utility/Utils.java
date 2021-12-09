package com.lielamar.armsrace.utility;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import com.lielamar.armsrace.Main;

public class Utils {

    /**
     * Clears a player
     *
     * @param main      Main class instance
     * @param player    Player to clear
     * @param health    Health
     * @param hunger    Hunger
     * @param maxHealth Max health
     * @param gameMode  GameMode
     */
    public static void clearPlayer(Main main, Player player, int health, int hunger, double maxHealth, GameMode gameMode) {
        player.getActivePotionEffects().clear();
        player.setWalkSpeed(0.2f);
        player.setFlySpeed(0.1f);
        player.setFlying(false);
        player.setAllowFlight(false);
        player.setMaxHealth(maxHealth);
        player.setHealth(health);
        player.setFoodLevel(hunger);
        player.setGameMode(gameMode);
        player.setExp(0);
        player.setLevel(0);

        Bukkit.getScheduler().runTask(main, () -> player.setFireTicks(0));
        main.getPlayerManager().updateSpawnProtection(player);
    }

    /**
     * Fixes decimal point to be only 2 digits
     */
    public static double fixDecimal(double number) {
        return Math.round(number * 100) / 100.0D;
    }

    public static String color(String s) {
        if (s == null) {
            return null;
        }
        return ChatColor.translateAlternateColorCodes('&', s);
    }

}
