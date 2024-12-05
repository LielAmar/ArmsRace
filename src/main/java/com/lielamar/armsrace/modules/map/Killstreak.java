package com.lielamar.armsrace.modules.map;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

@Setter
@Getter
public class Killstreak {

	private int level;
	private double coins;
	private List<ItemStack> items;
	private List<PotionEffect> effects;
	
	public Killstreak(int level, double coins) {
		this.level = level;
		this.coins = coins;
		this.items = new ArrayList<>();
		this.effects = new ArrayList<>();
	}

}
