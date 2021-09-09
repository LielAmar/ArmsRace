package com.lielamar.armsrace.managers;

import com.lielamar.armsrace.modules.killeffects.KillEffect;
import com.lielamar.armsrace.modules.killeffects.effects.*;
import org.bukkit.entity.Entity;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class KillEffectsManager {

	private final Map<String, KillEffect> killEffects;
	private final List<Entity> entities;
	
	public KillEffectsManager() {
		this.killEffects = new LinkedHashMap<>();
		this.entities = new ArrayList<>();
		
		this.killEffects.put("SQUID_MISSILE", new SquidMissileKillEffect());
		this.killEffects.put("FIREWORK", new FireworkKillEffect());
		this.killEffects.put("LIGHTNING_STRIKE", new LightningKillEffect());
		this.killEffects.put("TNT", new TNTKillEffect());
		this.killEffects.put("HEART_AURA", new HeartAuraKillEffect());
		this.killEffects.put("BURNING_SHOES", new BurningShoesKillEffect());
		this.killEffects.put("COOKIE_FOUNTAIN", new CookieFountainKillEffect());
		this.killEffects.put("HEAD_ROCKET", new HeadRocketKillEffect());
		this.killEffects.put("BLACK_MARK", new BlackMarkKillEffect()); 
		this.killEffects.put("LAST_CANDLE", new LastCandleKillEffect());
		this.killEffects.put("BLOOD", new BloodKillEffect()); //
		this.killEffects.put("REKT", new RektKillEffect());
		this.killEffects.put("PINATA", new PinataKillEffect());
		this.killEffects.put("TORNADO", new TornadoKillEffect()); //
	}
	
	public KillEffect getKillEffect(String killeffect) {
		if(this.killEffects.containsKey(killeffect))
			return this.killEffects.get(killeffect);
		return null;
	}

	public void addEntity(Entity ent) {
		this.entities.add(ent);
	}
	
	public void removeEntity(Entity ent) {
		this.entities.remove(ent);
	}
	
	public boolean containsEntity(Entity ent) {
		return this.entities.contains(ent);
	}
}
