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
import org.bukkit.plugin.java.JavaPlugin;

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

        getServer().getPluginManager().registerEvents(new OnPlayerJoin(this), this);
        getServer().getPluginManager().registerEvents(new OnPlayerQuit(this), this);

        // General Events
        getServer().getPluginManager().registerEvents(new OnDurabilityChange(this), this);
        getServer().getPluginManager().registerEvents(new OnPlayerDeath(this), this);

        // Shop events
        getServer().getPluginManager().registerEvents(new OnShopClick(this), this);

        // Skills
        getServer().getPluginManager().registerEvents(new OnSwordLaunch(this), this);

        // Trails
        getServer().getPluginManager().registerEvents(new OnProjectileShoot(this), this);

        // Kill effects
        getServer().getPluginManager().registerEvents(new OnTNTKillEffect(this), this);
        getServer().getPluginManager().registerEvents(new OnCookiePickup(this), this);
        getServer().getPluginManager().registerEvents(new OnPinataPickup(this), this);
        getServer().getPluginManager().registerEvents(new OnDamageByLightning(this), this);
        getServer().getPluginManager().registerEvents(new OnDamageByFirework(this), this);

        // Per map events
        getServer().getPluginManager().registerEvents(new OnBlock(this), this);
        getServer().getPluginManager().registerEvents(new OnFoodChange(this), this);
        getServer().getPluginManager().registerEvents(new OnHealthRegenerate(this), this);
        getServer().getPluginManager().registerEvents(new OnPlayerKill(this), this);
        getServer().getPluginManager().registerEvents(new OnDrop(this), this);
        getServer().getPluginManager().registerEvents(new OnPickup(this), this);
        getServer().getPluginManager().registerEvents(new OnSpawnProtection(this), this);
        getServer().getPluginManager().registerEvents(new OnDoubleDamage(this), this);

        getCommand("armsrace").setExecutor(new ArmsRaceCommand(this));
        getCommand("spawn").setExecutor(new SpawnCommand(this));

        Bukkit.getScheduler().scheduleSyncDelayedTask(this, ArmsRaceHook::attemptHooks, 1L);

        playerManager.getPlayers().values().forEach(this::initPlayer);

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

    private void initPlayer(CustomPlayer customPlayer) {
        Location spawn = getSettingsManager().getSpawn();
        Player player = customPlayer.getPlayer();

        scoreboardManager.addPlayer(customPlayer);
        Utils.clearPlayer(this, player, 20, 20, 20, GameMode.SURVIVAL);

        if (spawn != null) {
            player.teleport(spawn);
        }
    }

    private void destroyManagers() {
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
