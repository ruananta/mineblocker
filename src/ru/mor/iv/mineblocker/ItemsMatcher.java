package ru.mor.iv.mineblocker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.mor.iv.mineblocker.event.ActionEvent;
import ru.mor.iv.mineblocker.item.EventItem;
import ru.mor.iv.mineblocker.item.ListItem;
import ru.mor.iv.mineblocker.permission.ActionPermission;
import ru.mor.iv.mineblocker.permission.Permission;
import ru.mor.iv.mineblocker.permission.Permit;
import ru.mor.iv.mineblocker.permission.list.PermissionLine;

public class ItemsMatcher{
	
	
	public static boolean match(ActionEvent event, ActionPermission actionPermission, PermissionLine permission){
		actionPermission.setBanned(true);
		if(matchItems(actionPermission.getPermission(), event.getUsedItems(), permission.getMultiItem())){
			if(matchItems(null, event.getForWhatUsed(), permission.getMultiBlock())){
				if(matchSignLine(event.getSign(), permission.getSignLine())){
					return true;
				}
			}
		}
		return false;
	}
	
	private static boolean matchItems(Permission p, EventItem[] eventItemList, ListItem[] listItems) {
		if(listItems == null || eventItemList == null){
			if(eventItemList != null){
				setAllBanned(eventItemList);
			}
			return true;
		}
		//Match Place_Near list
		if(p == Permit.PLACE_NEAR){
			if(bannedPlaceNear(eventItemList, listItems)){
				return true;
			}
			return false;
		}
		//
		boolean b = false;
		for(ListItem listItem : listItems){
			for(EventItem eventItem : eventItemList){
				if(eventItem.equals(listItem)){
					if(listItem.isNot()){
						return false;
					}
					eventItem.setRemoveTicks(listItem.getRemoveTicks());
					eventItem.setBanned(true);
					b = true;
				}
			}
		}
		return b;
	}
	
	private static boolean bannedPlaceNear(EventItem[] eventItemList, ListItem[] list) {
		for(EventItem eventItem : eventItemList){
			for(ListItem listItem : list){
				if(eventItem.equals(listItem)){
					if(eventItem.getNearBlocks() != null){
						List<ListItem> listItemList = new ArrayList<>(Arrays.asList(list));
						listItemList.remove(listItem);
						for(EventItem i : eventItem.getNearBlocks()){
							if(i != null){
								for(ListItem li : listItemList){
									if(i.equals(li)){
										i.setBanned(true);
										eventItem.setBanned(true);
										return true;
									}
								}
							}
						}
					}
				}
			}
		}
		return false;
	}
	
	private static void setAllBanned(EventItem[] eventItemList) {
		for(EventItem i : eventItemList){
			i.setBanned(true);
		}
	}
	
	
	
	private static boolean matchSignLine(SignWrapper sign, String signLine) {
		if(sign == null){
			if(signLine == null) return true;
			else return false;
		}else if(signLine != null){
			return sign.contains(signLine);
		}
		return true;
		
	}

	
	
}
