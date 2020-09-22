package com.lielamar.armsrace.commands.subcommands;

import org.bukkit.entity.Player;

import com.lielamar.armsrace.Main;

public class ShopCommand extends SubCommand {
	
	@Override
	public void onCommand(Main main, Player p, String[] args) {
		if(!p.hasPermission("armsrace.commands.shop")) {
	        p.sendMessage(main.getMessages().noPermissions());
	        return;
		}
		p.openInventory(main.getShopManager().getShop(main.getShopManager().getMainShop()).getCustomInventory(main.getPlayerManager().getPlayer(p)));
	}

	@Override
	public String name() {
		return "shop";
	}

	@Override
	public String info() {
		return "ArmsRace shop";
	}

	@Override
	public String[] aliases() {
		return new String[] { "shop" };
	}
}