package com.lielamar.armsrace.commands;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.lielamar.armsrace.Main;
import com.lielamar.armsrace.listeners.custom.LeaveReason;
import com.lielamar.armsrace.modules.CustomPlayer;
import com.lielamar.armsrace.utils.Utils;

public class Spawn implements CommandExecutor {

	private Main main;
	
	public Spawn(Main main) {
		this.main = main;
	}
	
	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String cmdLabel, String[] args) {
		if(!(cs instanceof Player)) {
			cs.sendMessage(main.getMessages().mustBePlayer());
			return true;
		}
		
		Player p = (Player)cs;
		
		if(cmd.getName().equalsIgnoreCase("spawn")) {
			if(main.getSettingsManager().getSpawn() == null) return true;
			
			if(!cs.hasPermission("armsrace.commands.spawn")) {
				cs.sendMessage(main.getMessages().noPermissions());
				return true;
			}
			
			if(main.getCombatLogManager().isCombatLog(p)) {
				p.sendMessage(main.getMessages().youAreStillInCombat(main.getCombatLogManager().getDuration()-main.getCombatLogManager().getCombatLog(p)));
				return true;
			}
			
			p.sendMessage(main.getMessages().teleportingToSpawn());
			CustomPlayer cp = main.getPlayerManager().getPlayer(p);
			if(cp.getCurrentMap() != null)
				cp.getCurrentMap().removePlayer(p, LeaveReason.SPAWN);
			else {
				cp.getPlayer().teleport(this.main.getSettingsManager().getSpawn());
				Utils.clearPlayer(main, p, 20, 20, 20, GameMode.ADVENTURE, 0);
			}
			return true;
		}
		return true;
	}
}
