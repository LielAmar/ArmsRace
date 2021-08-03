package com.lielamar.armsrace.utility.packets.version;

import com.lielamar.armsrace.utility.packets.PacketVersion;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.lielamar.armsrace.modules.shop.TrailData;

import net.minecraft.server.v1_8_R2.EnumParticle;
import net.minecraft.server.v1_8_R2.IChatBaseComponent;
import net.minecraft.server.v1_8_R2.PacketPlayOutChat;
import net.minecraft.server.v1_8_R2.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R2.PacketPlayOutWorldParticles;

public class NMS_v1_8_R2 implements PacketVersion {

	@Override
	public void sendParticle(Player p, String trail, Location loc, TrailData td, int amount) {
		PacketPlayOutWorldParticles packet;
		if (td == null)
			packet = new PacketPlayOutWorldParticles(EnumParticle.valueOf(trail.toUpperCase()), true, (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(), (float) 0, (float) 0, (float) 0, 0, amount, null);
		else
			packet = new PacketPlayOutWorldParticles(EnumParticle.valueOf(trail.toUpperCase()), true, (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(), 0, 0, 0, 0, amount, td.getR(), td.getG(), td.getB());

		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
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
		PacketPlayOutChat packet = new PacketPlayOutChat(icbc, (byte)2);
		
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
	}
}
