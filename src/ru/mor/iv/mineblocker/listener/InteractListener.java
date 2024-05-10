package ru.mor.iv.mineblocker.listener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import ru.mor.iv.mineblocker.MineBlockerPlugin;
import ru.mor.iv.mineblocker.SignWrapper;
import ru.mor.iv.mineblocker.event.ActionEvent;
import ru.mor.iv.mineblocker.event.PlayerActionEvent;
import ru.mor.iv.mineblocker.full.FullPluginLoader;
import ru.mor.iv.mineblocker.item.EventItem;
import ru.mor.iv.mineblocker.item.EventItemBlock;
import ru.mor.iv.mineblocker.item.EventItemStack;
import ru.mor.iv.mineblocker.item.ListItem;
import ru.mor.iv.mineblocker.managers.VersionsBridge;
import ru.mor.iv.mineblocker.message.Messager;
import ru.mor.iv.mineblocker.permission.Permission;
import ru.mor.iv.mineblocker.permission.Permit;

public class InteractListener implements Listener, MineBlockerListener {
	private MineBlockerPlugin plugin;
	private Messager messageManager;
	private boolean isRegisterred = false;
	
	public InteractListener() {
		this.plugin = MineBlockerPlugin.getInst();
		this.messageManager = plugin.getMessager();
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = false)
	public void onClick(PlayerInteractEvent event){
		Player player = event.getPlayer();
		Permission[] defPerm = getDefaultPermission(event.getAction(), player.isSneaking());
		if(defPerm == null) return;
		Block clickedBlock = null;
		EventItem itemInHand = null;
		if(!containsPhysical(defPerm)){
			clickedBlock = event.getClickedBlock();
			if(event.getItem() == null){
				itemInHand = new EventItemStack(new ItemStack(Material.AIR));
			}else{
				itemInHand = new EventItemStack(event.getItem());
			}
		}else{
			itemInHand = new EventItemBlock(event.getClickedBlock());
		}
		
		
		if(defPerm != null){
			EventItem eventBlock = null;
			SignWrapper signWrapper = null;
			if(clickedBlock != null){
				eventBlock = new EventItemBlock(clickedBlock);
				signWrapper = SignWrapper.searchSing(clickedBlock);
			}
			Location loc = clickedBlock != null ? clickedBlock.getLocation() : player.getLocation();
			ActionEvent e = new PlayerActionEvent(player, defPerm, loc, itemInHand, eventBlock, signWrapper);
			plugin.getServer().getPluginManager().callEvent(e);
			if(e.isCancelled()){
				messageManager.sendMessage(e);
				if(e.getRemoveUsedItems() != null && FullPluginLoader.isFullPlugin()){
					for(EventItem item : e.getRemoveUsedItems()){
						FullPluginLoader.getFullPlugin().getItemRemover().remove(player, item.getItemStack(), item.getRemoveTicks());
					}
				}
				checkAndCloseInventory(player, e.getBannedPermissions());
				event.setCancelled(true);
				return;
			}
		}

		if(clickedBlock == null && !containsPhysical(defPerm) && FullPluginLoader.isFullPlugin()){
			ListItem rangeItem = FullPluginLoader.getFullPlugin().getRangeItemManager().getRangeItem(itemInHand);
			
			if(rangeItem != null){
				clickedBlock = VersionsBridge.getTarget(player, rangeItem.getRange());
			}
			if(clickedBlock != null && !clickedBlock.getType().equals(Material.AIR)){
				SignWrapper signWrapper = SignWrapper.searchSing(clickedBlock);
				defPerm = getDefaultPermissionOnBlock(event.getAction(), player.isSneaking());
				if(defPerm != null){
					ActionEvent e = new PlayerActionEvent(player, defPerm, clickedBlock.getLocation(), itemInHand, new EventItemBlock(clickedBlock), signWrapper);
					plugin.getServer().getPluginManager().callEvent(e);
					if(e.isCancelled()){
						messageManager.sendMessage(e);
						if(e.getRemoveUsedItems() != null && FullPluginLoader.isFullPlugin()){
							for(EventItem item : e.getRemoveUsedItems()){
								FullPluginLoader.getFullPlugin().getItemRemover().remove(player, item.getItemStack(), item.getRemoveTicks());
							}
						}
						checkAndCloseInventory(player, e.getBannedPermissions());
						event.setCancelled(true);
					}
				}
			}
		}
	}
	
	

	private void checkAndCloseInventory(final Player player, Permission[] bannedPermissions) {
		for (Permission p : bannedPermissions) {
			if (p == Permit.OPENING) {
				plugin.getServer().getScheduler().runTaskLater(this.plugin, new Runnable() {
					public void run() {
						if (player.getOpenInventory() != null) {
							player.closeInventory();
						}
					}
				}, 1L);
				return;
			}
		}
	}

	private boolean containsPhysical(Permission[] defPerm) {
		for(Permission p : defPerm){
			if(p == Permit.PHYSICAL){
				return true;
			}
		}
		return false;
	}

	private Permission[] getDefaultPermission(Action action, boolean shift) {
		Permission defPerm = null;
		boolean opening = false;
		switch (action) {
		case LEFT_CLICK_AIR:
			if(!shift)
				defPerm = Permit.LEFT_CLICK_AIR;
			else 
				defPerm = Permit.SHIFT_LEFT_CLICK_AIR;
			break;
		case LEFT_CLICK_BLOCK:
			if(!shift)
				defPerm = Permit.LEFT_CLICK_BLOCK;
			else 
				defPerm = Permit.SHIFT_LEFT_CLICK_BLOCK;
			break;
		case RIGHT_CLICK_AIR:
			if(!shift)
				defPerm = Permit.RIGHT_CLICK_AIR;
			else 
				defPerm = Permit.SHIFT_RIGHT_CLICK_AIR;
			break;
		case RIGHT_CLICK_BLOCK:
			opening = true;
			if(!shift)
				defPerm = Permit.RIGHT_CLICK_BLOCK;
			else 
				defPerm = Permit.SHIFT_RIGHT_CLICK_BLOCK;
			break;
		case PHYSICAL:
			defPerm = Permit.PHYSICAL;
		default:
			break;
		}
		Permission[] perms = null;
		if(opening && Permit.OPENING.isEnabled()){
			perms = new Permission[]{defPerm, Permit.OPENING};
		}else if(defPerm.isEnabled()){
			perms = new Permission[]{defPerm};
		}
		return perms;
	}
	
	private Permission[] getDefaultPermissionOnBlock(Action action, boolean shift) {
		Permission defPerm = null;
		boolean opening = false;
		if(action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK){
			if(!shift)
				defPerm = Permit.LEFT_CLICK_BLOCK;
			else 
				defPerm = Permit.SHIFT_LEFT_CLICK_BLOCK;
		}else if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK){
			if(!shift)
				defPerm = Permit.RIGHT_CLICK_BLOCK;
			else 
				defPerm = Permit.SHIFT_RIGHT_CLICK_BLOCK;
		}
		if(defPerm == null){
			return null;
		}
		Permission[] perms;
		if(opening && Permit.OPENING.isEnabled()){
			perms = new Permission[]{defPerm, Permit.OPENING};
		}else{
			perms = new Permission[]{defPerm};
		}
		if(defPerm.isEnabled()){
			return perms;
		}else{
			return null;
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
		return new Permission[]{Permit.LEFT_CLICK_AIR, Permit.LEFT_CLICK_BLOCK, Permit.SHIFT_LEFT_CLICK_AIR, Permit.SHIFT_LEFT_CLICK_BLOCK,
				Permit.RIGHT_CLICK_AIR, Permit.RIGHT_CLICK_BLOCK, Permit.SHIFT_RIGHT_CLICK_AIR, Permit.SHIFT_RIGHT_CLICK_BLOCK,
				Permit.PHYSICAL, Permit.OPENING};
	}
	
	@Override
	public boolean isRegisterred() {
		return isRegisterred;
	}
}
