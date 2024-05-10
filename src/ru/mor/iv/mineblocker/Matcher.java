package ru.mor.iv.mineblocker;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import ru.mor.iv.mineblocker.event.ActionEvent;
import ru.mor.iv.mineblocker.event.Cancel;
import ru.mor.iv.mineblocker.event.CancelType;
import ru.mor.iv.mineblocker.item.EventItem;
import ru.mor.iv.mineblocker.permission.ActionPermission;
import ru.mor.iv.mineblocker.permission.list.Content;
import ru.mor.iv.mineblocker.permission.list.PermissionLine;
import ru.mor.iv.mineblocker.permission.list.PermissionSection;

public class Matcher {
	private static MineBlockerPlugin plugin = MineBlockerPlugin.getInst();

	public static void init(ActionEvent event){
		for(PermissionSection ps : plugin.getPermissionsList().getSections().values()){
			init(event, ps);
		}
	}

	public static void init(ActionEvent event, PermissionSection ps) {
		if(!containsWorlds(ps.getWorlds(), event.getStringWorld())){
			return;
		}
		if(!containsGroups(ps.getGroups(), event.getActor())){
			return;
		}
		if(!containsGameModes(ps.getGamemodes(), event.getActor())){
			return;
		}
		if(!containsRegions(ps.getRegions(), event.getActionLocations())){
			return;
			//if(event.getActionLocation() == null), то проверяем не живые блоки
//			if(event.getActionLocations() == null && ps.getRegions() != null){
//				if(event.getUsedItems() != null){
//					boolean contains = false;
//					for(EventItem item : event.getUsedItems()){
//						if(item.getLocation() != null){
//							if(containsRegions(ps.getRegions(), item.getLocation())){
//								contains = true;
//								continue;
//							}
//						}
//					}
//					if(!contains){
//						for(EventItem item : event.getForWhatUsed()){
//							if(item.getLocation() != null){
//								if(containsRegions(ps.getRegions(), item.getLocation())){
//									contains = true;
//									continue;
//								}
//							}
//						}
//					}
//					if(!contains){
//						return;
//					}
//				}
//			}else{
//				return;
//			}
			
		}
		if(!containsSign(ps.getSignLines(), event.getSign())){
			return;
		}
		init2(event, ps);
	}

	
	

	private static void init2(ActionEvent event, PermissionSection ps) {
		Cancel c = event.getCancel();
		for(ActionPermission actionPermission : event.getPermissions()){
			List<PermissionLine> permissionLines = ps.getPermissions().getPermissionLines(actionPermission.getPermission());
			if(permissionLines == null) continue;
			for(PermissionLine permissionLine : permissionLines){
				if(ItemsMatcher.match(event, actionPermission, permissionLine)){
					if(permissionLine.isBan()){
						c.addCancel(permissionLine.getCancellType());
						c.addAllCancelIfNotNull(getCancelTypes(ps));
					}else{
						c.addUnCancel(permissionLine.getCancellType());
					}
					c.setPrivateRange(permissionLine.getPrivateRange());
					event.setCustomMessage(permissionLine.getCustoMessage());
					event.setMute(permissionLine.isMute());
				}
			}
		}
	}
	
	private static List<CancelType> getCancelTypes(PermissionSection ps) {
		List<CancelType> l = null;
		if(!ps.getGamemodes().isEmpty() || !ps.getRegions().isEmpty() || !ps.getWorlds().isEmpty()){
			l = new ArrayList<>();
		}
		if(!ps.getGamemodes().isEmpty()){
			l.add(CancelType.GAMEMODE);
		}
		if(!ps.getRegions().isEmpty()){
			l.add(CancelType.PRIVATE);
		}
		if(!ps.getWorlds().isEmpty()){
			l.add(CancelType.WORLD);
		}
		return l;
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
	
	private static boolean containsRegions(Content regions, Location[] locations) {
		if(locations == null){
			return false;
		}
		if(plugin.getPrivatePlugin() == null || regions.isEmpty()){
			return true;
		}
		for(String region : plugin.getPrivatePlugin().getRegions(locations)){
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
