package com.lielamar.armsrace.commands;

import com.lielamar.armsrace.Main;
import com.lielamar.armsrace.commands.impls.*;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class ArmsRaceCommand implements CommandExecutor {

    public final String armsrace = "armsrace";

    private final List<SubCommand> commands = new LinkedList<>();
    private final Main plugin;

    public ArmsRaceCommand(Main plugin) {
        this.plugin = plugin;
        setup();
    }

    /**
     * Sets the ArmsRace sub-commands
     */
    public void setup() {
        this.commands.add(new MapCommand());
        this.commands.add(new HelpCommand());
        this.commands.add(new DebugCommand());
        this.commands.add(new JoinCommand());
        this.commands.add(new LeaveCommand());
        this.commands.add(new EventCommand());
        this.commands.add(new ShopCommand());
        this.commands.add(new SetSpawnCommand());
        this.commands.add(new ReloadCommand());
        this.commands.add(new VerifyCommand());
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getMessages().mustBePlayer());
            return true;
        }

        Player player = (Player) sender;
        if (args.length == 0) {
            if (!player.hasPermission("armsrace.commands.help")) {
                player.sendMessage(plugin.getMessages().noPermissions());
                return true;
            }

            getCommand("help").onCommand(plugin, player, new String[0]);
            return true;
        }

        SubCommand target = this.getCommand(args[0]);

        if (target == null) {
            player.sendMessage(plugin.getMessages().invalidSubCommand());
            return true;
        }

        String[] arguments = new String[args.length - 1];
        System.arraycopy(args, 1, arguments, 0, arguments.length);

        try {
            target.onCommand(plugin, player, arguments);
        } catch (Exception e) {
            player.sendMessage(ChatColor.RED + "An error has occurred: " + e.getMessage());
            e.printStackTrace();
        }
        return true;
    }

    /**
     * @param name Name of a Sub-Command
     * @return A {@link SubCommand} instance
     */
    private SubCommand getCommand(String name) {
        return this.commands.stream()
                .filter(command -> command.equals(name))
                .findFirst()
                .orElse(null);
    }
}