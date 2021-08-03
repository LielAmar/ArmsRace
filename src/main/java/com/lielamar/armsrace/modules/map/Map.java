package com.lielamar.armsrace.modules.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.lielamar.armsrace.listeners.custom.LeaveReason;
import com.lielamar.armsrace.listeners.custom.PlayerJoinMapEvent;
import com.lielamar.armsrace.listeners.custom.PlayerLeaveMapEvent;
import com.lielamar.armsrace.utility.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.lielamar.armsrace.Main;
import com.lielamar.armsrace.modules.CustomLocation;
import com.lielamar.armsrace.modules.CustomPlayer;
import com.lielamar.armsrace.modules.CustomScoreboard;

public class Map {

	private Main main;
	private String name;
	private Random rnd;
	private CustomPlayer[] players;
	private List<CustomLocation> locations;
	private List<CustomLocation> pickupLocations;
	private List<Pickup> pickups;
	private List<Killstreak> killstreak;
	private Tier[] tiers;
	private CustomScoreboard sb;
	
	private CustomPlayer highestTier;
	private CustomPlayer highestKillstreak;
	
	private int healthOnKill;
	private boolean playersRegen;
	private boolean foodLevelChange;
	private int healthOnJoin;
	private int hungerOnJoin;
	private int maxHealth;
	private GameMode gamemode;
	private double coinsPerKill;
	private int spawnProtection;
	
	private boolean healingEnabled, doubleDamageEnabled, coinsEnabled, speedEnabled, resistanceEnabled, tierUpEnabled, oneTapEnabled;
	
	private int minimumMinutesForPickupSpawn;
	private int maximumMinutesForPickupSpawn;
	
	private double healthPerPickup;
	private double doubleDamageDuration;
	private double coinsPerPickup;
	private int speedLevel;
	private double speedDuration;
	private int resistanceLevel;
	private double resistanceDuration;
	private int tiersUpAmount;
	private double oneTapDuration;
	
	private boolean doubleCoinsEvent, doubleTiersEvent;
	
	public Map(Main main, String name, int max_players, List<CustomLocation> locations, List<CustomLocation> pickupLocations, List<Killstreak> killstreak,
			Tier[] tiers, CustomScoreboard sb,
			int healthOnKill, boolean playersRegen, boolean foodLevelChange,
			int healthOnJoin, int hungerOnJoin, int maxHealth, GameMode gamemode, double coinsPerKill, int spawnProtection,
			boolean healingEnabled, boolean doubleDamageEnabled, boolean coinsEnabled, boolean speedEnabled, boolean resistanceEnabled, boolean tierUpEnabled, boolean oneTapEnabled,
			int minimumMinutesForPickupSpawn, int maximumMinutesForPickupSpawn, double healthPerPickup, double doubleDamageDuration, double coinsPerPickup,
			int speedLevel, double speedDuration, int resistanceLevel, double resistanceDuration, int tiersUpAmount, double oneTapDuration) {
		this.main = main;
		this.name = name;
		this.rnd = new Random();
		this.players = new CustomPlayer[max_players];
		this.locations = locations;
		this.pickupLocations = pickupLocations;
		this.killstreak = killstreak;
		this.pickups = new ArrayList<Pickup>();
		this.tiers = tiers;
		this.sb = sb;
		
		this.highestKillstreak = null;
		this.highestTier = null;
		
		this.healthOnKill = healthOnKill;
		this.playersRegen = playersRegen;
		this.foodLevelChange = foodLevelChange;
		this.healthOnJoin = healthOnJoin;
		this.hungerOnJoin = hungerOnJoin;
		this.maxHealth = maxHealth;
		this.gamemode = gamemode;
		this.coinsPerKill = coinsPerKill;
		this.spawnProtection = spawnProtection;
		
		this.healingEnabled = healingEnabled;
		this.doubleDamageEnabled = doubleDamageEnabled;
		this.coinsEnabled = coinsEnabled;
		this.speedEnabled = speedEnabled;
		this.resistanceEnabled = resistanceEnabled;
		this.tierUpEnabled = tierUpEnabled;
		this.oneTapEnabled = oneTapEnabled;
		
		this.minimumMinutesForPickupSpawn = minimumMinutesForPickupSpawn;
		this.maximumMinutesForPickupSpawn = maximumMinutesForPickupSpawn;
		
		this.healthPerPickup = healthPerPickup;
		this.doubleDamageDuration = doubleDamageDuration;
		this.coinsPerPickup = coinsPerPickup;
		this.speedLevel = speedLevel;
		this.speedDuration = speedDuration;
		this.resistanceLevel = resistanceLevel;
		this.resistanceDuration = resistanceDuration;
		this.tiersUpAmount = tiersUpAmount;
		this.oneTapDuration = oneTapDuration;
		
		this.doubleCoinsEvent = false;
		this.doubleTiersEvent = false;
		
		startPickupTimer();
	}

	public String getName() {
		return (name.toLowerCase().endsWith(".yml")?name.toLowerCase().substring(0, name.length()-4):name.toLowerCase());
	}

	public void setName(String name) {
		this.name = name;
	}

	public CustomPlayer[] getPlayers() {
		return players;
	}

	public void setPlayers(CustomPlayer[] players) {
		this.players = players;
	}

	public List<CustomLocation> getLocations() {
		return locations;
	}

	public void setLocations(List<CustomLocation> locations) {
		this.locations = locations;
	}
	
	public List<CustomLocation> getPickupLocations() {
		return pickupLocations;
	}

	public void setPickupLocations(List<CustomLocation> pickupLocations) {
		this.pickupLocations = pickupLocations;
	}
	
	public Tier[] getTiers() {
		return tiers;
	}

	public void setTiers(Tier[] tiers) {
		this.tiers = tiers;
	}

	public List<Pickup> getPickups() {
		return pickups;
	}

	public void setPickups(List<Pickup> pickups) {
		this.pickups = pickups;
	}
	
	public List<Killstreak> getKillstreak() {
		return killstreak;
	}
	
	public Killstreak getKillstreak(int level) {
		for(Killstreak ks : killstreak) {
			if(level == ks.getLevel()) return ks;
		}
		return null;
	}
	
	public void setKillstreak(List<Killstreak> killstreak) {
		this.killstreak = killstreak;
	}
	
	public CustomScoreboard getSb() {
		return sb;
	}

	public void setSb(CustomScoreboard sb) {
		this.sb = sb;
	}

	public CustomPlayer getHighestKillstreak() {
		return highestKillstreak;
	}

	public void setHighestKillstreak(CustomPlayer highestKillstreak) {
		this.highestKillstreak = highestKillstreak;
	}

	public CustomPlayer getHighestTier() {
		return highestTier;
	}

	public void setHighestTier(CustomPlayer highestTier) {
		this.highestTier = highestTier;
	}

	
	public int getHealthOnKill() {
		return this.healthOnKill;
	}

	public void setHealthOnKill(int healthOnKill) {
		this.healthOnKill = healthOnKill;
	}
	
	public boolean isPlayersRegen() {
		return playersRegen;
	}

	public void setPlayersRegen(boolean playersRegen) {
		this.playersRegen = playersRegen;
	}

	public boolean isFoodLevelChange() {
		return foodLevelChange;
	}

	public void setFoodLevelChange(boolean foodLevelChange) {
		this.foodLevelChange = foodLevelChange;
	}

	public int getHealthOnJoin() {
		return healthOnJoin;
	}

	public void setHealthOnJoin(int healthOnJoin) {
		this.healthOnJoin = healthOnJoin;
	}

	public int getHungerOnJoin() {
		return hungerOnJoin;
	}

	public void setHungerOnJoin(int hungerOnJoin) {
		this.hungerOnJoin = hungerOnJoin;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	public GameMode getGamemode() {
		return gamemode;
	}

	public void setGamemode(GameMode gamemode) {
		this.gamemode = gamemode;
	}

	public double getCoinsPerKill() {
		if(isDoubleCoinsEvent())
			return 2*coinsPerKill;
		return coinsPerKill;
	}

	public void setCoinsPerKill(double coinsPerKill) {
		this.coinsPerKill = coinsPerKill;
	}

	public int getSpawnProtection() {
		return spawnProtection;
	}

	public void setSpawnProtection(int spawnProtection) {
		this.spawnProtection = spawnProtection;
	}
	
	public boolean isHealingEnabled() {
		return healingEnabled;
	}

	public void setHealingEnabled(boolean healingEnabled) {
		this.healingEnabled = healingEnabled;
	}

	public boolean isDoubleDamageEnabled() {
		return doubleDamageEnabled;
	}

	public void setDoubleDamageEnabled(boolean doubleDamageEnabled) {
		this.doubleDamageEnabled = doubleDamageEnabled;
	}

	public boolean isCoinsEnabled() {
		return coinsEnabled;
	}

	public void setCoinsEnabled(boolean coinsEnabled) {
		this.coinsEnabled = coinsEnabled;
	}

	public boolean isSpeedEnabled() {
		return speedEnabled;
	}

	public void setSpeedEnabled(boolean speedEnabled) {
		this.speedEnabled = speedEnabled;
	}

	public boolean isResistanceEnabled() {
		return resistanceEnabled;
	}

	public void setResistanceEnabled(boolean resistanceEnabled) {
		this.resistanceEnabled = resistanceEnabled;
	}

	public boolean isTierUpEnabled() {
		return tierUpEnabled;
	}

	public void setTierUpEnabled(boolean tierUpEnabled) {
		this.tierUpEnabled = tierUpEnabled;
	}

	public boolean isOneTapEnabled() {
		return oneTapEnabled;
	}

	public void setOneTapEnabled(boolean oneTapEnabled) {
		this.oneTapEnabled = oneTapEnabled;
	}

	public int getMinimumMinutesForPickupSpawn() {
		return minimumMinutesForPickupSpawn;
	}

	public void setMinimumMinutesForPickupSpawn(int minimumMinutesForPickupSpawn) {
		this.minimumMinutesForPickupSpawn = minimumMinutesForPickupSpawn;
	}

	public int getMaximumMinutesForPickupSpawn() {
		return maximumMinutesForPickupSpawn;
	}

	public void setMaximumMinutesForPickupSpawn(int maximumMinutesForPickupSpawn) {
		this.maximumMinutesForPickupSpawn = maximumMinutesForPickupSpawn;
	}
	
	public double getHealthPerPickup() {
		return healthPerPickup;
	}

	public void setHealthPerPickup(double healthPerPickup) {
		this.healthPerPickup = healthPerPickup;
	}

	public double getDoubleDamageDuration() {
		return doubleDamageDuration;
	}

	public void setDoubleDamageDuration(double doubleDamageDuration) {
		this.doubleDamageDuration = doubleDamageDuration;
	}

	public double getCoinsPerPickup() {
		return coinsPerPickup;
	}

	public void setCoinsPerPickup(double coinsPerPickup) {
		this.coinsPerPickup = coinsPerPickup;
	}
	
	public int getSpeedLevel() {
		return speedLevel;
	}

	public void setSpeedLevel(int speedLevel) {
		this.speedLevel = speedLevel;
	}

	public double getSpeedDuration() {
		return speedDuration;
	}

	public void setSpeedDuration(double speedDuration) {
		this.speedDuration = speedDuration;
	}

	public int getResistanceLevel() {
		return resistanceLevel;
	}

	public void setResistanceLevel(int resistanceLevel) {
		this.resistanceLevel = resistanceLevel;
	}

	public double getResistanceDuration() {
		return resistanceDuration;
	}

	public void setResistanceDuration(double resistanceDuration) {
		this.resistanceDuration = resistanceDuration;
	}

	public int getTiersUpAmount() {
		return tiersUpAmount;
	}

	public void setTiersUpAmount(int tiersUpAmount) {
		this.tiersUpAmount = tiersUpAmount;
	}

	public double getOneTapDuration() {
		return oneTapDuration;
	}

	public void setOneTapDuration(double oneTapDuration) {
		this.oneTapDuration = oneTapDuration;
	}
	
	public boolean isDoubleCoinsEvent() {
		return doubleCoinsEvent;
	}

	public boolean setDoubleCoinsEvent(boolean doubleCoinsEvent) {
		this.doubleCoinsEvent = doubleCoinsEvent;
		return doubleCoinsEvent;
	}

	public boolean isDoubleTiersEvent() {
		return doubleTiersEvent;
	}

	public boolean setDoubleTiersEvent(boolean doubleTiersEvent) {
		this.doubleTiersEvent = doubleTiersEvent;
		return doubleTiersEvent;
	}

	public CustomLocation getLocation(int id) {
		for(CustomLocation cl : locations) {
			if(cl.getId() == id) return cl;
		}
		return null;
	}
	
	public CustomLocation getPickupLocation(int id) {
		for(CustomLocation cl : pickupLocations) {
			if(cl.getId() == id) return cl;
		}
		return null;
	}
	
	/**
	 * Starts the pickup timer (spawns a pickup in random times)
	 */
	public void startPickupTimer() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(main, new Runnable() {
			
			int time = rnd.nextInt((maximumMinutesForPickupSpawn*60) - (minimumMinutesForPickupSpawn*60) + 1) + (minimumMinutesForPickupSpawn*60);
			
			@Override
			public void run() {
				time = time-1;
				if(time <= 0) {
					addPickup();
					time = rnd.nextInt((maximumMinutesForPickupSpawn*60) - (minimumMinutesForPickupSpawn*60) + 1) + (minimumMinutesForPickupSpawn*60);
				}
			}
		}, 0L, 20L);
	}
	
	/**
	 * @param type      The type of pickup we want to check
	 * @return          Is the pickup type valid
	 */
	public boolean isValidType(int type) {
		if(type == 1 && !healingEnabled) return false;
		if(type == 2 && !doubleDamageEnabled) return false;
		if(type == 3 && !coinsEnabled) return false;
		if(type == 4 && !speedEnabled) return false;
		if(type == 5 && !resistanceEnabled) return false;
		if(type == 6 && !tierUpEnabled) return false;
		if(type == 7 && !oneTapEnabled) return false;
		return true;
	}

	public void addPickup() {
		int type = rnd.nextInt(7-1+1)+1;
		CustomLocation randomLoc = getRandomPickupLocation();
		if(randomLoc == null) return;
		
		List<Pickup> removePickups = new ArrayList<Pickup>();
		for(Pickup pickup : pickups) {
			if(pickup == null) removePickups.add(pickup);
			if(pickup.getLoc() == null) removePickups.add(pickup);
			if(pickup.getLoc() != randomLoc.getLoc()) {
				removePickups.add(pickup);
			}
		}
		
		for(Pickup pickup : removePickups) {
			pickup.getPickup().remove();
			pickups.remove(pickup);
		}
		
		while(!isValidType(type)) {
			type = rnd.nextInt(7-1+1)+1;
		}
		
		Pickup pickup = new Pickup(this, PickupType.HEALTH, randomLoc.getLoc());
		if(type == 1) {
			pickup = new Pickup(this, PickupType.HEALTH, randomLoc.getLoc());
		} if(type == 2) {
			pickup = new Pickup(this, PickupType.DOUBLE_DAMAGE, randomLoc.getLoc());
		} if(type == 3) {
			pickup = new Pickup(this, PickupType.COINS, randomLoc.getLoc());
		} if(type == 4) {
			pickup = new Pickup(this, PickupType.SPEED, randomLoc.getLoc());
		} if(type == 5) {
			pickup = new Pickup(this, PickupType.RESISTANCE, randomLoc.getLoc());
		} if(type == 6) {
			pickup = new Pickup(this, PickupType.TIER_UP, randomLoc.getLoc());
		} if(type == 7) {
			pickup = new Pickup(this, PickupType.ONE_TAP, randomLoc.getLoc());
		}

		pickup.spawn();
		pickups.add(pickup);
		for(CustomPlayer cp : players) {
			if(cp != null) {
				cp.getPlayer().sendMessage(main.getMessages().pickupSpawned(pickup.getName()));
			}
		}
	}
	
	/**
	 * Adds a new Tier
	 * 
	 * @param armor       Armor to set to the tier
	 * @param content     Content to set to the tier
	 */
	public void addTier(ItemStack[] armor, ItemStack[] content) {
		Tier[] newTiers = new Tier[this.tiers.length+1];
		
		int i = 0;
		for(i = 0; i < this.tiers.length; i++) {
			newTiers[i] = tiers[i];
		}
		newTiers[i] = new Tier(armor, content);
		this.tiers = newTiers;
	}
	
	/**
	 * Sets a tier by id
	 * 
	 * @param tier      Id of the tier to edit
	 * @param armor     Armor to set to the tier
	 * @param content   Content to set to the tier
	 */
	public void setTier(int tier, ItemStack[] armor, ItemStack[] content) {
		this.tiers[tier] = new Tier(armor, content);
	}
	
	/**
	 * Removes a tier by id
	 * 
	 * @param tier     Id of tier to remove
	 */
	public void removeTier(int tier) {
		Tier[] newTiers = new Tier[this.tiers.length-1];
		
		int counter = 0;
		for(int i = 0; i < this.tiers.length; i++) {
			if(i == tier)
				continue;

			newTiers[counter] = tiers[i];
			counter++;
		}
		this.tiers = newTiers;
	}
	
	/**
	 * Adds a new location by id
	 * 
	 * @param id          Id of the location to add
	 * @param location    Location to attach to the id
	 */
	public void addLocation(int id, Location location) {
		locations.add(new CustomLocation(id, location));
	}
	
	/**
	 * Sets a location by id
	 * 
	 * @param id         Id of the location to edit
	 * @param loc        Location to attack to the id
	 */
	public void setLocation(int id, Location loc) {
		for(CustomLocation location : this.locations) {
			if(location.getId() == id)
				location.setLoc(loc);
		}
	}
	
	/**
	 * Removes a location by id
	 * 
	 * @param id       Id of the location to remove
	 */
	public void removeLocation(int id) {
		CustomLocation tmp = null;
		for(CustomLocation location : this.locations) {
			if(location.getId() == id)
				tmp = location;
		}
		if(tmp == null) return;
		int removedId = tmp.getId();
		locations.remove(tmp);
		for(CustomLocation cl : locations) {
			if(cl.getId() > removedId) {
				cl.setId(cl.getId()-1);
			}
		}
		System.gc();
	}
	
	/**
	 * Adds a new pickup location by id
	 * 
	 * @param id         Id of the new location
	 * @param location   Location to attach to the id
	 */
	public void addPickupLocation(int id, Location location) {
		pickupLocations.add(new CustomLocation(id, location));
	}
	
	/**
	 * Sets a pickup location by id
	 * 
	 * @param id      Id of the location to edit
	 * @param loc     Location to attack to the id
	 */
	public void setPickupLocation(int id, Location loc) {
		for(CustomLocation location : this.pickupLocations) {
			if(location.getId() == id)
				location.setLoc(loc);
		}
	}
	
	/**
	 * Removes a pickup location by id
	 * 
	 * @param id       Id of the location to remove
	 */
	public void removePickupLocation(int id) {
		CustomLocation tmp = null;
		for(CustomLocation location : this.pickupLocations) {
			if(location.getId() == id)
				tmp = location;
		}
		if(tmp == null) return;
		int removedId = tmp.getId();
		pickupLocations.remove(tmp);
		for(CustomLocation cl : pickupLocations) {
			if(cl.getId() > removedId) {
				cl.setId(cl.getId()-1);
			}
		}
		System.gc();
	}
	
	/**
	 * @return      Id of the available slot (-1 if none exists)
	 */
	public int getAvailableSlot() {
		int slot = -1;
		for(int i = 0; i < players.length; i++) {
			if(players[i] == null)
				slot = i;
		}
		
		return slot;
	}
	
	/**
	 * @return      Random location from the CustomLocation list
	 */
	public CustomLocation getRandomLocation() {
		if (locations.size() == 0) {
			return null;
		}
		int i = rnd.nextInt(locations.size()-1+1)+0;
		return getLocation(i);
	}
	
	/**
	 * @return      Random pickup location from the CustomLocation list
	 */
	public CustomLocation getRandomPickupLocation() {
		if(pickupLocations.size() == 0) return null;
		int i = rnd.nextInt(pickupLocations.size()-1+1)+0;
		return getPickupLocation(i);
	}
	
	public void addPlayer(Player p) {
		if(getLocations().size() == 0) {
			p.sendMessage(main.getMessages().cantJoinMapBecauseNoLocations());
			return;
		}
		
		if(getTiers().length == 0) {
			p.sendMessage(main.getMessages().cantJoinMapBecauseNoTiers());
			return;
		}
		
		CustomPlayer cp = main.getPlayerManager().getPlayer(p);
		
		if(cp.getCurrentMap() != null) {
			p.sendMessage(main.getMessages().youAreAlreadyInAMap());
			return;
		}
		
		int availableSlot = this.getAvailableSlot();
		if(availableSlot == -1) {
			p.sendMessage(main.getMessages().noAvailableRoom());
        	return;
		}
		
		PlayerJoinMapEvent e = new PlayerJoinMapEvent(p, cp, this);
		Bukkit.getPluginManager().callEvent(e);
		if(e.isCancelled())
			return;
		
		this.getPlayers()[availableSlot] = cp;
		cp.setCurrentMap(this);
		cp.setCurrentTier(this.getTiers()[0]);
		cp.setCurrentTierId(0);
		cp.setKillstreak(0);

		p.teleport(this.getRandomLocation().getLoc());
		Utils.clearPlayer(main, p, healthOnJoin, hungerOnJoin, cp.getPlayer().getMaxHealth(), gamemode, spawnProtection);
		
		if(cp.getSkillLevel("EXTRA_HEALTH") > 0) {
			int maxHealth = 20+main.getSettingsManager().getExtraHealthAmount().get(cp.getSkillLevel("EXTRA_HEALTH"));
			cp.getPlayer().setMaxHealth(maxHealth);
			cp.getPlayer().setHealth(maxHealth);
		}
		
		if(cp.getSkillLevel("SPAWN_RESISTANCE") > 0)
			cp.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, main.getSettingsManager().getSpawnResistanceDuration().get(cp.getSkillLevel("SPAWN_RESISTANCE"))*20, 0));

		if(cp.getSkillLevel("SPAWN_GAPPLE") > 0) {
			Random rnd = new Random();
			int chance = rnd.nextInt(100-1+1)+1;
			if(chance <= main.getSettingsManager().getSpawnGappleRate().get(cp.getSkillLevel("SPAWN_GAPPLE")))
				cp.getPlayer().getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE));
		}
		
		main.getPacketHandler().getNMSHandler().sendTitle(p, main.getMessages().joiningArena(), "", 20, 40, 20);
		
		main.getScoreboardManager().removePlayer(cp);
		main.getScoreboardManager().addPlayer(cp);
	}
	
	public void removePlayer(Player p, LeaveReason reason) {
		CustomPlayer cp = main.getPlayerManager().getPlayer(p);
		
		if(cp.getCurrentMap() == null) {
			p.sendMessage(main.getMessages().youAreNotInAMap());
			return;
		}
		
		for(int i = 0; i < this.getPlayers().length; i++) {
			if(this.getPlayers()[i] == cp)
				this.getPlayers()[i] = null;
		}
		
		if(reason != LeaveReason.RELOAD) {
			PlayerLeaveMapEvent e = new PlayerLeaveMapEvent(reason, p, cp, this);
			Bukkit.getPluginManager().callEvent(e);
			if(e.isCancelled())
				return;
		}
		
		cp.setCurrentMap(null);
		cp.setCurrentTier(null);
		cp.setCurrentTierId(0);
		cp.setKillstreak(0);
		cp.setLeftMap(true);
		cp.getPlayer().setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
		cp.getPlayer().setMaxHealth(20);
		cp.getPlayer().getInventory().clear();
		
		if(this.main.getSettingsManager().getSpawn() == null) {
			cp.getPlayer().damage(cp.getPlayer().getMaxHealth()*20);
			cp.getPlayer().spigot().respawn();
		} else {
			cp.getPlayer().teleport(this.main.getSettingsManager().getSpawn());
			Utils.clearPlayer(main, p, 20, 20, maxHealth, gamemode, spawnProtection);
		}
		
		main.getPacketHandler().getNMSHandler().sendTitle(p, main.getMessages().leavingArena(), "", 20, 40, 20);
		
		main.getScoreboardManager().removePlayer(cp);
		main.getScoreboardManager().addPlayer(cp);
	}
}
