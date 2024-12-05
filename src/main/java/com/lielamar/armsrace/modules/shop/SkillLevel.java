package com.lielamar.armsrace.modules.shop;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SkillLevel {

	private int level;
	private int price;
	
	public SkillLevel(int level, int price) {
		this.level = level;
		this.price = price;
	}

}
