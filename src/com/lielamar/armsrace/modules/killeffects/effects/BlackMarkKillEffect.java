package com.lielamar.armsrace.modules.killeffects.effects;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.lielamar.armsrace.Main;
import com.lielamar.armsrace.modules.killeffects.KillEffect;
import com.lielamar.armsrace.modules.shop.TrailData;

public class BlackMarkKillEffect implements KillEffect {

	@Override	
	public void playKillEffect(Main main, Location loc, Player victim, Player killer) {
		Random rnd = new Random();
		
		loc.getWorld().playSound(loc, Sound.EXPLODE, 1.0F, 4.0F);
		TrailData td = new TrailData(0, 0, 0);
		
		Location loc1, loc2, loc3, loc4, loc5;
		loc1 = new Location(loc.getWorld(), loc.getX()+rnd.nextDouble(), loc.getY()+1+rnd.nextDouble(), loc.getZ()+rnd.nextDouble());
		loc2 = new Location(loc.getWorld(), loc.getX()+rnd.nextDouble(), loc.getY()+1+rnd.nextDouble(), loc.getZ()+rnd.nextDouble());
		loc3 = new Location(loc.getWorld(), loc.getX()+rnd.nextDouble(), loc.getY()+1+rnd.nextDouble(), loc.getZ()+rnd.nextDouble());
		loc4 = new Location(loc.getWorld(), loc.getX()+rnd.nextDouble(), loc.getY()+1+rnd.nextDouble(), loc.getZ()+rnd.nextDouble());
		loc5 = new Location(loc.getWorld(), loc.getX()+rnd.nextDouble(), loc.getY()+1+rnd.nextDouble(), loc.getZ()+rnd.nextDouble());
		for(Player pl : Bukkit.getOnlinePlayers()) {
			main.getPacketHandler().getNMSHandler().sendParticle(pl, "SMOKE_LARGE", loc1, td, 1);
			main.getPacketHandler().getNMSHandler().sendParticle(pl, "SMOKE_LARGE", loc2, td, 1);
			main.getPacketHandler().getNMSHandler().sendParticle(pl, "SMOKE_LARGE", loc3, td, 1);
			main.getPacketHandler().getNMSHandler().sendParticle(pl, "SMOKE_LARGE", loc4, td, 1);
			main.getPacketHandler().getNMSHandler().sendParticle(pl, "SMOKE_LARGE", loc5, td, 1);
		}
	}
}
