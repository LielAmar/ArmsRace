package com.lielamar.armsrace;

import com.lielamar.armsrace.api.ArmsRaceAPI;
import com.lielamar.armsrace.commands.ArmsRaceCommand;
import com.lielamar.armsrace.commands.SpawnCommand;
import com.lielamar.armsrace.hook.ArmsRaceHook;
import com.lielamar.armsrace.listeners.OnDurabilityChange;
import com.lielamar.armsrace.listeners.OnPlayerDeath;
import com.lielamar.armsrace.listeners.OnPlayerJoin;
import com.lielamar.armsrace.listeners.OnPlayerQuit;
import com.lielamar.armsrace.listeners.killeffects.*;
import com.lielamar.armsrace.listeners.map.*;
import com.lielamar.armsrace.listeners.shop.OnShopClick;
import com.lielamar.armsrace.listeners.skills.OnSwordLaunch;
import com.lielamar.armsrace.listeners.trails.OnProjectileShoot;
import com.lielamar.armsrace.managers.*;
import com.lielamar.armsrace.managers.files.BukkitFileManager;
import com.lielamar.armsrace.managers.files.MapsFileManager;
import com.lielamar.armsrace.modules.CustomPlayer;
import com.lielamar.armsrace.modules.map.Map;
import com.lielamar.armsrace.modules.map.Pickup;
import com.lielamar.armsrace.utility.Messages;
import com.lielamar.armsrace.utility.Utils;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class Main extends JavaPlugin {

    private BukkitFileManager bfm; // File manager
    @Getter
    private Messages messages; // Messages instance

    @Getter
    private static ArmsRaceAPI armsRaceAPI;

    @Getter
    private SettingsManager settingsManager; // Settings manager instance
    @Getter
    private ShopManager shopManager; // Shop Manager instance
    @Getter
    private KillEffectsManager killEffectsManager; // Kill Effects Manager instance
    @Getter
    private GameManager gameManager; // Game Manager instance
    @Getter
    private MapsFileManager mapsFileManager; // Maps File Manager instance
    @Getter
    private PlayerManager playerManager; // Player manager
    @Getter
    private ScoreboardManager scoreboardManager; // Scoreboard manager
    private CombatLogManager combatlogManager; // Combat Log Manager

    @Override
    public void onEnable() {

        armsRaceAPI = new ArmsRaceAPI();

        registerManagers();
        registerEvents();
        registerCommands();
        registerHook();
        initPlayers();

    }

    @Override
    public void onDisable() {
        for (ArmorStand armorStand : this.playerManager.getSwordLaunchAs().values()) {
            armorStand.remove();
        }

        for (Map map : gameManager.getMapManager().getMaps().values()) {
            for (Pickup pickup : map.getPickups()) {
                pickup.getPickup().remove();
            }
        }

        destroyManagers();
    }

    public void registerManagers() {
        saveDefaultConfig();

        this.bfm = new BukkitFileManager(this);
        this.messages = new Messages(this.bfm.getConfig("messages"));
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

    private void registerEvents() {
        PluginManager manager = Bukkit.getPluginManager();

        // Default Events
        manager.registerEvents(new OnPlayerJoin(this), this);
        manager.registerEvents(new OnPlayerQuit(this), this);

        // General Events
        manager.registerEvents(new OnDurabilityChange(this), this);
        manager.registerEvents(new OnPlayerDeath(this), this);

        // Shop events
        manager.registerEvents(new OnShopClick(this), this);

        // Skills
        manager.registerEvents(new OnSwordLaunch(this), this);

        // Trails
        manager.registerEvents(new OnProjectileShoot(this), this);

        // Kill effects
        manager.registerEvents(new OnTNTKillEffect(this), this);
        manager.registerEvents(new OnCookiePickup(this), this);
        manager.registerEvents(new OnPinataPickup(this), this);
        manager.registerEvents(new OnDamageByLightning(this), this);
        manager.registerEvents(new OnDamageByFirework(this), this);

        // Per map events
        manager.registerEvents(new OnBlock(this), this);
        manager.registerEvents(new OnFoodChange(this), this);
        manager.registerEvents(new OnHealthRegenerate(this), this);
        manager.registerEvents(new OnPlayerKill(this), this);
        manager.registerEvents(new OnDrop(this), this);
        manager.registerEvents(new OnPickup(this), this);
        manager.registerEvents(new OnSpawnProtection(this), this);
        manager.registerEvents(new OnDoubleDamage(this), this);
    }

    private void registerCommands() {
        Objects.requireNonNull(getCommand("armsrace")).setExecutor(new ArmsRaceCommand(this));
        Objects.requireNonNull(getCommand("spawn")).setExecutor(new SpawnCommand(this));
    }

    private void registerHook() {
        ArmsRaceHook.attemptHooks();
    }

    private void initPlayers() {
        playerManager.getPlayers().values().forEach(this::initPlayer);
    }

    private void initPlayer(CustomPlayer customPlayer) {
        Location spawn = getSettingsManager().getSpawn();
        Player player = customPlayer.getPlayer();

        scoreboardManager.addPlayer(customPlayer);
        Utils.clearPlayer(this, player, 20, 20, 20, GameMode.ADVENTURE);

        if (spawn != null) {
            player.teleport(spawn);
        }
    }

    private void destroyManagers() {
        saveDefaultConfig();

        this.bfm = null;
        this.messages = null;
        this.settingsManager = null;
        this.shopManager = null;
        this.killEffectsManager = null;
        this.mapsFileManager = null;
        this.gameManager = null;
        this.playerManager = null;
        this.scoreboardManager = null;
        this.combatlogManager = null;
    }

    public BukkitFileManager getBukkitFileManager() {
        return this.bfm;
    }

    public CombatLogManager getCombatLogManager() {
        return this.combatlogManager;
    }
}
