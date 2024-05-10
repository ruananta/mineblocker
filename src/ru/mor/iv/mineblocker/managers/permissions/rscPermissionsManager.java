package ru.mor.iv.mineblocker.managers.permissions;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import ru.simsonic.rscPermissions.BridgeForBukkitAPI;

public class rscPermissionsManager implements PermissionsPlugin {
	private BridgeForBukkitAPI rscp = BridgeForBukkitAPI.getInstance();
	
	@Override
	public List<String> getPlayerGroups(Player player) {
		List<String> list = new ArrayList<String>();
		for(String group : rscp.getPermission().getPlayerGroups(player)){
			list.add(group);
		}
		return list;
	}

	@Override
	public List<String> getGroups() {
		List<String> list = new ArrayList<String>();
		for(String s : rscp.getPermission().getGroups()){
			list.add(s);
		}
		return list;
	}

	@Override
	public boolean isGroup(String group) {
		for(String s : getGroups()){
			if(group.equals(s)){
				return true;
			}
		}
		return false;
	}

}
