package ru.mor.iv.mineblocker.listener;

import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

import ru.mor.iv.mineblocker.MineBlockerPlugin;
import ru.mor.iv.mineblocker.event.ActionEvent;
import ru.mor.iv.mineblocker.event.PlayerActionEvent;
import ru.mor.iv.mineblocker.item.EventItemStack;
import ru.mor.iv.mineblocker.message.Messager;
import ru.mor.iv.mineblocker.permission.Permission;
import ru.mor.iv.mineblocker.permission.Permit;

public class PickupListener implements Listener, MineBlockerListener {
	private MineBlockerPlugin plugin;
	private Messager messageManager;
	private boolean isRegisterred = false;
	
	public PickupListener() {
		this.plugin = MineBlockerPlugin.getInst();
		this.messageManager = plugin.getMessager();
	}
	
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onPickup(PlayerPickupItemEvent event){
		Player player = event.getPlayer();
		Item i = event.getItem();
		ActionEvent e = new PlayerActionEvent(player, Permit.PACKUP, i.getLocation(), new EventItemStack(i.getItemStack()));
		plugin.getServer().getPluginManager().callEvent(e);
		if(e.isCancelled()){
			messageManager.sendMessage(e);
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
		return new Permission[]{Permit.PACKUP};
	}
	
	@Override
	public boolean isRegisterred() {
		return isRegisterred;
	}
}
