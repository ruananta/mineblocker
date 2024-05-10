package ru.mor.iv.mineblocker.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import ru.mor.iv.mineblocker.MineBlockerPlugin;
import ru.mor.iv.mineblocker.Utils;
import ru.mor.iv.mineblocker.config.ConfigHolder;
import ru.mor.iv.mineblocker.event.ActionEvent;
import ru.mor.iv.mineblocker.event.PlayerActionEvent;
import ru.mor.iv.mineblocker.full.FullPluginLoader;
import ru.mor.iv.mineblocker.item.EventItem;
import ru.mor.iv.mineblocker.item.EventItemStack;
import ru.mor.iv.mineblocker.managers.VersionsBridge;
import ru.mor.iv.mineblocker.message.Messager;
import ru.mor.iv.mineblocker.permission.Permission;
import ru.mor.iv.mineblocker.permission.Permit;

public class HaveListener implements Listener, MineBlockerListener {
	private MineBlockerPlugin plugin;
	private Messager messageManager;
	private boolean isRegisterred = false;
	
	public HaveListener() {
		this.plugin = MineBlockerPlugin.getInst();
		this.messageManager = plugin.getMessager();
	}
	
	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = false)
	public void onPlace(BlockPlaceEvent event){
		if(ConfigHolder.isBlockPlaceEventHaveCheck()){
			checkPlayerInventory(event.getPlayer());
		}
	}
	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = false)
	public void onBreak(BlockBreakEvent event){
		if(ConfigHolder.isBlockBreakEventHaveCheck()){
			checkPlayerInventory(event.getPlayer());
		}
	}
	
	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = false)
	public void onBucketEmpty(PlayerBucketEmptyEvent event){
		if(ConfigHolder.isBucketEventEmpty_HaveCheck()){
			checkPlayerInventory(event.getPlayer());
		}
	}
	
	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = false)
	public void onBucketFill(PlayerBucketFillEvent event){
		if(ConfigHolder.isBucketEventFill_HaveCheck()){
			checkPlayerInventory(event.getPlayer());
		}
	}
	
	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = false)
	public void onCraft(CraftItemEvent event){
		if(ConfigHolder.isCraftHaveCheck()){
			if(event.getWhoClicked() instanceof Player){
				checkPlayerInventory((Player) event.getWhoClicked());
			}
		}
	}
	
	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = false)
	public void onDrop(PlayerDropItemEvent event){
		if(ConfigHolder.isDropEventHaveCheck())
			checkPlayerInventory(event.getPlayer());
	}
	
	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = false)
	public void onDamage(EntityDamageByEntityEvent event){
		if(ConfigHolder.isEntityDamageEventHaveCheck()){
			Player player = null;
			if (event.getDamager() instanceof Player){
				player = (Player)event.getDamager();
			}else if(event.getDamager() instanceof Projectile){
		    	  Projectile projectile = (Projectile) event.getDamager();
		    	  if (projectile.getShooter() instanceof Player) {
		  			player = (Player) projectile.getShooter();
		    	  }
			}
			if(player != null){
				checkPlayerInventory(player);
			}
		}
	}	
	
	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = false)
	public void onClick(PlayerInteractEvent event){
		Action action = event.getAction();
		if(ConfigHolder.isLeftInteractEventHaveCheck()){
			if(action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK){
				checkPlayerInventory(event.getPlayer());
				return;
			}
		}
		if(ConfigHolder.isRightInteractEvent_HaveCheck()){
			if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK){
				checkPlayerInventory(event.getPlayer());
				return;
			}
		}
	}
	
	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = false)
	public void invClickHave(InventoryClickEvent event){
		if(ConfigHolder.isInventoryEventHaveCheck()){
			if(event.getWhoClicked() instanceof Player){
				Player player = (Player) event.getWhoClicked();
				checkPlayerInventory(player);
			}
		}
			
	}
	
	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = false)
	public void onPackup(PlayerPickupItemEvent event){	
		if(ConfigHolder.isPackupEventHaveCheck()){
			checkPlayerInventory(event.getPlayer());
		}
	}
	
	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = false)
	public void onClick(PlayerInteractEntityEvent event){
		if(ConfigHolder.isRightInteractEvent_HaveCheck()){
			checkPlayerInventory(event.getPlayer());
		}
	}
	
	
	public void checkPlayerInventory(Player player) {
		if(Permit.HAVE.isEnabled() || Permit.HAVE_R.isEnabled()){
			PlayerInventory inventory = player.getInventory();
			EventItem[] stacks = getItemStacks(inventory);
			if(stacks != null){
				if(FullPluginLoader.isFullPlugin()){
					FullPluginLoader.getFullPlugin().getFullHaveListener().checkMaxStackSize(stacks);
				}
		    	List<Permission> perms = new ArrayList<>();
	    		if(Permit.HAVE.isEnabled()){
	    			perms.add(Permit.HAVE);
	    		}
	    		if(Permit.HAVE_R.isEnabled()){
	    			perms.add(Permit.HAVE_R);
	    		}
	    		ActionEvent e = new PlayerActionEvent(player, perms.toArray(new Permission[perms.size()]), player.getLocation(), stacks);
	    		plugin.getServer().getPluginManager().callEvent(e);
	    		if(e.isCancelled()){
	    			messageManager.sendMessage(e);
	    			for(EventItem item : e.getBannedUsedItems()){
	    				remove(inventory, item.getItemStack());
		    			if(!containsRemovePermission(e.getBannedPermissions())){
		    				e.getActor().getWorld().dropItemNaturally(player.getLocation(), item.getItemStack());
		    			}
	    			}
	    			player.updateInventory();
	    		}
			}
		}
  	}

	

	private void remove(PlayerInventory inventory, ItemStack stack) {
		HashMap<Integer, ItemStack> map = inventory.removeItem(stack);
		if(map != null && !map.isEmpty()){
			for(ItemStack s : map.values()){
				if(s.equals(inventory.getHelmet())){
					inventory.setHelmet(null);
					continue;
				}else if(s.equals(inventory.getChestplate())){
					inventory.setChestplate(null);
					continue;
				}else if(s.equals(inventory.getLeggings())){
					inventory.setLeggings(null);
					continue;
				}else if(s.equals(inventory.getBoots())){
					inventory.setBoots(null);
					continue;
				}else if(s.equals(VersionsBridge.getItemInOffHand(inventory))){
					inventory.setItemInOffHand(null);
					continue;
				}
			}
		}
	}

	private EventItem[] getItemStacks(PlayerInventory inventory) {
		EventItem[] items = null;
		for (ItemStack stack : inventory.getContents()) {
	    	if (stack != null && stack.getType() != Material.AIR) {	
	    		items = Utils.addItem(items, new EventItemStack(stack));
	    	}
		}
		ItemStack offHand = VersionsBridge.getItemInOffHand(inventory);
		if(offHand != null && offHand.getType() != Material.AIR){
			items = Utils.addItem(items, new EventItemStack(offHand));
		}
		return items;
	}

	private boolean containsRemovePermission(Permission[] bannedPermissions) {
		for(Permission p : bannedPermissions){
			if(p == Permit.HAVE_R){
				return true;
			}
		}
		return false;
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
		return new Permission[]{Permit.HAVE, Permit.HAVE_R};
	}
	
	@Override
	public boolean isRegisterred() {
		return isRegisterred;
	}

}

