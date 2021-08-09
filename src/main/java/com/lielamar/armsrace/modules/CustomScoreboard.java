package com.lielamar.armsrace.modules;

public class CustomScoreboard {

	public boolean enabled;
	public String title;
	public String footer;
	public String[] lines;

	public CustomScoreboard(boolean enabled, String title, String footer, String[] lines) {
		this.enabled = enabled;
		this.title = title;
		this.footer = footer;
		this.lines = lines;
	}

}
