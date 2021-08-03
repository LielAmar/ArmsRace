package com.lielamar.armsrace.api;

import org.bukkit.entity.Player;

import com.lielamar.armsrace.Main;
import com.lielamar.armsrace.listeners.custom.LeaveReason;
import com.lielamar.armsrace.modules.CustomPlayer;
import com.lielamar.armsrace.modules.map.Map;

public class ArmsRaceAPI {

	static Main main = Main.getPlugin(Main.class);
	
	/**
	 * @param name     Name of the map
	 * @return         A {@link Map} object
	 */
	public static Map getMap(String name) {
		return main.getGameManager().getMapManager().getMap(name);
	}
	
	/**
	 * @param p        A player
	 * @return         A {@link Map} object which contains player
	 */
	public static Map getPlayersMap(Player p) {
		return main.getPlayerManager().getPlayer(p).getCurrentMap();
	}
	
	/**
	 * @param p        Player to get
	 * @return         a {@link CustomPlayer} instance of the player
	 */
	public static CustomPlayer getPlayer(Player p) {
		return main.getPlayerManager().getPlayer(p);
	}
	
	/**
	 * @param p        Player to add to a map
	 * @param map      the requested map
	 */
	public static void addPlayerToMap(Player p, Map map) {
		map.addPlayer(p);
	}
	
	/**
	 * @param p        Player to remove from their map
	 */
	public static void removePlayerFromMap(Player p) {
		main.getPlayerManager().getPlayer(p).getCurrentMap().removePlayer(p, LeaveReason.FORCE);
	}
}