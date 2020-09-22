package com.lielamar.armsrace.commands.subcommands;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.lielamar.armsrace.Main;

public class HelpCommand extends SubCommand {
	
	final int commands_per_page = 8;
	Map<String, String> pluginCommands;
	final int pages;
	
	public HelpCommand() {
		this.pluginCommands = new HashMap<String, String>();
		this.pluginCommands.put(ChatColor.AQUA + "- " + ChatColor.YELLOW + "/ArmsRace Map Create <Name>", "armsrace.commands.map.create");
		this.pluginCommands.put(ChatColor.AQUA + "- " + ChatColor.YELLOW + "/ArmsRace Map Delete <Name>", "armsrace.commands.map.delete");
		this.pluginCommands.put(ChatColor.AQUA + "- " + ChatColor.YELLOW + "/ArmsRace Map List", "armsrace.commands.map.list");
		this.pluginCommands.put(ChatColor.AQUA + "- " + ChatColor.YELLOW + "/ArmsRace Map AddTier <Map Name>", "armsrace.commands.map.addtier");
		this.pluginCommands.put(ChatColor.AQUA + "- " + ChatColor.YELLOW + "/ArmsRace Map SetTier <Map Name> <Tier ID>", "armsrace.commands.map.settier");
		this.pluginCommands.put(ChatColor.AQUA + "- " + ChatColor.YELLOW + "/ArmsRace Map RemoveTier <Map Name> <Tier ID>", "armsrace.commands.map.deletetier");
		this.pluginCommands.put(ChatColor.AQUA + "- " + ChatColor.YELLOW + "/ArmsRace Map ListTier <Map Name>", "armsrace.commands.map.listtier");
		this.pluginCommands.put(ChatColor.AQUA + "- " + ChatColor.YELLOW + "/ArmsRace Map LoadTier <Map Name> <Tier ID>", "armsrace.commands.map.loadtier");
		this.pluginCommands.put(ChatColor.AQUA + "- " + ChatColor.YELLOW + "/ArmsRace Map AddLocation <Map Name>", "armsrace.commands.map.addlocation");
		this.pluginCommands.put(ChatColor.AQUA + "- " + ChatColor.YELLOW + "/ArmsRace Map SetLocation <Map Name> <Location ID>", "armsrace.commands.map.setlocation");
		this.pluginCommands.put(ChatColor.AQUA + "- " + ChatColor.YELLOW + "/ArmsRace Map DeleteLocation <Map Name> <Location ID>", "armsrace.commands.map.deletelocation");
		this.pluginCommands.put(ChatColor.AQUA + "- " + ChatColor.YELLOW + "/ArmsRace Map ListLocations <Map Name>", "armsrace.commands.map.listlocations");
		this.pluginCommands.put(ChatColor.AQUA + "- " + ChatColor.YELLOW + "/ArmsRace Map TeleportLocation <Map Name> <Location ID>", "armsrace.commands.map.teleportlocation");
		this.pluginCommands.put(ChatColor.AQUA + "- " + ChatColor.YELLOW + "/ArmsRace Map AddPickupLocation <Map Name>", "armsrace.commands.map.addpickuplocation");
		this.pluginCommands.put(ChatColor.AQUA + "- " + ChatColor.YELLOW + "/ArmsRace Map SetPickupLocation <Map Name> <Location ID>", "armsrace.commands.map.setpickuplocation");
		this.pluginCommands.put(ChatColor.AQUA + "- " + ChatColor.YELLOW + "/ArmsRace Map DeletePickupLocation <Map Name> <Location ID>", "armsrace.commands.map.deletepickuplocation");
		this.pluginCommands.put(ChatColor.AQUA + "- " + ChatColor.YELLOW + "/ArmsRace Map ListPickupLocations <Map Name>", "armsrace.commands.map.listpickuplocations");
		this.pluginCommands.put(ChatColor.AQUA + "- " + ChatColor.YELLOW + "/ArmsRace Map TeleportPickupLocation <Map Name> <Location ID>", "armsrace.commands.map.teleportpickuplocation");
		this.pluginCommands.put(ChatColor.AQUA + "- " + ChatColor.YELLOW + "/ArmsRace Join <Map Name>", "");
		this.pluginCommands.put(ChatColor.AQUA + "- " + ChatColor.YELLOW + "/ArmsRace Leave", "");
		this.pluginCommands.put(ChatColor.AQUA + "- " + ChatColor.YELLOW + "/ArmsRace event <Map Name> <Event>", "armsrace.commands.events");
		this.pages = (pluginCommands.size()/commands_per_page)+1;
	}
	
	@Override
	public void onCommand(Main main, Player p, String[] args) {
		if(args.length == 0) {
	        if(!p.hasPermission("armsrace.commands.help")) {
	        	p.sendMessage(main.getMessages().noPermissions());
	        	return;
	        }
	        
			sendPages(p, 0);
		} else {
			try {
		        if(!p.hasPermission("armsrace.commands.help")) {
		        	p.sendMessage(main.getMessages().noPermissions());
		        	return;
		        }
		        
				sendPages(p, Integer.parseInt(args[0]));
			} catch(Exception e) {
				p.sendMessage(main.getMessages().invalidArgument());
			}
		}
	}

	@Override
	public String name() {
		return "help";
	}

	@Override
	public String info() {
		return "ArmsRace help";
	}

	@Override
	public String[] aliases() {
		return new String[] { "help" };
	}
	
	
	/**
	 * Sends a list of commands based on permissions
	 * 
	 * @param p       Receiver of the messages
	 * @param int     Number of pages
	 */
	public void sendPages(Player p, int page) {
		if(page == 0) page = 1;
		int counter = 0;
		int skipper = (page-1)*commands_per_page;
		
		p.sendMessage(ChatColor.AQUA + "======== " + ChatColor.YELLOW + "ArmsRace (Page " + page + "/" + pages + ")" + ChatColor.AQUA + " ========");
		for(String s : pluginCommands.keySet()) {
			if(skipper > 0) {
				skipper = skipper-1;
				continue;
			}
			if(counter < page*commands_per_page) {
				if(p.hasPermission(pluginCommands.get(s)) || s.length() == 0)
				p.sendMessage(s);
				counter++;
			}
		}
	}
}