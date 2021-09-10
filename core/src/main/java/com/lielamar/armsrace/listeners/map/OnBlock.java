package com.lielamar.armsrace.listeners.map;

import com.lielamar.armsrace.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class OnBlock implements Listener {

    private final Main main;

    public OnBlock(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();

        if (main.getPlayerManager().getPlayer(p).getCurrentMap() == null) return;
        e.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();

        if (main.getPlayerManager().getPlayer(p).getCurrentMap() == null) return;
        e.setCancelled(true);
    }
}
