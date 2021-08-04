package com.lielamar.armsrace.listeners.killeffects;

import com.lielamar.armsrace.Main;
import com.lielamar.armsrace.modules.CustomPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class OnDamageByFirework implements Listener {

    private Main main;

    public OnDamageByFirework(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onLightning(EntityDamageByEntityEvent e) {
        if(!(e.getEntity() instanceof Player)) return;
        if(!(e.getDamager() instanceof Firework)) return;
        if(e.getCause() != EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) return;
        Player p = (Player)e.getEntity();

        CustomPlayer cp = main.getPlayerManager().getPlayer(p);
        if(cp.getCurrentMap() != null)
            e.setCancelled(true);
    }
}
