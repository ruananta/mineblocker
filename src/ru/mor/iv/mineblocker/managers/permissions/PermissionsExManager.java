package ru.mor.iv.mineblocker.managers.permissions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import ru.mor.iv.mineblocker.MineBlockerPlugin;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class PermissionsExManager implements PermissionsPlugin{
	private Map<Player, PermissionCache> cache;
	
	public PermissionsExManager() {
		cache = new HashMap<>();
		startCleaner();
	}
	
	private void startCleaner() {
		Bukkit.getScheduler().runTaskTimer(MineBlockerPlugin.getInst(), new Runnable() {
			@Override
			public void run() {
				if (!cache.isEmpty()) {
					long time = System.currentTimeMillis();
					Iterator<Entry<Player, PermissionCache>> iter = cache.entrySet().iterator();
					while (iter.hasNext()) {
						Map.Entry<Player, PermissionCache> entry = iter.next();
						if (time - entry.getValue().getTime() > 10000) {
							iter.remove();
						}
					}
				}
			}
		}, 40L, 40L);
	}

	public List<String> getPlayerGroups(Player player) {
		List<String> list = getCacheGroups(player);
		if(list != null){
			return list;
		}
		list = new ArrayList<String>();
		PermissionUser user = PermissionsEx.getUser(player);
		@SuppressWarnings("deprecation")
		PermissionGroup[] groups = user.getGroups();
		for(PermissionGroup group : groups){
			list.add(group.getName());
		}
		return list;
	}

	private List<String> getCacheGroups(Player player) {
		PermissionCache cache = this.cache.get(player);
		if(cache == null){
			return null;
		}
		return cache.getGroups();
	}

	public List<String> getGroups() {
		List<String> list = new ArrayList<String>();
		for(PermissionGroup pg : PermissionsEx.getPermissionManager().getGroupList()){
			list.add(pg.getName());
		}
		return list;
	}

	public boolean isGroup(String group) {
		for(String s : getGroups()){
			if(group.equals(s)){
				return true;
			}
		}
		return false;
	}
}
