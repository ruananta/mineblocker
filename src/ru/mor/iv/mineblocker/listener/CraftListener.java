package ru.mor.iv.mineblocker.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

import ru.mor.iv.mineblocker.MineBlockerPlugin;
import ru.mor.iv.mineblocker.event.ActionEvent;
import ru.mor.iv.mineblocker.event.PlayerActionEvent;
import ru.mor.iv.mineblocker.item.EventItemStack;
import ru.mor.iv.mineblocker.message.Messager;
import ru.mor.iv.mineblocker.permission.Permission;
import ru.mor.iv.mineblocker.permission.Permit;

public class CraftListener implements Listener, MineBlockerListener{
	private MineBlockerPlugin plugin;
	private Messager messageManager;
	private boolean isRegisterred = false;
	
	
	public CraftListener () {
		this.plugin = MineBlockerPlugin.getInst();
		this.messageManager = plugin.getMessager();
	}
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onCraft(CraftItemEvent event){
		if(event.getWhoClicked() instanceof Player){
			Player player = (Player)event.getWhoClicked();
			ActionEvent e = new PlayerActionEvent(player, Permit.CRAFT, player.getLocation(), new EventItemStack(event.getRecipe().getResult()));
			plugin.getServer().getPluginManager().callEvent(e);
			if(e.isCancelled()){
				messageManager.sendMessage(e);
				event.setCancelled(true);
			}		
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
		return new Permission[]{Permit.CRAFT};
	}

	@Override
	public boolean isRegisterred() {
		return isRegisterred;
	}

}
