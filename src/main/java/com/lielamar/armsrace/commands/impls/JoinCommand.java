package com.lielamar.armsrace.commands.impls;

import org.bukkit.entity.Player;

import com.lielamar.armsrace.Main;
import com.lielamar.armsrace.modules.map.Map;

public class JoinCommand extends SubCommand {

	@Override
	public void onCommand(Main main, Player p, String[] args) {
		if (args.length == 0) {
			p.sendMessage(main.getMessages().invalidArgument());
		} else {
			String mapName = args[0];

			Map map = main.getGameManager().getMapManager().getMap(mapName);
			if (map == null) {
				p.sendMessage(main.getMessages().couldntFindMap(mapName));
				return;
			}

			if (!p.hasPermission("armsrace.commands.join.*") && !p.hasPermission("armsrace.commands.join." + map.getName())) {
				p.sendMessage(main.getMessages().noPermissions());
				return;
			}

			map.addPlayer(p);
		}
	}

	@Override
	public String name() {
		return "join";
	}

	@Override
	public String info() {
		return "ArmsRace join";
	}

	@Override
	public String[] aliases() {
		return new String[]{"j"};
	}
}