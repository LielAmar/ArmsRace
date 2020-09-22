package com.lielamar.armsrace.modules.map;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.lielamar.armsrace.Main;

public enum PickupType {

	HEALTH (Main.getPlugin(Main.class), "Healing"),
	DOUBLE_DAMAGE (Main.getPlugin(Main.class), "DoubleDamage"),
	COINS (Main.getPlugin(Main.class), "Coins"),
	SPEED (Main.getPlugin(Main.class), "Speed"),
	RESISTANCE (Main.getPlugin(Main.class), "Resistance"),
	TIER_UP (Main.getPlugin(Main.class), "TierUp"),
	ONE_TAP (Main.getPlugin(Main.class), "OneTap");

	private final Material material;
	private final String name;

	private PickupType(Main main, String key) {
		this.material = Material.valueOf(main.getConfig().getString("Pickups." + key + ".Item"));
		this.name = ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("Pickups." + key + ".Name"));
	}
	
    public Material getMaterial() {
        return this.material;
    }
    
    public String getName() {
    	return this.name;
    }
}
