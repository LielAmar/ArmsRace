package com.lielamar.armsrace;

import com.lielamar.armsrace.api.ArmsRaceAPI;
import com.lielamar.armsrace.bootstrap.Injector;
import com.lielamar.armsrace.commands.ArmsRaceCommand;
import com.lielamar.armsrace.commands.SpawnCommand;
import com.lielamar.armsrace.dependency.DependencyData;
import com.lielamar.armsrace.dependency.RelocationHandler;
import com.lielamar.armsrace.dependency.Repository;
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
import com.lielamar.armsrace.nms.NMS;
import com.lielamar.armsrace.utility.Messages;
import com.lielamar.armsrace.utility.NMSUtils;
import com.lielamar.armsrace.utility.Utils;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main extends JavaPlugin {

    private BukkitFileManager bfm; // File manager
    private Messages messages; // Messages instance

    @Getter
    private static ArmsRaceAPI armsRaceAPI;

    @Getter
    private NMS nmsHandler;

    private static final DependencyData DEPENDENCIES = DependencyData.builder()

            .dependency("com.github.cryptomorin", "XSeries", "8.6.1")
            .relocate("com#cryptomorin#xseries", "xseries")
            .build();

    private SettingsManager settingsManager; // Settings manager instance
    private ShopManager shopManager; // Shop Manager instance
    private KillEffectsManager killEffectsManager; // Kill Effects Manager instance
    private GameManager gameManager; // Game Manager instance
    private MapsFileManager mapsFileManager; // Maps File Manager instance
    private PlayerManager playerManager; // Player manager
    private ScoreboardManager scoreboardManager; // Scoreboard manager
    private CombatLogManager combatlogManager; // Combat Log Manager

    private final Injector injector = Injector.Factory.create((URLClassLoader) getClassLoader());
    private final RelocationHandler relocationHandler;
    private final File librariesFolder = new File(getDataFolder(), "libraries");
    private final boolean informAboutLibraries;

    public void onLoad() {
        try {
            if (informAboutLibraries)
                getLogger().info("Downloading libraries. Please give me a minute.");
            DEPENDENCIES.load(librariesFolder, relocationHandler, injector);

        } catch (Throwable e) {
            throw new IllegalStateException("Unable to load ArmsRace", e);
        }
    }

    public Main() {
        try {
            informAboutLibraries = !librariesFolder.exists();
            librariesFolder.mkdirs();
            Repository.MAVEN_CENTRAL.downloadFile(RelocationHandler.RELOCATOR, librariesFolder);
            List<URL> urls = new ArrayList<>();
            for (File file : Objects.requireNonNull(librariesFolder.listFiles(c -> c.getName().endsWith(".jar")))) {
                urls.add(file.toURI().toURL());
            }
            relocationHandler = new RelocationHandler(urls.toArray(new URL[0]));
        } catch (Throwable e) {
            throw new IllegalStateException("Failed to download relocator", e);
        }
    }

    @Override
    public void onEnable() {
        if (!this.loadNMS()) {
            return;
        }

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

    private boolean loadNMS() {
        try {
            Class<?> targetNMS = Class.forName("com.lielamar.armsrace.nms." + NMSUtils.getServerVersion());
            if (NMS.class.isAssignableFrom(targetNMS)) {
                nmsHandler = (NMS) targetNMS.newInstance();
                System.out.print("NMS loaded successfully! [" + NMSUtils.getServerVersion() + "]");
            } else {
                System.out.print(ChatColor.RED
                        + "I found the class but it is not related to NMS! Please report this to the developer!");
                this.getServer().getPluginManager().disablePlugin(this);
            }
            return true;
        } catch (Exception e) {
            System.out.print(
                    ChatColor.RED
                            + "The current server version does not support this plugin! That's too bad! [Current: "
                            + NMSUtils.getServerVersion() + "]");
            System.out.print(ChatColor.RED + "Available versions: " + NMSUtils.getSupportVersions());
            this.getServer().getPluginManager().disablePlugin(this);
            return false;
        }
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
        this.nmsHandler = null;
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

    public Messages getMessages() {
        return this.messages;
    }

    public SettingsManager getSettingsManager() {
        return this.settingsManager;
    }

    public ShopManager getShopManager() {
        return this.shopManager;
    }

    public KillEffectsManager getKillEffectsManager() {
        return this.killEffectsManager;
    }

    public MapsFileManager getMapsFileManager() {
        return this.mapsFileManager;
    }

    public GameManager getGameManager() {
        return this.gameManager;
    }

    public PlayerManager getPlayerManager() {
        return this.playerManager;
    }

    public ScoreboardManager getScoreboardManager() {
        return this.scoreboardManager;
    }

    public CombatLogManager getCombatLogManager() {
        return this.combatlogManager;
    }
}
