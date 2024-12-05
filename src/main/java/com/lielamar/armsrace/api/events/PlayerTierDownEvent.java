package com.lielamar.armsrace.api.events;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.lielamar.armsrace.modules.CustomPlayer;
import com.lielamar.armsrace.modules.map.Map;
import com.lielamar.armsrace.modules.map.Tier;

@Getter
public class PlayerTierDownEvent extends Event {

    private static final HandlerList HANDLERS_LIST = new HandlerList();

    private final Player player, killer;
    private final CustomPlayer customPlayer, customKiller;
    private final Map map;
    private final Tier tier;
    private final int tierId;

    public PlayerTierDownEvent(Player player, CustomPlayer customPlayer, Player killer, CustomPlayer customKiller, Map map, Tier tier, int tierId) {
        this.player = player;
        this.customPlayer = customPlayer;
        this.killer = killer;
        this.customKiller = customKiller;
        this.map = map;
        this.tier = tier;
        this.tierId = tierId;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

}
