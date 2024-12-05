package com.lielamar.armsrace.modules.map;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

@Setter
@Getter
public class Tier {

	private ItemStack[] armor;
	private ItemStack[] content;
	
	public Tier(ItemStack[] armor, ItemStack[] content) {
		this.armor = armor;
		this.content = content;
	}

}
