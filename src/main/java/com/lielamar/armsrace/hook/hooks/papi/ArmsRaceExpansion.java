package com.lielamar.armsrace.hook.hooks.papi;

import com.lielamar.armsrace.Main;
import com.lielamar.armsrace.modules.CustomPlayer;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ArmsRaceExpansion extends PlaceholderExpansion {

    private final Main plugin;

    public ArmsRaceExpansion(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "armsrace";
    }

    @Override
    public @NotNull String getAuthor() {
        return "yenil";
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String identifier) {
        if (player == null) {
            return null;
        }
        CustomPlayer cp = plugin.getPlayerManager().getPlayer(player);
        if (identifier.equalsIgnoreCase("coins")) {
            return String.valueOf(cp.getCoins());
        }
        if (identifier.equalsIgnoreCase("kills")) {
            return String.valueOf(cp.getKills());
        }
        if (identifier.equalsIgnoreCase("deaths")) {
            return String.valueOf(cp.getDeaths());
        }
        if (identifier.equalsIgnoreCase("trail")) {
            return String.valueOf(cp.getCurrentTrail());
        }
        if (identifier.equalsIgnoreCase("killeffect")) {
            return String.valueOf(cp.getCurrentKillEffect());
        }
        return null;
    }
}