package com.lielamar.armsrace.managers;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import com.lielamar.armsrace.Main;
import com.lielamar.armsrace.managers.files.BukkitFileManager;
import com.lielamar.armsrace.managers.files.MapFile;
import com.lielamar.armsrace.modules.CustomLocation;
import com.lielamar.armsrace.modules.CustomScoreboard;
import com.lielamar.armsrace.modules.map.Killstreak;
import com.lielamar.armsrace.modules.map.Map;
import com.lielamar.armsrace.modules.map.Tier;

public class MapManager {

	private Main main;
	private java.util.Map<String, Map> maps;
	
	public MapManager(Main main) {
		this.main = main;
		
		this.maps = new LinkedHashMap<>();
	}
	
	public Main getMain() {
		return main;
	}

	public void setMain(Main main) {
		this.main = main;
	}

	public java.util.Map<String, Map> getMaps() {
		return this.maps;
	}
	
	/**
	 * Loads all maps from their files.
	 */
	public void loadMaps() {
		File dest = new File(main.getDataFolder() + "/maps/");
		for(File files : dest.listFiles()) {
			if(!files.getName().toLowerCase().endsWith(".yml")) continue;
			MapFile mapFile = main.getMapsFileManager().addMap(files.getName());
			loadMap(mapFile);
		}
	}
	
	/**
	 * Loads a map 
	 * 
	 * @param mapFile    A {@link MapFile} object to load from
	 * @return           A {@link Map} object
	 */
	public Map loadMap(MapFile mapFile) {
		// Load locations
		List<CustomLocation> locations = new ArrayList<>();
		if(mapFile.getConfig().contains("SpawnLocations")) {
			for(String s : mapFile.getConfig().getConfigurationSection("SpawnLocations").getKeys(false)) {
				try {
				locations.add(new CustomLocation(Integer.parseInt(s),new Location(
						Bukkit.getWorld((String)mapFile.getConfig().get("SpawnLocations." + s + ".world")),
						mapFile.getConfig().getDouble("SpawnLocations." + s + ".x"),
						mapFile.getConfig().getDouble("SpawnLocations." + s + ".y"),
						mapFile.getConfig().getDouble("SpawnLocations." + s + ".z"),
						mapFile.getConfig().getLong("SpawnLocations." + s + ".yaw"),
						mapFile.getConfig().getLong("SpawnLocations." + s + ".pitch"))));
				} catch(Exception e) {
					System.out.println("Couldn't load location ID: " + s + " for map: " + mapFile.getName() + ". Make sure it's an integer in the map config!");
				}
			}
		}
		
		// Load pickup locations
		List<CustomLocation> pickupLocations = new ArrayList<>();
		if(mapFile.getConfig().contains("PickupLocations")) {
			for(String s : mapFile.getConfig().getConfigurationSection("PickupLocations").getKeys(false)) {
				try {
					pickupLocations.add(new CustomLocation(Integer.parseInt(s),new Location(
						Bukkit.getWorld((String)mapFile.getConfig().get("PickupLocations." + s + ".world")),
						mapFile.getConfig().getDouble("PickupLocations." + s + ".x"),
						mapFile.getConfig().getDouble("PickupLocations." + s + ".y"),
						mapFile.getConfig().getDouble("PickupLocations." + s + ".z"),
						mapFile.getConfig().getLong("PickupLocations." + s + ".yaw"),
						mapFile.getConfig().getLong("PickupLocations." + s + ".pitch"))));
				} catch(Exception e) {
					System.out.println("Couldn't load pickup location ID: " + s + " for map: " + mapFile.getName() + ". Make sure it's an integer in the map config!");
				}
			}
		}
		
		// Load tiers
		Tier[] tiers = new Tier[0];
		if(mapFile.getConfig().contains("Tiers")) {
			int amount_of_tiers = mapFile.getConfig().getConfigurationSection("Tiers").getKeys(false).size();
			tiers = new Tier[amount_of_tiers];
			int counter = 0;
			for(String s : mapFile.getConfig().getConfigurationSection("Tiers").getKeys(false)) {
				@SuppressWarnings("unchecked")
				ItemStack[] armor = ((List<ItemStack>) mapFile.getConfig().get("Tiers." + s + ".armor")).toArray(new ItemStack[0]);
				@SuppressWarnings("unchecked")
				ItemStack[] content = ((List<ItemStack>) mapFile.getConfig().get("Tiers." + s + ".content")).toArray(new ItemStack[0]);
				
				tiers[counter] = new Tier(armor, content);
				counter++;
			}
		}
		
		// Load scoreboard
		CustomScoreboard sb = new CustomScoreboard(false, null, null, null);
		if(mapFile.getConfig().contains("Scoreboard")) {
			boolean enabled = mapFile.getConfig().getBoolean("Scoreboard.Enabled");
			sb.setEnabled(enabled);
			
			String title = mapFile.getConfig().getString("Scoreboard.Title");
			sb.setTitle(title);
			
			String footer = mapFile.getConfig().getString("Scoreboard.Footer");
			sb.setFooter(footer);
			
			List<String> configList = mapFile.getConfig().getStringList("Scoreboard.Lines");
			String[] lines = new String[configList.size()];
			for(int i = 0; i < lines.length; i++)
				lines[i] = configList.get(i);
			
			String[] fixed = new String[lines.length];
			int temp = 0;
			for (int i = lines.length-1; i > 0; i--) {
				fixed[temp] = lines[i];
				temp++;
			}
			sb.setLines(fixed);		
		}
		
		// Load killstreaks
		List<Killstreak> killstreaks = new ArrayList<>();
		if(mapFile.getConfig().contains("Killstreak")) {
			for(String s : mapFile.getConfig().getConfigurationSection("Killstreak").getKeys(false)) {
				try {
					killstreaks.add(new Killstreak(Integer.parseInt(s), mapFile.getConfig().getDouble("Killstreak." + s + ".Coins")));
				} catch(Exception e) {
					System.out.println("Couldn't load killstreak ID: " + s + " for map: " + mapFile.getName() + ". Make sure it's an integer in the map config!");
				}
			}
		}
		
		
		// Load general settings & data
		String name = mapFile.getName();
		
		if(!mapFile.getConfig().contains("Settings.MaxPlayers"))
			mapFile.getConfig().set("Settings.MaxPlayers", 24);
		int max_players = (int)mapFile.getConfig().get("Settings.MaxPlayers");
			
		if(!mapFile.getConfig().contains("Settings.HealthRegenOnKill"))
			mapFile.getConfig().set("Settings.HealthRegenOnKill", 20);
		int healthOnKill = (int)mapFile.getConfig().get("Settings.HealthRegenOnKill");
		
		if(!mapFile.getConfig().contains("Settings.DoPlayersRegen"))
			mapFile.getConfig().set("Settings.DoPlayersRegen", true);
		boolean playersRegen = (boolean)mapFile.getConfig().get("Settings.DoPlayersRegen");
		
		if(!mapFile.getConfig().contains("Settings.DoesFoodLevelChange"))
			mapFile.getConfig().set("Settings.DoesFoodLevelChange", false);
		boolean foodLevelChange = (boolean)mapFile.getConfig().get("Settings.DoesFoodLevelChange");
		
		if(!mapFile.getConfig().contains("Settings.SetHealthWhenJoinMap"))
			mapFile.getConfig().set("Settings.SetHealthWhenJoinMap", 20);
		int healthOnJoin = (int)mapFile.getConfig().get("Settings.SetHealthWhenJoinMap");
		
		if(!mapFile.getConfig().contains("Settings.SetHungerWhenJoinMap"))
			mapFile.getConfig().set("Settings.SetHungerWhenJoinMap", 20);
		int hungerOnJoin = (int)mapFile.getConfig().get("Settings.SetHungerWhenJoinMap");
		
		if(!mapFile.getConfig().contains("Settings.MaxPlayersHealth"))
			mapFile.getConfig().set("Settings.MaxPlayersHealth", 20);
		int maxHealth = (int)mapFile.getConfig().get("Settings.MaxPlayersHealth");
		
		
		if(!mapFile.getConfig().contains("Settings.PlayersGameMode"))
			mapFile.getConfig().set("Settings.PlayersGameMode", "ADVENTURE");
		GameMode gamemode = GameMode.valueOf((String)mapFile.getConfig().get("Settings.PlayersGameMode"));
		if(gamemode == null)
			gamemode = GameMode.ADVENTURE;
		
		if(!mapFile.getConfig().contains("Settings.CoinsPerKill"))
			mapFile.getConfig().set("Settings.CoinsPerKill", 5.0);
		double coinsPerKill = (double)mapFile.getConfig().get("Settings.CoinsPerKill");
			
		if(!mapFile.getConfig().contains("Settings.SpawnProtectionSeconds"))
			mapFile.getConfig().set("Settings.SpawnProtectionSeconds", 20);
		int spawnProtection = (int)mapFile.getConfig().get("Settings.SpawnProtectionSeconds");
		
		
		if(!mapFile.getConfig().contains("Pickups.Healing"))
			mapFile.getConfig().set("Pickups.Healing", true);
		boolean healingEnabled = (boolean)mapFile.getConfig().get("Pickups.Healing");
		
		if(!mapFile.getConfig().contains("Pickups.DoubleDamage"))
			mapFile.getConfig().set("Pickups.DoubleDamage", true);
		boolean doubleDamageEnabled = (boolean)mapFile.getConfig().get("Pickups.DoubleDamage");
		
		if(!mapFile.getConfig().contains("Pickups.Coins"))
			mapFile.getConfig().set("Pickups.Coins", true);
		boolean coinsEnabled = (boolean)mapFile.getConfig().get("Pickups.Coins");
		
		if(!mapFile.getConfig().contains("Pickups.Speed"))
			mapFile.getConfig().set("Pickups.Speed", true);
		boolean speedEnabled = (boolean)mapFile.getConfig().get("Pickups.Speed");
		
		if(!mapFile.getConfig().contains("Pickups.Resistance"))
			mapFile.getConfig().set("Pickups.Resistance", true);
		boolean resistanceEnabled = (boolean)mapFile.getConfig().get("Pickups.Resistance");
		
		if(!mapFile.getConfig().contains("Pickups.TiersUp"))
			mapFile.getConfig().set("Pickups.TiersUp", true);
		boolean tiersUpEnabled = (boolean)mapFile.getConfig().get("Pickups.TiersUp");
		
		if(!mapFile.getConfig().contains("Pickups.OneTap"))
			mapFile.getConfig().set("Pickups.OneTap", true);
		boolean oneTapEnabled = (boolean)mapFile.getConfig().get("Pickups.OneTap");
		
		
		if(!mapFile.getConfig().contains("Settings.MinimumMinutesForPickupSpawn"))
			mapFile.getConfig().set("Settings.MinimumMinutesForPickupSpawn", 20);
		int minimumMinutesForPickupSpawn = (int)mapFile.getConfig().get("Settings.MinimumMinutesForPickupSpawn");
		
		if(!mapFile.getConfig().contains("Settings.MaximumMinutesForPickupSpawn"))
			mapFile.getConfig().set("Settings.MaximumMinutesForPickupSpawn", 20);
		int maximumMinutesForPickupSpawn = (int)mapFile.getConfig().get("Settings.MaximumMinutesForPickupSpawn");
		
		
		if(!mapFile.getConfig().contains("Settings.HealthPerPickup"))
			mapFile.getConfig().set("Settings.HealthPerPickup", 10.0);
		double healthPerPickup = (double)mapFile.getConfig().get("Settings.HealthPerPickup");
		
		if(!mapFile.getConfig().contains("Settings.DoubleDamageDurationSeconds"))
			mapFile.getConfig().set("Settings.DoubleDamageDurationSeconds", 10.0);
		double doubleDamageDurationSeconds = (double)mapFile.getConfig().get("Settings.DoubleDamageDurationSeconds");
		
		if(!mapFile.getConfig().contains("Settings.CoinsPerPickup"))
			mapFile.getConfig().set("Settings.CoinsPerPickup", 10.0);
		double coinsPerPickup = (double)mapFile.getConfig().get("Settings.CoinsPerPickup");
		
		
		
		
		if(!mapFile.getConfig().contains("Settings.SpeedLevel"))
			mapFile.getConfig().set("Settings.SpeedLevel", 1);
		int speedLevel = (int)mapFile.getConfig().get("Settings.SpeedLevel");
		
		if(!mapFile.getConfig().contains("Settings.SpeedDurationSeconds"))
			mapFile.getConfig().set("Settings.SpeedDurationSeconds", 10.0);
		double speedDurationSeconds = (double)mapFile.getConfig().get("Settings.SpeedDurationSeconds");
		
		if(!mapFile.getConfig().contains("Settings.ResistanceLevel"))
			mapFile.getConfig().set("Settings.ResistanceLevel", 1);
		int resistanceLevel = (int)mapFile.getConfig().get("Settings.ResistanceLevel");
		
		if(!mapFile.getConfig().contains("Settings.ResistanceDurationSeconds"))
			mapFile.getConfig().set("Settings.ResistanceDurationSeconds", 10.0);
		double resistanceDurationSeconds = (double)mapFile.getConfig().get("Settings.ResistanceDurationSeconds");
		
		if(!mapFile.getConfig().contains("Settings.TiersUp"))
			mapFile.getConfig().set("Settings.TiersUp", 1);
		int tiersUp = (int)mapFile.getConfig().get("Settings.TiersUp");
		
		if(!mapFile.getConfig().contains("Settings.OneTapDurationSeconds"))
			mapFile.getConfig().set("Settings.OneTapDurationSeconds", 10.0);
		double oneTapDurationSeconds = (double)mapFile.getConfig().get("Settings.OneTapDurationSeconds");
		
		mapFile.saveConfig();
		
		Map map = new Map(main, name, max_players, locations, pickupLocations, killstreaks, tiers, sb,
				healthOnKill, playersRegen, foodLevelChange,
				healthOnJoin, hungerOnJoin, maxHealth, gamemode, coinsPerKill, spawnProtection,
				healingEnabled, doubleDamageEnabled, coinsEnabled, speedEnabled, resistanceEnabled, tiersUpEnabled, oneTapEnabled,
				minimumMinutesForPickupSpawn, maximumMinutesForPickupSpawn, healthPerPickup, doubleDamageDurationSeconds, coinsPerPickup,
				speedLevel, speedDurationSeconds, resistanceLevel, resistanceDurationSeconds, tiersUp, oneTapDurationSeconds);
		this.maps.put(name, map);
		
		return map;
	}
	
	public Map reloadMap(MapFile mapFile) {
		unloadMap(mapFile.getName());
		return loadMap(mapFile);
	}

	public void unloadMap(String name) {
		name = BukkitFileManager.fixName(name);
		maps.remove(name);
	}
	
	public Map getMap(String name) {
		name = BukkitFileManager.fixName(name);
		return maps.get(name);
	}
	
	public Map setMap(Map map) {
		maps.put(map.getName(), map);
		return map;
	}
}
