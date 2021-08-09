package com.lielamar.armsrace.commands.impls;

import org.bukkit.entity.Player;

import com.lielamar.armsrace.Main;

import java.util.Arrays;

public abstract class SubCommand {

    public abstract void onCommand(Main main, Player player, String[] args);

    public abstract String name();

    public abstract String info();

    public abstract String[] aliases();

    public boolean equals(String name) {
        return name().equalsIgnoreCase(name) || Arrays.stream(aliases()).anyMatch(aliases -> aliases.equalsIgnoreCase(name));
    }

}