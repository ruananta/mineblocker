package ru.mor.iv.mineblocker.full.delete;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;

import ru.mor.iv.mineblocker.MineBlockerPlugin;
import ru.mor.iv.mineblocker.Utils;
import ru.mor.iv.mineblocker.event.ActionEvent;
import ru.mor.iv.mineblocker.event.PlayerActionEvent;
import ru.mor.iv.mineblocker.full.FullPluginLoader;
import ru.mor.iv.mineblocker.item.EventItem;
import ru.mor.iv.mineblocker.item.EventItemBlock;
import ru.mor.iv.mineblocker.managers.VersionsBridge;
import ru.mor.iv.mineblocker.permission.Permit;

public class FullPlaceNearListener {

	public void processEvent(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		Block block = event.getBlock();
		Location loc = block.getLocation();
		ActionEvent e = new PlayerActionEvent(player,  Permit.PLACE_NEAR, loc, new EventItemBlock(block));
		MineBlockerPlugin.getInst().getServer().getPluginManager().callEvent(e);
		if(e.isCancelled()){
			MineBlockerPlugin.getInst().getMessager().sendMessage(e);
			if(e.getRemoveUsedItems() != null && FullPluginLoader.isFullPlugin()){
				for(EventItem item : e.getRemoveUsedItems()){
					FullPluginLoader.getFullPlugin().getItemRemover().remove(player, Utils.getItemStackFromEventItem(VersionsBridge.getItemsHand(player), item), item.getRemoveTicks());
				}
			}
			event.setCancelled(true);	
		}
	}
}
