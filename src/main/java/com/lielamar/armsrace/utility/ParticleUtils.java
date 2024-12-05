package com.lielamar.armsrace.utility;

import com.lielamar.armsrace.modules.shop.TrailData;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class ParticleUtils {

    public static void sendParticle(Player player, String trail, Location location, TrailData trailData, int amount) {
        try {
            if (trailData == null) {
                trailData = new TrailData(0, 0, 0);
            }

            try {
                player.spawnParticle(
                        Particle.valueOf(trail),
                        location,
                        amount,
                        trailData.getRed(),
                        trailData.getGreen(),
                        trailData.getBlue(),
                        0
                );
                return;
            } catch (NoSuchMethodError | IllegalArgumentException ignored) {
            }

            Class<?> enumParticleClass = Class.forName("net.minecraft.server." + NMSUtils.getNMSVersion() + ".EnumParticle");
            Class<?> packetClass = Class.forName("net.minecraft.server." + NMSUtils.getNMSVersion() + ".PacketPlayOutWorldParticles");

            Object particle = Enum.valueOf((Class<Enum>) enumParticleClass, trail.toUpperCase());
            Object packet = packetClass.getConstructor(
                    enumParticleClass, boolean.class, float.class, float.class, float.class,
                    float.class, float.class, float.class, float.class, int.class, float.class, float.class, float.class
            ).newInstance(
                    particle, true, (float) location.getX(), (float) location.getY(), (float) location.getZ(),
                    0f, 0f, 0f, 0f, amount, trailData.getRed(), trailData.getGreen(), trailData.getBlue()
            );

            Object handle = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", Class.forName("net.minecraft.server." + NMSUtils.getNMSVersion() + ".Packet"))
                    .invoke(playerConnection, packet);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}