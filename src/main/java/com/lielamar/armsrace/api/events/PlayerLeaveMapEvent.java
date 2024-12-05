package com.lielamar.armsrace.api.events;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.lielamar.armsrace.modules.CustomPlayer;
import com.lielamar.armsrace.modules.map.Map;
import org.jetbrains.annotations.NotNull;

public class PlayerLeaveMapEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean isCancelled;

    @Getter
    private final LeaveReason leaveReason;
    @Getter
    private final Player player;
    @Getter
    private final CustomPlayer customPlayer;
    @Getter
    private final Map map;

    public PlayerLeaveMapEvent(LeaveReason leaveReason, Player player, CustomPlayer customPlayer, Map map) {
        this.leaveReason = leaveReason;
        this.player = player;
        this.customPlayer = customPlayer;
        this.map = map;
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

    public enum LeaveReason {
        QUIT_GAME, LEAVE_COMMAND, FORCE, RELOAD, SPAWN
    }
}
