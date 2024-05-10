package ru.mor.iv.mineblocker.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import ru.mor.iv.mineblocker.MineBlockerPlugin;
import ru.mor.iv.mineblocker.event.ActionEvent;
import ru.mor.iv.mineblocker.permission.Permission;
import ru.mor.iv.mineblocker.permission.PermissionHolder;

public class ActionListener implements Listener, MineBlockerListener {
	private MineBlockerPlugin plugin = MineBlockerPlugin.getInst();
	private boolean isRegisterred = false;
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onAction(ActionEvent event){
		plugin.getPermissionManager().checkEvent(event);
	}
	
	@Override
	public void register() {
		isRegisterred = true;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@Override
	public void unregister() {
		isRegisterred = false;
		HandlerList.unregisterAll(this);
	}
	
	@Override
	public Permission[] getUsedPermissions() {
		return PermissionHolder.getPermissions();
	}
	
	@Override
	public boolean isRegisterred() {
		return isRegisterred;
	}
}
