package com.lielamar.armsrace.utility;

import org.bukkit.ChatColor;
import org.bukkit.Location;

import com.lielamar.armsrace.managers.files.BukkitFileManager.Config;
import com.lielamar.armsrace.modules.map.Killstreak;

public class Messages {

	// This class is managing the messages. They are being loaded at the startup & reloaded after reloading the config.
	// Messages are being updated only once and then you can simply get them via methods

	private final String mustBePlayer;
	private final String youAreStillInCooldown;
	private final String notEnoughCoins;
	private final String noPermissions;
	private final String couldntFindMap;
	private final String armsraceHelp;
	private final String invalidSubCommand;
	private final String invalidPageNumber;
	private final String invalidArgument;
	private final String cantJoinMapBecauseNoLocations;
	private final String cantJoinMapBecauseNoTiers;
	private final String youAreAlreadyInAMap;
	private final String youAreNotInAMap;
	private final String cantHitPlayersOutsideMap;
	private final String cantHitPlayersDifferentMap;
	private final String noAvailableRoom;
	private final String joiningArena;
	private final String leavingArena;
	private final String youReceivedCoinsForKilling;
	private final String youWerePromoted;
	private final String youWereDemoted;
	private final String youWereRespawned;
	private final String kill;
	private final String death;
	private final String pickupSpawned;
	private final String resistancePickedUp;
	private final String doubleDamagePickedUp;
	private final String speedPickedUp;
	private final String doubleDamageEnded;
	private final String onetapPickedUp;
	private final String onetapEnded;
	private final String tiersupPickedUp;
	private final String coinsPickedUp;
	private final String healthPickedUp;
	private final String killStreakReward;
	private final String eventDisabled;
	private final String eventEnabled;
	private final String eventDisabledAnnounce;
	private final String eventEnabledAnnounce;
	private final String purchasedSkill;
	private final String purchasedKillEffect;
	private final String killEffectChangedTo;
	private final String purchasedTrail;
	private final String trailChangedTo;
	private final String spawnSet;
	private final String youAreNowInCombat;
	private final String noLongerInCombat;
	private final String youAreStillInCombat;
	private final String teleportingToSpawn;

	public Messages(Config config) {
		this.mustBePlayer = ChatColor.translateAlternateColorCodes('&', ((String)config.get("MustBePlayer")));
		this.youAreStillInCooldown = ChatColor.translateAlternateColorCodes('&', ((String)config.get("YouAreStillInCooldown")));
		this.notEnoughCoins = ChatColor.translateAlternateColorCodes('&', ((String)config.get("NotEnoughCoins")));
		this.noPermissions = ChatColor.translateAlternateColorCodes('&', ((String)config.get("NoPermissions")));
		this.couldntFindMap = ChatColor.translateAlternateColorCodes('&', ((String)config.get("CouldntFindMap")));
		this.armsraceHelp = ChatColor.translateAlternateColorCodes('&', ((String)config.get("ArmsraceHelp")));
		this.invalidSubCommand = ChatColor.translateAlternateColorCodes('&', ((String)config.get("InvalidSubCommand")));
		this.invalidPageNumber = ChatColor.translateAlternateColorCodes('&', ((String)config.get("InvalidPageNumber")));
		this.invalidArgument = ChatColor.translateAlternateColorCodes('&', ((String)config.get("InvalidArgument")));
		this.cantJoinMapBecauseNoLocations = ChatColor.translateAlternateColorCodes('&', ((String)config.get("CantJoinMapBecauseNoLocations")));
		this.cantJoinMapBecauseNoTiers = ChatColor.translateAlternateColorCodes('&', ((String)config.get("CantJoinMapBecauseNoTiers")));
		this.youAreAlreadyInAMap = ChatColor.translateAlternateColorCodes('&', ((String)config.get("YouAreAlreadyInAMap")));
		this.youAreNotInAMap = ChatColor.translateAlternateColorCodes('&', ((String)config.get("YouAreNotInAMap")));
		this.cantHitPlayersOutsideMap = ChatColor.translateAlternateColorCodes('&', ((String)config.get("CantHitPlayersOutsideMap")));
		this.cantHitPlayersDifferentMap = ChatColor.translateAlternateColorCodes('&', ((String)config.get("CantHitPlayersDifferentMap")));
		this.noAvailableRoom = ChatColor.translateAlternateColorCodes('&', ((String)config.get("NoAvailableRoom")));
		this.joiningArena = ChatColor.translateAlternateColorCodes('&', ((String)config.get("JoiningArenaTitle")));
		this.leavingArena = ChatColor.translateAlternateColorCodes('&', ((String)config.get("LeavingArenaTitle")));
		this.youReceivedCoinsForKilling = ChatColor.translateAlternateColorCodes('&', ((String)config.get("YouReceivedCoinsForKilling")));
		this.youWerePromoted = ChatColor.translateAlternateColorCodes('&', ((String)config.get("YouWerePromoted")));
		this.youWereDemoted = ChatColor.translateAlternateColorCodes('&', ((String)config.get("YouWereDemoted")));
		this.youWereRespawned = ChatColor.translateAlternateColorCodes('&', ((String)config.get("YouWereRespawned")));
		this.kill = ChatColor.translateAlternateColorCodes('&', ((String)config.get("Kill")));
		this.death = ChatColor.translateAlternateColorCodes('&', ((String)config.get("Death")));
		this.pickupSpawned = ChatColor.translateAlternateColorCodes('&', ((String)config.get("PickupSpawned")));
		this.resistancePickedUp = ChatColor.translateAlternateColorCodes('&', ((String)config.get("ResistancePickedUp")));
		this.doubleDamagePickedUp = ChatColor.translateAlternateColorCodes('&', ((String)config.get("DoubleDamagePickedUp")));
		this.speedPickedUp = ChatColor.translateAlternateColorCodes('&', ((String)config.get("SpeedPickedUp")));
		this.doubleDamageEnded =  ChatColor.translateAlternateColorCodes('&', ((String)config.get("DoubleDamageEnded")));
		this.onetapPickedUp = ChatColor.translateAlternateColorCodes('&', ((String)config.get("OneTapPickedUp")));
		this.onetapEnded = ChatColor.translateAlternateColorCodes('&', ((String)config.get("OneTapEnded")));
		this.tiersupPickedUp = ChatColor.translateAlternateColorCodes('&', ((String)config.get("TierUpPickedUp")));
		this.coinsPickedUp = ChatColor.translateAlternateColorCodes('&', ((String)config.get("CoinsPickedUp")));
		this.healthPickedUp = ChatColor.translateAlternateColorCodes('&', ((String)config.get("HealthPickedUp")));
		this.killStreakReward = ChatColor.translateAlternateColorCodes('&', ((String)config.get("KillStreakReward")));
		this.eventDisabled = ChatColor.translateAlternateColorCodes('&', ((String)config.get("EventDisabled")));
		this.eventEnabled = ChatColor.translateAlternateColorCodes('&', ((String)config.get("EventEnabled")));
		this.eventDisabledAnnounce = ChatColor.translateAlternateColorCodes('&', ((String)config.get("EventDisabledAnnounce")));
		this.eventEnabledAnnounce = ChatColor.translateAlternateColorCodes('&', ((String)config.get("EventEnabledAnnounce")));
		this.purchasedSkill = ChatColor.translateAlternateColorCodes('&', ((String)config.get("PurchasedSkill")));
		this.purchasedKillEffect = ChatColor.translateAlternateColorCodes('&', ((String)config.get("PurchasedKillEffect")));
		this.killEffectChangedTo = ChatColor.translateAlternateColorCodes('&', ((String)config.get("KillEffectChangedTo")));
		this.purchasedTrail = ChatColor.translateAlternateColorCodes('&', ((String)config.get("PurchasedTrail")));
		this.trailChangedTo = ChatColor.translateAlternateColorCodes('&', ((String)config.get("TrailChangedTo")));
		this.spawnSet = ChatColor.translateAlternateColorCodes('&', ((String)config.get("SpawnSet")));
		this.youAreNowInCombat = ChatColor.translateAlternateColorCodes('&', ((String)config.get("YouAreNowInCombat")));
		this.noLongerInCombat = ChatColor.translateAlternateColorCodes('&', ((String)config.get("NoLongerInCombat")));
		this.youAreStillInCombat = ChatColor.translateAlternateColorCodes('&', ((String)config.get("YouAreStillInCombat")));
		this.teleportingToSpawn = ChatColor.translateAlternateColorCodes('&', ((String)config.get("TeleportingToSpawn")));
	}

	public String mustBePlayer() { return mustBePlayer; }
	public String youAreStillInCooldown(int seconds) { return youAreStillInCooldown.replaceAll("%seconds%", seconds + ""); }
	public String notEnoughCoins(double coins) { return notEnoughCoins.replaceAll("%coins%", coins + ""); }
	public String noPermissions() { return noPermissions; }
	public String couldntFindMap(String mapName) { return couldntFindMap.replaceAll("%mapname%", mapName); }
	public String armsraceHelp() { return armsraceHelp; }
	public String invalidSubCommand() { return invalidSubCommand; }
	public String invalidPageNumber() { return invalidPageNumber; }
	public String invalidArgument() { return invalidArgument; }
	public String cantJoinMapBecauseNoLocations() { return cantJoinMapBecauseNoLocations; }
	public String cantJoinMapBecauseNoTiers() { return cantJoinMapBecauseNoTiers; }
	public String youAreAlreadyInAMap() { return youAreAlreadyInAMap; }
	public String youAreNotInAMap() { return youAreNotInAMap; }
	public String cantHitPlayersOutsideMap() { return cantHitPlayersOutsideMap; }
	public String cantHitPlayersDifferentMap() { return cantHitPlayersDifferentMap; }
	public String noAvailableRoom() { return noAvailableRoom; }
	public String joiningArena() { return joiningArena; }
	public String leavingArena() { return leavingArena; }
	public String youReceivedCoinsForKilling(double coins, String victim) { return youReceivedCoinsForKilling.replaceAll("%coins%", coins + "").replaceAll("%victim%", victim); }
	public String youWerePromoted(int tier) { return youWerePromoted.replaceAll("%tier%", tier + ""); }
	public String youWereDemoted(int tier) { return youWereDemoted.replaceAll("%tier%", tier + ""); }
	public String youWereRespawned() { return youWereRespawned; }
	public String kill(String victim) { return kill.replaceAll("%victim%", victim); }
	public String death() { return death; }
	public String pickupSpawned(String pickup) { return pickupSpawned.replaceAll("%pickup%", pickup).replaceAll("%pickupraw%", ChatColor.stripColor(pickup)); }
	public String resistancePickedUp(double time, int level) { return resistancePickedUp.replaceAll("%time%", time + "").replaceAll("%level%", level + ""); }
	public String doubleDamagePickedUp(double time) { return doubleDamagePickedUp.replaceAll("%time%", time + ""); }
	public String speedPickedUp(double time, int level) { return speedPickedUp.replaceAll("%time%", time + "").replaceAll("%level%", level + ""); }
	public String doubleDamageEnded() { return doubleDamageEnded; }
	public String onetapPickedUp(double time) { return onetapPickedUp.replaceAll("%time%", time + ""); }
	public String onetapEnded() { return onetapEnded; }
	public String tiersupPickedUp(int amount) { return tiersupPickedUp.replaceAll("%amount%", amount + "");}
	public String coinsPickedUp(double coins) { return coinsPickedUp.replaceAll("%coins%", coins + ""); }
	public String healthPickedUp(double health) { return healthPickedUp.replaceAll("%health%", health + ""); }
	public String killStreakReward(Killstreak ks) { return killStreakReward.replaceAll("%coins%", ks.getCoins() + "").replaceAll("%level%", ks.getLevel() + ""); }
	public String eventDisabled(String event) { return eventDisabled.replaceAll("%event%", event); }
	public String eventEnabled(String event) { return eventEnabled.replaceAll("%event%", event); }
	public String eventDisabledAnnounce(String event) { return eventDisabledAnnounce.replaceAll("%event%", event); }
	public String eventEnabledAnnounce(String event) { return eventEnabledAnnounce.replaceAll("%event%", event); }
	public String purchasedSkill(String name, int level, double coins) { return purchasedSkill.replaceAll("%name%", name).replaceAll("%level%", level + "").replaceAll("%coins%", coins + ""); }
	public String purchasedKillEffect(String killeffect, double coins) { return purchasedKillEffect.replaceAll("%killeffect%", killeffect).replaceAll("%coins%", coins + ""); }
	public String killEffectChangedTo(String killeffect) { return killEffectChangedTo.replaceAll("%killeffect%", killeffect); }
	public String purchasedTrail(String trail, double coins) { return purchasedTrail.replaceAll("%trail%", trail).replaceAll("%coins%", coins + ""); }
	public String trailChangedTo(String trail) { return trailChangedTo.replaceAll("%trail%", trail); }
	public String spawnSet(Location loc) { return spawnSet.replaceAll("%x%", "" + Utils.fixDecimal(loc.getX())).replaceAll("%y%", "" + Utils.fixDecimal(loc.getY())).replaceAll("%z%", "" + Utils.fixDecimal(loc.getZ())); }
	public String youAreNowInCombat() { return youAreNowInCombat; }
	public String noLongerInCombat() { return noLongerInCombat; }
	public String youAreStillInCombat(int duration) { return youAreStillInCombat.replace("%duration%", duration + ""); }
	public String teleportingToSpawn() { return teleportingToSpawn; }
}
