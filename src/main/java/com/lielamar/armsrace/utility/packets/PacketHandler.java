package com.lielamar.armsrace.utility.packets;

import com.lielamar.armsrace.utility.packets.version.*;
import org.bukkit.Bukkit;

public class PacketHandler {

	private PacketVersion nmsHandler;

	public PacketHandler() {
		String version = null;

		try {
			version = Bukkit.getServer().getClass().getPackage().getName().toString().split("\\.")[3];
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		System.out.println("[ArmsRace] Your version: " + version);
		if(version.equalsIgnoreCase("v1_8_r1"))
			this.nmsHandler = new NMS_v1_8_R1();
		else if(version.equalsIgnoreCase("v1_8_r2"))
			this.nmsHandler = new NMS_v1_8_R2();
		else if(version.equalsIgnoreCase("v1_8_r3"))
			this.nmsHandler = new NMS_v1_8_R3();
		else if(version.equalsIgnoreCase("v1_9_r1"))
			this.nmsHandler = new NMS_v1_9_R1();
		else if(version.equalsIgnoreCase("v1_9_r2"))
			this.nmsHandler = new NMS_v1_9_R2();
		else if(version.equalsIgnoreCase("v1_10_r1"))
			this.nmsHandler = new NMS_v1_10_R1();
		else if(version.equalsIgnoreCase("v1_11_r1"))
			this.nmsHandler = new NMS_v1_11_R1();
		else if(version.equalsIgnoreCase("v1_12_r1"))
			this.nmsHandler = new NMS_v1_12_R1();
		else if(version.equalsIgnoreCase("v1_13_r1"))
			this.nmsHandler = new NMS_v1_13_R1();
		else if(version.equalsIgnoreCase("v1_13_r2"))
			this.nmsHandler = new NMS_v1_13_R2();
		else if(version.equalsIgnoreCase("v1_14_r1"))
			this.nmsHandler = new NMS_v1_14_R1();
		else if(version.equalsIgnoreCase("v1_15_r1"))
			this.nmsHandler = new NMS_v1_15_R1();
		else if(version.equalsIgnoreCase("v1_16_r1"))
			this.nmsHandler = new NMS_v1_16_R1();
		else if(version.equalsIgnoreCase("v1_16_r2"))
			this.nmsHandler = new NMS_v1_16_R2();
		else if(version.equalsIgnoreCase("v1_16_r3"))
			this.nmsHandler = new NMS_v1_16_R3();
		else if(version.equalsIgnoreCase("v1_17_r1"))
			this.nmsHandler = new NMS_v1_17_R1();
		else {
			System.out.println("[ArmsRace] You are using an unsupported spigot version; therefore certain features of ArmsRace won't work!");
			System.out.println("[ArmsRace] Supported Versions: 1_8_R1, 1_8_R2, 1_8_R3, 1_9_R1, 1_9_R2, 1_10_R1, 1_11_R1, 1_12_R1, 1_13_R1, 1_13_R2, 1_14_R1, 1_15_R1, 1_16_R1, 1_16_R2, 1_16_R3, 1_17_R1");
			this.nmsHandler = null;
		}
	}

	/**
	 * @return     A {@link PacketVersion} object depending on the server version
	 */
	public PacketVersion getNMSHandler() {
		return this.nmsHandler;
	}
}
