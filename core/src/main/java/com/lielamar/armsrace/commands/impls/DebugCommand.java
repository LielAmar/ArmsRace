package com.lielamar.armsrace.commands.impls;

import org.bukkit.entity.Player;

import com.lielamar.armsrace.Main;
import com.lielamar.armsrace.modules.CustomPlayer;

public class DebugCommand extends SubCommand {

	@Override
	public void onCommand(Main main, Player p, String[] args) {
		if (args.length < 1) {
			if (!p.hasPermission("armsrace.commands.debug")) {
				p.sendMessage(main.getMessages().noPermissions());
				return;
			}

			p.sendMessage(main.getMessages().invalidSubCommand());
		} else {
			if (args[0].equalsIgnoreCase("locations")) {
				for (int i = 0; i < main.getGameManager().getMapManager().getMap(args[1].toLowerCase()).getLocations().size(); i++)
					p.sendMessage("[ArmsRace Debug] i: " + i + ", " + main.getGameManager().getMapManager().getMap(args[1].toLowerCase()).getLocations().toString());
				return;
			} else if (args[0].equalsIgnoreCase("tiers")) {
				for (int i = 0; i < main.getGameManager().getMapManager().getMap(args[1].toLowerCase()).getTiers().length; i++)
					p.sendMessage("[ArmsRace Debug] i: " + i + ", " + main.getGameManager().getMapManager().getMap(args[1].toLowerCase()).getTiers()[i].toString());
				return;
			} else if (args[0].equalsIgnoreCase("pickuplocations")) {
				for (int i = 0; i < main.getGameManager().getMapManager().getMap(args[1].toLowerCase()).getPickupLocations().size(); i++)
					p.sendMessage("[ArmsRace Debug] i: " + i + ", " + main.getGameManager().getMapManager().getMap(args[1].toLowerCase()).getPickupLocations().toString());
				return;
			} else if (args[0].equalsIgnoreCase("shops")) {
				p.openInventory(main.getShopManager().getShop(args[1].toLowerCase()).getInventory());
			} else if (args[0].equalsIgnoreCase("spawnpickup")) {
				CustomPlayer cp = main.getPlayerManager().getPlayer(p);
				cp.getCurrentMap().addPickup();
			} else if (args[0].equalsIgnoreCase("killeffect")) {
				main.getKillEffectsManager().getKillEffect(args[1].toUpperCase()).playKillEffect(main, p.getLocation(), p, p);
			}
		}
	}

	@Override
	public String name() {
		return "debug";
	}

	@Override
	public String info() {
		return "ArmsRace debug";
	}

	@Override
	public String[] aliases() {
		return new String[]{"dbug"};
	}
}
