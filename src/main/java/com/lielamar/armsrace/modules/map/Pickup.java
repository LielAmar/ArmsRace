package com.lielamar.armsrace.modules.map;

import com.cryptomorin.xseries.XEnchantment;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

public class Pickup {

	private final Map map;
	@Setter
    @Getter
    private Item pickup;
	@Getter
    @Setter
    private PickupType type;
	@Setter
    @Getter
    private Location loc;
	
	public Pickup(Map map, PickupType type, Location loc) {
		this.map = map;
		this.loc = loc;
		this.type = type;
	}
	
	public void spawn() {
		ItemStack item = new ItemStack(this.type.getMaterial().parseMaterial());
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(map.getName());
		meta.addEnchant(XEnchantment.UNBREAKING.getEnchant(), 1, false);
		item.setItemMeta(meta);
		this.pickup = loc.getWorld().dropItem(loc, item);
		this.pickup.setCustomName(type.getName());
		this.pickup.setCustomNameVisible(true);
		this.pickup.setVelocity(new Vector());
	}

    public String getName() {
		return type.getName();
	}
}
