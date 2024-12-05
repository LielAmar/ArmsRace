package com.lielamar.armsrace.modules.shop;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

public class CustomItem {

	@Setter
    @Getter
    private ItemType type;
	@Setter
    @Getter
    private String name;
	@Setter
    @Getter
    private ItemStack item;
	
	@Getter
    @Setter
    private String action;
	
	@Getter
    @Setter
    private List<SkillLevel> levels;
	@Setter
    @Getter
    private String skillType;
	
	@Setter
    @Getter
    private int price;
	@Setter
    @Getter
    private String trailType;
	@Setter
    @Getter
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

    public String getKillEffectType() {
		return this.killeffectType;
	}
	
	public void setKillEffectType(String killeffectType) {
		this.killeffectType = killeffectType;
	}
}
