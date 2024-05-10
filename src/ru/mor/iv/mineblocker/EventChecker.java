package ru.mor.iv.mineblocker;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import ru.mor.iv.mineblocker.event.ActionEvent;
import ru.mor.iv.mineblocker.item.EventItem;
import ru.mor.iv.mineblocker.permission.list.Content;
import ru.mor.iv.mineblocker.permission.list.PermissionSection;
import ru.mor.iv.mineblocker.permission.list.PermissionsList;

public class EventChecker {
	private static MineBlockerPlugin plugin;
	private PermissionsList permissionsList;
	private ActionEvent actionEvent;
	private boolean canBuild;
	private boolean out;

	public EventChecker() {
		plugin = MineBlockerPlugin.getInst();
		permissionsList = plugin.getPermissionsList();
	}
	
	public void check(ActionEvent actionEvent) {
		this.actionEvent = actionEvent;
		for(PermissionSection ps : permissionsList.getSections().values()){	
			check(ps);
		}
	}

	private void check(PermissionSection ps) {
		if(!containsWorlds(ps.getWorlds(), actionEvent.getStringWorld())){
			return;
		}
		if(!containsGroups(ps.getGroups(), actionEvent.getActor())){
			return;
		}
		if(!containsGameModes(ps.getGamemodes(), actionEvent.getActor())){
			return;
		}
		if(!containsRegions(ps.getRegions(), actionEvent.getActionLocations())){
			//if(actionEvent.getActionLocation() == null), то проверяем не живые блоки
			if(actionEvent.getActionLocations() == null && ps.getRegions() != null){
				if(actionEvent.getUsedItems() != null){
					boolean contains = false;
					for(EventItem item : actionEvent.getUsedItems()){
						if(item.getLocation() != null){
							if(containsRegions(ps.getRegions(), item.getLocation())){
								contains = true;
								continue;
							}
						}
					}
					if(!contains){
						for(EventItem item : actionEvent.getForWhatUsed()){
							if(item.getLocation() != null){
								if(containsRegions(ps.getRegions(), item.getLocation())){
									contains = true;
									continue;
								}
							}
						}
					}
					if(!contains){
						return;
					}
				}
			}else{
				return;
			}
			
		}
		if(!containsSign(ps.getSignLines(), actionEvent.getSign())){
			return;
		}
	}
	
	
	private static boolean containsWorlds(Content worlds, String stringWorld) {
		return worlds.isEmpty() || worlds.contains(stringWorld);
	}
	
	private static boolean containsGroups(Content groups, Player actor) {
		if(groups.isEmpty() || actor == null || plugin.getPermissionsPlugin() == null){
			return true;
		}
		for(String group : plugin.getPermissionsPlugin().getPlayerGroups(actor)){
			if(groups.contains(group)){
				return true;
			}
		}
		return false;
	}
	
	private static boolean containsGameModes(Content gamemodes, Player actor) {
		if(gamemodes.isEmpty() || actor == null){
			return true;
		}
		return gamemodes.contains(actor.getGameMode().toString());
	}
	
	private static boolean containsRegions(Content regions, Location[] actionLocations) {
		if(actionLocations == null){
			return false;
		}
		for(Location actionLocation : actionLocations){
			if(!containsRegions(regions, actionLocation)){
				return false;
			}
		}
		return true;
	}
	
	private static boolean containsRegions(Content regions, Location actionLocation) {
		if(actionLocation == null){
			return false;
		}
		if(plugin.getPrivatePlugin() == null || regions.isEmpty()){
			return true;
		}
		for(String region : plugin.getPrivatePlugin().getRegions(actionLocation)){
			if(regions.contains(region)){
				return true;
			}
		}
		return false;
	}
	
	private static boolean containsSign(Content signLines, SignWrapper sign) {
		if(signLines.isEmpty()){
			return true;
		}else if(sign == null){
			return false;
		}
		for(String line : signLines.getContents()){
			if(sign.contains(line)){
				return true;
			}
		}
		return false;
	}
}
