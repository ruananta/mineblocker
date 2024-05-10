package ru.mor.iv.mineblocker.full.delete;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import ru.mor.iv.mineblocker.MineBlockerPlugin;
import ru.mor.iv.mineblocker.event.ActionEvent;
import ru.mor.iv.mineblocker.event.PlayerActionEvent;
import ru.mor.iv.mineblocker.full.PacketManager;
import ru.mor.iv.mineblocker.item.EventItem;
import ru.mor.iv.mineblocker.item.EventItemBlock;
import ru.mor.iv.mineblocker.item.EventItemEntity;
import ru.mor.iv.mineblocker.managers.VersionsBridge;
import ru.mor.iv.mineblocker.message.Messager;
import ru.mor.iv.mineblocker.permission.Permit;

public class FullInventoryDoubleOpeningListener implements Listener {
	private MineBlockerPlugin plugin;
	private Messager messageManager;
	private Map<Player, BlockState> openBlockInventory = new HashMap<>();
	private Map<Player, Entity> openEntityInventory = new HashMap<>();
	
	public FullInventoryDoubleOpeningListener() {
		this.plugin = MineBlockerPlugin.getInst();
		this.messageManager = plugin.getMessager();
	}
	

	public void processEvent(PlayerInteractEvent event){
		if(event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getPlayer().isSneaking() && !isEmptyHand(event.getPlayer())){
			return;
		}
		Block block = event.getClickedBlock();
		if(!PacketManager.hasInventory(block)){
			return;
		}
		Player player = event.getPlayer();
		if(openBlockInventory.get(player) != null && !openBlockInventory.get(player).equals(block.getState())){
			player.closeInventory();
			openBlockInventory.put(player, block.getState());
			event.setCancelled(true);
			return;
		}
		for(BlockState entry : openBlockInventory.values()){
			if(entry.equals(block.getState())){
				ActionEvent e = new PlayerActionEvent(player, Permit.DOUBLE_OPENING, block.getLocation(), new EventItemBlock(block));
				plugin.getServer().getPluginManager().callEvent(e);
				if(e.isCancelled()){
					messageManager.sendMessage(e);
					event.setCancelled(true);
					return;
				}
			}
		}
		openBlockInventory.put(player, block.getState());
	}
	
	private boolean isEmptyHand(Player player) {
		for(EventItem e : VersionsBridge.getItemsHand(player)){
			if(e.getName().getType() != Material.AIR){
				return false;
			}
		}
		return true;
	}

	public void processEvent(PlayerInteractEntityEvent event){
		Entity entity = event.getRightClicked();
		if(!PacketManager.hasInventory(entity)){
			return;
		}
		Player player = event.getPlayer();
		if(openEntityInventory.get(player) != null && !openEntityInventory.get(player).equals(entity)){
			player.closeInventory();
			openEntityInventory.put(player, entity);
			event.setCancelled(true);
			return;
		}
		for(Entity entry : openEntityInventory.values()){
			if(entry.equals(entity)){
				PlayerActionEvent e = new PlayerActionEvent(player, Permit.DOUBLE_OPENING, entity.getLocation(), new EventItemEntity(entity));
				plugin.getServer().getPluginManager().callEvent(e);
				if(e.isCancelled()){
					messageManager.sendMessage(e);
					event.setCancelled(true);
					return;
				}
			}
		}
		openEntityInventory.put(player, entity);
	}
	
	public void processEvent(InventoryCloseEvent event) {
		openBlockInventory.remove(event.getPlayer());
		openEntityInventory.remove(event.getPlayer());
	}
	
	public void processEvent(PlayerQuitEvent event) {
		openBlockInventory.remove(event.getPlayer());
		openEntityInventory.remove(event.getPlayer());
	}
	
}
