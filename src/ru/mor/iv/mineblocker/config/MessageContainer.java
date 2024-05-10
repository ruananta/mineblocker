package ru.mor.iv.mineblocker.config;

import java.util.HashMap;
import java.util.Map;

public class MessageContainer {
	private static Map<String, String> map = new HashMap<>();
	
	public static void addMessage(String name, String message) {
		map.put(name, message);
	}
	
	public static String getMessage(String name) {
		return map.get(name);
	}
	
	public static void clear(){
		map.clear();
	}
	
}
