package com.lielamar.armsrace.managers;

import com.lielamar.armsrace.Main;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.LinkedHashMap;
import java.util.Map;

public class SettingsManager {

	private final Main main;

	@Setter
    @Getter
    private Location spawn;

	@Setter
    @Getter
    private Map<Integer, Integer> swordLaunchCooldown;
	@Getter
    @Setter
    private Map<Integer, Integer> headShotRate;
	@Setter
    @Getter
    private Map<Integer, Integer> extraGoldAmount;
	@Setter
    @Getter
    private Map<Integer, Integer> skipTierRate;
	@Setter
    @Getter
    private Map<Integer, Integer> spawnResistanceDuration;
	@Setter
    @Getter
    private Map<Integer, Integer> extraHealthAmount;
	@Setter
    @Getter
    private Map<Integer, Integer> spawnGappleRate;

	@Getter
    private int trailsAmount;
	@Getter
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
		if (this.main.getConfig().getBoolean("Spawn.enabled")) {
			World world = Bukkit.getWorld(this.main.getConfig().getString("Spawn.world", Bukkit.getWorlds().get(0).getName()));
			if (world == null) world = Bukkit.getWorlds().get(0);

			this.spawn = new Location(world,
					this.main.getConfig().getDouble("Spawn.x"),
					this.main.getConfig().getDouble("Spawn.y"),
					this.main.getConfig().getDouble("Spawn.z"),
					(float) this.main.getConfig().getDouble("Spawn.yaw"),
					(float) this.main.getConfig().getDouble("Spawn.pitch"));
		}

		swordLaunchCooldown = new LinkedHashMap<>();
		headShotRate = new LinkedHashMap<>();
		extraGoldAmount = new LinkedHashMap<>();
		skipTierRate = new LinkedHashMap<>();
		spawnResistanceDuration = new LinkedHashMap<>();
		extraHealthAmount = new LinkedHashMap<>();
		spawnGappleRate = new LinkedHashMap<>();

		this.trailsAmount = main.getConfig().getInt("Trails.Amount");
		this.trailsSpeed = main.getConfig().getInt("Trails.Speed");

		for (String s : main.getConfig().getConfigurationSection("Skills.SwordLaunch.CooldownSeconds").getKeys(false)) {
			try {
				swordLaunchCooldown.put(Integer.parseInt(s), main.getConfig().getInt("Skills.SwordLaunch.CooldownSeconds." + s));
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Config.yml - the levels must be an integer!");
			}
		}

		for (String s : main.getConfig().getConfigurationSection("Skills.HeadShot.Rate").getKeys(false)) {
			try {
				headShotRate.put(Integer.parseInt(s), main.getConfig().getInt("Skills.HeadShot.Rate." + s));
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Config.yml - the levels must be an integer!");
			}
		}

		for (String s : main.getConfig().getConfigurationSection("Skills.ExtraGold.Amount").getKeys(false)) {
			try {
				extraGoldAmount.put(Integer.parseInt(s), main.getConfig().getInt("Skills.ExtraGold.Amount." + s));
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Config.yml - the levels must be an integer!");
			}
		}

		for (String s : main.getConfig().getConfigurationSection("Skills.SkipTier.Rate").getKeys(false)) {
			try {
				skipTierRate.put(Integer.parseInt(s), main.getConfig().getInt("Skills.SkipTier.Rate." + s));
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Config.yml - the levels must be an integer!");
			}
		}

		for (String s : main.getConfig().getConfigurationSection("Skills.SpawnResistance.DurationSeconds").getKeys(false)) {
			try {
				spawnResistanceDuration.put(Integer.parseInt(s), main.getConfig().getInt("Skills.SpawnResistance.DurationSeconds." + s));
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Config.yml - the levels must be an integer!");
			}
		}

		for (String s : main.getConfig().getConfigurationSection("Skills.ExtraHealth.Amount").getKeys(false)) {
			try {
				extraHealthAmount.put(Integer.parseInt(s), main.getConfig().getInt("Skills.ExtraHealth.Amount." + s));
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Config.yml - the levels must be an integer!");
			}
		}

		for (String s : main.getConfig().getConfigurationSection("Skills.SpawnGapple.Rate").getKeys(false)) {
			try {
				spawnGappleRate.put(Integer.parseInt(s), main.getConfig().getInt("Skills.SpawnGapple.Rate." + s));
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Config.yml - the levels must be an integer!");
			}
		}
	}

}