package com.lielamar.armsrace.modules.killeffects;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.lielamar.armsrace.Main;

public interface KillEffect {

	void playKillEffect(Main main, Location loc, Player victim, Player killer);
	
}
