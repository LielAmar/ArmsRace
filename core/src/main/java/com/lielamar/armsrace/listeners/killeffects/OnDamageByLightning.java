package com.lielamar.armsrace.listeners.killeffects;

import com.lielamar.armsrace.Main;
import com.lielamar.armsrace.modules.CustomPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class OnDamageByLightning implements Listener {

    private final Main plugin;

    public OnDamageByLightning(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDamageByEntityEvent(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player) || event.getCause() != DamageCause.LIGHTNING) return;

        Player player = (Player) event.getEntity();
        CustomPlayer customPlayer = plugin.getPlayerManager().getPlayer(player);

        if (customPlayer.getCurrentMap() != null) {
            event.setCancelled(true);
        }
    }

}
