package ru.mor.iv.mineblocker;

import org.bukkit.Bukkit;

public class Debug {
	
	public static void send(String message){
		Bukkit.getLogger().info("[MineBlocker] " + message);
	}

	public static void send(int i) {
		send(String.valueOf(i));
	}

	public static void send(boolean b) {
		send(String.valueOf(b));
	}
	
}
