package com.lielamar.armsrace.listeners.killeffects;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

import com.lielamar.armsrace.Main;
import org.bukkit.inventory.ItemStack;

public class OnCookiePickup implements Listener {

    private final Main plugin;

    public OnCookiePickup(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent event) {
        Item item = event.getItem();
        ItemStack itemStack = item.getItemStack();

        if (itemStack.getType() == Material.COOKIE && plugin.getKillEffectsManager().containsEntity(item)) {
            event.setCancelled(true);
        }
    }

}
