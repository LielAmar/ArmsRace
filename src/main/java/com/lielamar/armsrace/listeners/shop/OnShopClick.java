package com.lielamar.armsrace.listeners.shop;

import com.lielamar.armsrace.Main;
import com.lielamar.armsrace.modules.CustomPlayer;
import com.lielamar.armsrace.modules.shop.CustomItem;
import com.lielamar.armsrace.modules.shop.ItemType;
import com.lielamar.armsrace.modules.shop.Shop;
import com.lielamar.armsrace.modules.shop.SkillLevel;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class OnShopClick implements Listener {

	private final Main main;

	public OnShopClick(Main main) {
		this.main = main;
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (e.getInventory() == null) return;
		if (e.getCurrentItem() == null) return;
		if (e.getClickedInventory() == null) return;

		Player p = (Player) e.getWhoClicked();
		CustomPlayer cp = main.getPlayerManager().getPlayer(p);
		double money = main.getPlayerManager().getEconomy().getBalance(p);

		for (Shop s : main.getShopManager().getShops().values()) {
			if (e.getView().getTitle().equals(ChatColor.translateAlternateColorCodes('&', s.getName()))) {
				CustomItem item = s.getCustomItem(e.getCurrentItem());
				e.setCancelled(true);
				if (item == null) {
					return;
				}
				if (item.getType() == ItemType.SHOP) {
					Shop shop = main.getShopManager().getShop(item.getAction());
					if (shop == null) {
						e.setCancelled(true);
						return;
					}
					p.openInventory(shop.getCustomInventory(cp));
				} else if (item.getType() == ItemType.SKILL) {
					if (item.getLevels().size() == 0) return;

					SkillLevel nextPlayerLevel = null;
					double playerLevel = cp.getSkillLevel(item.getSkillType());

					for (SkillLevel lvl : item.getLevels()) {
						if (playerLevel + 1 == lvl.getLevel())
							nextPlayerLevel = lvl;
					}

					if (nextPlayerLevel == null)
						return;

					if (cp.getSkillLevel(item.getSkillType()) >= nextPlayerLevel.getLevel())
						return;

					if (money < nextPlayerLevel.getPrice()) {
						p.sendMessage(main.getMessages().notEnoughCoins(nextPlayerLevel.getPrice()));
						p.closeInventory();
						return;
					}
					main.getPlayerManager().getEconomy().withdrawPlayer(p, nextPlayerLevel.getPrice()).transactionSuccess();
					cp.setSkillLevel(item.getSkillType(), nextPlayerLevel.getLevel());
					p.sendMessage(main.getMessages().purchasedSkill(ChatColor.stripColor(item.getName()), nextPlayerLevel.getLevel(), nextPlayerLevel.getPrice()));
					p.closeInventory();
				} else if (item.getType() == ItemType.PROJECTILE) {
					if (item.getTrailType() == null) return;

					boolean hasTrail = cp.hasTrail(item.getTrailType());
					if (hasTrail) {
						cp.setCurrentTrail(item.getTrailType());
						p.sendMessage(main.getMessages().trailChangedTo(ChatColor.stripColor(item.getName())));
						p.closeInventory();
						return;
					}

					int price = item.getPrice();
					if (money < price) {
						p.sendMessage(main.getMessages().notEnoughCoins(price));
						p.closeInventory();
						return;
					}
					p.sendMessage(main.getMessages().purchasedTrail(ChatColor.stripColor(item.getName()), price));
					p.sendMessage(main.getMessages().trailChangedTo(ChatColor.stripColor(item.getName())));
					p.closeInventory();
					main.getPlayerManager().getEconomy().withdrawPlayer(p, price).transactionSuccess();
					cp.setTrails(item.getTrailType(), true);
					cp.setCurrentTrail(item.getTrailType());

					if (item.getTrailData() != null)
						cp.setCurrentTrailData(item.getTrailData());
				} else if (item.getType() == ItemType.KILL_EFFECT) {
					if (item.getKillEffectType() == null) return;

					boolean hasKilleffect = cp.hasKilleffect(item.getKillEffectType());
					if (hasKilleffect) {
						cp.setCurrentKillEffect(item.getKillEffectType());
						p.sendMessage(main.getMessages().killEffectChangedTo(ChatColor.stripColor(item.getName())));
						p.closeInventory();
						return;
					}

					int price = item.getPrice();
					if (money < price) {
						p.sendMessage(main.getMessages().notEnoughCoins(price));
						p.closeInventory();
						return;
					}
					main.getPlayerManager().getEconomy().withdrawPlayer(p, price).transactionSuccess();
					cp.setKillEffects(item.getKillEffectType(), true);
					cp.setCurrentKillEffect(item.getKillEffectType());
					p.sendMessage(main.getMessages().purchasedKillEffect(ChatColor.stripColor(item.getName()), price));
					p.sendMessage(main.getMessages().killEffectChangedTo(ChatColor.stripColor(item.getName())));
					p.closeInventory();
					return;
				}
			}
		}
	}
}