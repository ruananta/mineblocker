package ru.mor.iv.mineblocker.listener;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;

import ru.mor.iv.mineblocker.MineBlockerPlugin;
import ru.mor.iv.mineblocker.event.ActionEvent;
import ru.mor.iv.mineblocker.full.FullPluginLoader;
import ru.mor.iv.mineblocker.item.EventItem;
import ru.mor.iv.mineblocker.item.EventItemBlock;
import ru.mor.iv.mineblocker.managers.VersionsBridge;
import ru.mor.iv.mineblocker.permission.Permission;
import ru.mor.iv.mineblocker.permission.Permit;

public class PistonListener implements Listener, MineBlockerListener {
	private MineBlockerPlugin plugin = MineBlockerPlugin.getInst();
	private boolean isRegisterred = false;

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onPistonExtend(BlockPistonExtendEvent event){
		if(!Permit.PISTON_EXTEND.isEnabled()){
			return;
		}
		if(!FullPluginLoader.isFullPlugin()){
			return;
		}
		String world = event.getBlock().getWorld().getName();
		List<EventItem> list = new ArrayList<EventItem>();
		for(Block b : event.getBlocks()){
			if(b != null){
				list.add(new EventItemBlock(b));
			}
		}
		EventItem[] array = list.toArray(new EventItem[list.size()]);
		ActionEvent e = new ActionEvent(new Permission[]{Permit.PISTON_EXTEND}, array);
		plugin.getServer().getPluginManager().callEvent(e);
		if(e.isCancelled()){
			event.setCancelled(true);
			return;
		}
		if(FullPluginLoader.getFullPlugin().getPistonBlackList().contains(world, list)){
			event.setCancelled(true);
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onPistonRetract(BlockPistonRetractEvent event){
		if(!Permit.PISTON_RETRACT.isEnabled()){
			return;
		}
		if(!FullPluginLoader.isFullPlugin()){
			return;
		}
		if(!event.isSticky()){
			return;
		}
		String world = event.getBlock().getWorld().getName();
		List<EventItem> list = VersionsBridge.getRetractBlocks(event);
		EventItem[] array = list.toArray(new EventItem[list.size()]);
		ActionEvent e = new ActionEvent(new Permission[]{Permit.PISTON_RETRACT}, array);
		plugin.getServer().getPluginManager().callEvent(e);
		if(e.isCancelled()){
			event.setCancelled(true);
			return;
		}
		if(FullPluginLoader.getFullPlugin().getPistonBlackList().contains(world, list)){
			event.setCancelled(true);
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
		return new Permission[]{Permit.PISTON_EXTEND, Permit.PISTON_RETRACT};
	}

	@Override
	public boolean isRegisterred() {
		return isRegisterred;
	}
}
