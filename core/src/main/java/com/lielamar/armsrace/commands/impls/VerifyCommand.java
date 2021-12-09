package com.lielamar.armsrace.commands.impls;

import com.lielamar.armsrace.Main;
import com.lielamar.armsrace.premium.PremiumHandler;
import com.lielamar.armsrace.utility.Utils;
import org.bukkit.entity.Player;

public class VerifyCommand extends SubCommand {

    @Override
    public void onCommand(Main main, Player p, String[] args) {
        if (!p.hasPermission("armsrace.commands.verify")) {
            p.sendMessage(main.getMessages().noPermissions());
            return;
        }

        p.sendMessage(Utils.color("&eBuyer information:"));
        p.sendMessage(Utils.color("  &7Buyer ID: &e" + PremiumHandler.getUserID()));
        p.sendMessage(Utils.color("  &7Resource ID: &e" + PremiumHandler.getResourceID()));
        p.sendMessage(Utils.color("  &7Download ID: &e" + PremiumHandler.getDownloadID()));
        p.sendMessage(Utils.color("  &7License: &e" + PremiumHandler.formatUserURL()));
        p.sendMessage(Utils.color("  &7Verified: &e" + PremiumHandler.isPremium()));
        return;
    }

    @Override
    public String name() {
        return "verify";
    }

    @Override
    public String info() {
        return "ArmsRace verify";
    }

    @Override
    public String[] aliases() {
        return new String[]{"verify"};
    }
}