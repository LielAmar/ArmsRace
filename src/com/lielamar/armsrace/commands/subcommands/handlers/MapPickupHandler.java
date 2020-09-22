package com.lielamar.armsrace.commands.subcommands.handlers;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.lielamar.armsrace.Main;
import com.lielamar.armsrace.managers.files.MapFile;
import com.lielamar.armsrace.modules.CustomLocation;
import com.lielamar.armsrace.modules.map.Map;

public class MapPickupHandler {

	/**
	 * Handles adding, setting, deleting, and listing maps' pickups locations
	 * 
	 * @param main      Instance of {@link com.lielamar.armsrace.Main}
	 * @param p         Command sender
	 * @param args      Command arguments
	 */
	public void handle(Main main, Player p, String[] args) {
		if(args[0].equalsIgnoreCase("addpickuplocation")) {
			
	        if(!p.hasPermission("armsrace.commands.map.addpickuplocation")) {
	        	p.sendMessage(main.getMessages().noPermissions());
	        	return;
	        }
			
			if(args.length == 1) {
				p.sendMessage(main.getMessages().invalidSubCommand());
				return;
			}
			
			String name = args[1].toLowerCase();
			
			boolean exists = main.getMapsFileManager().existsMap(name);
			if(!exists) {
				p.sendMessage(main.getMessages().couldntFindMap(name));
				return;
			}
			
			MapFile mapFile = main.getMapsFileManager().getMap(name);
			
			int id = 0;
			if(mapFile.getConfig().contains("PickupLocations")) id = mapFile.getConfig().getConfigurationSection("PickupLocations").getKeys(false).size();
			mapFile.addPickupLocation(id, p.getLocation());
			
			p.sendMessage(ChatColor.GREEN + "Saved your current location as pickup location number " + id + " for map " + name + "!");
			return;
		}
		
		if(args[0].equalsIgnoreCase("setpickuplocation")) {
			
	        if(!p.hasPermission("armsrace.commands.map.setpickuplocation")) {
	        	p.sendMessage(main.getMessages().noPermissions());
	        	return;
	        }
			
			if(args.length == 1 || args.length == 2) {
				p.sendMessage(main.getMessages().invalidSubCommand());
				return;
			}
			
			String name = args[1].toLowerCase();
			
			boolean exists = main.getMapsFileManager().existsMap(name);
			if(!exists) {
				p.sendMessage(main.getMessages().couldntFindMap(name));
				return;
			}
			
			MapFile mapFile = main.getMapsFileManager().getMap(name);

			try {
				int id = Integer.parseInt(args[2]);
				
				mapFile.setPickupLocation(id, p.getLocation());
				
				p.sendMessage(ChatColor.GREEN + "Saved your current location as pickup location number " + id + " for map " + name + "!");
				return;
			} catch(Exception e) {
				p.sendMessage(main.getMessages().invalidArgument());
				return;
			}
		}
		
		if(args[0].equalsIgnoreCase("removepickuplocation") || args[0].equalsIgnoreCase("delpickuplocation") || args[0].equalsIgnoreCase("deletepickuplocation")) {
			
	        if(!p.hasPermission("armsrace.commands.map.deletepickuplocation")) {
	        	p.sendMessage(main.getMessages().noPermissions());
	        	return;
	        }
			
			if(args.length == 1 || args.length == 2) {
				p.sendMessage(main.getMessages().invalidSubCommand());
				return;
			}
			
			String name = args[1].toLowerCase();
			
			boolean exists = main.getMapsFileManager().existsMap(name);
			if(!exists) {
				p.sendMessage(main.getMessages().couldntFindMap(name));
				return;
			}
			
			MapFile mapFile = main.getMapsFileManager().getMap(name);

			try {
				int tier = Integer.parseInt(args[2]);
				
				mapFile.removePickupLocation(tier);
				
				p.sendMessage(ChatColor.RED + "Deleted pickup location number " + tier + " for map " + name + "!");
				return;
			} catch(Exception e) {
				p.sendMessage(main.getMessages().invalidArgument());
				return;
			}
		}
		
		if(args[0].equalsIgnoreCase("listpickuplocations") || args[0].equalsIgnoreCase("listpickuplocation") ||
				args[0].equalsIgnoreCase("pickuplocationlist") || args[0].equalsIgnoreCase("pickuploclist") || args[0].equalsIgnoreCase("listpickuploc")) {
			
	        if(!p.hasPermission("armsrace.commands.map.listpickuplocations")) {
	        	p.sendMessage(main.getMessages().noPermissions());
	        	return;
	        }
			
			if(args.length == 1) {
				p.sendMessage(main.getMessages().invalidSubCommand());
				return;
			}
			
			String name = args[1].toLowerCase();
			
			boolean exists = main.getMapsFileManager().existsMap(name);
			if(!exists) {
				p.sendMessage(main.getMessages().couldntFindMap(name));
				return;
			}

			Map map = main.getGameManager().getMapManager().getMap(name);
			for(CustomLocation cl : map.getPickupLocations()) {
				p.sendMessage(ChatColor.AQUA + "" + cl.getId() + ": " + ChatColor.YELLOW + "World: " + cl.getLoc().getWorld().getName().toString() + ", X:"
						+ cl.getLoc().getX() + ", Y:"
						+ cl.getLoc().getY() + ", Z: "
						+ cl.getLoc().getZ() + ", Yaw: "
						+ cl.getLoc().getYaw() + ", Pitch: "
						+ cl.getLoc().getPitch() + "");
			}
			return;
		}
		
		if(args[0].equalsIgnoreCase("teleportpickuplocation") || args[0].equalsIgnoreCase("pickuplocationteleport") ||
				args[0].equalsIgnoreCase("tppickuploc") || args[0].equalsIgnoreCase("pickuploctp")) {
			
	        if(!p.hasPermission("armsrace.commands.map.teleportpickuplocation")) {
	        	p.sendMessage(main.getMessages().noPermissions());
	        	return;
	        }
			
	        if(args.length == 1 || args.length == 2) {
				p.sendMessage(main.getMessages().invalidSubCommand());
				return;
			}
			
			String name = args[1].toLowerCase();
			
			boolean exists = main.getMapsFileManager().existsMap(name);
			if(!exists) {
				p.sendMessage(main.getMessages().couldntFindMap(name));
				return;
			}
			
			try {
				int id = Integer.parseInt(args[2]);
				
				Map map = main.getGameManager().getMapManager().getMap(name);
				
				CustomLocation cl = map.getPickupLocation(id);
				if(cl == null) {
					p.sendMessage(main.getMessages().invalidArgument());
					return;
				}
				
				p.teleport(cl.getLoc());
				p.sendMessage(ChatColor.GREEN + "Teleporting to pickup location number " + id + " of map " + name + "!");
				return;
			} catch(Exception e) {
				p.sendMessage(main.getMessages().invalidArgument());
				return;
			}
		}
	}
}
