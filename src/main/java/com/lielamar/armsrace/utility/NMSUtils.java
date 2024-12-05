package com.lielamar.armsrace.utility;

import org.bukkit.Bukkit;

public class NMSUtils {

    public static String getNMSVersion() {
        String packageName = Bukkit.getServer().getClass().getPackage().getName();
        return packageName.substring(packageName.lastIndexOf('.') + 1);
    }
}
