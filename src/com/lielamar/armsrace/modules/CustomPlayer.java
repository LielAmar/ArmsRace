package com.lielamar.armsrace.modules;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.lielamar.armsrace.Main;
import com.lielamar.armsrace.modules.map.Killstreak;
import com.lielamar.armsrace.modules.map.Tier;
import com.lielamar.armsrace.modules.shop.TrailData;

public class CustomPlayer {

	private Main main;
	private Player p;
	
	// In Game Variables
	private com.lielamar.armsrace.modules.map.Map currentMap;
	private Tier currentTier;
	private int currentTierId;
	private boolean LeftMap;
	private int killstreak;
	private Player lastDamager;
	private Long swordLaunch;
	
	// Data Save Variables
	private LinkedTreeMap<String, Double> kills;
	private LinkedTreeMap<String, Double> deaths;
	private LinkedTreeMap<String, Double> hightier;
	
	private LinkedTreeMap<String, Double> skills;
	
	private String currenttrail;
	private LinkedTreeMap<String, Boolean> trails;
	private LinkedTreeMap<Character, Integer> currenttraildata;
	
	private String currentkilleffect;
	private LinkedTreeMap<String, Boolean> killeffects;
	
	private File path;
	private File f;
	
	public CustomPlayer(Main main, Player p) {
		this.main = main;
		this.p = p;
		
		this.currentTier = null;
		this.currentTierId = -1;
		this.setLeftMap(false);
		this.killstreak = 0;
		this.lastDamager = null;
		this.swordLaunch = (long) -1;
		
		this.path = new File(this.main.getDataFolder() + "/players/");
		if(!this.path.exists())
			this.path.mkdir();
		this.f = new File(path.getPath() + "/" + this.p.getUniqueId().toString() + ".json");
		
		setup();
	}
	
	public Player getPlayer() {
		return p;
	}

	public void setPlayer(Player p) {
		this.p = p;
	}
	
	public Tier getCurrentTier() {
		return this.currentTier;
	}
	
	public void setCurrentTier(Tier tier) {
		this.currentTier = tier;
		if(this.currentMap == null) return;
		
		if(tier != null) {
			this.p.getInventory().setArmorContents(tier.getArmor());
			this.p.getInventory().setContents(tier.getContent());
		}
		this.p.updateInventory();
	}
	
	public int getCurrentTierId() {
		return this.currentTierId;
	}
	
	public void setCurrentTierId(int tier) {
		this.currentTierId = tier;
		p.setLevel(tier);
		if(this.currentMap == null) return;
		if(this.currentMap.getHighestTier() == null || this.currentMap.getHighestTier().getCurrentTierId() < this.getCurrentTierId())
			this.currentMap.setHighestTier(this);
	}
	
	public boolean isLeftMap() {
		return LeftMap;
	}

	public void setLeftMap(boolean leftMap) {
		LeftMap = leftMap;
	}
	
	public int getKillstreak() {
		return killstreak;
	}
	
	public void setSkillLevel(String skill, int level) {
		double lvl = (double)level;
		this.skills.put(skill, lvl);
		save();
	}
	
	public boolean setTrails(String trail, boolean value) {
		this.trails.put(trail, value);
		save();
		return value;
	}
	
	public void setCurrentTrail(String trail) {
		this.currenttrail = trail;
		save();
	}
	
	public String getCurrentTrail() {
		return this.currenttrail;
	}
	
	public void setCurrentTrailData(TrailData td) {
		this.currenttraildata.put('r', td.getR());
		this.currenttraildata.put('g', td.getG());
		this.currenttraildata.put('b', td.getB());
		save();
	}
	
	public boolean setKillEffects(String killeffect, boolean value) {
		this.killeffects.put(killeffect, value);
		save();
		return value;
	}
	
	public void setCurrentKillEffect(String killeffect) {
		this.currentkilleffect = killeffect;
		save();
	}
	
	public String getCurrentKillEffect() {
		return this.currentkilleffect;
	}
	
	public Player getLastDamager() {
		return lastDamager;
	}

	public void setLastDamager(Player lastDamager) {
		this.lastDamager = lastDamager;
	}

	public Long getSwordLaunch() {
		return swordLaunch;
	}

	public void setSwordLaunch(Long swordLaunch) {
		this.swordLaunch = swordLaunch;
	}
	
	public com.lielamar.armsrace.modules.map.Map getCurrentMap() {
		return this.currentMap;
	}
	
	public void setCurrentMap(com.lielamar.armsrace.modules.map.Map map) {
		this.currentMap = map;
	}
	
	public File getFile() {
		return f;
	}

	public void setFile(File f) {
		this.f = f;
	}
	
	public int getCoins() {
		return (int)main.getPlayerManager().getEconomy().getBalance(this.p);
	}

	public double setCoins(double coins) {
		main.getPlayerManager().getEconomy().withdrawPlayer(this.p, getCoins());
		return main.getPlayerManager().getEconomy().depositPlayer(this.p, coins).balance;
	}
	
	public double giveCoins(double coins) {
		return main.getPlayerManager().getEconomy().depositPlayer(this.p, coins).balance;
	}
	
	public double takeCoins(double coins) {
		return main.getPlayerManager().getEconomy().withdrawPlayer(this.p, coins).balance;
	}
	
	public double getKills(String mapName) {
		if(!this.kills.containsKey(mapName))
			this.kills.put(mapName, 0.0);
		return this.kills.get(mapName);
	}
	
	private void setKills(String mapName, double kills) {
		this.kills.put(mapName, kills);
		save();
	}
	
	public void addKill(String mapName) {
		this.setKills(mapName, getKills(mapName)+1);
	}

	public double getDeaths(String mapName) {
		if(!this.deaths.containsKey(mapName))
			this.deaths.put(mapName, 0.0);
		return this.deaths.get(mapName);
	}
	
	private void setDeaths(String mapName, double deaths) {
		this.deaths.put(mapName, deaths);
		save();
	}
	
	public void addDeath(String mapName) {
		this.setDeaths(mapName, getDeaths(mapName)+1);
	}
	
	public boolean containsHightier(String mapName) {
		return this.hightier.containsKey(mapName);
	}
	
	public double getHightier(String mapName) {
		return this.hightier.get(mapName);
	}

	public void setHightier(String mapName, double tier) {
		this.hightier.put(mapName, tier);
		save();
	}
	
	/**
	 * @param skill    Name of skill
	 * @return         Level of a given skill
	 */
	public int getSkillLevel(String skill) {
		if(this.skills.containsKey(skill)) {
			double lvl = this.skills.get(skill);
			return (int)lvl;
		}
		this.skills.put(skill, 0.0);
		return 0;
	}
	
	/**
	 * @param skill    Name of trail
	 * @return         Whether or not the player has the trail
	 */
	public boolean hasTrail(String trail) {
		if(this.trails.containsKey(trail))
			return this.trails.get(trail);
		return setTrails(trail, false);
	}
	
	/**
	 * @return         Current player's trail data
	 */
	public TrailData getCurrentTrailData() {
		if(this.currenttraildata.containsKey('r')
				&& this.currenttraildata.containsKey('g')
				&& this.currenttraildata.containsKey('b'))
			return new TrailData(this.currenttraildata.get('r'), this.currenttraildata.get('g'), this.currenttraildata.get('b'));
		return null;
	}
	
	
	/**
	 * @param skill    Name of killeffect
	 * @return         Whether or not the player has the killeffect
	 */
	public boolean hasKilleffect(String killeffect) {
		if(this.killeffects.containsKey(killeffect))
			return this.killeffects.get(killeffect);
		return setKillEffects(killeffect, false);
	}
	
	/**
	 * Sets player's killstreak
	 * 
	 * @param killstreak     Killstreak level
	 */
	public void setKillstreak(int killstreak) {
		this.killstreak = killstreak;
		if(this.currentMap == null) return;
		if(this.currentMap.getHighestKillstreak() == null || this.currentMap.getHighestKillstreak().getKillstreak() < this.getKillstreak())
			this.currentMap.setHighestKillstreak(this);
		
		Killstreak ks = this.currentMap.getKillstreak(this.killstreak);
		if(ks != null) {
			for(ItemStack item : ks.getItems())
				this.p.getInventory().addItem(item);
			for(PotionEffect pe : ks.getEffects())
				this.p.addPotionEffect(pe);
			this.giveCoins(ks.getCoins());
			this.p.sendMessage(main.getMessages().killStreakReward(ks));
		}
	}
	
	/**
	 * @return       Sum of kills from available maps
	 */
	public double getKills() {
		int sum = 0;
		for(String s : this.kills.keySet()) {
			sum+=this.kills.get(s);
		}
		return sum;
	}
	
	/**
	 * @return       Sum of deaths from available maps
	 */
	public double getDeaths() {
		int sum = 0;
		for(String s : this.deaths.keySet()) {
			sum+=this.deaths.get(s);
		}
		return sum;
	}
	

	/**
	 * Sets up the player's data
	 */
	public void setup() {
		this.kills = new LinkedTreeMap <String, Double>();
		this.deaths = new LinkedTreeMap <String, Double>();
		this.hightier = new LinkedTreeMap <String, Double>();
		this.skills = new LinkedTreeMap <String, Double>();
		
		this.currenttrail = "";
		this.trails = new LinkedTreeMap <String, Boolean>();
		this.currenttraildata = new LinkedTreeMap <Character, Integer>();
		
		this.currentkilleffect = "";
		this.killeffects = new LinkedTreeMap<String, Boolean>();
		
		if(!this.f.exists()) {
			save();
		} else {
			load();
			save();
		}
	}
	
	/**
	 * Save the JSON file
	 */
	public void save() {
		try {
			Map<String, Object> values = new HashMap<String, Object>();
			values.put("name", p.getName());
			values.put("skills", this.skills);
			values.put("kills", this.kills);
			values.put("deaths", this.deaths);
			values.put("hightier", this.hightier);
			
			values.put("currenttrail", this.currenttrail);
			values.put("trails", this.trails);
			values.put("currenttraildata", this.currenttraildata);
			
			values.put("currentkilleffect", this.currentkilleffect);
			values.put("killeffects", this.killeffects);
			
			Writer writer = new FileWriter(this.f);
			new Gson().toJson(values, writer);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Load the JSON file
	 */
	@SuppressWarnings("unchecked")
	public void load() {
		try {
			Gson gson = new Gson();
		    Reader reader = Files.newBufferedReader(this.f.toPath());
		    Map<?, ?> values = gson.fromJson(reader, Map.class);
		    
		    if(values.containsKey("kills")) {
		    	this.kills = (LinkedTreeMap<String, Double>) values.get("kills");
			}   
		    
		    if(values.containsKey("deaths")) {
		    	this.deaths = (LinkedTreeMap<String, Double>) values.get("deaths");
			}
		    
		    if(values.containsKey("hightier")) {
		    	this.hightier = (LinkedTreeMap<String, Double>) values.get("hightier");
		    }
		    
		  
		    if(values.containsKey("skills")) {
		    	this.skills = (LinkedTreeMap<String, Double>) values.get("skills");  
		    }
		    
		    
		    if(values.containsKey("currenttrail")) {
		    	this.currenttrail = (String) values.get("currenttrail");
		    }
		    
		    if(values.containsKey("trails")) {
		    	this.trails = (LinkedTreeMap<String, Boolean>) values.get("trails");  
		    }
		    
		    if(values.containsKey("currenttraildata")) {
		    	this.currenttraildata = (LinkedTreeMap<Character, Integer>) values.get("currenttraildata");
		    }
		    
		    
		    if(values.containsKey("currentkilleffect")) {
		    	this.currentkilleffect = (String) values.get("currentkilleffect");
		    }
		    
		    if(values.containsKey("killeffects")) {
		    	this.killeffects = (LinkedTreeMap<String, Boolean>) values.get("killeffects");
		    }

		    reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
