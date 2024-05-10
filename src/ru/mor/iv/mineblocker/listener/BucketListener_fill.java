package ru.mor.iv.mineblocker.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketFillEvent;

import ru.mor.iv.mineblocker.MineBlockerPlugin;
import ru.mor.iv.mineblocker.event.ActionEvent;
import ru.mor.iv.mineblocker.event.PlayerActionEvent;
import ru.mor.iv.mineblocker.full.FullPluginLoader;
import ru.mor.iv.mineblocker.item.EventItem;
import ru.mor.iv.mineblocker.item.EventItemBlock;
import ru.mor.iv.mineblocker.message.Messager;
import ru.mor.iv.mineblocker.permission.Permission;
import ru.mor.iv.mineblocker.permission.Permit;

public class BucketListener_fill implements Listener, MineBlockerListener {
	private MineBlockerPlugin plugin;
	private Messager messageManager;
	private boolean isRegisterred = false;
	
	public BucketListener_fill () {
		this.plugin = MineBlockerPlugin.getInst();
		this.messageManager = plugin.getMessager();
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onBucketFill(PlayerBucketFillEvent event){
		Player player = event.getPlayer();
		ActionEvent e = new PlayerActionEvent(player, Permit.BUCKET_FILL, event.getBlockClicked().getLocation(), new EventItemBlock(event.getBlockClicked()));
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
		return new Permission[]{Permit.BUCKET_FILL};
	}

	@Override
	public boolean isRegisterred() {
		return isRegisterred;
	}

}
