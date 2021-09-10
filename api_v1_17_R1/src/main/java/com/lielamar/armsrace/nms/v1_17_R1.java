package com.lielamar.armsrace.nms;

import com.lielamar.armsrace.modules.shop.TrailData;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class v1_17_R1 implements NMS {

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
        player.sendTitle(title, subtitle, fadeIn, fade, fadeOut);
    }

    public void sendActionBar(Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
    }
}
