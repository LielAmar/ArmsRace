package com.lielamar.armsrace.modules;

public class CustomScoreboard {

	private boolean enabled;
	private String title;
	private String footer;
	private String[] lines;

	public CustomScoreboard(boolean enabled, String title, String footer, String[] lines) {
		this.enabled = enabled;
		this.title = title;
		this.footer = footer;
		this.lines = lines;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFooter() {
		return footer;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}

	public String[] getLines() {
		return lines;
	}

	public void setLines(String[] lines) {
		this.lines = lines;
	}
}
