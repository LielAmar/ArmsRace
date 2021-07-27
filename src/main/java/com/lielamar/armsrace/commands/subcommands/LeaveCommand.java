package com.lielamar.armsrace.commands.subcommands;

import org.bukkit.entity.Player;

import com.lielamar.armsrace.Main;
import com.lielamar.armsrace.listeners.custom.LeaveReason;
import com.lielamar.armsrace.modules.map.Map;

public class LeaveCommand extends SubCommand {

	@Override
	public void onCommand(Main main, Player p, String[] args) {
		if(!p.hasPermission("armsrace.commands.leave")) {
			p.sendMessage(main.getMessages().noPermissions());
			return;
		}

		Map map = main.getPlayerManager().getPlayer(p).getCurrentMap();
		if(map == null) {
			p.sendMessage(main.getMessages().youAreNotInAMap());
			return;
		}
		map.removePlayer(p, LeaveReason.LEAVE_COMMAND);
		return;
	}

	@Override
	public String name() {
		return "leave";
	}

	@Override
	public String info() {
		return "ArmsRace leave";
	}

	@Override
	public String[] aliases() {
		return new String[] { "l", "quit", "q" };
	}
}