package com.lielamar.armsrace.utility;

import com.cryptomorin.xseries.reflection.XReflection;
import com.lielamar.armsrace.modules.shop.TrailData;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

public class ParticleUtils {

    public static void sendParticle(Player player, String trail, Location location, TrailData trailData, int amount) {
        if (trailData == null) {
            trailData = new TrailData(0, 0, 0);
        }

        if (XReflection.supports(9)) {
            player.spawnParticle(
                    Particle.valueOf(trail.toUpperCase()),
                    location,
                    amount,
                    trailData.getRed(),
                    trailData.getGreen(),
                    trailData.getBlue(),
                    0
            );
        } else {
            new ParticleBuilder(ParticleEffect.valueOf(trail.toUpperCase()))
                    .setAmount(amount)
                    .setLocation(location)
                    .setOffset(0, 0, 0)
                    .display(player);
        }
    }
}