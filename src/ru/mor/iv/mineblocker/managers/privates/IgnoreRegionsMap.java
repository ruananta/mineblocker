package ru.mor.iv.mineblocker.managers.privates;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IgnoreRegionsMap {
	
	private static Map<String, List<String>> world$regions = new HashMap<>();
	
	
	
	public boolean contains(String w, String id) {
		List<String> l = world$regions.get(w);
		if (l != null) {
			for (String rg : l) {
				if (rg.equalsIgnoreCase(id)) {
					return true;
				}
			}
		}
		return false;
	}



	public static void clear() {
		world$regions.clear();
	}



	public static void put(String world, List<String> regionsList) {
		world$regions.put(world, regionsList);
	}
}
