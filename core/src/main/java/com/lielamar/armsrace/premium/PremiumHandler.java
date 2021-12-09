package com.lielamar.armsrace.premium;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class PremiumHandler {

    @Contract(pure = true)
    @NotNull
    public static String getUserID() {
        return "%%__USER__%%";
    }

    @Contract(pure = true)
    @NotNull
    public static String getResourceID() {
        return "%%__RESOURCE__%%";
    }

    @Contract(pure = true)
    @NotNull
    public static String getDownloadID() {
        return "%%__NONCE__%%";
    }

    @Contract(pure = true)
    @NotNull
    public static String isSongoda() {
        return "%%__SONGODA__%%";
    }

    @NotNull
    public static String formatUserURL() {
        return "https://songoda.com/profile/%%__USERNAME__%%";
    }

    public static boolean isPremium() {
        return Boolean.parseBoolean("%%__SONGODA__%%");
    }
}