package ru.mor.iv.mineblocker.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import ru.mor.iv.mineblocker.MineBlockerPlugin;
import ru.mor.iv.mineblocker.full.FullPluginLoader;
import ru.mor.iv.mineblocker.permission.Permission;
import ru.mor.iv.mineblocker.permission.Permit;

public class PlaceNearListener implements Listener, MineBlockerListener {

	private MineBlockerPlugin plugin;
	private boolean isRegisterred = false;
	
	public PlaceNearListener () {
		this.plugin = MineBlockerPlugin.getInst();
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onPlace(BlockPlaceEvent event){
		if(FullPluginLoader.isFullPlugin()){
			FullPluginLoader.getFullPlugin().getFullPlaceNearListener().processEvent(event);
		}
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
		return new Permission[]{Permit.PLACE_NEAR};
	}
	
	@Override
	public boolean isRegisterred() {
		return isRegisterred;
	}
		
}
