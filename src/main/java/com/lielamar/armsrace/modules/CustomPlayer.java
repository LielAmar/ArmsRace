package com.lielamar.armsrace.modules;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.lielamar.armsrace.Main;
import com.lielamar.armsrace.modules.map.Killstreak;
import com.lielamar.armsrace.modules.map.Tier;
import com.lielamar.armsrace.modules.shop.TrailData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class CustomPlayer {

    private final Main plugin;
    private Player player;

    // In Game Variables
    private com.lielamar.armsrace.modules.map.Map currentMap;
    private Tier currentTier;
    private int currentTierId;
    private boolean LeftMap;
    private int killstreak;
    private Player lastDamager;
    private Long swordLaunch;

    // Data Save Variables
    private Map<String, Integer> kills = new LinkedHashMap<>();
    private Map<String, Integer> deaths = new LinkedHashMap<>();
    private Map<String, Integer> highTier = new LinkedHashMap<>();

    private Map<String, Integer> skills = new LinkedHashMap<>();

    private String currentTrail, currentKillEffect;
    private Map<String, Boolean> trails = new LinkedHashMap<>();
    private Map<Character, Integer> currentTrailData = new LinkedHashMap<>();

    private Map<String, Boolean> killEffects = new LinkedHashMap<>();

    private final File file;

    public CustomPlayer(Main plugin, Player player) {
        this.plugin = plugin;
        this.player = player;

        this.currentTier = null;
        this.currentTierId = -1;
        this.setLeftMap(false);
        this.killstreak = 0;
        this.lastDamager = null;
        this.swordLaunch = (long) -1;

        File dataFolder = new File(this.plugin.getDataFolder() + "/players/");
        if (!dataFolder.exists() && !dataFolder.mkdirs()) {
            throw new RuntimeException();
        }

        this.file = new File(dataFolder.getPath() + "/" + this.player.getUniqueId() + ".json");
        setup();
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player p) {
        this.player = p;
    }

    public Tier getCurrentTier() {
        return this.currentTier;
    }

    public void setCurrentTier(Tier tier) {
        this.currentTier = tier;
        if (this.currentMap == null) return;

        if (tier != null) {
            this.player.getInventory().setArmorContents(tier.getArmor());
            this.player.getInventory().setContents(tier.getContent());
        }
        this.player.updateInventory();
    }

    public int getCurrentTierId() {
        return this.currentTierId;
    }

    public void setCurrentTierId(int tier) {
        if (tier < 0) tier = 0;

        this.currentTierId = tier;
        player.setLevel(tier);
        if (this.currentMap == null) return;
        if (this.currentMap.getHighestTier() == null || this.currentMap.getHighestTier().getCurrentTierId() < this.getCurrentTierId())
            this.currentMap.setHighestTier(this);
    }

    public boolean isLeftMap() {
        return LeftMap;
    }

    public void setLeftMap(boolean leftMap) {
        LeftMap = leftMap;
    }

    public int getKillStreak() {
        return killstreak;
    }

    public void setSkillLevel(String skill, int level) {
        this.skills.put(skill, level);
        save();
    }

    public boolean setTrails(String trail, boolean value) {
        this.trails.put(trail, value);
        save();
        return value;
    }

    public void setCurrentTrail(String trail) {
        this.currentTrail = trail;
        save();
    }

    public String getCurrentTrail() {
        return this.currentTrail;
    }

    public void setCurrentTrailData(TrailData td) {
        this.currentTrailData.put('r', td.getRed());
        this.currentTrailData.put('g', td.getGreen());
        this.currentTrailData.put('b', td.getBlue());
        save();
    }

    public boolean setKillEffects(String killEffect, boolean value) {
        this.killEffects.put(killEffect, value);
        save();
        return value;
    }

    public void setCurrentKillEffect(String killEffect) {
        this.currentKillEffect = killEffect;
        save();
    }

    public String getCurrentKillEffect() {
        return this.currentKillEffect;
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

    public int getCoins() {
        return (int) plugin.getPlayerManager().getEconomy().getBalance(this.player);
    }

    public double setCoins(double coins) {
        plugin.getPlayerManager().getEconomy().withdrawPlayer(this.player, getCoins());
        return plugin.getPlayerManager().getEconomy().depositPlayer(this.player, coins).balance;
    }

    public double giveCoins(double coins) {
        return plugin.getPlayerManager().getEconomy().depositPlayer(this.player, coins).balance;
    }

    public double takeCoins(double coins) {
        return plugin.getPlayerManager().getEconomy().withdrawPlayer(this.player, coins).balance;
    }

    public int getKills(String mapName) {
        if (!this.kills.containsKey(mapName))
            this.kills.put(mapName, 0);
        return this.kills.get(mapName);
    }

    private void setKills(String mapName, int kills) {
        this.kills.put(mapName, kills);
        save();
    }

    public void addKill(String mapName) {
        this.setKills(mapName, getKills(mapName) + 1);
    }

    public int getDeaths(String mapName) {
        if (!this.deaths.containsKey(mapName))
            this.deaths.put(mapName, 0);
        return this.deaths.get(mapName);
    }

    private void setDeaths(String mapName, int deaths) {
        this.deaths.put(mapName, deaths);
        save();
    }

    public void addDeath(String mapName) {
        this.setDeaths(mapName, getDeaths(mapName) + 1);
    }

    public boolean containsHighTier(String mapName) {
        return this.highTier.containsKey(mapName);
    }

    public int getHighTier(String mapName) {
        return this.highTier.get(mapName);
    }

    public void setHighTier(String mapName, int tier) {
        this.highTier.put(mapName, tier);
        save();
    }

    /**
     * @param skill Name of skill
     * @return Level of a given skill
     */
    public int getSkillLevel(String skill) {
        if (!this.skills.containsKey(skill)) {
            this.skills.put(skill, 0);
        }
        return this.skills.get(skill);
    }

    /**
     * @return Whether or not the player has the trail
     */
    public boolean hasTrail(String trail) {
        if (this.trails.containsKey(trail))
            return this.trails.get(trail);
        return setTrails(trail, false);
    }

    /**
     * @return Current player's trail data
     */
    public TrailData getCurrentTrailData() {
        if (this.currentTrailData.containsKey('r')
                && this.currentTrailData.containsKey('g')
                && this.currentTrailData.containsKey('b'))
            return new TrailData(this.currentTrailData.get('r'), this.currentTrailData.get('g'), this.currentTrailData.get('b'));
        return null;
    }


    /**
     * @return Whether or not the player has the killeffect
     */
    public boolean hasKilleffect(String killeffect) {
        if (this.killEffects.containsKey(killeffect))
            return this.killEffects.get(killeffect);
        return setKillEffects(killeffect, false);
    }

    /**
     * Sets player's killstreak
     *
     * @param killstreak Killstreak level
     */
    public void setKillstreak(int killstreak) {
        this.killstreak = killstreak;
        if (this.currentMap == null) return;
        if (this.currentMap.getHighestKillstreak() == null || this.currentMap.getHighestKillstreak().getKillStreak() < this.getKillStreak())
            this.currentMap.setHighestKillstreak(this);

        Killstreak ks = this.currentMap.getKillstreak(this.killstreak);
        if (ks != null) {
            for (ItemStack item : ks.getItems())
                this.player.getInventory().addItem(item);
            for (PotionEffect pe : ks.getEffects())
                this.player.addPotionEffect(pe);
            this.giveCoins(ks.getCoins());
            this.player.sendMessage(plugin.getMessages().killStreakReward(ks));
        }
    }

    /**
     * @return Sum of kills from available maps
     */
    public int getKills() {
        int sum = 0;
        for (String s : this.kills.keySet()) {
            sum += this.kills.get(s);
        }
        return sum;
    }

    /**
     * @return Sum of deaths from available maps
     */
    public int getDeaths() {
        int sum = 0;
        for (String s : this.deaths.keySet()) {
            sum += this.deaths.get(s);
        }
        return sum;
    }


    /**
     * Sets up the player's data
     */
    public void setup() {
        this.kills = new LinkedTreeMap<>();
        this.deaths = new LinkedTreeMap<>();
        this.highTier = new LinkedTreeMap<>();
        this.skills = new LinkedTreeMap<>();

        this.currentTrail = "";
        this.trails = new LinkedTreeMap<>();
        this.currentTrailData = new LinkedTreeMap<>();

        this.currentKillEffect = "";
        this.killEffects = new LinkedTreeMap<>();

        if (this.file.exists()) {
            load();
        }
        save();
    }

    /**
     * Save the JSON file
     */
    public void save() {
        try {
            Map<String, Object> values = new HashMap<>();
            values.put("name", player.getName());
            values.put("skills", this.skills);
            values.put("kills", this.kills);
            values.put("deaths", this.deaths);
            values.put("hightier", this.highTier);

            values.put("currenttrail", this.currentTrail);
            values.put("trails", this.trails);
            values.put("currenttraildata", this.currentTrailData);

            values.put("currentkilleffect", this.currentKillEffect);
            values.put("killeffects", this.killEffects);

            Writer writer = new FileWriter(this.file);
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
            Reader reader = Files.newBufferedReader(this.file.toPath());
            Map<?, ?> values = gson.fromJson(reader, Map.class);

            if (values.containsKey("kills")) {
                this.kills.clear();
                this.kills.putAll((Map<String, Integer>) values.get("kills"));
            }

            if (values.containsKey("deaths")) {
                this.deaths.clear();
                this.deaths.putAll((Map<String, Integer>) values.get("deaths"));
            }

            if (values.containsKey("hightier")) {
                this.highTier.clear();
                this.highTier.putAll((Map<String, Integer>) values.get("hightier"));
            }

            if (values.containsKey("skills")) {
                this.skills.clear();
                this.skills.putAll((Map<String, Integer>) values.get("skills"));
            }

            if (values.containsKey("currenttrail")) {
                this.currentTrail = (String) values.get("currenttrail");
            }

            if (values.containsKey("trails")) {
                this.trails.clear();
                this.trails.putAll((Map<String, Boolean>) values.get("trails"));
            }

            if (values.containsKey("currenttraildata")) {
                this.currentTrailData.clear();
                this.currentTrailData.putAll((Map<Character, Integer>) values.get("currenttraildata"));
            }

            if (values.containsKey("currentkilleffect")) {
                this.currentKillEffect = (String) values.get("currentkilleffect");
            }

            if (values.containsKey("killeffects")) {
                this.killEffects.clear();
                this.killEffects.putAll((Map<String, Boolean>) values.get("killeffects"));
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
