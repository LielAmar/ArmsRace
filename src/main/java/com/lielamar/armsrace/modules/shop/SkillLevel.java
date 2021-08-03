package com.lielamar.armsrace.modules.shop;

public class SkillLevel {

	private int level;
	private int price;
	
	public SkillLevel(int level, int price) {
		this.level = level;
		this.price = price;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
}
