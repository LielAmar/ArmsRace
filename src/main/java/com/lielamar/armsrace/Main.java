package com.lielamar.armsrace;

import com.lielamar.armsrace.commands.ArmsRace;
import com.lielamar.armsrace.commands.Spawn;
import com.lielamar.armsrace.listeners.OnDurabilityChange;
import com.lielamar.armsrace.listeners.OnPlayerDeath;
import com.lielamar.armsrace.listeners.OnPlayerJoin;
import com.lielamar.armsrace.listeners.OnPlayerQuit;
import com.lielamar.armsrace.listeners.killeffects.OnCookiePickup;
import com.lielamar.armsrace.listeners.killeffects.OnDamageByLightning;
import com.lielamar.armsrace.listeners.killeffects.OnPinataPickup;
import com.lielamar.armsrace.listeners.killeffects.OnTNTKillEffect;
import com.lielamar.armsrace.listeners.map.*;
import com.lielamar.armsrace.listeners.shop.OnShopClick;
import com.lielamar.armsrace.listeners.skills.OnSwordLaunch;
import com.lielamar.armsrace.listeners.trails.OnProjectileShoot;
import com.lielamar.armsrace.modules.map.Map;
import com.lielamar.armsrace.utility.Messages;
import com.lielamar.armsrace.utility.Utils;
import com.lielamar.armsrace.utility.packets.PacketHandler;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.lielamar.armsrace.managers.CombatLogManager;
import com.lielamar.armsrace.managers.GameManager;
import com.lielamar.armsrace.managers.KillEffectsManager;
import com.lielamar.armsrace.managers.PlayerManager;
import com.lielamar.armsrace.managers.ScoreboardManager;
import com.lielamar.armsrace.managers.SettingsManager;
import com.lielamar.armsrace.managers.ShopManager;
import com.lielamar.armsrace.managers.files.BukkitFileManager;
import com.lielamar.armsrace.managers.files.MapsFileManager;
import com.lielamar.armsrace.modules.CustomPlayer;
import com.lielamar.armsrace.modules.map.Pickup;

public class Main extends JavaPlugin {
	
	private BukkitFileManager bfm; // File manager
	private Messages messages; // Messages instance
	private PacketHandler packetHandler;
	private SettingsManager settingsManager; // Settings manager instance
	private ShopManager shopManager; // Shop Manager instance
	private KillEffectsManager killEffectsManager; // Kill Effects Manager instance
	private GameManager gameManager; // Game Manager instance
	private MapsFileManager mapsFileManager; // Maps File Manager instance
	private PlayerManager playerManager; // Player manager
	private ScoreboardManager scoreboardManager; // Scoreboard manager
	private CombatLogManager combatlogManager; // Combat Log Manager
	
	@Override
	public void onEnable() {
		registerManagers();
		registerEvents();
		registerCommands();
		
		Location spawn = getSettingsManager().getSpawn();
		if(spawn != null) {
			for(Player pl : Bukkit.getOnlinePlayers()) {
				CustomPlayer cpl = playerManager.getPlayer(pl);
				scoreboardManager.addPlayer(cpl);
				Utils.clearPlayer(this, pl, 20, 20, 20, GameMode.ADVENTURE, 0);
				pl.teleport(spawn);
			}
		}
	}
	
	@Override
	public void onDisable() {
		for(ArmorStand as : this.playerManager.getSwordLaunchAs().values()) {
			as.remove();
		}
			
		for(Map map : gameManager.getMapManager().getMaps().values()) {
			for(Pickup pickup : map.getPickups()) {
				pickup.getPickup().remove();
			}
		}
		
		destoryManagers();
	}

	private void registerEvents() {
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new OnPlayerJoin(this), this);
		pm.registerEvents(new OnPlayerQuit(this), this);
		
		// General Events
		pm.registerEvents(new OnDurabilityChange(this), this);
		pm.registerEvents(new OnPlayerDeath(this), this);
		
		// Shop events
		pm.registerEvents(new OnShopClick(this), this);
	
		// Skills
		pm.registerEvents(new OnSwordLaunch(this), this);
		
		// Trails
		pm.registerEvents(new OnProjectileShoot(this), this);
		
		// Kill effects
		pm.registerEvents(new OnTNTKillEffect(this), this);
		pm.registerEvents(new OnCookiePickup(this), this);
		pm.registerEvents(new OnPinataPickup(this), this);
		pm.registerEvents(new OnDamageByLightning(this), this);
		
		// Per map events
		pm.registerEvents(new OnBlock(this), this);
		pm.registerEvents(new OnFoodChange(this), this);
		pm.registerEvents(new OnHealthRegenerate(this), this);
		pm.registerEvents(new OnPlayerKill(this), this);
		pm.registerEvents(new OnDrop(this), this);
		pm.registerEvents(new OnPickup(this), this);
		pm.registerEvents(new OnSpawnProtection(this), this);
		pm.registerEvents(new OnDoubleDamage(this), this);
	}
	
	private void registerCommands() {
		getCommand("armsrace").setExecutor(new ArmsRace(this));
		getCommand("spawn").setExecutor(new Spawn(this));
	}
	

	public void registerManagers() {
		saveDefaultConfig();
		
		this.bfm = new BukkitFileManager(this);

		this.messages = new Messages(this.bfm.getConfig("messages"));
		
		this.packetHandler = new PacketHandler();
		
		this.settingsManager = new SettingsManager(this);
		
		this.shopManager = new ShopManager(this, this.bfm.getConfig("shop"));
		
		this.killEffectsManager = new KillEffectsManager();
		
		this.mapsFileManager = new MapsFileManager(this);
		
		this.gameManager = new GameManager(this);	
		this.gameManager.getMapManager().loadMaps();
	
		this.playerManager = new PlayerManager(this);
		
		this.scoreboardManager = new ScoreboardManager(this);
		
		this.combatlogManager = new CombatLogManager(this);
	}
	
	private void destoryManagers() {
		saveDefaultConfig();
		
		this.bfm = null;

		this.messages = null;
		
		this.packetHandler = null;
		
		this.settingsManager = null;
		
		this.shopManager = null;
		
		this.killEffectsManager = null;
		
		this.mapsFileManager = null;
		
		this.gameManager = null;
		
		this.playerManager = null;
		
		this.scoreboardManager = null;
		
		this.combatlogManager = null;
	}
	
	public BukkitFileManager getBukkitFileManager() { return this.bfm; }
	public Messages getMessages() { return this.messages; }
	public PacketHandler getPacketHandler() { return this.packetHandler; }
	public SettingsManager getSettingsManager() { return this.settingsManager; }
	public ShopManager getShopManager() { return this.shopManager; }
	public KillEffectsManager getKillEffectsManager() { return this.killEffectsManager; }
	public MapsFileManager getMapsFileManager() { return this.mapsFileManager; }
	public GameManager getGameManager() { return this.gameManager; }
	public PlayerManager getPlayerManager() { return this.playerManager; }
	public ScoreboardManager getScoreboardManager() { return this.scoreboardManager; }
	public CombatLogManager getCombatLogManager() { return this.combatlogManager; }
}
