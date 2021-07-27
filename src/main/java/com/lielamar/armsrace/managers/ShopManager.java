package com.lielamar.armsrace.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cryptomorin.xseries.XMaterial;
import com.lielamar.armsrace.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.lielamar.armsrace.managers.files.BukkitFileManager.Config;
import com.lielamar.armsrace.modules.shop.CustomItem;
import com.lielamar.armsrace.modules.shop.ItemType;
import com.lielamar.armsrace.modules.shop.Shop;
import com.lielamar.armsrace.modules.shop.SkillLevel;
import com.lielamar.armsrace.modules.shop.TrailData;

public class ShopManager {

	private Main main;
	private Config config;
	private Map<String, Shop> shops;
	private String mainShop;

	public ShopManager(Main main, Config config) {
		this.main = main;
		this.config = config;
		this.shops = new HashMap<String, Shop>();

		setup();
	}

	public Main getMain() {
		return this.main;
	}

	public String getMainShop() {
		return this.mainShop;
	}

	public Shop getShop(String key) {
		return this.shops.get(key);
	}

	public Map<String, Shop> getShops() {
		return this.shops;
	}

	public void setup() {
		for(String shop : config.getConfigurationSection("shops").getKeys(false)) {
			Shop tmpShop = new Shop((String)config.get("shops." + shop + ".name"), (boolean)config.get("shops." + shop + ".enabled"), (int)config.get("shops." + shop + ".rows"));
			if(config.getConfigurationSection("shops." + shop + ".data") == null) continue;
			for(String s : config.getConfigurationSection("shops." + shop + ".data").getKeys(false)) {
				try {
					CustomItem i = getItem(shop, s);
					if(i == null) continue;
					tmpShop.addCustomItem(i);
					tmpShop.getInventory().setItem(Integer.parseInt(s), i.getItem());
				} catch(Exception e) {
					e.printStackTrace();
					System.out.println("The items in shop.yml are not valid. Make sure the id is Integer!");
				}
			}
			shops.put(shop, tmpShop);
		}
		this.mainShop = (String)config.get("mainshop");
	}

	private CustomItem getItem(String shop, String id) {
		if(!(boolean)config.get("shops." + shop + ".data." + id + ".enabled")) return null; // Don't add disabed items

		// Basic data (Material, quantity, name, lore)
		Material material = XMaterial.valueOf((String)config.get("shops." + shop + ".data." + id + ".material")).parseMaterial();
		int quantity = (int)config.get("shops." + shop + ".data." + id + ".quantity");
		String name = ChatColor.translateAlternateColorCodes('&', (String)config.get("shops." + shop + ".data." + id + ".name"));
		List<String> lore = new ArrayList<>();
		for(String s : config.getStringList("shops." + shop + ".data." + id + ".lore"))
			lore.add(ChatColor.translateAlternateColorCodes('&', s));

		ItemStack item = new ItemStack(material, quantity);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(lore);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
		meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		item.setItemMeta(meta);


		// Action (shop to open if at all)
		String action = "";
		if(config.contains("shops." + shop + ".data." + id + ".shop"))
			action = (String)config.get("shops." + shop + ".data." + id + ".shop");


		// Levels (skill levels if at all)
		List<SkillLevel> levels = new ArrayList<>();
		if(config.contains("shops." + shop + ".data." + id + ".levels")) {
			for(String s : config.getConfigurationSection("shops." + shop + ".data." + id + ".levels").getKeys(false)) {
				SkillLevel level = new SkillLevel(Integer.parseInt(s), (double)config.get("shops." + shop + ".data." + id + ".levels." + s + ".price"));
				levels.add(level);
			}
		}

		// Type of the trail (if at all)
		String skillType = null;
		if(config.contains("shops." + shop + ".data." + id + ".skill"))
			skillType = (String)config.get("shops." + shop + ".data." + id + ".skill");


		// Price (trail/kill effect price if at all)
		double price = 0;
		if(config.contains("shops." + shop + ".data." + id + ".price"))
			price = (double)config.get("shops." + shop + ".data." + id + ".price");

		// Type of the trail (if at all)
		String trailType = null;
		if(config.contains("shops." + shop + ".data." + id + ".trailtype"))
			trailType = (String)config.get("shops." + shop + ".data." + id + ".trailtype");

		// Data of the trail (if at all)
		TrailData trailData = null;
		if(config.contains("shops." + shop + ".data." + id + ".traildata"))
			trailData = new TrailData((int)config.get("shops." + shop + ".data." + id + ".traildata.r"),
					(int)config.get("shops." + shop + ".data." + id + ".traildata.g"),
					(int)config.get("shops." + shop + ".data." + id + ".traildata.b"));


		String killeffectType = null;
		if(config.contains("shops." + shop + ".data." + id + ".killeffect"))
			killeffectType = (String)config.get("shops." + shop + ".data." + id + ".killeffect");


		ItemType type = ItemType.GENERAL;
		if(action.length() > 0) {
			type = ItemType.SHOP;
		} else if(levels.size() > 0) {
			type = ItemType.SKILL;
		} else if(trailType != null) {
			type = ItemType.PROJECTILE;
		} else if(killeffectType != null){
			type = ItemType.KILL_EFFECT;
		}

		return new CustomItem(type, ChatColor.stripColor(name), item, action, levels, skillType, price, trailType, trailData, killeffectType);
	}
}
