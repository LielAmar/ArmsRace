package com.lielamar.armsrace.modules.shop;

public class SkillLevel {

	private int level;
	private double price;

	public SkillLevel(int level, double price) {
		this.level = level;
		this.price = price;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
