package com.lielamar.armsrace.utility.packets.version;

import com.lielamar.armsrace.modules.shop.TrailData;
import com.lielamar.armsrace.utility.packets.PacketVersion;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class NMS_v1_13_R1 implements PacketVersion {

	@Override
	public void sendParticle(Player p, String trail, Location loc, TrailData td, int amount) {
		if (td == null)
			p.spawnParticle(Particle.valueOf(trail), loc, amount, 0, 0, 0, 0, null);
		else
			p.spawnParticle(Particle.valueOf(trail), loc, amount, td.getR(), td.getG(), td.getB(), 0, null);
	}

	public void sendTitle(Player p, String title, String subtitle, int fadeInTime, int showTime, int fadeOutTime) {
		p.sendTitle(title, subtitle, fadeInTime, showTime, fadeOutTime);
	}

	public void sendActionBar(Player p, String message) {
		p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
	}
}

