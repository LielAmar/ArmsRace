package com.lielamar.armsrace.utils.packets.version;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.lielamar.armsrace.modules.shop.TrailData;
import com.lielamar.armsrace.utils.packets.PacketVersion;

import net.minecraft.server.v1_8_R1.ChatSerializer;
import net.minecraft.server.v1_8_R1.EnumParticle;
import net.minecraft.server.v1_8_R1.EnumTitleAction;
import net.minecraft.server.v1_8_R1.IChatBaseComponent;
import net.minecraft.server.v1_8_R1.PacketPlayOutChat;
import net.minecraft.server.v1_8_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R1.PacketPlayOutWorldParticles;

public class NMS_v1_8_R1 implements PacketVersion {
	
	@Override
	public void sendParticle(Player p, String trail, Location loc, TrailData td, int amount) {
		PacketPlayOutWorldParticles packet = null;
		if (td == null)
			packet = new PacketPlayOutWorldParticles(EnumParticle.valueOf(trail.toString().toUpperCase()), true, (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(), (float) 0, (float) 0, (float) 0, 0, amount, null);
		else
			packet = new PacketPlayOutWorldParticles(EnumParticle.valueOf(trail.toString().toUpperCase()), true, (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(), 0, 0, 0, 0, amount, td.getR(), td.getG(), td.getB());

		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
	}
	
	public void sendTitle(Player p, String title, String subtitle, int fadeInTime, int showTime, int fadeOutTime) {
		IChatBaseComponent icbc = ChatSerializer.a("{\"text\": \"" + title + "\"}");
		PacketPlayOutTitle packetTitle = new PacketPlayOutTitle(EnumTitleAction.TITLE, icbc, fadeInTime, showTime, fadeOutTime);
		icbc = ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");
		PacketPlayOutTitle packetSubtitle = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, icbc, fadeInTime, showTime, fadeOutTime);
		
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packetTitle);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packetSubtitle);
	}

	public void sendActionBar(Player p, String message) {
		IChatBaseComponent icbc = ChatSerializer.a("{\"text\": \"" + message + "\"}");
		PacketPlayOutChat packet = new PacketPlayOutChat(icbc, (byte)2);
		
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
	}
}
