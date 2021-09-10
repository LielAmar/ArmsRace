package com.lielamar.armsrace.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.lielamar.armsrace.modules.CustomPlayer;
import com.lielamar.armsrace.modules.map.Map;
import com.lielamar.armsrace.modules.map.Tier;

public class PlayerTierUpEvent extends Event {

    private static final HandlerList HANDLERS_LIST = new HandlerList();

    private final Player player, victim;
    private final CustomPlayer customPlayer, customVictim;
    private final Map map;
    private final Tier tier;
    private final int tierId;

    public PlayerTierUpEvent(Player player, CustomPlayer customPlayer, Player victim, CustomPlayer customKiller, Map map, Tier tier, int tierId) {
        this.player = player;
        this.customPlayer = customPlayer;
        this.victim = victim;
        this.customVictim = customKiller;
        this.map = map;
        this.tier = tier;
        this.tierId = tierId;
    }

    public Player getPlayer() {
        return this.player;
    }

    public CustomPlayer getCustomPlayer() {
        return this.customPlayer;
    }

    public Player getVictim() {
        return this.victim;
    }

    public CustomPlayer getCustomVictim() {
        return this.customVictim;
    }

    public Map getMap() {
        return this.map;
    }

    public Tier getTier() {
        return this.tier;
    }

    public int getTierId() {
        return this.tierId;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

}
