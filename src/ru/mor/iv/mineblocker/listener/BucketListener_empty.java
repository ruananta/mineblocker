package ru.mor.iv.mineblocker.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.inventory.ItemStack;

import ru.mor.iv.mineblocker.MineBlockerPlugin;
import ru.mor.iv.mineblocker.event.ActionEvent;
import ru.mor.iv.mineblocker.event.PlayerActionEvent;
import ru.mor.iv.mineblocker.full.FullPluginLoader;
import ru.mor.iv.mineblocker.item.EventItem;
import ru.mor.iv.mineblocker.item.EventItemStack;
import ru.mor.iv.mineblocker.message.Messager;
import ru.mor.iv.mineblocker.permission.Permission;
import ru.mor.iv.mineblocker.permission.Permit;

public class BucketListener_empty implements Listener, MineBlockerListener{
	private MineBlockerPlugin plugin;
	private Messager messageManager;
	private boolean isRegisterred = false;
	
	public BucketListener_empty () {
		this.plugin = MineBlockerPlugin.getInst();
		this.messageManager = plugin.getMessager();
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onBucketEmpty(PlayerBucketEmptyEvent event){
		Player player = event.getPlayer();
		ActionEvent e = new PlayerActionEvent(player, Permit.BUCKET_EMPTY, event.getBlockClicked().getLocation(), new EventItemStack(new ItemStack(event.getBucket())));
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
		return new Permission[]{Permit.BUCKET_EMPTY};
	}

	@Override
	public boolean isRegisterred() {
		return isRegisterred;
	}
}
