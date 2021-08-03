package com.lielamar.armsrace.commands.subcommands;

import org.bukkit.ChatColor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import com.lielamar.armsrace.Main;
import com.lielamar.armsrace.modules.map.Map;
import com.lielamar.armsrace.modules.map.Pickup;

public class ReloadCommand extends SubCommand{

	@Override
	public void onCommand(Main main, Player p, String[] args) {
		if(!p.hasPermission("armsrace.commands.reload")) {
			p.sendMessage(main.getMessages().noPermissions());
	        return;
		}
		
		for(ArmorStand as : main.getPlayerManager().getSwordLaunchAs().values()) {
			as.remove();
		}
			
		for(Map map : main.getGameManager().getMapManager().getMaps().values()) {
			for(Pickup pickup : map.getPickups()) {
				pickup.getPickup().remove();
			}
		}
		
		main.onDisable();
		main.registerManagers();
		p.sendMessage(ChatColor.GREEN + "Reloaded config of ArmsRace!");
		return;
	}
	
	@Override
	public String name() {
		return "reload";
	}

	@Override
	public String info() {
		return "ArmsRace reload";
	}

	@Override
	public String[] aliases() {
		return new String[] { "reload" };
	}
}