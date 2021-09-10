package com.lielamar.armsrace.modules.shop;

import java.util.List;

import org.bukkit.inventory.ItemStack;

public class CustomItem {

	private ItemType type;
	private String name;
	private ItemStack item;
	
	private String action;
	
	private List<SkillLevel> levels;
	private String skillType;
	
	private int price;
	private String trailType;
	private TrailData trailData;
	
	private String killeffectType;

	public CustomItem(ItemType type, String name, ItemStack item, String action, List<SkillLevel> levels, String skillType, int price, String trailType, TrailData trailData, String killeffectType) {
		this.type = type;
		this.name = name;
		this.item = item;
		
		this.action = action;
		
		this.levels = levels;
		this.skillType = skillType;
		
		this.price = price;
		this.trailType = trailType;
		this.trailData = trailData;
		
		this.killeffectType = killeffectType;
	}

	public ItemType getType() {
		return type;
	}

	public void setType(ItemType type) {
		this.type = type;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public ItemStack getItem() {
		return item;
	}

	public void setItem(ItemStack item) {
		this.item = item;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public List<SkillLevel> getLevels() {
		return levels;
	}

	public void setLevels(List<SkillLevel> levels) {
		this.levels = levels;
	}
	
	public String getSkillType() {
		return skillType;
	}
	
	public void setSkillType(String skillType) {
		this.skillType = skillType;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
	public String getTrailType() {
		return this.trailType;
	}
	
	public void setTrailType(String trailType) {
		this.trailType = trailType;
	}
	
	public TrailData getTrailData() {
		return this.trailData;
	}
	
	public void setTrailData(TrailData trailData) {
		this.trailData = trailData;
	}
	
	public String getKillEffectType() {
		return this.killeffectType;
	}
	
	public void setKillEffectType(String killeffectType) {
		this.killeffectType = killeffectType;
	}
}
