package com.lielamar.armsrace.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.lielamar.armsrace.Main;
import com.lielamar.armsrace.commands.subcommands.DebugCommand;
import com.lielamar.armsrace.commands.subcommands.EventCommand;
import com.lielamar.armsrace.commands.subcommands.HelpCommand;
import com.lielamar.armsrace.commands.subcommands.JoinCommand;
import com.lielamar.armsrace.commands.subcommands.LeaveCommand;
import com.lielamar.armsrace.commands.subcommands.MapCommand;
import com.lielamar.armsrace.commands.subcommands.ReloadCommand;
import com.lielamar.armsrace.commands.subcommands.SetSpawnCommand;
import com.lielamar.armsrace.commands.subcommands.ShopCommand;
import com.lielamar.armsrace.commands.subcommands.SubCommand;

public class ArmsRace implements CommandExecutor {

    private ArrayList<SubCommand> commands = new ArrayList<>();
    private Main main;

    public ArmsRace(Main main) {
    	this.main = main;
    	setup();
    }

    public String armsrace = "armsrace";

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
    }

    public boolean onCommand(CommandSender cs, Command cmd, String cmdLabel, String[] args) {

        if(!(cs instanceof Player)) {
            cs.sendMessage(main.getMessages().mustBePlayer());
            return true;
        }

        Player p = (Player) cs;
        
        if(cmd.getName().equalsIgnoreCase(armsrace)) {
            if(args.length == 0) {
                if(!p.hasPermission("armsrace.commands.help")) {
                	p.sendMessage(main.getMessages().noPermissions());
                	return true;
                }
                
            	get("help").onCommand(main, p, new String[0]);
                return true;
            }

            SubCommand target = this.get(args[0]);

            if(target == null) {
                p.sendMessage(main.getMessages().invalidSubCommand());
                return true;
            }

            ArrayList<String> arrayList = new ArrayList<>();

            arrayList.addAll(Arrays.asList(args));
            arrayList.remove(0);
            
            String arguments[] = new String[arrayList.size()];
            
            try{
                target.onCommand(main, p, arrayList.toArray(arguments));
            }catch (Exception e){
                p.sendMessage(ChatColor.RED + "An error has occurred.");
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * @param name      Name of a Sub-Command
     * @return          A {@link SubCommand} instance
     */
    private SubCommand get(String name) {
        Iterator<SubCommand> subcommands = this.commands.iterator();
        while (subcommands.hasNext()) {

            SubCommand sc = subcommands.next();

            if (sc.name().equalsIgnoreCase(name)) {
                return sc;
            }

            String[] aliases;
            int length = (aliases = sc.aliases()).length;

            for (int var5 = 0; var5 < length; ++var5) {
                String alias = aliases[var5];
                if (name.equalsIgnoreCase(alias)) {
                    return sc;
                }
            }
        }
        return null;
    }
}