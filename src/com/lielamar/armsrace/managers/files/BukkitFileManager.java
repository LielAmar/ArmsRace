package com.lielamar.armsrace.managers.files;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitFileManager {
	 
    private final JavaPlugin plugin;
    private HashMap<String, Config> configs;
 
    public BukkitFileManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.configs = new HashMap<String, Config>();
        
        if(!this.plugin.getDataFolder().exists())
        	this.plugin.getDataFolder().mkdir();
    }
 
    /**
     * Returns a config by name
     * 
     * @param name    Name of the config to get
     * @return        A {@link com.lielamar.armsrace.managers.files.BukkitFileManager.Config} instance
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
     * @param name    Name of the config to save
     * @return        A {@link com.lielamar.armsrace.managers.files.BukkitFileManager.Config} instance
     */
    public Config saveConfig(String name) {
    	name = fixName(name);
    	
        return getConfig(name).save();
    }
    
    /**
     * Reloads a config by name
     * 
     * @param name    Name of the config to reload
     * @return        A {@link com.lielamar.armsrace.managers.files.BukkitFileManager.Config} instance
     */
    public Config reloadConfig(String name) {
    	name = fixName(name);
    	
        return getConfig(name).reload();
    }
 
    /**
     * Fixes a given name to a <name>.yml
     * 
     * @param name     Name to fix
     * @return         Fixed name
     */
    public static String fixName(String name) {
    	name = name.toLowerCase();
    	
    	if(!name.endsWith(".yml"))
    		name += ".yml";
    	return name;
    }

    public class Config {
     
        private String name;
        private File file;
        private YamlConfiguration config;
     
        public Config(String name) {
            this.name = name;
            this.saveDefaultConfig();
        }
        
        public Config save() {
            if((this.config == null) || (this.file == null))
                return this;
            
            try{
                if(config.getConfigurationSection("").getKeys(true).size() != 0)
                    config.save(this.file);
            }
            
            catch (IOException e) {
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
            if(!file.exists())
            	plugin.saveResource(this.name, false);
            return this;
        }
        
        public Config reload() {
            if (file == null)
                this.file = new File(plugin.getDataFolder(), this.name);
         
            this.config = YamlConfiguration.loadConfiguration(file);
         
            Reader isReader;
            try {
            	isReader = new InputStreamReader(plugin.getResource(this.name), "UTF8");
             
                if(isReader != null) {
                    YamlConfiguration config = YamlConfiguration.loadConfiguration(isReader);
                    this.config.setDefaults(config);
                }
            }
            
            catch (UnsupportedEncodingException | NullPointerException e) {
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