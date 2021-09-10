package com.lielamar.armsrace.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.lielamar.armsrace.modules.CustomPlayer;
import com.lielamar.armsrace.modules.map.Map;
import org.jetbrains.annotations.NotNull;

public class PlayerJoinMapEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean isCancelled;

    private final Player player;
    private final CustomPlayer customPlayer;
    private final Map map;

    public PlayerJoinMapEvent(Player player, CustomPlayer customPlayer, Map map) {
        this.player = player;
        this.customPlayer = customPlayer;
        this.map = map;
    }

    public Player getPlayer() {
        return this.player;
    }

    public CustomPlayer getCustomPlayer() {
        return this.customPlayer;
    }

    public Map getMap() {
        return this.map;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.isCancelled = cancelled;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

}
