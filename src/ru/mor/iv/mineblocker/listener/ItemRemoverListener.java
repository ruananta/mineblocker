package ru.mor.iv.mineblocker.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import ru.mor.iv.mineblocker.MineBlockerPlugin;
import ru.mor.iv.mineblocker.full.FullPluginLoader;
import ru.mor.iv.mineblocker.permission.Permission;
import ru.mor.iv.mineblocker.permission.Permit;

public class ItemRemoverListener implements MineBlockerListener, Listener {
	
	private boolean isRegisterred;
	private MineBlockerPlugin plugin;
	
	public ItemRemoverListener() {
		plugin = MineBlockerPlugin.getInst();
	}
	
	@EventHandler
	public void onJoin(PlayerLoginEvent event){
		if(FullPluginLoader.isFullPlugin())
			FullPluginLoader.getFullPlugin().getItemRemover().load(event.getPlayer());
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event){
		if(FullPluginLoader.isFullPlugin())
			FullPluginLoader.getFullPlugin().getItemRemover().unload(event.getPlayer());
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
		return new Permission[]{Permit.LEFT_CLICK_AIR, Permit.LEFT_CLICK_BLOCK, Permit.RIGHT_CLICK_AIR,
				Permit.RIGHT_CLICK_BLOCK, Permit.SHIFT_LEFT_CLICK_AIR, Permit.SHIFT_RIGHT_CLICK_BLOCK,
				Permit.DAMAGE, Permit.BUCKET_EMPTY, Permit.BUCKET_FILL, Permit.PLACE};
	}
	
	@Override
	public boolean isRegisterred() {
		return isRegisterred;
	}

}
