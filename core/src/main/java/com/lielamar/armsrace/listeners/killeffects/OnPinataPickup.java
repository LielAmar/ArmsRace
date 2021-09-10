package com.lielamar.armsrace.listeners.killeffects;

import com.lielamar.armsrace.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class OnPinataPickup implements Listener {

    private final Main plugin;

    public OnPinataPickup(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent event) {
        String name = event.getItem().getType().name();
        String version = Bukkit.getVersion();

        if (version.contains("1.8") || version.contains("1.9") || version.contains("1.10") || version.contains("1.11") || version.contains("1.12")) {
            return;
        }

        if (!(name.contains("DYE"))) {
            return;
        }

        if (plugin.getKillEffectsManager().containsEntity(event.getItem())) {
            event.setCancelled(true);
        }
    }

}
