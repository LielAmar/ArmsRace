package com.lielamar.armsrace.commands;

import com.lielamar.armsrace.Main;
import com.lielamar.armsrace.api.events.PlayerLeaveMapEvent;
import com.lielamar.armsrace.modules.CustomPlayer;
import com.lielamar.armsrace.utility.Utils;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SpawnCommand implements CommandExecutor {

    private final Main plugin;

    public SpawnCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getMessages().mustBePlayer());
            return true;
        }

        Player player = (Player) sender;
        Location spawnLocation = plugin.getSettingsManager().getSpawn();

        if (spawnLocation == null) {
            return true;
        } else if (!sender.hasPermission("armsrace.commands.spawn")) {
            sender.sendMessage(plugin.getMessages().noPermissions());
            return true;
        } else if (plugin.getCombatLogManager().isCombatLog(player)) {
            player.sendMessage(plugin.getMessages().youAreStillInCombat(plugin.getCombatLogManager().getDuration() - plugin.getCombatLogManager().getCombatLog(player)));
            return true;
        }

        CustomPlayer customPlayer = plugin.getPlayerManager().getPlayer(player);
        if (customPlayer.getCurrentMap() != null) {
            customPlayer.getCurrentMap().removePlayer(player, PlayerLeaveMapEvent.LeaveReason.SPAWN);
        } else {
            customPlayer.getPlayer().teleport(spawnLocation);
            Utils.clearPlayer(plugin, player, 20, 20, 20, GameMode.ADVENTURE);
        }

        player.sendMessage(plugin.getMessages().teleportingToSpawn());
        return true;
    }
}
