package com.lielamar.armsrace.managers.files;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import com.lielamar.armsrace.Main;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

public class MapFile {

	private Main main;
	private String name;

	private File file;
	private YamlConfiguration config;


	public MapFile(Main main, String name, File file) {
		this.main = main;
		this.name = name;
		this.file = file;
		this.config = YamlConfiguration.loadConfiguration(file);
		saveDefaultConfig();
	}

	public String getName() {
		return name;
	}

	public File getFile() {
		return file;
	}

	public YamlConfiguration getConfig() {
		return config;
	}

	public void saveConfig() {
		try { getConfig().save(this.file); } catch (IOException e) { e.printStackTrace(); }
	}

	private void saveDefaultConfig() {
		if (!file.exists()) {
			try {
				file.createNewFile();
				loadDefaults();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void loadDefaults() {
		if (file == null)
			return;
		if (file.length() > 0)
			return;

		Reader isReader;
		try {
			isReader = new InputStreamReader(main.getResource("map.yml"), "UTF8");

			if (isReader != null) {
				this.config = YamlConfiguration.loadConfiguration(isReader);
				this.config.save(this.file);
			}
		} catch (NullPointerException | IOException e) {
			e.printStackTrace();
		}
	}

	public void set(String key, Object object) {
		getConfig().set(key, object);
	}

	public void addTier(ItemStack[] armor, ItemStack[] content, int id) {
		this.set("Tiers." + id + ".armor", armor);
		this.set("Tiers." + id + ".content", content);
		this.saveConfig();
		main.getGameManager().getMapManager().getMap(this.getName()).addTier(armor, content); // Add new tier to map via MapManager
	}

	public void setTier(ItemStack[] armor, ItemStack[] content, int id) {
		this.set("Tiers." + id + ".armor", armor);
		this.set("Tiers." + id + ".content", content);
		this.saveConfig();
		main.getGameManager().getMapManager().getMap(this.getName()).setTier(id, armor, content);  // Update the tier in map via MapManager
	}

	public void removeTier(int id) {
		int i = 0;
		for(i = 0; i < this.getConfig().getConfigurationSection("Tiers").getKeys(false).size()-1; i++) {
			if(i >= id) {
				this.set("Tiers." + (i) + ".armor", this.config.get("Tiers." + (i+1) + ".armor"));
				this.set("Tiers." + (i) + ".content", this.config.get("Tiers." + (i+1) + ".content"));
			}
		}
		this.set("Tiers." + i, null);
		this.saveConfig();
		main.getGameManager().getMapManager().getMap(this.getName()).removeTier(id); // Remove the tier from the map via MapManager
	}

	public void addLocation(int id, Location loc) {
		this.getConfig().set("SpawnLocations." + id + ".world", loc.getWorld().getName().toString());
		this.getConfig().set("SpawnLocations." + id + ".x", loc.getX());
		this.getConfig().set("SpawnLocations." + id + ".y", loc.getY());
		this.getConfig().set("SpawnLocations." + id + ".z", loc.getZ());
		this.getConfig().set("SpawnLocations." + id + ".yaw", loc.getYaw());
		this.getConfig().set("SpawnLocations." + id + ".pitch", loc.getPitch());
		this.saveConfig();
		main.getGameManager().getMapManager().getMap(this.getName()).addLocation(id, loc); // Add a location to the map via MapManager
	}

	public void setLocation(int id, Location loc) {
		this.getConfig().set("SpawnLocations." + id + ".world", loc.getWorld().getName().toString());
		this.getConfig().set("SpawnLocations." + id + ".x", loc.getX());
		this.getConfig().set("SpawnLocations." + id + ".y", loc.getY());
		this.getConfig().set("SpawnLocations." + id + ".z", loc.getZ());
		this.getConfig().set("SpawnLocations." + id + ".yaw", loc.getYaw());
		this.getConfig().set("SpawnLocations." + id + ".pitch", loc.getPitch());
		this.saveConfig();
		main.getGameManager().getMapManager().getMap(this.getName()).setLocation(id, loc); // Set a location in the map via MapManager
	}

	public void removeLocation(int id) {
		boolean changed = false;
		int i = 0;
		for(i = 0; i < getConfig().getConfigurationSection("SpawnLocations").getKeys(false).size(); i++) {
			if(i == id)
				changed = true;
			if(changed)
				this.getConfig().set("SpawnLocations." + (i-1), getConfig().get("SpawnLocation." + i));
		}

		this.getConfig().set("SpawnLocations." + i, null);
		this.saveConfig();
		main.getGameManager().getMapManager().getMap(this.getName()).removeLocation(id); // Remove the location from the map via MapManager
	}

	public void addPickupLocation(int id, Location loc) {
		this.getConfig().set("PickupLocations." + id + ".world", loc.getWorld().getName().toString());
		this.getConfig().set("PickupLocations." + id + ".x", loc.getX());
		this.getConfig().set("PickupLocations." + id + ".y", loc.getY());
		this.getConfig().set("PickupLocations." + id + ".z", loc.getZ());
		this.getConfig().set("PickupLocations." + id + ".yaw", loc.getYaw());
		this.getConfig().set("PickupLocations." + id + ".pitch", loc.getPitch());
		this.saveConfig();
		main.getGameManager().getMapManager().getMap(this.getName()).addPickupLocation(id, loc); // Add a location to the map via MapManager
	}

	public void setPickupLocation(int id, Location loc) {
		this.getConfig().set("PickupLocations." + id + ".world", loc.getWorld().getName().toString());
		this.getConfig().set("PickupLocations." + id + ".x", loc.getX());
		this.getConfig().set("PickupLocations." + id + ".y", loc.getY());
		this.getConfig().set("PickupLocations." + id + ".z", loc.getZ());
		this.getConfig().set("PickupLocations." + id + ".yaw", loc.getYaw());
		this.getConfig().set("PickupLocations." + id + ".pitch", loc.getPitch());
		this.saveConfig();
		main.getGameManager().getMapManager().getMap(this.getName()).setPickupLocation(id, loc); // Set a location in the map via MapManager
	}

	public void removePickupLocation(int id) {
		boolean changed = false;
		int i = 0;
		for(i = 0; i < getConfig().getConfigurationSection("PickupLocations").getKeys(false).size(); i++) {
			if(i == id)
				changed = true;
			if(changed)
				this.getConfig().set("PickupLocations." + (i-1), getConfig().get("PickupLocations." + i));
		}

		this.getConfig().set("PickupLocations." + i, null);
		this.saveConfig();
		main.getGameManager().getMapManager().getMap(this.getName()).removePickupLocation(id); // Remove the location from the map via MapManager
	}
}
