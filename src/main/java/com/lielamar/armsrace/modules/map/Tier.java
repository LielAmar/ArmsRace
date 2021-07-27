package com.lielamar.armsrace.modules.map;

import org.bukkit.inventory.ItemStack;

public class Tier {

	private ItemStack[] armor;
	private ItemStack[] content;

	public Tier(ItemStack[] armor, ItemStack[] content) {
		this.armor = armor;
		this.content = content;
	}

	public ItemStack[] getArmor() {
		return armor;
	}

	public void setArmor(ItemStack[] armor) {
		this.armor = armor;
	}

	public ItemStack[] getContent() {
		return content;
	}

	public void setContent(ItemStack[] content) {
		this.content = content;
	}
}
