package com.lielamar.armsrace.managers;

import com.lielamar.armsrace.Main;

public class GameManager {

	private Main main;
	
	private MapManager mapManager;
	
	public GameManager(Main main) {
		this.main = main;
		this.mapManager = new MapManager(main);
	}

	public Main getMain() {
		return main;
	}
	
	public void setMain(Main main) {
		this.main = main;
	}

	public MapManager getMapManager() {
		return this.mapManager;
	}

	public void setMapManager(MapManager mapManager) {
		this.mapManager = mapManager;
	}
}
