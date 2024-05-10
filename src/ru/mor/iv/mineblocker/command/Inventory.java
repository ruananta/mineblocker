package ru.mor.iv.mineblocker.command;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import ru.mor.iv.mineblocker.config.string.MessageStrings;
import ru.mor.iv.mineblocker.full.PacketManager;
import ru.mor.iv.mineblocker.managers.VersionsBridge;

public class Inventory extends Argument implements Runnable {
	private static Map<Player, String> PLAYER_OPEN_INVENTORY = new HashMap<>();
	private static boolean isEnable;
	private static BukkitTask bukkitTask;
	
	public Inventory() {
		super(new String[] { "inventory", "inv"});
	}

	@Override
	public void commandInit(CommandSender sender, String[] args) {
		if(!isPlayer(sender)){
			messager.sendCommandMessage(sender, plugin.getMessage(MessageStrings.SENDER_ERROR));
			return;
		}
		Player player = (Player) sender;
		if(!isEnable(player)){
			messager.sendCommandMessage(sender, plugin.getMessage(MessageStrings.INVENTORY_OPEN));
			switchPlayer(player);
		}else{
			messager.sendCommandMessage(sender, plugin.getMessage(MessageStrings.INVENTORY_CLOSE));
			switchPlayer(player);
		}
	}

	public void switchPlayer(Player p){
		if(!isEnable(p)){
			PLAYER_OPEN_INVENTORY.put(p, null);
		}else{
			PLAYER_OPEN_INVENTORY.remove(p);
		}
		if(!isEnable){
			isEnable = true;
			bukkitTask = plugin.getServer().getScheduler().runTaskTimer(plugin, this, 20, 20);
		}
		if(isEnable && PLAYER_OPEN_INVENTORY.isEmpty()){
			isEnable = false;
			bukkitTask.cancel();
		}
	}
	
	public boolean isEnable(Player player) {
		for(Player p : new HashMap<>(PLAYER_OPEN_INVENTORY).keySet()){
			if(!p.isOnline()){
				PLAYER_OPEN_INVENTORY.remove(p);
			}
			if(p.equals(player)){
				return true;
			}
		}
		return false;
	}
	
	public static Set<Player> getPlayerList() {
		return PLAYER_OPEN_INVENTORY.keySet();
	}
	
	public void inventoryInfo(Player player) {
		if(isEnable(player)){
			String commandMessage = plugin.getMessage(MessageStrings.INFORM_INVENTORY);
			String fullInventoryName;
			String inventoryClass = PacketManager.getOpenInventoryName(player);
			String inventoryName = VersionsBridge.getPlayerOpenInventory(player);
			if(inventoryName != null){
				fullInventoryName = inventoryName + "&a || &4" + inventoryClass;
			}else{
				fullInventoryName = inventoryClass;
			}
			String mapName = PLAYER_OPEN_INVENTORY.get(player);
			if(mapName == null || !mapName.equals(fullInventoryName)){
				PLAYER_OPEN_INVENTORY.put(player, fullInventoryName);
				messager.sendCommandMessage(player, commandMessage, fullInventoryName);
			}
		}
	}

	@Override
	public void run() {
		for(Player p : getPlayerList()){
			if(p.isOnline()){
				inventoryInfo(p);
			}
		}
	}

}
