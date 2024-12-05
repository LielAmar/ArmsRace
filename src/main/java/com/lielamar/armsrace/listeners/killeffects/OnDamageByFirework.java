package com.lielamar.armsrace.listeners.killeffects;

import com.lielamar.armsrace.Main;
import com.lielamar.armsrace.modules.CustomPlayer;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class OnDamageByFirework implements Listener {

    private final Main plugin;

    public OnDamageByFirework(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player || event.getDamager() instanceof Firework) || event.getCause() != EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
            return;
        }

        Player player = (Player) event.getEntity();
        CustomPlayer customPlayer = plugin.getPlayerManager().getPlayer(player);

        if (customPlayer.getCurrentMap() != null) {
            event.setCancelled(true);
        }
    }

}
