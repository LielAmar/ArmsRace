package com.lielamar.armsrace.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lielamar.armsrace.modules.killeffects.KillEffect;
import org.bukkit.entity.Entity;

import com.lielamar.armsrace.modules.killeffects.effects.BlackMarkKillEffect;
import com.lielamar.armsrace.modules.killeffects.effects.BloodKillEffect;
import com.lielamar.armsrace.modules.killeffects.effects.BurningShoesKillEffect;
import com.lielamar.armsrace.modules.killeffects.effects.CookieFountainKillEffect;
import com.lielamar.armsrace.modules.killeffects.effects.FireworkKillEffect;
import com.lielamar.armsrace.modules.killeffects.effects.HeadRocketKillEffect;
import com.lielamar.armsrace.modules.killeffects.effects.HeartAuraKillEffect;
import com.lielamar.armsrace.modules.killeffects.effects.LastCandleKillEffect;
import com.lielamar.armsrace.modules.killeffects.effects.LightningKillEffect;
import com.lielamar.armsrace.modules.killeffects.effects.PinataKillEffect;
import com.lielamar.armsrace.modules.killeffects.effects.RektKillEffect;
import com.lielamar.armsrace.modules.killeffects.effects.SquidMissileKillEffect;
import com.lielamar.armsrace.modules.killeffects.effects.TNTKillEffect;
import com.lielamar.armsrace.modules.killeffects.effects.TornadoKillEffect;

public class KillEffectsManager {

	private Map<String, KillEffect> killEffects;
	private List<Entity> entities;
	
	public KillEffectsManager() {
		this.killEffects = new HashMap<>();
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
