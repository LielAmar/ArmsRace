package com.lielamar.armsrace.listeners.custom;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.lielamar.armsrace.modules.CustomPlayer;
import com.lielamar.armsrace.modules.map.Map;
import com.lielamar.armsrace.modules.map.Tier;

public class PlayerTierDownEvent extends Event {

    private Player p;
    private CustomPlayer cp;
    private Player k;
    private CustomPlayer ck;
    private Map map;
    private Tier tier;
    private int tierId;
    private static final HandlerList HANDLERS_LIST = new HandlerList();

    public PlayerTierDownEvent(Player p, CustomPlayer cp, Player k, CustomPlayer ck, Map map, Tier tier, int tierId){
        this.p = p;
        this.cp = cp;
        this.k = k;
        this.ck = ck;
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

    public Player getPlayer() {
        return this.p;
    }

    public CustomPlayer getCustomPlayer() {
        return this.cp;
    }

    public Player getKiller() {
        return this.k;
    }

    public CustomPlayer getCustomKiller() {
        return this.ck;
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
}
