package com.lielamar.armsrace.commands.impls.handlers;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.lielamar.armsrace.Main;
import com.lielamar.armsrace.managers.files.MapFile;
import com.lielamar.armsrace.modules.CustomLocation;
import com.lielamar.armsrace.modules.map.Map;

public class MapLocationHandler {

	/**
	 * Handles adding, setting, deleting, and listing maps' locations
	 *
	 * @param main Instance of {@link Main}
	 * @param p    Command sender
	 * @param args Command arguments
	 */
	public void handle(Main main, Player p, String[] args) {
		if (args[0].equalsIgnoreCase("addlocation")) {

			if (!p.hasPermission("armsrace.commands.map.addlocation")) {
				p.sendMessage(main.getMessages().noPermissions());
				return;
			}

			if (args.length == 1) {
				p.sendMessage(main.getMessages().invalidSubCommand());
				return;
			}

			String name = args[1];

			boolean exists = main.getMapsFileManager().existsMap(name);
			if (!exists) {
				p.sendMessage(main.getMessages().couldntFindMap(name));
				return;
			}

			MapFile mapFile = main.getMapsFileManager().getMap(name);

			int id = 0;
			if (mapFile.getConfig().contains("SpawnLocations"))
				id = mapFile.getConfig().getConfigurationSection("SpawnLocations").getKeys(false).size();
			mapFile.addLocation(id, p.getLocation());

			p.sendMessage(ChatColor.GREEN + "Saved your current location as location number " + id + " for map " + name + "!");
			return;
		}

		if (args[0].equalsIgnoreCase("setlocation")) {

			if (!p.hasPermission("armsrace.commands.map.setlocation")) {
				p.sendMessage(main.getMessages().noPermissions());
				return;
			}

			if (args.length == 1 || args.length == 2) {
				p.sendMessage(main.getMessages().invalidSubCommand());
				return;
			}

			String name = args[1];

			boolean exists = main.getMapsFileManager().existsMap(name);
			if (!exists) {
				p.sendMessage(main.getMessages().couldntFindMap(name));
				return;
			}

			MapFile mapFile = main.getMapsFileManager().getMap(name);

			try {
				int id = Integer.parseInt(args[2]);

				mapFile.setLocation(id, p.getLocation());

				p.sendMessage(ChatColor.GREEN + "Saved your current location as location number " + id + " for map " + name + "!");
				return;
			} catch (Exception e) {
				p.sendMessage(main.getMessages().invalidArgument());
				return;
			}
		}

		if (args[0].equalsIgnoreCase("removelocation") || args[0].equalsIgnoreCase("dellocation") || args[0].equalsIgnoreCase("deletelocation")) {

			if (!p.hasPermission("armsrace.commands.map.deletelocation")) {
				p.sendMessage(main.getMessages().noPermissions());
				return;
			}

			if (args.length == 1 || args.length == 2) {
				p.sendMessage(main.getMessages().invalidSubCommand());
				return;
			}

			String name = args[1];

			boolean exists = main.getMapsFileManager().existsMap(name);
			if (!exists) {
				p.sendMessage(main.getMessages().couldntFindMap(name));
				return;
			}

			MapFile mapFile = main.getMapsFileManager().getMap(name);

			try {
				int tier = Integer.parseInt(args[2]);

				mapFile.removeLocation(tier);

				p.sendMessage(ChatColor.RED + "Deleted location number " + tier + " for map " + name + "!");
				return;
			} catch (Exception e) {
				p.sendMessage(main.getMessages().invalidArgument());
				return;
			}
		}

		if (args[0].equalsIgnoreCase("listlocations") || args[0].equalsIgnoreCase("listlocation") || args[0].equalsIgnoreCase("locationlist") || args[0].equalsIgnoreCase("loclist") || args[0].equalsIgnoreCase("listloc")) {

			if (!p.hasPermission("armsrace.commands.map.listlocations")) {
				p.sendMessage(main.getMessages().noPermissions());
				return;
			}

			if (args.length == 1) {
				p.sendMessage(main.getMessages().invalidSubCommand());
				return;
			}

			String name = args[1];

			boolean exists = main.getMapsFileManager().existsMap(name);
			if (!exists) {
				p.sendMessage(main.getMessages().couldntFindMap(name));
				return;
			}

			Map map = main.getGameManager().getMapManager().getMap(name);
			for (CustomLocation cl : map.getLocations()) {
				p.sendMessage(ChatColor.AQUA + "" + cl.getId() + ": " + ChatColor.YELLOW + "World: " + cl.getLocation().getWorld().getName().toString() + ", X:"
						+ cl.getLocation().getX() + ", Y:"
						+ cl.getLocation().getY() + ", Z: "
						+ cl.getLocation().getZ() + ", Yaw: "
						+ cl.getLocation().getYaw() + ", Pitch: "
						+ cl.getLocation().getPitch() + "");
			}
			return;
		}

		if (args[0].equalsIgnoreCase("teleportlocation") || args[0].equalsIgnoreCase("locationteleport") || args[0].equalsIgnoreCase("tploc") || args[0].equalsIgnoreCase("loctp")) {

			if (!p.hasPermission("armsrace.commands.map.teleportlocation")) {
				p.sendMessage(main.getMessages().noPermissions());
				return;
			}

			if (args.length == 1 || args.length == 2) {
				p.sendMessage(main.getMessages().invalidSubCommand());
				return;
			}

			String name = args[1];

			boolean exists = main.getMapsFileManager().existsMap(name);
			if (!exists) {
				p.sendMessage(main.getMessages().couldntFindMap(name));
				return;
			}

			try {
				int id = Integer.parseInt(args[2]);

				Map map = main.getGameManager().getMapManager().getMap(name);

				CustomLocation cl = map.getLocation(id);
				if (cl == null) {
					p.sendMessage(main.getMessages().invalidArgument());
					return;
				}

				p.teleport(cl.getLocation());
				p.sendMessage(ChatColor.GREEN + "Teleporting to location number " + id + " of map " + name + "!");
			} catch (Exception e) {
				p.sendMessage(main.getMessages().invalidArgument());
			}
		}
	}
}
