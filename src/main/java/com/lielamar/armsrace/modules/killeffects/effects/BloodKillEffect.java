package com.lielamar.armsrace.modules.killeffects.effects;

import java.util.Random;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.lielamar.armsrace.Main;
import com.lielamar.armsrace.modules.killeffects.KillEffect;

public class BloodKillEffect implements KillEffect {

	@Override
	public void playKillEffect(Main main, Location loc, Player victim, Player p) {
		Random rnd = new Random();
		Location loc1 = new Location(loc.getWorld(), loc.getX()+rnd.nextDouble(), loc.getY()+1, loc.getZ()+rnd.nextDouble());
		Location loc2 = new Location(loc.getWorld(), loc.getX()+rnd.nextDouble(), loc.getY()+1, loc.getZ()+rnd.nextDouble());
		Location loc3 = new Location(loc.getWorld(), loc.getX()+rnd.nextDouble(), loc.getY()+1, loc.getZ()+rnd.nextDouble());

		loc.getWorld().playEffect(loc1, Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
		loc.getWorld().playEffect(loc2, Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
		loc.getWorld().playEffect(loc3, Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
	}
}
