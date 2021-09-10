package com.lielamar.armsrace.modules;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class CustomLocation {

	public int id;
	private String world;
	private double x;
	private double y;
	private double z;
	private float yaw;
	private float pitch;

	public CustomLocation(int id, Location location) {
		this(id, location.getWorld().getName(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
	}

	public CustomLocation(int id, String world, double x, double y, double z, float yaw, float pitch) {
		this.id = id;
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
	}

	public Location getLocation() {
		return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
	}

	public void setLocation(Location location) {
		this.world = location.getWorld().getName();
		this.x = location.getX();
		this.y = location.getY();
		this.z = location.getZ();
		this.yaw = location.getYaw();
		this.pitch = location.getPitch();
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

}
