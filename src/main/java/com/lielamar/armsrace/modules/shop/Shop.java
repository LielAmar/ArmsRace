package com.lielamar.armsrace.modules.shop;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.lielamar.armsrace.modules.CustomPlayer;

import net.md_5.bungee.api.ChatColor;

public class Shop {

	private String name;
	private boolean enabled;
	private Inventory inv;
	private List<CustomItem> ci;

	public Shop(String name, boolean enabled, int rows) {
		this.name = name;
		this.enabled = enabled;
		this.inv = Bukkit.createInventory(null, rows*9, ChatColor.translateAlternateColorCodes('&', name));
		this.ci = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Inventory getInventory() {
		return this.inv;
	}

	public void setInventory(Inventory inv) {
		this.inv = inv;
	}

	public void addCustomItem(CustomItem ci) {
		this.ci.add(ci);
	}

	/**
	 * @param cp      A {@link CustomPlayer} object
	 * @return        A custom inventory for the given player
	 */
	public Inventory getCustomInventory(CustomPlayer cp) {
		InventoryView openInventory = cp.getPlayer().getOpenInventory();
		Inventory copy = Bukkit.createInventory(null, inv.getSize(), openInventory.getTitle());
		copy.setContents(inv.getContents());

		for(ItemStack i : copy) {
			if(i == null || i.getType() == Material.AIR) continue;
			CustomItem ci = getCustomItem(i);

			if(ci.getType() == ItemType.GENERAL) {
				if(i.hasItemMeta() && i.getItemMeta().hasLore()) {
					ItemMeta meta = i.getItemMeta();
					List<String> lore = new ArrayList<>();
					for(String s : meta.getLore())
						lore.add(s.replaceAll("%coins%", cp.getCoins() + ""));
					meta.setLore(lore);
					i.setItemMeta(meta);
				}
			} if(ci.getType() == ItemType.SKILL) {
				if(i.hasItemMeta() && i.getItemMeta().hasLore()) {
					ItemMeta meta = i.getItemMeta();
					List<String> lore = new ArrayList<>();
					for(String s : meta.getLore())
						lore.add(s.replaceAll("%price%", nextPrice(cp, ci) + "").replaceAll("%level%", cp.getSkillLevel(ci.getSkillType()) + ""));
					meta.setLore(lore);
					i.setItemMeta(meta);
				}
			} else if(ci.getType() == ItemType.PROJECTILE) {
				if(i.hasItemMeta() && i.getItemMeta().hasLore()) {
					ItemMeta meta = i.getItemMeta();
					List<String> lore = new ArrayList<>();
					for(String s : meta.getLore())
						lore.add(s.replaceAll("%price%", nextPrice(cp, ci) + ""));
					meta.setLore(lore);
					i.setItemMeta(meta);
				}
			} else if(ci.getType() == ItemType.KILL_EFFECT) {
				ItemMeta meta = i.getItemMeta();
				List<String> lore = new ArrayList<>();
				for(String s : meta.getLore())
					lore.add(s.replaceAll("%price%", nextPrice(cp, ci) + ""));
				meta.setLore(lore);
				i.setItemMeta(meta);
			}
		}
		return copy;
	}

	/**
	 * Get a custom item from an ItemStack
	 *
	 * @param item - itemstack to get the custom item of
	 * @return - the custom item
	 */
	/**
	 * @param item      An ItemStack object
	 * @return          A {@link CustomItem} object
	 */
	public CustomItem getCustomItem(ItemStack item) {
		for(CustomItem ci : ci) {
			if(equal(ci.getItem(), item))
				return ci;
		}
		return null;
	}

	public boolean equal(ItemStack one, ItemStack two) {
		if(one.hasItemMeta() && two.hasItemMeta()
				&& one.getItemMeta().hasDisplayName() && two.getItemMeta().hasDisplayName()
				&& one.getItemMeta().getDisplayName().equalsIgnoreCase(two.getItemMeta().getDisplayName()))
			return true;
		return false;
	}

	/**
	 * @param cp      Player to calculate the price of
	 * @param item    The linked item of a skill
	 * @return        Next price of a skill
	 */
	public double nextPrice(CustomPlayer cp, CustomItem item) {
		double nextPrice = item.getPrice();
		if(item.getType() == ItemType.PROJECTILE) {
			boolean hasTrail = cp.hasTrail(item.getTrailType());
			if(hasTrail)
				nextPrice = 0;
		} else if(item.getType() == ItemType.KILL_EFFECT) {
			boolean hasTrail = cp.hasKilleffect(item.getKillEffectType());
			if(hasTrail)
				nextPrice = 0;
		} else if(item.getType() == ItemType.SKILL) {
			int nextLevel = cp.getSkillLevel(item.getSkillType())+1;

			for(SkillLevel lvl : item.getLevels()) {
				if(lvl.getLevel() == nextLevel)
					nextPrice = lvl.getPrice();
			}
		}
		return nextPrice;
	}
}
