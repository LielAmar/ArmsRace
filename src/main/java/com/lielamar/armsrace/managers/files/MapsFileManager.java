package com.lielamar.armsrace.managers.files;

import java.io.File;
import java.util.HashMap;

import com.lielamar.armsrace.Main;

public class MapsFileManager {

    private final Main main;
    private HashMap<String, MapFile> configs;
 
    private String path;
    
    public MapsFileManager(Main main) {
        this.main = main;
        this.configs = new HashMap<>();
        
        if(!this.main.getDataFolder().exists())
        	this.main.getDataFolder().mkdir();
        
        path = this.main.getDataFolder() + "/maps";
        File pathFile = new File(path);
        
        if(!pathFile.exists())
        	pathFile.mkdir(); 
    }
 
    public MapFile getMap(String key) {
    	key = BukkitFileManager.fixName(key);
    	if(configs.containsKey(key)) return configs.get(key);
    	if(new File(path + "/" + key).exists()) return addMap(key);
    	return null;
    }

    public MapFile addMap(String key) {
    	key = BukkitFileManager.fixName(key);
    	MapFile map = new MapFile(main, key, new File(path + "/" + key));
    	configs.put(key, map);
    	return map;
    }
    
    public boolean existsMap(String key) {
    	key = BukkitFileManager.fixName(key);
    	return (configs.containsKey(key) || new File(path + "/" + key).exists());
    }
    
    public void deleteMap(String key) {
    	if(existsMap(key)) {
    		new File(path + "/" + key).delete();
    		if(configs.containsKey(key)) configs.remove(key);
    	}
    }
}