package com.lielamar.armsrace.modules.killeffects.effects;

import com.lielamar.armsrace.modules.killeffects.KillEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.util.Vector;

import com.lielamar.armsrace.Main;

public class TNTKillEffect implements KillEffect {

	@Override	
	public void playKillEffect(Main main, Location loc, Player victim, Player killer) {
		TNTPrimed tnt = (TNTPrimed) loc.getWorld().spawnEntity(loc, EntityType.PRIMED_TNT);
		tnt.setVelocity(new Vector(0, 0.75, 0));
		tnt.setFuseTicks(40);
		main.getKillEffectsManager().addEntity(tnt);
	}
}
