package com.lielamar.armsrace.modules;

import org.bukkit.Location;

public class CustomLocation {

	private int id;
	private Location loc;

	public CustomLocation(int id, Location loc) {
		this.id = id;
		this.loc = loc;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Location getLoc() {
		return loc;
	}

	public void setLoc(Location loc) {
		this.loc = loc;
	}
}
