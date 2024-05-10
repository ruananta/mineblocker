package ru.mor.iv.mineblocker.full.delete;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.mor.iv.mineblocker.config.string.Strings;
import ru.mor.iv.mineblocker.item.EventItem;
import ru.mor.iv.mineblocker.item.ListItem;

public class PistonBlackList {
	private Map<String, List<ListItem>> world$listItems = new HashMap<>();

	public void put(String world, List<ListItem> items) {
		world$listItems.put(world, items);
	}

	public boolean isEmpty() {
		return world$listItems.isEmpty();
	}
	
	public boolean contains(String world, EventItem eventItem){
		List<ListItem> l = new ArrayList<>();
		for(String w : world$listItems.keySet()){
			if(w.equalsIgnoreCase(world) || w.equals(Strings.ALL_WORLDS)){
				l.addAll(world$listItems.get(w));
			}
		}
		for(ListItem listItem : l){
			if(eventItem.equals(listItem)){
				return true;
			}
		}
		return false;
	}
	
	public boolean contains(String world, List<EventItem> list) {
		for(EventItem i : list){
			if(contains(world, i)){
				return true;
			}
		}
		return false;
	}

	public void clear() {
		world$listItems.clear();
	}
}
