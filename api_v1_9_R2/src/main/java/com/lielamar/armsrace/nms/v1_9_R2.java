package com.lielamar.armsrace.nms;

import com.lielamar.armsrace.modules.shop.TrailData;
import net.minecraft.server.v1_9_R2.ChatComponentText;
import net.minecraft.server.v1_9_R2.Packet;
import net.minecraft.server.v1_9_R2.PacketPlayOutChat;
import net.minecraft.server.v1_9_R2.PacketPlayOutTitle;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class v1_9_R2 implements NMS {

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
