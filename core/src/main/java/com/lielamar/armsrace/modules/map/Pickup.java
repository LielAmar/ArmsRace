package com.lielamar.armsrace.modules.map;

import org.bukkit.Location;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

public class Pickup {

	private final Map map;
	private Item pickup;
	private PickupType type;
	private Location loc;
	
	public Pickup(Map map, PickupType type, Location loc) {
		this.map = map;
		this.loc = loc;
		this.type = type;
	}
	
	public void spawn() {
		ItemStack item = new ItemStack(this.type.getMaterial());
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(map.getName());
		meta.addEnchant(Enchantment.DURABILITY, 1, false);
		item.setItemMeta(meta);
		this.pickup = loc.getWorld().dropItem(loc, item);
		this.pickup.setCustomName(type.getName());
		this.pickup.setCustomNameVisible(true);
		this.pickup.setVelocity(new Vector());
	}
	
	public Item getPickup() {
		return pickup;
	}

	public void setPickup(Item pickup) {
		this.pickup = pickup;
	}

	public PickupType getType() {
		return type;
	}

	public void setType(PickupType type) {
		this.type = type;
	}

	public Location getLoc() {
		return loc;
	}

	public void setLoc(Location loc) {
		this.loc = loc;
	}
	
	public String getName() {
		return type.getName();
	}
}
