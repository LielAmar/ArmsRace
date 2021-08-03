package com.lielamar.armsrace.modules;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class CustomLocation {

	private int id;
	private String world;
	private double x;
	private double y;
	private double z;
	private float yaw;
	private float pitch;

	public CustomLocation(int id, String world, double x, double y, double z, float yaw, float pitch) {
		this.id = id;
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
	}

	public CustomLocation(int id, Location loc) {
		this(id, loc.getWorld().getName(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Location getLoc() {
		return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
	}

	public void setLoc(Location loc) {
		this.world = loc.getWorld().getName();
		this.x = loc.getX();
		this.y = loc.getY();
		this.z = loc.getZ();
		this.yaw = loc.getYaw();
		this.pitch = loc.getPitch();
	}
}
