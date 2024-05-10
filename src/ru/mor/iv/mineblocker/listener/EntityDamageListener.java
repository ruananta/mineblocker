package ru.mor.iv.mineblocker.listener;

import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import ru.mor.iv.mineblocker.MineBlockerPlugin;
import ru.mor.iv.mineblocker.SignWrapper;
import ru.mor.iv.mineblocker.event.ActionEvent;
import ru.mor.iv.mineblocker.event.PlayerActionEvent;
import ru.mor.iv.mineblocker.full.FullPluginLoader;
import ru.mor.iv.mineblocker.item.EventItem;
import ru.mor.iv.mineblocker.item.EventItemEntity;
import ru.mor.iv.mineblocker.managers.VersionsBridge;
import ru.mor.iv.mineblocker.message.Messager;
import ru.mor.iv.mineblocker.permission.Permission;
import ru.mor.iv.mineblocker.permission.Permit;

public class EntityDamageListener implements Listener, MineBlockerListener {
	private MineBlockerPlugin plugin;
	private Messager messageManager;
	private boolean isRegisterred = false;

	public EntityDamageListener() {
		this.plugin = MineBlockerPlugin.getInst();
		this.messageManager = plugin.getMessager();
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void damage(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			Player player = (Player) event.getDamager();
			ActionEvent e = new PlayerActionEvent(player, Permit.DAMAGE, event.getEntity().getLocation(),
					new EventItemEntity(event.getEntity()), SignWrapper.searchSing(event.getEntity()));
			plugin.getServer().getPluginManager().callEvent(e);
			if (e.isCancelled()) {
				messageManager.sendMessage(e);
				if (e.getRemoveUsedItems() != null && FullPluginLoader.isFullPlugin()) {
					for (EventItem item : e.getRemoveUsedItems()) {
						FullPluginLoader.getFullPlugin().getItemRemover().remove(player,
								VersionsBridge.getItemsHand(player), item.getRemoveTicks());
					}
				}
				event.setCancelled(true);
			}
		} else if (event.getDamager() instanceof Projectile) {
			Projectile projectile = (Projectile) event.getDamager();
			if (projectile.getShooter() instanceof Player) {
				Player player = (Player) projectile.getShooter();
				PlayerActionEvent e = new PlayerActionEvent(player, Permit.DAMAGE, event.getEntity().getLocation(),
						new EventItemEntity(event.getEntity()), SignWrapper.searchSing(event.getEntity()));
				plugin.getServer().getPluginManager().callEvent(e);
				if (e.isCancelled()) {
					messageManager.sendMessage(e);
					projectile.remove();
					if (e.getRemoveUsedItems() != null && FullPluginLoader.isFullPlugin()) {
						for (EventItem item : e.getRemoveUsedItems()) {
							FullPluginLoader.getFullPlugin().getItemRemover().remove(player,
									VersionsBridge.getItemsHand(player), item.getRemoveTicks());
						}
					}
					event.setCancelled(true);
				}
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
		return new Permission[] { Permit.DAMAGE };
	}

	@Override
	public boolean isRegisterred() {
		return isRegisterred;
	}
}
