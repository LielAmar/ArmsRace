package com.lielamar.armsrace.nms;

import com.lielamar.armsrace.modules.shop.TrailData;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class v1_8_R3 implements NMS {

    @Override
    public void sendParticle(Player player, String trail, Location location, TrailData trailData, int amount) {
        try {
            PacketPlayOutWorldParticles packet;

            if (trailData != null) {
                packet = new PacketPlayOutWorldParticles(EnumParticle.valueOf(trail.toUpperCase()), true, (float) location.getX(), (float) location.getY(), (float) location.getZ(),
                        0f, 0f, 0f, 0f, amount, trailData.getRed(), trailData.getGreen(), trailData.getBlue());
            } else {
                packet = new PacketPlayOutWorldParticles(EnumParticle.valueOf(trail.toUpperCase()), true, (float) location.getX(), (float) location.getY(), (float) location.getZ(),
                        0f, 0f, 0f, 0f, amount);
            }

            sendPacket(player, packet);
        } catch (IllegalArgumentException ignored) {
        }
    }

    private void sendPacket(Player player, Packet<?> packet) {
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }
}
