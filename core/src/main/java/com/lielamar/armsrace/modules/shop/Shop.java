package com.lielamar.armsrace.modules.shop;

import com.lielamar.armsrace.modules.CustomPlayer;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Shop {

    private String name;
    private boolean enabled;
    private Inventory inventory;

    private final List<CustomItem> customItems = new LinkedList<>();

    public Shop(String name, boolean enabled, int rows) {
        this.name = name;
        this.enabled = enabled;
        this.inventory = Bukkit.createInventory(null, rows * 9, ChatColor.translateAlternateColorCodes('&', name));
    }

    /**
     * @param customPlayer A {@link CustomPlayer} object
     * @return A custom inventory for the given player
     */
    public Inventory getCustomInventory(CustomPlayer customPlayer) {
        Inventory copyInventory = Bukkit.createInventory(null, inventory.getSize(), ChatColor.translateAlternateColorCodes('&', name));
        copyInventory.setContents(inventory.getContents());

        for (ItemStack itemStack : copyInventory) {
            if (itemStack == null || itemStack.getType() == Material.AIR) {
                continue;
            }

            CustomItem customItem = getCustomItem(itemStack);
            if (customItem.getType() == ItemType.GENERAL) {
                setLore(itemStack, line -> line.replace("%coins%", "" + customPlayer.getCoins()));
            } else if (customItem.getType() == ItemType.SKILL) {
                setLore(itemStack, line -> line.replace("%price%", "" + customPlayer.getCoins())
                        .replace("%level%", "" + customPlayer.getSkillLevel(customItem.getSkillType())));
            } else if (customItem.getType() == ItemType.PROJECTILE || customItem.getType() == ItemType.KILL_EFFECT) {
                setLore(itemStack, line -> line.replace("%price%", "" + nextPrice(customPlayer, customItem)));
            }
        }

        return copyInventory;
    }

    public void setLore(ItemStack itemStack, Function<String, String> mapper) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta == null) {
            return;
        }

        itemMeta.setLore(itemMeta.getLore() != null ? itemMeta.getLore().stream()
                .map(mapper)
                .collect(Collectors.toList()) : new LinkedList<>());
        itemStack.setItemMeta(itemMeta);
    }

    /**
     * Get a custom item from an ItemStack
     *
     * @param item - itemStack to get the custom item of
     * @return - the custom item
     */
    public CustomItem getCustomItem(ItemStack item) {
        for (CustomItem customItem : customItems) {
            if (equals(customItem.getItem(), item))
                return customItem;
        }
        return null;
    }

    /**
     * @param cp   Player to calculate the price of
     * @param item The linked item of a skill
     * @return Next price of a skill
     */
    public double nextPrice(CustomPlayer cp, CustomItem item) {
        double nextPrice = item.getPrice();
        if (item.getType() == ItemType.PROJECTILE) {
            boolean hasTrail = cp.hasTrail(item.getTrailType());
            if (hasTrail)
                nextPrice = 0;
        } else if (item.getType() == ItemType.KILL_EFFECT) {
            boolean hasTrail = cp.hasKilleffect(item.getKillEffectType());
            if (hasTrail)
                nextPrice = 0;
        } else if (item.getType() == ItemType.SKILL) {
            double nextLevel = cp.getSkillLevel(item.getSkillType()) + 1;

            for (SkillLevel lvl : item.getLevels()) {
                if (lvl.getLevel() == nextLevel)
                    nextPrice = lvl.getPrice();
            }
        }
        return nextPrice;
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
        return this.inventory;
    }

    public void setInventory(Inventory inv) {
        this.inventory = inv;
    }

    public void addCustomItem(CustomItem customItem) {
        this.customItems.add(customItem);
    }

    public boolean equals(ItemStack one, ItemStack two) {
        return one.hasItemMeta() && two.hasItemMeta()
                && Objects.equals(one.getItemMeta().getDisplayName(), two.getItemMeta().getDisplayName());
    }

}
