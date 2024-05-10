package ru.mor.iv.mineblocker.managers.permissions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.permission.Permission;
import ru.mor.iv.mineblocker.MineBlockerPlugin;

public class VaultPermissionsManager implements PermissionsPlugin{
	private Permission permission;
	private Map<Player, PermissionCache> cache;
	
	public VaultPermissionsManager() {
		if(!setup()){
			Bukkit.getLogger().info("----------------------------------------------------------------");
			Bukkit.getLogger().info("[MineBlocker] Error start plugin. Vault is not configured. ");
			Bukkit.getLogger().info("----------------------------------------------------------------");
		}else{
			cache = new HashMap<>();
			startCleaner();
		}
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
	
	private boolean setup() {
		RegisteredServiceProvider<Permission> permissionProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        }
        return (permission != null);
	}

	@Override
	public List<String> getPlayerGroups(Player player) {
		List<String> list = getCacheGroups(player);
		if(list != null){
			return list;
		}
		list = new ArrayList<String>();
		for(String g : permission.getPlayerGroups(player)){
			list.add(g);
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

	@Override
	public List<String> getGroups() {
		List<String> list = new ArrayList<String>();
		for(String g : permission.getGroups()){
			list.add(g);
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
