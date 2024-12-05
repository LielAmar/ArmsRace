package com.lielamar.armsrace.listeners.killeffects;

import com.cryptomorin.xseries.reflection.XReflection;
import com.lielamar.armsrace.Main;
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

        if (!XReflection.supports(13)) {
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
