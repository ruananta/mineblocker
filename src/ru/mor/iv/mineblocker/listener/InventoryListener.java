package ru.mor.iv.mineblocker.listener;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import ru.mor.iv.mineblocker.MineBlockerPlugin;
import ru.mor.iv.mineblocker.event.ActionEvent;
import ru.mor.iv.mineblocker.event.PlayerActionEvent;
import ru.mor.iv.mineblocker.full.FullPluginLoader;
import ru.mor.iv.mineblocker.full.PacketManager;
import ru.mor.iv.mineblocker.item.EventItem;
import ru.mor.iv.mineblocker.item.EventItemStack;
import ru.mor.iv.mineblocker.item.EventItemString;
import ru.mor.iv.mineblocker.managers.VersionsBridge;
import ru.mor.iv.mineblocker.message.Messager;
import ru.mor.iv.mineblocker.permission.Permission;
import ru.mor.iv.mineblocker.permission.Permit;


public class InventoryListener implements Listener, MineBlockerListener {
	private MineBlockerPlugin plugin;
	private Messager messageManager;
	private boolean isRegisterred = false;
	
	public InventoryListener() {
		this.plugin = MineBlockerPlugin.getInst();
		this.messageManager = plugin.getMessager();
	}
	
	
	//Inventory click event
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void invClick(InventoryClickEvent event){
		if(event.getWhoClicked() instanceof Player){	
			Permission permission = getPermission(event.getClick());
			if(permission == null || !permission.isEnabled()){
				return;
			}
			Player player = (Player) event.getWhoClicked();
			ItemStack currentStack = event.getCurrentItem();
			ItemStack numberStack = permission == Permit.INVCLICK_NUMBER_KEY ? player.getInventory().getItem(event.getHotbarButton()) : null;
			ItemStack cursorStack = event.getCursor();
			List<EventItem> stacks = new ArrayList<>();
			if(currentStack != null && currentStack.getType() != Material.AIR){
				stacks.add(new EventItemStack(currentStack));
			}
			if(numberStack != null && numberStack.getType() != Material.AIR){
				stacks.add(new EventItemStack(numberStack));
			}
			if(cursorStack != null && cursorStack.getType() != Material.AIR){
				stacks.add(new EventItemStack(cursorStack));
			}
			List<EventItem> inventory = new ArrayList<>();
			if(FullPluginLoader.isFullPlugin() && permission.isForWhatEnabled()){
				String inventoryClass = PacketManager.getOpenInventoryName(player);
				String inventoryName = VersionsBridge.getPlayerOpenInventory(player);
				if(inventoryName != null){
					inventory.add(new EventItemString(inventoryName));
				}
				inventory.add(new EventItemString(inventoryClass));
			}
			if(!stacks.isEmpty()){
				ActionEvent e = new PlayerActionEvent(player, permission, player.getLocation(), stacks.toArray(new EventItem[stacks.size()]), 
						inventory.toArray(new EventItem[inventory.size()]));
				plugin.getServer().getPluginManager().callEvent(e);
				if(e.isCancelled()){
					messageManager.sendMessage(e);
					event.setCancelled(true);
					player.updateInventory();
				}	
			}					
		}
		
	}
	private Permission getPermission(ClickType click) {
		switch (click) {
		case CREATIVE:
			return Permit.INVCLICK_CREATIVE;
		case DROP:
			return Permit.INVCLICK_DROP;
		case CONTROL_DROP:
			return Permit.INVCLICK_CONTROL_DROP;
		case LEFT:
			return Permit.INVCLICK_LEFT;
		case SHIFT_LEFT:
			return Permit.INVCLICK_SHIFT_LEFT;
		case RIGHT:
			return Permit.INVCLICK_RIGHT;
		case SHIFT_RIGHT:
			return Permit.INVCLICK_SHIFT_RIGHT;
		case DOUBLE_CLICK:
			return Permit.INVCLICK_DOUBLE_CLICK;
		case MIDDLE:
			return Permit.INVCLICK_MIDDLE;
		case NUMBER_KEY:
			return Permit.INVCLICK_NUMBER_KEY;
		case UNKNOWN:
			return Permit.INVCLICK_UNKNOWN;
		default:
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
		Permission[] perms = new Permission[]{
				Permit.INVCLICK_CREATIVE,
				Permit.INVCLICK_DROP,
				Permit.INVCLICK_CONTROL_DROP,
				Permit.INVCLICK_LEFT,
				Permit.INVCLICK_SHIFT_LEFT,
				Permit.INVCLICK_RIGHT,
				Permit.INVCLICK_SHIFT_RIGHT,
				Permit.INVCLICK_DOUBLE_CLICK,
				Permit.INVCLICK_MIDDLE,
				Permit.INVCLICK_NUMBER_KEY,
				Permit.INVCLICK_UNKNOWN
		};
		return perms;
	}
	
	@Override
	public boolean isRegisterred() {
		return isRegisterred;
	}
	
	
}
