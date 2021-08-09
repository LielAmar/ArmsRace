package com.lielamar.armsrace.commands.impls;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.lielamar.armsrace.Main;
import com.lielamar.armsrace.modules.map.Map;

public class EventCommand extends SubCommand {
	
	@Override
	public void onCommand(Main main, Player p, String[] args) {
		if(args.length == 0 || args.length == 1) {
			p.sendMessage(main.getMessages().invalidArgument());
			return;
		} else {
			String mapName = args[0];
			String eventName = args[1];
			
			Map map = main.getGameManager().getMapManager().getMap(mapName);
			if(map == null) {
				p.sendMessage(main.getMessages().couldntFindMap(mapName));
	        	return;
			}
			
			if(!p.hasPermission("armsrace.commands.events")) {
				p.sendMessage(main.getMessages().noPermissions());
	        	return;
			}
			
			boolean enabled = false;
			if(eventName.equalsIgnoreCase("doublecoins"))
				enabled = map.setDoubleCoinsEvent(!map.isDoubleCoinsEvent());
			else if(eventName.equalsIgnoreCase("doubletiers"))
				enabled = map.setDoubleTiersEvent(!map.isDoubleTiersEvent());
			else {
				p.sendMessage(ChatColor.AQUA + "Available events: DoubleCoins, DoubleTiers");
				return;
			}
			if(enabled) {
				p.sendMessage(main.getMessages().eventEnabled(eventName));
				Bukkit.broadcastMessage(main.getMessages().eventEnabledAnnounce(eventName));
			} else {
				p.sendMessage(main.getMessages().eventDisabled(eventName));
				Bukkit.broadcastMessage(main.getMessages().eventDisabledAnnounce(eventName));
			}
			return;
		}
	}

	@Override
	public String name() {
		return "event";
	}

	@Override
	public String info() {
		return "ArmsRace event";
	}

	@Override
	public String[] aliases() {
		return new String[] { "events" };
	}
}