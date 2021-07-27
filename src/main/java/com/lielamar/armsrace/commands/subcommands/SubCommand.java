package com.lielamar.armsrace.commands.subcommands;

import org.bukkit.entity.Player;

import com.lielamar.armsrace.Main;

public abstract class SubCommand {

    public SubCommand() {}

    public abstract void onCommand(Main main, Player player, String[] args);

    public abstract String name();

    public abstract String info();

    public abstract String[] aliases();
}