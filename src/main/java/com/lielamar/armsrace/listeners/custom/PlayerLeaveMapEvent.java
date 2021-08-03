package com.lielamar.armsrace.listeners.custom;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.lielamar.armsrace.modules.CustomPlayer;
import com.lielamar.armsrace.modules.map.Map;

public class PlayerLeaveMapEvent extends Event implements Cancellable {

	private LeaveReason lr;
	private Player p;
	private CustomPlayer cp;
	private Map map;
	private static final HandlerList HANDLERS_LIST = new HandlerList();
	private boolean isCancelled;
	
    public PlayerLeaveMapEvent(LeaveReason lr, Player p, CustomPlayer cp, Map map){
        this.p = p;
        this.cp = cp;
        this.isCancelled = false;
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
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    public LeaveReason getLeaveReason() {
    	return this.lr;
    }
    
    public Player getPlayer() {
    	return this.p;
    }
    
    public CustomPlayer getCustomPlayer() {
    	return this.cp;
    }
    
    public Map getMap() {
    	return this.map;
    }
}
