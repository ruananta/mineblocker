package ru.mor.iv.mineblocker.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import ru.mor.iv.mineblocker.MineBlockerPlugin;
import ru.mor.iv.mineblocker.full.FullPluginLoader;
import ru.mor.iv.mineblocker.permission.Permission;
import ru.mor.iv.mineblocker.permission.Permit;

public class InventoryDoubleOpeningListener implements Listener, MineBlockerListener {
	private MineBlockerPlugin plugin;
	private boolean isRegisterred = false;
	
	public InventoryDoubleOpeningListener() {
		this.plugin = MineBlockerPlugin.getInst();
	}
	
	//Inventory dubleOpen 
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void invOpen(PlayerInteractEvent event){
		if(FullPluginLoader.isFullPlugin()){
			FullPluginLoader.getFullPlugin().getFullInventoryDoubleOpeningListener().processEvent(event);
		}
	}


	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void invOpen(PlayerInteractEntityEvent event){
		if(FullPluginLoader.isFullPlugin()){
			FullPluginLoader.getFullPlugin().getFullInventoryDoubleOpeningListener().processEvent(event);
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onInvetoryClose(InventoryCloseEvent event) {
		if(FullPluginLoader.isFullPlugin()){
			FullPluginLoader.getFullPlugin().getFullInventoryDoubleOpeningListener().processEvent(event);
		}
	}
	@EventHandler(priority = EventPriority.MONITOR)
	public void onQuit(PlayerQuitEvent event) {
		if(FullPluginLoader.isFullPlugin()){
			FullPluginLoader.getFullPlugin().getFullInventoryDoubleOpeningListener().processEvent(event);
		}	
	}
	//Inventory dubleOpen
	
	
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
		return new Permission[]{Permit.DOUBLE_OPENING};
	}
	
	@Override
	public boolean isRegisterred() {
		return isRegisterred;
	}
}
