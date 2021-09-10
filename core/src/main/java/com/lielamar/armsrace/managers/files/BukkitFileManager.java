package com.lielamar.armsrace.managers.files;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class BukkitFileManager {

    private final JavaPlugin plugin;
    private final HashMap<String, Config> configs = new LinkedHashMap<>();

    public BukkitFileManager(JavaPlugin plugin) {
        this.plugin = plugin;

        if (!this.plugin.getDataFolder().exists() && !this.plugin.getDataFolder().mkdirs()) {
            throw new RuntimeException();
        }
    }

    /**
     * Returns a config by name
     *
     * @param name Name of the config to get
     * @return A {@link BukkitFileManager.Config} instance
     */
    public Config getConfig(String name) {
        name = fixName(name);

        if (!configs.containsKey(name))
            configs.put(name, new Config(name));

        return configs.get(name);
    }

    /**
     * Saves a config by name
     *
     * @param name Name of the config to save
     * @return A {@link BukkitFileManager.Config} instance
     */
    public Config saveConfig(String name) {
        name = fixName(name);

        return getConfig(name).save();
    }

    /**
     * Reloads a config by name
     *
     * @param name Name of the config to reload
     * @return A {@link BukkitFileManager.Config} instance
     */
    public Config reloadConfig(String name) {
        name = fixName(name);

        return getConfig(name).reload();
    }

    /**
     * Fixes a given name to a <name>.yml
     *
     * @param name Name to fix
     * @return Fixed name
     */
    public static String fixName(String name) {
        name = name.toLowerCase();

        if (!name.endsWith(".yml"))
            name += ".yml";
        return name;
    }

    public class Config {

        private final String name;
        private File file;
        private YamlConfiguration config;

        public Config(String name) {
            this.name = name;
            this.saveDefaultConfig();
        }

        public Config save() {
            if (this.config == null || this.file == null)
                return this;

            try {
                if (config.getConfigurationSection("").getKeys(true).size() != 0)
                    config.save(this.file);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return this;
        }

        public YamlConfiguration getConfig() {
            if (this.config == null)
                reload();

            return this.config;
        }

        public Config saveDefaultConfig() {
            file = new File(plugin.getDataFolder(), this.name);
            if (!file.exists()) {
                plugin.saveResource(this.name, false);
            }
            return this;
        }

        public Config reload() {
            if (file == null)
                this.file = new File(plugin.getDataFolder(), this.name);

            this.config = YamlConfiguration.loadConfiguration(file);

            Reader isReader;
            try {
                isReader = new InputStreamReader(plugin.getResource(this.name), StandardCharsets.UTF_8);
                YamlConfiguration config = YamlConfiguration.loadConfiguration(isReader);
                this.config.setDefaults(config);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            return this;
        }

        public Config copyDefaults(boolean force) {
            getConfig().options().copyDefaults(force);
            return this;
        }

        public Config set(String key, Object value) {
            getConfig().set(key, value);
            return this;
        }

        public Object get(String key) {
            return getConfig().get(key);
        }

        public String getString(String key) {
            return getConfig().getString(key);
        }

        public boolean contains(String key) {
            return getConfig().contains(key);
        }

        public List<String> getStringList(String key) {
            return getConfig().getStringList(key);
        }

        public ConfigurationSection getConfigurationSection(String key) {
            return getConfig().getConfigurationSection(key);
        }
    }
}