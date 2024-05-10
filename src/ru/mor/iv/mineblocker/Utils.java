package ru.mor.iv.mineblocker;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import ru.mor.iv.mineblocker.item.EventItem;
import ru.mor.iv.mineblocker.item.ListItem;
import ru.mor.iv.mineblocker.permission.Permission;

public class Utils {
	
	public static String[] toArray(List<String> list){
		if(list == null || list.isEmpty())
			return null;
		return list.toArray(new String[list.size()]);
	}

	public static Permission[] toArrayPermission(List<Permission> list) {
		if(list == null || list.isEmpty())
			return null;
		return list.toArray(new Permission[list.size()]);
	}

	public static ListItem[] toArrayItem(List<ListItem> list) {
		if(list == null || list.isEmpty())
			return null;
		return list.toArray(new ListItem[list.size()]);
	}
	
	public static EventItem[] addItem(EventItem[] array, EventItem eventItem) {
		if(array == null){
			array = new EventItem[1];
			array[0] = eventItem;
		}else{
			EventItem[] newArray = new  EventItem[array.length + 1];
			System.arraycopy(array, 0, newArray, 0, array.length);
			newArray[newArray.length - 1] = eventItem;
			array = newArray;
		}
		return array;
	}
	
	public static String[] addString(String[] array, String s) {
		if(array == null){
			array = new String[1];
			array[0] = s;
		}else{
			String[] newArray = new  String[array.length + 1];
			System.arraycopy(array, 0, newArray, 0, array.length);
			newArray[newArray.length - 1] = s;
			array = newArray;
		}
		return array;
	}
	
	
	public static Permission[] addPermission(Permission[] array, Permission p) {
		if(array == null){
			array = new Permission[1];
			array[0] = p;
		}else{
			Permission[] newArray = new  Permission[array.length + 1];
			System.arraycopy(array, 0, newArray, 0, array.length);
			newArray[newArray.length - 1] = p;
			array = newArray;
		}
		return array;
	}
	
	public static Location[] addLocation(Location[] array, Location p) {
		if(array == null){
			array = new Location[1];
			array[0] = p;
		}else{
			Location[] newArray = new  Location[array.length + 1];
			System.arraycopy(array, 0, newArray, 0, array.length);
			newArray[newArray.length - 1] = p;
			array = newArray;
		}
		return array;
	}

	public static boolean notNull(Object[] items) {
		if(items != null){
			for(Object o : items){
				if(o == null){
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	public static ItemStack getItemStackFromEventItem(EventItem[] itemsHand, EventItem item) {
		for(EventItem itemHand : itemsHand){
			if(itemHand.equals(item)){
				return itemHand.getItemStack();
			}
		}
		return null;
	}
}
