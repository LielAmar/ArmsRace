package com.lielamar.armsrace.utility.packets.version;

import com.lielamar.armsrace.modules.shop.TrailData;
import com.lielamar.armsrace.utility.packets.PacketVersion;
import net.minecraft.server.v1_9_R1.ChatComponentText;
import net.minecraft.server.v1_9_R1.Packet;
import net.minecraft.server.v1_9_R1.PacketPlayOutChat;
import net.minecraft.server.v1_9_R1.PacketPlayOutTitle;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class NMS_v1_9_R1 implements PacketVersion {

    @Override
    public void sendParticle(Player player, String trail, Location location, TrailData trailData, int amount) {
        try {
            if (trailData == null) {
                trailData = new TrailData(0, 0, 0);
            }
            player.spawnParticle(Particle.valueOf(trail), location, amount, trailData.getRed(), trailData.getGreen(), trailData.getBlue(), null);
        } catch (IllegalArgumentException ignored) {
            // Particle not found in enum
        }
    }

    public void sendTitle(Player player, String title, String subtitle, int fadeIn, int fade, int fadeOut) {
        sendPacket(player, getTitlePacket(PacketPlayOutTitle.EnumTitleAction.TITLE, title, fadeIn, fade, fadeOut));
        sendPacket(player, getTitlePacket(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, subtitle, fadeIn, fade, fadeOut));
    }

    public void sendActionBar(Player player, String message) {
        sendPacket(player, new PacketPlayOutChat(new ChatComponentText(message), (byte) 2));
    }

    private void sendPacket(Player player, Packet<?> packet) {
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    private PacketPlayOutTitle getTitlePacket(PacketPlayOutTitle.EnumTitleAction titleAction, String text, int fadeIn, int fade, int fadeOut) {
        return new PacketPlayOutTitle(titleAction, new ChatComponentText(text != null ? text : ""), fadeIn, fade, fadeOut);
    }

}
