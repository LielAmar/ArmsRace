package com.lielamar.armsrace.api;

import com.lielamar.armsrace.Main;
import com.lielamar.armsrace.api.events.PlayerLeaveMapEvent;
import com.lielamar.armsrace.modules.CustomPlayer;
import com.lielamar.armsrace.modules.map.Map;
import org.bukkit.entity.Player;

public class ArmsRaceAPI {

    static Main main = Main.getPlugin(Main.class);

    /**
     * @param name Name of the map
     * @return A {@link Map} object
     */
    public static Map getMap(String name) {
        return main.getGameManager().getMapManager().getMap(name);
    }

    /**
     * @param player Player to get
     * @return a {@link CustomPlayer} instance of the player
     */
    public static CustomPlayer getPlayer(Player player) {
        return main.getPlayerManager().getPlayer(player);
    }

    /**
     * @param player A player
     * @return A {@link Map} object which contains player
     */
    public static Map getPlayersMap(Player player) {
        return getPlayer(player).getCurrentMap();
    }

    /**
     * @param player Player to add to a map
     * @param map    the requested map
     */
    public static void addPlayerToMap(Player player, Map map) {
        map.addPlayer(player);
    }

    /**
     * @param player Player to remove from their map
     */
    public static void removePlayerFromMap(Player player) {
        getPlayersMap(player).removePlayer(player, PlayerLeaveMapEvent.LeaveReason.FORCE);
    }

}