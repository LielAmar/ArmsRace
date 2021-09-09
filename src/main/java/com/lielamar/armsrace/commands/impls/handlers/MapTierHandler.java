package com.lielamar.armsrace.commands.impls.handlers;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.lielamar.armsrace.Main;
import com.lielamar.armsrace.managers.files.MapFile;
import com.lielamar.armsrace.modules.map.Map;

public class MapTierHandler {

	/**
	 * Handles adding, setting, deleting, listing and loading maps' tiers
	 *
	 * @param main Instance of {@link Main}
	 * @param p    Command sender
	 * @param args Command arguments
	 */
	public void handle(Main main, Player p, String[] args) {
		if (args[0].equalsIgnoreCase("addtier")) {

			if (!p.hasPermission("armsrace.commands.map.addtier")) {
				p.sendMessage(main.getMessages().noPermissions());
				return;
			}

			if (args.length == 1) {
				p.sendMessage(main.getMessages().invalidSubCommand());
				return;
			}

			String name = args[1].toLowerCase();

			boolean exists = main.getMapsFileManager().existsMap(name);
			if (!exists) {
				p.sendMessage(main.getMessages().couldntFindMap(name));
				return;
			}

			MapFile mapFile = main.getMapsFileManager().getMap(name);

			int nextTier = 0;
			if (mapFile.getConfig().contains("Tiers"))
				nextTier = mapFile.getConfig().getConfigurationSection("Tiers").getKeys(false).size();
			mapFile.addTier(p.getInventory().getArmorContents(), p.getInventory().getContents(), nextTier);


			p.sendMessage(ChatColor.GREEN + "Saved your current layout as tier number " + nextTier + " for map " + name + "!");
			return;
		}

		if (args[0].equalsIgnoreCase("settier")) {

			if (!p.hasPermission("armsrace.commands.map.settier")) {
				p.sendMessage(main.getMessages().noPermissions());
				return;
			}

			if (args.length == 1 || args.length == 2) {
				p.sendMessage(main.getMessages().invalidSubCommand());
				return;
			}

			String name = args[1].toLowerCase();

			boolean exists = main.getMapsFileManager().existsMap(name);
			if (!exists) {
				p.sendMessage(main.getMessages().couldntFindMap(name));
				return;
			}

			MapFile mapFile = main.getMapsFileManager().getMap(name);

			try {
				int tier = Integer.parseInt(args[2]);

				mapFile.setTier(p.getInventory().getArmorContents(), p.getInventory().getContents(), tier);

				p.sendMessage(ChatColor.GREEN + "Saved your current layout as tier number " + tier + " for map " + name + "!");
				return;
			} catch (Exception e) {
				p.sendMessage(main.getMessages().invalidArgument());
				return;
			}
		}

		if (args[0].equalsIgnoreCase("removetier") || args[0].equalsIgnoreCase("deltier") || args[0].equalsIgnoreCase("deletetier")) {

			if (!p.hasPermission("armsrace.commands.map.deletetier")) {
				p.sendMessage(main.getMessages().noPermissions());
				return;
			}

			if (args.length == 1 || args.length == 2) {
				p.sendMessage(main.getMessages().invalidSubCommand());
				return;
			}

			String name = args[1].toLowerCase();

			boolean exists = main.getMapsFileManager().existsMap(name);
			if (!exists) {
				p.sendMessage(main.getMessages().couldntFindMap(name));
				return;
			}

			MapFile mapFile = main.getMapsFileManager().getMap(name);

			try {
				int tier = Integer.parseInt(args[2]);

				mapFile.removeTier(tier);

				p.sendMessage(ChatColor.RED + "Deleted tier number " + tier + " for map " + name + "!");
				return;
			} catch (Exception e) {
				p.sendMessage(main.getMessages().invalidArgument());
				return;
			}
		}

		if (args[0].equalsIgnoreCase("listtier") || args[0].equalsIgnoreCase("tierlist")) {

			if (!p.hasPermission("armsrace.commands.map.listtier")) {
				p.sendMessage(main.getMessages().noPermissions());
				return;
			}

			if (args.length == 1) {
				p.sendMessage(main.getMessages().invalidSubCommand());
				return;
			}

			String name = args[1].toLowerCase();

			boolean exists = main.getMapsFileManager().existsMap(name);
			if (!exists) {
				p.sendMessage(main.getMessages().couldntFindMap(name));
				return;
			}

			Map map = main.getGameManager().getMapManager().getMap(name);
			for (int i = 0; i < map.getTiers().length; i++) {
				p.sendMessage(ChatColor.AQUA + "" + i + ": " + ChatColor.YELLOW + map.getTiers()[i].getContent().toString());
			}
			return;
		}

		if (args[0].equalsIgnoreCase("loadtier") || args[0].equalsIgnoreCase("tierload")) {

			if (!p.hasPermission("armsrace.commands.map.loadtier")) {
				p.sendMessage(main.getMessages().noPermissions());
				return;
			}

			if (args.length == 1 || args.length == 2) {
				p.sendMessage(main.getMessages().invalidSubCommand());
				return;
			}

			String name = args[1].toLowerCase();

			boolean exists = main.getMapsFileManager().existsMap(name);
			if (!exists) {
				p.sendMessage(main.getMessages().couldntFindMap(name));
				return;
			}

			try {
				int tier = Integer.parseInt(args[2]);

				Map map = main.getGameManager().getMapManager().getMap(name);
				p.getInventory().setContents(map.getTiers()[tier].getContent());
				p.getInventory().setArmorContents(map.getTiers()[tier].getArmor());
				p.sendMessage(ChatColor.GREEN + "Loading tier " + tier + " of map " + name + "!");
				return;
			} catch (Exception e) {
				p.sendMessage(main.getMessages().invalidArgument());
				return;
			}
		}
	}
}
