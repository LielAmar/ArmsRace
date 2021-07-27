package com.lielamar.armsrace.utility.packets;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.lielamar.armsrace.modules.shop.TrailData;

public interface PacketVersion {

	void sendParticle(Player p, String trail, Location loc, TrailData td, int amount);

	void sendTitle(Player p, String title, String subtitle, int fadeInTime, int showTime, int fadeOutTime);

	void sendActionBar(Player p, String message);
}
