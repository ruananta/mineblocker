package ru.mor.iv.mineblocker.listener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import ru.mor.iv.mineblocker.MineBlockerPlugin;
import ru.mor.iv.mineblocker.event.ActionEvent;
import ru.mor.iv.mineblocker.event.PlayerActionEvent;
import ru.mor.iv.mineblocker.full.FullPluginLoader;
import ru.mor.iv.mineblocker.item.EventItem;
import ru.mor.iv.mineblocker.item.EventItemBlock;
import ru.mor.iv.mineblocker.item.EventItemEntity;
import ru.mor.iv.mineblocker.managers.VersionsBridge;
import ru.mor.iv.mineblocker.message.Messager;
import ru.mor.iv.mineblocker.permission.Permission;
import ru.mor.iv.mineblocker.permission.Permit;

public class EntityAtListener implements Listener, MineBlockerListener {
	private MineBlockerPlugin plugin;
	private Messager messageManager;
	private boolean isRegisterred = false;
	
	public EntityAtListener() {
		this.plugin = MineBlockerPlugin.getInst();
		this.messageManager = plugin.getMessager();
	}
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onClick(PlayerInteractAtEntityEvent event){
		Entity entity = event.getRightClicked();
		Player player = event.getPlayer();
		ActionEvent e;
		EventItem[] forWhat = new EventItem[]{new EventItemEntity(entity),  new EventItemBlock(VersionsBridge.getTarget(player, 100))};
		if(Permit.SHIFT_RIGHT_CLICK_BLOCK.isEnabled() && player.isSneaking()){
			e = new PlayerActionEvent(player, Permit.SHIFT_RIGHT_CLICK_BLOCK, entity.getLocation(), VersionsBridge.getItemsHand(player), forWhat);
		}else if(Permit.RIGHT_CLICK_BLOCK.isEnabled() && !player.isSneaking()){
			e = new PlayerActionEvent(player, Permit.RIGHT_CLICK_BLOCK, entity.getLocation(), VersionsBridge.getItemsHand(player), forWhat);
		}else{
			return;
		}
		plugin.getServer().getPluginManager().callEvent(e);
		if(e.isCancelled()){
			messageManager.sendMessage(e);
			if(e.getRemoveUsedItems() != null && FullPluginLoader.isFullPlugin()){
				for(EventItem item : e.getRemoveUsedItems()){
					FullPluginLoader.getFullPlugin().getItemRemover().remove(player, item.getItemStack(), item.getRemoveTicks());
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
		return new Permission[]{Permit.RIGHT_CLICK_BLOCK, Permit.SHIFT_RIGHT_CLICK_BLOCK};
	}

	@Override
	public boolean isRegisterred() {
		return isRegisterred;
	}
}
