package com.lielamar.armsrace.listeners.custom;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.lielamar.armsrace.modules.CustomPlayer;
import com.lielamar.armsrace.modules.map.Map;
import com.lielamar.armsrace.modules.map.Tier;

public class PlayerTierUpEvent extends Event {

    private Player p;
    private CustomPlayer cp;
    private Player v;
    private CustomPlayer cv;
    private Map map;
    private Tier tier;
    private int tierId;
    private static final HandlerList HANDLERS_LIST = new HandlerList();

    public PlayerTierUpEvent(Player p, CustomPlayer cp, Player v, CustomPlayer cv, Map map, Tier tier, int tierId){
        this.p = p;
        this.cp = cp;
        this.v = v;
        this.cv = cv;
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

    public Player getVictim() {
        return this.v;
    }

    public CustomPlayer getCustomVictim() {
        return this.cv;
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
