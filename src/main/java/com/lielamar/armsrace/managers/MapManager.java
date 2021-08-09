package com.lielamar.armsrace.managers;

import com.lielamar.armsrace.Main;
import com.lielamar.armsrace.managers.files.BukkitFileManager;
import com.lielamar.armsrace.managers.files.MapFile;
import com.lielamar.armsrace.modules.CustomLocation;
import com.lielamar.armsrace.modules.CustomScoreboard;
import com.lielamar.armsrace.modules.map.Killstreak;
import com.lielamar.armsrace.modules.map.Map;
import com.lielamar.armsrace.modules.map.Tier;
import org.bukkit.GameMode;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.*;

public class MapManager {

    private Main plugin;
    private final java.util.Map<String, Map> maps = new LinkedHashMap<>();

    public MapManager(Main main) {
        this.plugin = main;
    }

    /**
     * Loads all maps from their files.
     */
    public void loadMaps() {
        File dest = new File(plugin.getDataFolder() + "/maps/");
        File[] listFiles = dest.listFiles();

        if (listFiles == null) {
            return;
        }

        for (File file : listFiles) {
            if (file.isFile() && !file.getName().toLowerCase().endsWith(".yml")) {
                continue;
            }

            MapFile mapFile = plugin.getMapsFileManager().addMap(file.getName());
            loadMap(mapFile);
        }
    }

    /**
     * Loads a map
     *
     * @param mapFile A {@link MapFile} object to load from
     * @return A {@link Map} object
     */
    @SuppressWarnings("unchecked")
    public Map loadMap(MapFile mapFile) {
        // Load locations
        List<CustomLocation> locations = new LinkedList<>();
        if (mapFile.getConfig().contains("SpawnLocations")) {
            for (String key : mapFile.getConfig().getConfigurationSection("SpawnLocations").getKeys(false)) {
                try {
                    locations.add(new CustomLocation(Integer.parseInt(key),
                            mapFile.getConfig().getString("SpawnLocations." + key + ".world"),
                            mapFile.getConfig().getDouble("SpawnLocations." + key + ".x"),
                            mapFile.getConfig().getDouble("SpawnLocations." + key + ".y"),
                            mapFile.getConfig().getDouble("SpawnLocations." + key + ".z"),
                            mapFile.getConfig().getLong("SpawnLocations." + key + ".yaw"),
                            mapFile.getConfig().getLong("SpawnLocations." + key + ".pitch")));
                } catch (Exception e) {
                    System.out.println("Couldn't load location ID: " + key + " for map: " + mapFile.getName() + ". Make sure it's an integer in the map config!");
                }
            }
        }

        // Load pickup locations
        List<CustomLocation> pickupLocations = new ArrayList<>();
        if (mapFile.getConfig().contains("PickupLocations")) {
            for (String key : mapFile.getConfig().getConfigurationSection("PickupLocations").getKeys(false)) {
                try {
                    pickupLocations.add(new CustomLocation(Integer.parseInt(key),
                            mapFile.getConfig().getString("PickupLocations." + key + ".world"),
                            mapFile.getConfig().getDouble("PickupLocations." + key + ".x"),
                            mapFile.getConfig().getDouble("PickupLocations." + key + ".y"),
                            mapFile.getConfig().getDouble("PickupLocations." + key + ".z"),
                            mapFile.getConfig().getLong("PickupLocations." + key + ".yaw"),
                            mapFile.getConfig().getLong("PickupLocations." + key + ".pitch")));
                } catch (Exception e) {
                    System.out.println("Couldn't load pickup location ID: " + key + " for map: " + mapFile.getName() + ". Make sure it's an integer in the map config!");
                }
            }
        }

        // Load tiers
        List<Tier> tiers = new LinkedList<>();
        if (mapFile.getConfig().contains("Tiers")) {
            int counter = 0;
            for (String s : mapFile.getConfig().getConfigurationSection("Tiers").getKeys(false)) {
                ItemStack[] armor = ((List<ItemStack>) Objects.requireNonNull(mapFile.getConfig().get("Tiers." + s + ".armor"))).toArray(new ItemStack[0]);
                ItemStack[] content = ((List<ItemStack>) Objects.requireNonNull(mapFile.getConfig().get("Tiers." + s + ".content"))).toArray(new ItemStack[0]);

                tiers.add(new Tier(armor, content));
            }
        }

        // Load scoreboard
        CustomScoreboard customScoreboard = new CustomScoreboard(false, null, null, null);
        if (mapFile.getConfig().contains("Scoreboard")) {
            customScoreboard.enabled = mapFile.getConfig().getBoolean("Scoreboard.Enabled");
            customScoreboard.title = mapFile.getConfig().getString("Scoreboard.Title");
            customScoreboard.footer = mapFile.getConfig().getString("Scoreboard.Footer");

            List<String> lines = mapFile.getConfig().getStringList("Scoreboard.Lines");
            Collections.reverse(lines);

            customScoreboard.lines = lines.toArray(new String[0]);
        }

        // Load killstreaks
        List<Killstreak> killStreak = new ArrayList<>();
        if (mapFile.getConfig().contains("Killstreak")) {
            for (String s : mapFile.getConfig().getConfigurationSection("Killstreak").getKeys(false)) {
                try {
                    killStreak.add(new Killstreak(Integer.parseInt(s), mapFile.getConfig().getDouble("Killstreak." + s + ".Coins")));
                } catch (Exception e) {
                    System.out.println("Couldn't load killstreak ID: " + s + " for map: " + mapFile.getName() + ". Make sure it's an integer in the map config!");
                }
            }
        }

        // Load general settings & data
        String name = mapFile.getName();

        if (!mapFile.getConfig().contains("Settings.MaxPlayers"))
            mapFile.getConfig().set("Settings.MaxPlayers", 24);
        int max_players = mapFile.getConfig().getInt("Settings.MaxPlayers");

        if (!mapFile.getConfig().contains("Settings.HealthRegenOnKill"))
            mapFile.getConfig().set("Settings.HealthRegenOnKill", 20);
        int healthOnKill = mapFile.getConfig().getInt("Settings.HealthRegenOnKill");

        if (!mapFile.getConfig().contains("Settings.DoPlayersRegen"))
            mapFile.getConfig().set("Settings.DoPlayersRegen", true);
        boolean playersRegen = mapFile.getConfig().getBoolean("Settings.DoPlayersRegen");

        if (!mapFile.getConfig().contains("Settings.DoesFoodLevelChange"))
            mapFile.getConfig().set("Settings.DoesFoodLevelChange", false);
        boolean foodLevelChange = mapFile.getConfig().getBoolean("Settings.DoesFoodLevelChange");

        if (!mapFile.getConfig().contains("Settings.SetHealthWhenJoinMap"))
            mapFile.getConfig().set("Settings.SetHealthWhenJoinMap", 20);
        int healthOnJoin = mapFile.getConfig().getInt("Settings.SetHealthWhenJoinMap");

        if (!mapFile.getConfig().contains("Settings.SetHungerWhenJoinMap"))
            mapFile.getConfig().set("Settings.SetHungerWhenJoinMap", 20);
        int hungerOnJoin = mapFile.getConfig().getInt("Settings.SetHungerWhenJoinMap");

        if (!mapFile.getConfig().contains("Settings.MaxPlayersHealth"))
            mapFile.getConfig().set("Settings.MaxPlayersHealth", 20);
        int maxHealth = mapFile.getConfig().getInt("Settings.MaxPlayersHealth");


        if (!mapFile.getConfig().contains("Settings.PlayersGameMode"))
            mapFile.getConfig().set("Settings.PlayersGameMode", "ADVENTURE");
        
        GameMode gamemode = GameMode.valueOf(mapFile.getConfig().getString("Settings.PlayersGameMode"));

        if (!mapFile.getConfig().contains("Settings.CoinsPerKill"))
            mapFile.getConfig().set("Settings.CoinsPerKill", 5.0);
        double coinsPerKill = mapFile.getConfig().getDouble("Settings.CoinsPerKill");

        if (!mapFile.getConfig().contains("Settings.SpawnProtectionSeconds"))
            mapFile.getConfig().set("Settings.SpawnProtectionSeconds", 20);
        int spawnProtection = mapFile.getConfig().getInt("Settings.SpawnProtectionSeconds");


        if (!mapFile.getConfig().contains("Pickups.Healing"))
            mapFile.getConfig().set("Pickups.Healing", true);
        boolean healingEnabled = mapFile.getConfig().getBoolean("Pickups.Healing");

        if (!mapFile.getConfig().contains("Pickups.DoubleDamage"))
            mapFile.getConfig().set("Pickups.DoubleDamage", true);
        boolean doubleDamageEnabled = mapFile.getConfig().getBoolean("Pickups.DoubleDamage");

        if (!mapFile.getConfig().contains("Pickups.Coins"))
            mapFile.getConfig().set("Pickups.Coins", true);
        boolean coinsEnabled = mapFile.getConfig().getBoolean("Pickups.Coins");

        if (!mapFile.getConfig().contains("Pickups.Speed"))
            mapFile.getConfig().set("Pickups.Speed", true);
        boolean speedEnabled = mapFile.getConfig().getBoolean("Pickups.Speed");

        if (!mapFile.getConfig().contains("Pickups.Resistance"))
            mapFile.getConfig().set("Pickups.Resistance", true);
        boolean resistanceEnabled = mapFile.getConfig().getBoolean("Pickups.Resistance");

        if (!mapFile.getConfig().contains("Pickups.TiersUp"))
            mapFile.getConfig().set("Pickups.TiersUp", true);
        boolean tiersUpEnabled = mapFile.getConfig().getBoolean("Pickups.TiersUp");

        if (!mapFile.getConfig().contains("Pickups.OneTap"))
            mapFile.getConfig().set("Pickups.OneTap", true);
        boolean oneTapEnabled = mapFile.getConfig().getBoolean("Pickups.OneTap");


        if (!mapFile.getConfig().contains("Settings.MinimumMinutesForPickupSpawn"))
            mapFile.getConfig().set("Settings.MinimumMinutesForPickupSpawn", 20);
        int minimumMinutesForPickupSpawn = mapFile.getConfig().getInt("Settings.MinimumMinutesForPickupSpawn");

        if (!mapFile.getConfig().contains("Settings.MaximumMinutesForPickupSpawn"))
            mapFile.getConfig().set("Settings.MaximumMinutesForPickupSpawn", 20);
        int maximumMinutesForPickupSpawn = mapFile.getConfig().getInt("Settings.MaximumMinutesForPickupSpawn");


        if (!mapFile.getConfig().contains("Settings.HealthPerPickup"))
            mapFile.getConfig().set("Settings.HealthPerPickup", 10.0);
        double healthPerPickup = mapFile.getConfig().getDouble("Settings.HealthPerPickup");

        if (!mapFile.getConfig().contains("Settings.DoubleDamageDurationSeconds"))
            mapFile.getConfig().set("Settings.DoubleDamageDurationSeconds", 10.0);
        double doubleDamageDurationSeconds = mapFile.getConfig().getDouble("Settings.DoubleDamageDurationSeconds");

        if (!mapFile.getConfig().contains("Settings.CoinsPerPickup"))
            mapFile.getConfig().set("Settings.CoinsPerPickup", 10.0);
        double coinsPerPickup = mapFile.getConfig().getDouble("Settings.CoinsPerPickup");


        if (!mapFile.getConfig().contains("Settings.SpeedLevel"))
            mapFile.getConfig().set("Settings.SpeedLevel", 1);
        int speedLevel = mapFile.getConfig().getInt("Settings.SpeedLevel");

        if (!mapFile.getConfig().contains("Settings.SpeedDurationSeconds"))
            mapFile.getConfig().set("Settings.SpeedDurationSeconds", 10.0);
        double speedDurationSeconds = mapFile.getConfig().getDouble("Settings.SpeedDurationSeconds");

        if (!mapFile.getConfig().contains("Settings.ResistanceLevel"))
            mapFile.getConfig().set("Settings.ResistanceLevel", 1);
        int resistanceLevel = mapFile.getConfig().getInt("Settings.ResistanceLevel");

        if (!mapFile.getConfig().contains("Settings.ResistanceDurationSeconds"))
            mapFile.getConfig().set("Settings.ResistanceDurationSeconds", 10.0);
        double resistanceDurationSeconds = mapFile.getConfig().getDouble("Settings.ResistanceDurationSeconds");

        if (!mapFile.getConfig().contains("Settings.TiersUp"))
            mapFile.getConfig().set("Settings.TiersUp", 1);
        int tiersUp = mapFile.getConfig().getInt("Settings.TiersUp");

        if (!mapFile.getConfig().contains("Settings.OneTapDurationSeconds"))
            mapFile.getConfig().set("Settings.OneTapDurationSeconds", 10.0);
        double oneTapDurationSeconds = mapFile.getConfig().getDouble("Settings.OneTapDurationSeconds");

        mapFile.saveConfig();

        Map map = new Map(plugin, name, max_players, locations, pickupLocations, killStreak, tiers.toArray(new Tier[0]), customScoreboard,
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

    public Main getPlugin() {
        return plugin;
    }

    public void setPlugin(Main plugin) {
        this.plugin = plugin;
    }

    public java.util.Map<String, Map> getMaps() {
        return this.maps;
    }

}
