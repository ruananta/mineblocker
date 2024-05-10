package ru.mor.iv.mineblocker.listener;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import ru.mor.iv.mineblocker.MineBlockerPlugin;
import ru.mor.iv.mineblocker.Utils;
import ru.mor.iv.mineblocker.event.ActionEvent;
import ru.mor.iv.mineblocker.event.PlayerActionEvent;
import ru.mor.iv.mineblocker.full.FullPluginLoader;
import ru.mor.iv.mineblocker.item.EventItem;
import ru.mor.iv.mineblocker.item.EventItemBlock;
import ru.mor.iv.mineblocker.managers.VersionsBridge;
import ru.mor.iv.mineblocker.message.Messager;
import ru.mor.iv.mineblocker.permission.Permission;
import ru.mor.iv.mineblocker.permission.Permit;

public class PlaceListener implements Listener, MineBlockerListener {

	private MineBlockerPlugin plugin;
	private Messager messageManager;
	private boolean isRegisterred = false;
	
	public PlaceListener () {
		this.plugin = MineBlockerPlugin.getInst();
		this.messageManager = plugin.getMessager();
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onPlace(BlockPlaceEvent event){
		Player player = event.getPlayer();
		Block block = event.getBlock();
		Location loc = block.getLocation();
		ActionEvent e = new PlayerActionEvent(player, Permit.PLACE, loc, new EventItemBlock(block));
		plugin.getServer().getPluginManager().callEvent(e);
		if(e.isCancelled()){
			messageManager.sendMessage(e);
			if(e.getRemoveUsedItems() != null && FullPluginLoader.isFullPlugin()){
				for(EventItem item : e.getRemoveUsedItems()){
					FullPluginLoader.getFullPlugin().getItemRemover().remove(player, Utils.getItemStackFromEventItem(VersionsBridge.getItemsHand(player), item), item.getRemoveTicks());
				}
			}
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
		return new Permission[]{Permit.PLACE};
	}
	
	@Override
	public boolean isRegisterred() {
		return isRegisterred;
	}
}
