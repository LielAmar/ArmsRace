package com.lielamar.armsrace.commands.subcommands;

import org.bukkit.entity.Player;

import com.lielamar.armsrace.Main;

public class SetSpawnCommand extends SubCommand{

	@Override
	public void onCommand(Main main, Player p, String[] args) {
		if(!p.hasPermission("armsrace.commands.setspawn")) {
			p.sendMessage(main.getMessages().noPermissions());
	        return;
		}
		
		main.getSettingsManager().setSpawn(p.getLocation());
		main.getConfig().set("Spawn.x", p.getLocation().getX());
		main.getConfig().set("Spawn.y", p.getLocation().getY());
		main.getConfig().set("Spawn.z", p.getLocation().getZ());
		main.getConfig().set("Spawn.yaw", p.getLocation().getYaw());
		main.getConfig().set("Spawn.pitch", p.getLocation().getPitch());
		main.getConfig().set("Spawn.world", p.getLocation().getWorld().getName());
		main.saveConfig();
		p.sendMessage(main.getMessages().spawnSet(p.getLocation()));
		return;
	}
	
	@Override
	public String name() {
		return "setspawn";
	}

	@Override
	public String info() {
		return "ArmsRace setspawn";
	}

	@Override
	public String[] aliases() {
		return new String[] { "setspawn" };
	}
}