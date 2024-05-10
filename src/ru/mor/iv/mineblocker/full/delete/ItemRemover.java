package ru.mor.iv.mineblocker.full.delete;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import ru.mor.iv.mineblocker.MineBlockerPlugin;
import ru.mor.iv.mineblocker.Utils;
import ru.mor.iv.mineblocker.item.EventItem;

public class ItemRemover implements Runnable{
	private static File dir = new File(MineBlockerPlugin.getInst().getDataFolder() + "/items");
	private static ItemRemover instance;
	private static BukkitTask bukkitTask;
	private static List<PlayerRemoveItems> list = new ArrayList<>();
	
	private void init(){
		if(instance == null){
			if(!dir.exists()) dir.mkdirs();
			instance = new ItemRemover();
			bukkitTask = MineBlockerPlugin.getInst().getServer().getScheduler().runTaskTimer(MineBlockerPlugin.getInst(), instance, 1L, 1L);
		}
		
	}
	public void load(Player player){
		getPlayerRemoveItems(player);
		init();
	}
	
	public void unload(Player player){
		PlayerRemoveItems playerRemoveItems = getPlayerRemoveItems(player);
		if(!playerRemoveItems.getItems().isEmpty()){
			playerRemoveItems.save();
			list.remove(playerRemoveItems);
		}
	}
	
	public void remove(Player player, ItemStack item, int removeTicks){
		if(player != null && item != null && removeTicks > 0){
			if(item.getType() != Material.AIR){
				PlayerRemoveItems playerRemoveItems = getPlayerRemoveItems(player);
				playerRemoveItems.removeItem(item, removeTicks);
				init();
			}
		}
	}
	public void remove(Player player, EventItem[] items, int removeTicks) {
		if(player != null && Utils.notNull(items) && removeTicks > 0){
			for(EventItem i : items){
				remove(player, i.getItemStack(), removeTicks);
			}
		}
	}
	
	private PlayerRemoveItems getPlayerRemoveItems(Player player) {
		for(PlayerRemoveItems playerRemoveItems : list){
			if(playerRemoveItems.getPlayer().equals(player)){
				return playerRemoveItems;
			}
		}
		PlayerRemoveItems playerRemoveItems = new PlayerRemoveItems(player);
		list.add(playerRemoveItems);
		return playerRemoveItems;
	}
	
	public void shutdown(){
		for(PlayerRemoveItems items : list){
			if(items.getItems().isEmpty()){
				items.delete();
			}else{
				items.save();
			}
		}
	}
	
	private void stop(){
		bukkitTask.cancel();
		bukkitTask = null;
		instance = null;
	}

	@Override
	public void run() {
		Iterator<PlayerRemoveItems> i = list.iterator();
		while(i.hasNext()){
			PlayerRemoveItems items = i.next();
			for(RemovedItem e: items.getItems()){
				if(e.getTicks() == 0){
					items.returnItem(e);
				}else{
					e.setTicks(e.getTicks() - 1);
				}
			}
			if(items.getItems().isEmpty()){
				items.delete();
				//list.remove(items);
				i.remove();
			}
		}
		if(list.isEmpty()){
			stop();
		}
	}
	
	public static File getDir() {
		return dir;
	}
	
}
