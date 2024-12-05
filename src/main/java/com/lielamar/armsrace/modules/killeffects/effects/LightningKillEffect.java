package com.lielamar.armsrace.modules.killeffects.effects;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.lielamar.armsrace.Main;
import com.lielamar.armsrace.modules.killeffects.KillEffect;

public class LightningKillEffect implements KillEffect {

	@Override
	public void playKillEffect(Main main, Location loc, Player victim, Player killer) {
		loc.getWorld().strikeLightningEffect(loc);
	}
}
