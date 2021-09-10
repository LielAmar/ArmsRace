package com.lielamar.armsrace.nms;

import com.lielamar.armsrace.modules.shop.TrailData;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface NMS {

    void sendParticle(Player player, String trail, Location location, TrailData trailData, int amount);

    void sendTitle(Player player, String title, String subtitle, int fadeIn, int fade, int fadeOut);

    void sendActionBar(Player player, String message);

}
