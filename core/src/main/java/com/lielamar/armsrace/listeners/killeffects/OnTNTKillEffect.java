package com.lielamar.armsrace.listeners.killeffects;

import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import com.lielamar.armsrace.Main;

public class OnTNTKillEffect implements Listener {

    private final Main plugin;

    public OnTNTKillEffect(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onTNT(EntityExplodeEvent event) {
        if (event.getEntity() instanceof TNTPrimed && plugin.getKillEffectsManager().containsEntity(event.getEntity())) {
            event.blockList().clear();
        }
    }

    @EventHandler
    public void onDamageTNT(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof TNTPrimed && plugin.getKillEffectsManager().containsEntity(event.getDamager())) {
            event.setCancelled(true);
        }
    }

}
