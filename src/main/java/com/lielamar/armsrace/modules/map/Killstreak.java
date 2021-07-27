package com.lielamar.armsrace.modules.map;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

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

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public double getCoins() {
		return coins;
	}

	public void setCoins(double coins) {
		this.coins = coins;
	}

	public List<ItemStack> getItems() {
		return items;
	}

	public void setItems(List<ItemStack> items) {
		this.items = items;
	}

	public List<PotionEffect> getEffects() {
		return effects;
	}

	public void setEffects(List<PotionEffect> effects) {
		this.effects = effects;
	}
}
