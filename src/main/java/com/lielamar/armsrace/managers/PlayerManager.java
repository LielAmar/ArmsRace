package com.lielamar.armsrace.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.lielamar.armsrace.Main;
import com.lielamar.armsrace.modules.CustomPlayer;

import net.milkbowl.vault.economy.Economy;

public class PlayerManager {

	private Main main;
	private Economy econ = null;

	private Map<UUID, Long> spawnProtection;
	private List<UUID> doubleDamage;
	private List<UUID> oneTap;
	private Map<UUID, Long> swordlaunch;
	private Map<UUID, ArmorStand> swordlaunchAs;

	private Map<UUID, CustomPlayer> players;

	public PlayerManager(Main main) {
		this.main = main;

		RegisteredServiceProvider<Economy> economyProvider = main.getServer().getServicesManager().getRegistration(Economy.class);
		if (economyProvider != null)
			econ = economyProvider.getProvider();

		this.spawnProtection = new HashMap<>();
		this.doubleDamage = new ArrayList<>();
		this.oneTap = new ArrayList<>();
		this.swordlaunch = new HashMap<>();
		this.swordlaunchAs = new HashMap<>();

		this.players = new HashMap<>();

		for(Player pl : Bukkit.getOnlinePlayers()) {
			addPlayer(pl);
		}
	}

	public Economy getEconomy() {
		return this.econ;
	}

	public Map<UUID, CustomPlayer> getPlayers() {
		return this.players;
	}

	public CustomPlayer getPlayer(Player p) {
		return players.get(p.getUniqueId());
	}

	public CustomPlayer addPlayer(Player p) {
		CustomPlayer cp = new CustomPlayer(this.main, p);
		this.players.put(p.getUniqueId(), cp);
		return cp;
	}

	public Long getSpawnProtection(Player p) {
		return (spawnProtection.containsKey(p.getUniqueId())?spawnProtection.get(p.getUniqueId()):-1L);
	}

	public void updateSpawnProtection(Player p) {
		this.spawnProtection.put(p.getUniqueId(), System.currentTimeMillis());
	}

	public void removeSpawnProtection(Player p) {
		this.spawnProtection.remove(p.getUniqueId());
	}

	public void addDoubleDamage(Player p) {
		this.doubleDamage.add(p.getUniqueId());
	}

	public void removeDoubleDamage(Player p) {
		this.doubleDamage.remove(p.getUniqueId());
	}

	public boolean containsDoubleDamage(Player p) {
		return this.doubleDamage.contains(p.getUniqueId());
	}

	public void addOneTap(Player p) {
		this.oneTap.add(p.getUniqueId());
	}

	public void removeOneTap(Player p) {
		this.oneTap.remove(p.getUniqueId());
	}

	public boolean containsOneTap(Player p) {
		return this.oneTap.contains(p.getUniqueId());
	}

	public Long getSwordLaunch(Player p) {
		return (swordlaunch.containsKey(p.getUniqueId())?swordlaunch.get(p.getUniqueId()):-1L);
	}

	public void updateSwordLaunch(Player p) {
		this.swordlaunch.put(p.getUniqueId(), System.currentTimeMillis());
	}

	public void removeSwordLaunch(Player p) {
		this.swordlaunch.remove(p.getUniqueId());
	}

	public ArmorStand getSwordLaunchAs(Player p) {
		return (swordlaunchAs.containsKey(p.getUniqueId())?swordlaunchAs.get(p.getUniqueId()):null);
	}

	public Map<UUID, ArmorStand> getSwordLaunchAs() {
		return this.swordlaunchAs;
	}

	public void updateSwordLaunchAs(Player p, ArmorStand as) {
		this.swordlaunchAs.put(p.getUniqueId(), as);
	}

	public void removeSwordLaunchAs(Player p) {
		this.swordlaunch.remove(p.getUniqueId());
	}
}
