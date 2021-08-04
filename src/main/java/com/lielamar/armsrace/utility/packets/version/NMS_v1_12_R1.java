package com.lielamar.armsrace.utility.packets.version;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.lielamar.armsrace.modules.shop.TrailData;
import com.lielamar.armsrace.utility.packets.PacketVersion;

import net.minecraft.server.v1_12_R1.ChatMessageType;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle;

public class NMS_v1_12_R1 implements PacketVersion {

	@Override
	public void sendParticle(Player p, String trail, Location loc, TrailData td, int amount) {
		try {
			if(td == null)
				p.spawnParticle(Particle.valueOf(trail), loc, amount, 0, 0, 0, 0, null);
			else
				p.spawnParticle(Particle.valueOf(trail), loc, amount, td.getR(), td.getG(), td.getB(), 0, null);
		} catch(IllegalArgumentException ignored) {
			// Particle not found in enum
		}
	}
	
	public void sendTitle(Player p, String title, String subtitle, int fadeInTime, int showTime, int fadeOutTime) {
		IChatBaseComponent icbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}");
		PacketPlayOutTitle packetTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, icbc, fadeInTime, showTime, fadeOutTime);
		icbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");
		PacketPlayOutTitle packetSubtitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, icbc, fadeInTime, showTime, fadeOutTime);
		
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packetTitle);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packetSubtitle);
	}
	
	public void sendActionBar(Player p, String message) {
		IChatBaseComponent icbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}");
		PacketPlayOutChat packet = new PacketPlayOutChat(icbc, ChatMessageType.GAME_INFO);
		
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
	}
}
