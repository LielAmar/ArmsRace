package com.lielamar.armsrace.managers;

import com.lielamar.armsrace.Main;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GameManager {

	private Main main;
	
	private MapManager mapManager;
	
	public GameManager(Main main) {
		this.main = main;
		this.mapManager = new MapManager(main);
	}

}
