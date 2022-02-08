package com.lielamar.armsrace.nms;

import com.lielamar.armsrace.modules.shop.TrailData;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class v1_15_R1 implements NMS {

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
}
