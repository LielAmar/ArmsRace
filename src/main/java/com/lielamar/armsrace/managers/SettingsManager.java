package com.lielamar.armsrace.managers;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.lielamar.armsrace.Main;
import org.bukkit.World;

public class SettingsManager {

	private Main main;
	
	private Location spawn;
	
	private Map<Integer, Integer> swordLaunchCooldown;
	private Map<Integer, Integer> headShotRate;
	private Map<Integer, Integer> extraGoldAmount;
	private Map<Integer, Integer> skipTierRate;
	private Map<Integer, Integer> spawnResistanceDuration;
	private Map<Integer, Integer> extraHealthAmount;
	private Map<Integer, Integer> spawnGappleRate;
	
	private int trailsAmount;
	private int trailsSpeed;

	public SettingsManager(Main main) {
		this.main = main;
		load();
	}
	
	/**
	 * Loads all the general settings from the config
	 */
	public void load() {
		
		spawn = null;
		if(this.main.getConfig().getBoolean("Spawn.enabled")) {
			World world = Bukkit.getWorld(this.main.getConfig().getString("Spawn.world", Bukkit.getWorlds().get(0).getName()));
			if(world == null) world = Bukkit.getWorlds().get(0);

			this.spawn = new Location(world,
					this.main.getConfig().getDouble("Spawn.x"),
					this.main.getConfig().getDouble("Spawn.y"),
					this.main.getConfig().getDouble("Spawn.z"),
					(float)this.main.getConfig().getDouble("Spawn.yaw"),
					(float)this.main.getConfig().getDouble("Spawn.pitch"));
		}
		
		swordLaunchCooldown = new HashMap<Integer, Integer>();
		headShotRate = new HashMap<Integer, Integer>();
		extraGoldAmount = new HashMap<Integer, Integer>();
		skipTierRate = new HashMap<Integer, Integer>();
		spawnResistanceDuration = new HashMap<Integer, Integer>();
		extraHealthAmount = new HashMap<Integer, Integer>();
		spawnGappleRate = new HashMap<Integer, Integer>();
		
		this.trailsAmount = main.getConfig().getInt("Trails.Amount");
		this.trailsSpeed = main.getConfig().getInt("Trails.Speed");
		
		for(String s : main.getConfig().getConfigurationSection("Skills.SwordLaunch.CooldownSeconds").getKeys(false)) {
			try {
				swordLaunchCooldown.put(Integer.parseInt(s), main.getConfig().getInt("Skills.SwordLaunch.CooldownSeconds." + s));
			} catch(Exception e) {
				e.printStackTrace();
				System.out.println("Config.yml - the levels must be an integer!");
			}
		}
		
		for(String s : main.getConfig().getConfigurationSection("Skills.HeadShot.Rate").getKeys(false)) {
			try {
				headShotRate.put(Integer.parseInt(s), main.getConfig().getInt("Skills.HeadShot.Rate." + s));
			} catch(Exception e) {
				e.printStackTrace();
				System.out.println("Config.yml - the levels must be an integer!");
			}
		}
		
		for(String s : main.getConfig().getConfigurationSection("Skills.ExtraGold.Amount").getKeys(false)) {
			try {
				extraGoldAmount.put(Integer.parseInt(s), main.getConfig().getInt("Skills.ExtraGold.Amount." + s));
			} catch(Exception e) {
				e.printStackTrace();
				System.out.println("Config.yml - the levels must be an integer!");
			}
		}
		
		for(String s : main.getConfig().getConfigurationSection("Skills.SkipTier.Rate").getKeys(false)) {
			try {
				skipTierRate.put(Integer.parseInt(s), main.getConfig().getInt("Skills.SkipTier.Rate." + s));
			} catch(Exception e) {
				e.printStackTrace();
				System.out.println("Config.yml - the levels must be an integer!");
			}
		}
		
		for(String s : main.getConfig().getConfigurationSection("Skills.SpawnResistance.DurationSeconds").getKeys(false)) {
			try {
				spawnResistanceDuration.put(Integer.parseInt(s), main.getConfig().getInt("Skills.SpawnResistance.DurationSeconds." + s));
			} catch(Exception e) {
				e.printStackTrace();
				System.out.println("Config.yml - the levels must be an integer!");
			}
		}
		
		for(String s : main.getConfig().getConfigurationSection("Skills.ExtraHealth.Amount").getKeys(false)) {
			try {
				extraHealthAmount.put(Integer.parseInt(s), main.getConfig().getInt("Skills.ExtraHealth.Amount." + s));
			} catch(Exception e) {
				e.printStackTrace();
				System.out.println("Config.yml - the levels must be an integer!");
			}
		}
		
		for(String s : main.getConfig().getConfigurationSection("Skills.SpawnGapple.Rate").getKeys(false)) {
			try {
				spawnGappleRate.put(Integer.parseInt(s), main.getConfig().getInt("Skills.SpawnGapple.Rate." + s));
			} catch(Exception e) {
				e.printStackTrace();
				System.out.println("Config.yml - the levels must be an integer!");
			}
		}
	}

	public Location getSpawn() {
		return this.spawn;
	}
	
	public void setSpawn(Location loc) {
		this.spawn = loc;
	}
	
	public Map<Integer, Integer> getSwordLaunchCooldown() {
		return swordLaunchCooldown;
	}

	public void setSwordLaunchCooldown(Map<Integer, Integer> swordLaunchCooldown) {
		this.swordLaunchCooldown = swordLaunchCooldown;
	}

	public Map<Integer, Integer> getHeadShotRate() {
		return headShotRate;
	}

	public void setHeadShotRate(Map<Integer, Integer> headShotRate) {
		this.headShotRate = headShotRate;
	}

	public Map<Integer, Integer> getExtraGoldAmount() {
		return extraGoldAmount;
	}

	public void setExtraGoldAmount(Map<Integer, Integer> extraGoldAmount) {
		this.extraGoldAmount = extraGoldAmount;
	}

	public Map<Integer, Integer> getSkipTierRate() {
		return skipTierRate;
	}

	public void setSkipTierRate(Map<Integer, Integer> skipTierRate) {
		this.skipTierRate = skipTierRate;
	}

	public Map<Integer, Integer> getSpawnResistanceDuration() {
		return spawnResistanceDuration;
	}

	public void setSpawnResistanceDuration(Map<Integer, Integer> spawnResistanceDuration) {
		this.spawnResistanceDuration = spawnResistanceDuration;
	}

	public Map<Integer, Integer> getExtraHealthAmount() {
		return extraHealthAmount;
	}

	public void setExtraHealthAmount(Map<Integer, Integer> extraHealthAmount) {
		this.extraHealthAmount = extraHealthAmount;
	}

	public Map<Integer, Integer> getSpawnGappleRate() {
		return spawnGappleRate;
	}

	public void setSpawnGappleRate(Map<Integer, Integer> spawnGappleRate) {
		this.spawnGappleRate = spawnGappleRate;
	}
	
	public int getTrailsAmount() {
		return this.trailsAmount;
	}
	
	public int getTrailsSpeed() {
		return this.trailsSpeed;
	}
}