package ru.mor.iv.mineblocker.managers.permissions;

import java.util.List;

import org.bukkit.entity.Player;

public interface PermissionsPlugin {
	
	public List<String> getPlayerGroups(Player player);
	
	public List<String> getGroups();
	
	public boolean isGroup(String group);
	
}
