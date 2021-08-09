package com.lielamar.armsrace.commands.impls;

import org.bukkit.entity.Player;

import com.lielamar.armsrace.Main;
import com.lielamar.armsrace.commands.impls.handlers.MapCreationHandler;
import com.lielamar.armsrace.commands.impls.handlers.MapLocationHandler;
import com.lielamar.armsrace.commands.impls.handlers.MapPickupHandler;
import com.lielamar.armsrace.commands.impls.handlers.MapTierHandler;

public class MapCommand extends SubCommand {

	@Override
	public void onCommand(Main main, Player p, String[] args) {
		if(args.length <= 0) {
			p.sendMessage(main.getMessages().invalidSubCommand());
			return;
		}
		
		// ===========================================================================
		// ||                                  Map                                  ||
		// ===========================================================================
		else if(args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("add") ||
				args[0].equalsIgnoreCase("delete") || args[0].equalsIgnoreCase("remove") ||
				args[0].equalsIgnoreCase("list") || args[0].equalsIgnoreCase("maplist")) {
			new MapCreationHandler().handle(main, p, args);
		}
		// =============================================================================
		// ||                                  Tiers                                  ||
		// =============================================================================
		else if(args[0].equalsIgnoreCase("addtier") ||
				args[0].equalsIgnoreCase("settier") ||
				args[0].equalsIgnoreCase("removetier") || args[0].equalsIgnoreCase("deltier") || args[0].equalsIgnoreCase("deletetier") ||
				args[0].equalsIgnoreCase("listtier") || args[0].equalsIgnoreCase("tierlist") ||
				args[0].equalsIgnoreCase("loadtier") || args[0].equalsIgnoreCase("tierload")) {
			new MapTierHandler().handle(main, p, args);
		}
		
		// =================================================================================
		// ||                                  Locations                                  ||
		// =================================================================================
		else if(args[0].equalsIgnoreCase("addlocation") ||
				args[0].equalsIgnoreCase("setlocation") ||
				args[0].equalsIgnoreCase("removelocation") || args[0].equalsIgnoreCase("dellocation") || args[0].equalsIgnoreCase("deletelocation") ||
				args[0].equalsIgnoreCase("listlocations") || args[0].equalsIgnoreCase("listlocation") || args[0].equalsIgnoreCase("locationlist") || args[0].equalsIgnoreCase("loclist") ||
				args[0].equalsIgnoreCase("teleportlocation") || args[0].equalsIgnoreCase("locationteleport") || args[0].equalsIgnoreCase("tploc") || args[0].equalsIgnoreCase("loctp")) {
			new MapLocationHandler().handle(main, p, args);
		}
		
		// ==============================================================================
		// ||                                  Pickup                                  ||
		// ==============================================================================
		else if(args[0].equalsIgnoreCase("addpickuplocation") ||
				args[0].equalsIgnoreCase("setpickuplocation") ||
				args[0].equalsIgnoreCase("removepickuplocation") || args[0].equalsIgnoreCase("delpickuplocation") || args[0].equalsIgnoreCase("deletepickuplocation") ||
				args[0].equalsIgnoreCase("listpickuplocations") || args[0].equalsIgnoreCase("listpickuplocation") || args[0].equalsIgnoreCase("pickuplocationlist") || args[0].equalsIgnoreCase("pickuploclist") ||
				args[0].equalsIgnoreCase("teleportpickuplocation") || args[0].equalsIgnoreCase("pickuplocationteleport") || args[0].equalsIgnoreCase("tppickuploc") || args[0].equalsIgnoreCase("pickuploctp")) {
			new MapPickupHandler().handle(main, p, args);
		}
		
		// =============================================================================
		// ||                                  Error                                  ||
		// =============================================================================
		else {
			p.sendMessage(main.getMessages().invalidSubCommand());
			return;
		}
	}

	@Override
	public String name() {
		return "map";
	}

	@Override
	public String info() {
		return "ArmsRace map";
	}

	@Override
	public String[] aliases() {
		return new String[] { "maps" };
	}
}