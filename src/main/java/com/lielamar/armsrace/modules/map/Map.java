package com.lielamar.armsrace.modules.map;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.cryptomorin.xseries.XPotion;
import com.cryptomorin.xseries.messages.Titles;
import com.lielamar.armsrace.api.events.PlayerJoinMapEvent;
import com.lielamar.armsrace.api.events.PlayerLeaveMapEvent;
import com.lielamar.armsrace.utility.Utils;
import lombok.Getter;
import lombok.Setter;
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

	private final Main main;
	@Setter
    private String name;
	private final Random rnd;
	@Setter
    @Getter
    private CustomPlayer[] players;
	@Getter
    @Setter
    private List<CustomLocation> locations;
	@Getter
    @Setter
    private List<CustomLocation> pickupLocations;
	@Getter
    @Setter
    private List<Pickup> pickups;
	@Setter
    @Getter
    private List<Killstreak> killstreak;
	@Getter
    @Setter
    private Tier[] tiers;
	@Setter
    private CustomScoreboard scoreboard;

	@Setter
    @Getter
    private CustomPlayer highestTier;
	@Setter
    @Getter
    private CustomPlayer highestKillstreak;

	@Setter
    @Getter
    private int healthOnKill;
	@Setter
    @Getter
    private boolean playersRegen;
	@Setter
    @Getter
    private boolean foodLevelChange;
	@Setter
    @Getter
    private int healthOnJoin;
	@Setter
    @Getter
    private int hungerOnJoin;
	@Getter
    @Setter
    private int maxHealth;
	@Setter
    @Getter
    private GameMode gamemode;
	@Setter
    private double coinsPerKill;
	@Setter
    @Getter
    private int spawnProtection;

	@Getter
    @Setter
    private boolean healingEnabled, doubleDamageEnabled, coinsEnabled, speedEnabled, resistanceEnabled, tierUpEnabled, oneTapEnabled;

	@Getter
    @Setter
    private int minimumMinutesForPickupSpawn;
	@Setter
    @Getter
    private int maximumMinutesForPickupSpawn;

	@Getter
    @Setter
    private double healthPerPickup;
	@Setter
    @Getter
    private double doubleDamageDuration;
	@Getter
    @Setter
    private double coinsPerPickup;
	@Setter
    @Getter
    private int speedLevel;
	@Setter
    @Getter
    private double speedDuration;
	@Getter
    @Setter
    private int resistanceLevel;
	@Getter
    @Setter
    private double resistanceDuration;
	@Setter
    @Getter
    private int tiersUpAmount;
	@Setter
    @Getter
    private double oneTapDuration;

	@Getter
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
		this.pickups = new LinkedList<>();
		this.tiers = tiers;
		this.scoreboard = sb;

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
		return (name.toLowerCase().endsWith(".yml") ? name.toLowerCase().substring(0, name.length() - 4) : name.toLowerCase());
	}

    public Killstreak getKillstreak(int level) {
		for (Killstreak ks : killstreak) {
			if (level == ks.getLevel()) return ks;
		}
		return null;
	}

    public CustomScoreboard getCustomScoreboard() {
		return scoreboard;
	}


    public double getCoinsPerKill() {
		if (isDoubleCoinsEvent())
			return 2 * coinsPerKill;
		return coinsPerKill;
	}

    public boolean setDoubleCoinsEvent(boolean doubleCoinsEvent) {
		this.doubleCoinsEvent = doubleCoinsEvent;
		return doubleCoinsEvent;
	}

    public boolean setDoubleTiersEvent(boolean doubleTiersEvent) {
		this.doubleTiersEvent = doubleTiersEvent;
		return doubleTiersEvent;
	}

	public CustomLocation getLocation(int id) {
		for (CustomLocation cl : locations) {
			if (cl.getId() == id) return cl;
		}
		return null;
	}

	public CustomLocation getPickupLocation(int id) {
		for (CustomLocation cl : pickupLocations) {
			if (cl.getId() == id) return cl;
		}
		return null;
	}

	/**
	 * Starts the pickup timer (spawns a pickup in random times)
	 */
	public void startPickupTimer() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(main, new Runnable() {

			int time = rnd.nextInt((maximumMinutesForPickupSpawn * 60) - (minimumMinutesForPickupSpawn * 60) + 1) + (minimumMinutesForPickupSpawn * 60);

			@Override
			public void run() {
				time = time - 1;
				if (time <= 0) {
					addPickup();
					time = rnd.nextInt((maximumMinutesForPickupSpawn * 60) - (minimumMinutesForPickupSpawn * 60) + 1) + (minimumMinutesForPickupSpawn * 60);
				}
			}
		}, 0L, 20L);
	}

	/**
	 * @param type The type of pickup we want to check
	 * @return Is the pickup type valid
	 */
	public boolean isValidType(int type) {
		if (type == 1 && !healingEnabled) return false;
		if (type == 2 && !doubleDamageEnabled) return false;
		if (type == 3 && !coinsEnabled) return false;
		if (type == 4 && !speedEnabled) return false;
		if (type == 5 && !resistanceEnabled) return false;
		if (type == 6 && !tierUpEnabled) return false;
		if (type == 7 && !oneTapEnabled) return false;
		return true;
	}

	public void addPickup() {
		int type = rnd.nextInt(7 - 1 + 1) + 1;
		CustomLocation randomLoc = getRandomPickupLocation();
		if (randomLoc == null) return;

		List<Pickup> removePickups = new ArrayList<Pickup>();
		for (Pickup pickup : pickups) {
			if (pickup == null) removePickups.add(pickup);
			if (pickup.getLoc() == null) removePickups.add(pickup);
			if (pickup.getLoc() != randomLoc.getLocation()) {
				removePickups.add(pickup);
			}
		}

		for (Pickup pickup : removePickups) {
			pickup.getPickup().remove();
			pickups.remove(pickup);
		}

		while (!isValidType(type)) {
			type = rnd.nextInt(7 - 1 + 1) + 1;
		}

		Pickup pickup = new Pickup(this, PickupType.HEALTH, randomLoc.getLocation());
		if (type == 1) {
			pickup = new Pickup(this, PickupType.HEALTH, randomLoc.getLocation());
		}
		if (type == 2) {
			pickup = new Pickup(this, PickupType.DOUBLE_DAMAGE, randomLoc.getLocation());
		}
		if (type == 3) {
			pickup = new Pickup(this, PickupType.COINS, randomLoc.getLocation());
		}
		if (type == 4) {
			pickup = new Pickup(this, PickupType.SPEED, randomLoc.getLocation());
		}
		if (type == 5) {
			pickup = new Pickup(this, PickupType.RESISTANCE, randomLoc.getLocation());
		}
		if (type == 6) {
			pickup = new Pickup(this, PickupType.TIER_UP, randomLoc.getLocation());
		}
		if (type == 7) {
			pickup = new Pickup(this, PickupType.ONE_TAP, randomLoc.getLocation());
		}

		pickup.spawn();
		pickups.add(pickup);
		for (CustomPlayer cp : players) {
			if (cp != null) {
				cp.getPlayer().sendMessage(main.getMessages().pickupSpawned(pickup.getName()));
			}
		}
	}

	/**
	 * Adds a new Tier
	 *
	 * @param armor   Armor to set to the tier
	 * @param content Content to set to the tier
	 */
	public void addTier(ItemStack[] armor, ItemStack[] content) {
		Tier[] newTiers = new Tier[this.tiers.length + 1];

		int i = 0;
		for (i = 0; i < this.tiers.length; i++) {
			newTiers[i] = tiers[i];
		}
		newTiers[i] = new Tier(armor, content);
		this.tiers = newTiers;
	}

	/**
	 * Sets a tier by id
	 *
	 * @param tier    Id of the tier to edit
	 * @param armor   Armor to set to the tier
	 * @param content Content to set to the tier
	 */
	public void setTier(int tier, ItemStack[] armor, ItemStack[] content) {
		this.tiers[tier] = new Tier(armor, content);
	}

	/**
	 * Removes a tier by id
	 *
	 * @param tier Id of tier to remove
	 */
	public void removeTier(int tier) {
		Tier[] newTiers = new Tier[this.tiers.length - 1];

		int counter = 0;
		for (int i = 0; i < this.tiers.length; i++) {
			if (i == tier)
				continue;

			newTiers[counter] = tiers[i];
			counter++;
		}
		this.tiers = newTiers;
	}

	/**
	 * Adds a new location by id
	 *
	 * @param id       Id of the location to add
	 * @param location Location to attach to the id
	 */
	public void addLocation(int id, Location location) {
		locations.add(new CustomLocation(id, location));
	}

	/**
	 * Sets a location by id
	 *
	 * @param id  Id of the location to edit
	 * @param loc Location to attack to the id
	 */
	public void setLocation(int id, Location loc) {
		for (CustomLocation location : this.locations) {
			if (location.getId() == id)
				location.setLocation(loc);
		}
	}

	/**
	 * Removes a location by id
	 *
	 * @param id Id of the location to remove
	 */
	public void removeLocation(int id) {
		CustomLocation tmp = null;
		for (CustomLocation location : this.locations) {
			if (location.getId() == id)
				tmp = location;
		}
		if (tmp == null) return;
		int removedId = tmp.getId();
		locations.remove(tmp);
		for (CustomLocation cl : locations) {
			if (cl.getId() > removedId) {
				cl.setId(cl.getId() - 1);
			}
		}
		System.gc();
	}

	/**
	 * Adds a new pickup location by id
	 *
	 * @param id       Id of the new location
	 * @param location Location to attach to the id
	 */
	public void addPickupLocation(int id, Location location) {
		pickupLocations.add(new CustomLocation(id, location));
	}

	/**
	 * Sets a pickup location by id
	 *
	 * @param id  Id of the location to edit
	 * @param loc Location to attack to the id
	 */
	public void setPickupLocation(int id, Location loc) {
		for (CustomLocation location : this.pickupLocations) {
			if (location.getId() == id)
				location.setLocation(loc);
		}
	}

	/**
	 * Removes a pickup location by id
	 *
	 * @param id Id of the location to remove
	 */
	public void removePickupLocation(int id) {
		CustomLocation tmp = null;
		for (CustomLocation location : this.pickupLocations) {
			if (location.getId() == id)
				tmp = location;
		}
		if (tmp == null) return;
		int removedId = tmp.getId();
		pickupLocations.remove(tmp);
		for (CustomLocation cl : pickupLocations) {
			if (cl.getId() > removedId) {
				cl.setId(cl.getId() - 1);
			}
		}
		System.gc();
	}

	/**
	 * @return Id of the available slot (-1 if none exists)
	 */
	public int getAvailableSlot() {
		int slot = -1;
		for (int i = 0; i < players.length; i++) {
			if (players[i] == null)
				slot = i;
		}

		return slot;
	}

	/**
	 * @return Random location from the CustomLocation list
	 */
	public CustomLocation getRandomLocation() {
		if (locations.isEmpty()) {
			return null;
		}
		int i = rnd.nextInt(locations.size() - 1 + 1);
		return getLocation(i);
	}

	/**
	 * @return Random pickup location from the CustomLocation list
	 */
	public CustomLocation getRandomPickupLocation() {
		if (pickupLocations.isEmpty()) return null;
		int i = rnd.nextInt(pickupLocations.size() - 1 + 1);
		return getPickupLocation(i);
	}

	public void addPlayer(Player p) {
		if (getLocations().isEmpty()) {
			p.sendMessage(main.getMessages().cantJoinMapBecauseNoLocations());
			return;
		}

		if (getTiers().length == 0) {
			p.sendMessage(main.getMessages().cantJoinMapBecauseNoTiers());
			return;
		}

		CustomPlayer cp = main.getPlayerManager().getPlayer(p);

		if (cp.getCurrentMap() != null) {
			p.sendMessage(main.getMessages().youAreAlreadyInAMap());
			return;
		}

		int availableSlot = this.getAvailableSlot();
		if (availableSlot == -1) {
			p.sendMessage(main.getMessages().noAvailableRoom());
			return;
		}

		PlayerJoinMapEvent e = new PlayerJoinMapEvent(p, cp, this);
		Bukkit.getPluginManager().callEvent(e);
		if (e.isCancelled())
			return;

		this.getPlayers()[availableSlot] = cp;
		cp.setCurrentMap(this);
		cp.setCurrentTier(this.getTiers()[0]);
		cp.setCurrentTierId(0);
		cp.setKillstreak(0);

		p.teleport(this.getRandomLocation().getLocation());
		Utils.clearPlayer(main, p, healthOnJoin, hungerOnJoin, cp.getPlayer().getMaxHealth(), gamemode);

		if (cp.getSkillLevel("EXTRA_HEALTH") > 0) {
			int maxHealth = 20 + main.getSettingsManager().getExtraHealthAmount().get(cp.getSkillLevel("EXTRA_HEALTH"));
			cp.getPlayer().setMaxHealth(maxHealth);
			cp.getPlayer().setHealth(maxHealth);
		}

		if (cp.getSkillLevel("SPAWN_RESISTANCE") > 0)
			cp.getPlayer().addPotionEffect(new PotionEffect(XPotion.RESISTANCE.getPotionEffectType(), main.getSettingsManager().getSpawnResistanceDuration().get(cp.getSkillLevel("SPAWN_RESISTANCE")) * 20, 0));

		if (cp.getSkillLevel("SPAWN_GAPPLE") > 0) {
			Random rnd = new Random();
			int chance = rnd.nextInt(100 - 1 + 1) + 1;
			if (chance <= main.getSettingsManager().getSpawnGappleRate().get(cp.getSkillLevel("SPAWN_GAPPLE")))
				cp.getPlayer().getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE));
		}

		Titles.sendTitle(p, 5, 20, 5, main.getMessages().joiningArena(), "");

		main.getScoreboardManager().removePlayer(cp);
		main.getScoreboardManager().addPlayer(cp);
	}

	public void removePlayer(Player p, PlayerLeaveMapEvent.LeaveReason reason) {
		CustomPlayer cp = main.getPlayerManager().getPlayer(p);

		if (cp.getCurrentMap() == null) {
			p.sendMessage(main.getMessages().youAreNotInAMap());
			return;
		}

		for (int i = 0; i < this.getPlayers().length; i++) {
			if (this.getPlayers()[i] == cp)
				this.getPlayers()[i] = null;
		}

		if (reason != PlayerLeaveMapEvent.LeaveReason.RELOAD) {
			PlayerLeaveMapEvent e = new PlayerLeaveMapEvent(reason, p, cp, this);
			Bukkit.getPluginManager().callEvent(e);
			if (e.isCancelled())
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

		if (this.main.getSettingsManager().getSpawn() == null) {
			cp.getPlayer().damage(cp.getPlayer().getMaxHealth() * 20);
			cp.getPlayer().spigot().respawn();
		} else {
			cp.getPlayer().teleport(this.main.getSettingsManager().getSpawn());
			Utils.clearPlayer(main, p, 20, 20, maxHealth, gamemode);
		}

		Titles.sendTitle(p, 5, 20, 5, main.getMessages().leavingArena(), "");

		main.getScoreboardManager().removePlayer(cp);
		main.getScoreboardManager().addPlayer(cp);
	}
}
