package com.lielamar.armsrace.utility;

import com.lielamar.armsrace.Main;
import org.bukkit.plugin.Plugin;

public class APIUtils {

    public static boolean isPlaceholderAPI() {
        Plugin plugin = Main.getPlugin(Main.class).getServer().getPluginManager().getPlugin("PlaceholderAPI");
        return plugin != null && plugin.isEnabled();
    }
}
