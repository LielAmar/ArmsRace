package com.lielamar.armsrace.utility;

import org.bukkit.Bukkit;

public class NMSUtils {

    public static String getSupportVersions() {
        return "v1_8_R3, v1_9_R2, v1_10_R1, v1_11_R1, v1_12_R1, v1_13_R2, v1_14_R1, v1_15_R1, v1_16_R3, v1_17_R1";
    }

    public static String getServerVersion() {
        String name = Bukkit.getServer().getClass().getPackage().getName();
        return name.substring(name.lastIndexOf(46) + 1);
    }
}
