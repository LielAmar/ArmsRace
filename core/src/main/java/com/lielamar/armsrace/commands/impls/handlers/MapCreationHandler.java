package com.lielamar.armsrace.commands.impls.handlers;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.lielamar.armsrace.Main;
import com.lielamar.armsrace.managers.files.MapFile;
import com.lielamar.armsrace.modules.CustomPlayer;

public class MapCreationHandler {

	/**
	 * Handles creating, deleting and listing maps
	 *
	 * @param main Instance of {@link Main}
	 * @param p    Command sender
	 * @param args Command arguments
	 */
	public void handle(Main main, Player p, String[] args) {
		if (args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("add")) {

			if (!p.hasPermission("armsrace.commands.map.create")) {
				p.sendMessage(main.getMessages().noPermissions());
				return;
			}

			if (args.length == 1) {
				p.sendMessage(main.getMessages().invalidSubCommand());
				return;
			}

			String name = args[1];

			boolean exists = main.getMapsFileManager().existsMap(name);
			if (exists) {
				p.sendMessage(ChatColor.RED + "Map by the name " + name + " already exists!");
				return;
			}

			MapFile mapFile = main.getMapsFileManager().addMap(name);
			main.getGameManager().getMapManager().loadMap(mapFile);

			mapFile.addLocation(0, p.getLocation());

			p.sendMessage(ChatColor.GREEN + "Created new map named " + name);
			return;
		}

		if (args[0].equalsIgnoreCase("delete") || args[0].equalsIgnoreCase("remove")) {

			if (!p.hasPermission("armsrace.commands.map.delete")) {
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
				p.sendMessage(ChatColor.RED + "Couldn't find a map by the name " + name + "!");
				return;
			}

			for (CustomPlayer cp : main.getGameManager().getMapManager().getMap(name).getPlayers()) {
				if (cp == null) continue;
				cp.setCurrentMap(null);
				cp.setCurrentTier(null);
				cp.setCurrentTierId(-1);
				cp.getPlayer().setHealth(0);
				cp.getPlayer().spigot().respawn();
				cp.getPlayer().sendMessage(main.getMessages().youWereRespawned());
			}

			main.getMapsFileManager().deleteMap(name);
			main.getGameManager().getMapManager().unloadMap(name);

			p.sendMessage(ChatColor.RED + "Deleted map named " + name);
			return;
		}

		if (args[0].equalsIgnoreCase("list") || args[0].equalsIgnoreCase("maplist")) {

			if (!p.hasPermission("armsrace.commands.map.list")) {
				p.sendMessage(main.getMessages().noPermissions());
				return;
			}

			int amount = main.getGameManager().getMapManager().getMaps().size(); // 1 2 3 4 5 6 7 8 9 10
			int pages = (amount / 10) + 1;
			int page = 1;

			if (args.length != 1) {
				try {
					page = Integer.parseInt(args[1]);
				} catch (Exception e) {
					p.sendMessage(main.getMessages().invalidArgument());
					return;
				}
			}
			p.sendMessage(ChatColor.AQUA + "======== " + ChatColor.YELLOW + "Map List (" + page + "/" + pages + ")" + ChatColor.AQUA + " ========");

			int skipper = 0;
			for (String map : main.getGameManager().getMapManager().getMaps().keySet()) {
				if (skipper >= (page - 1) * 10 && skipper < page * 10)
					p.sendMessage(ChatColor.AQUA + "- " + ChatColor.YELLOW + map.substring(0, map.length() - 4));
				skipper++;
			}

			return;
		}
	}
}
