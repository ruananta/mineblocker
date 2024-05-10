package ru.mor.iv.mineblocker.full.delete;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import ru.mor.iv.mineblocker.MineBlockerPlugin;
import ru.mor.iv.mineblocker.full.PacketManager;
import ru.mor.iv.mineblocker.managers.VersionsBridge;

public class PlayerRemoveItems {
	private File file;
	private String itemsSection = "items";
	private Player player;
	private List<RemovedItem> items = new ArrayList<>();
	
	public PlayerRemoveItems(Player player) {
		this.player = player;
		load();
	}
	

	public Player getPlayer() {
		return player;
	}

	public List<RemovedItem> getItems() {
		return new ArrayList<>(items);
	}
	
	public void removeItem(ItemStack item, int ticks) {
		if(VersionsBridge.removeItem(player, item)){
			items.add(new RemovedItem(item, ticks));
			player.updateInventory();
			return;
		}
		HashMap<Integer, ItemStack> e = player.getInventory().removeItem(item);
		if(!e.isEmpty()){
			MineBlockerPlugin.debug("[MineBLocker] remove stack error!");
		}
		items.add(new RemovedItem(item, ticks));
		player.updateInventory();
	}
	
	
	public void returnItem(RemovedItem item) {
		items.remove(item);
		HashMap<Integer, ItemStack> m = player.getInventory().addItem(item.getItemsStack());
		if(!m.isEmpty()){
			for(ItemStack i : m.values()){
				items.add(new RemovedItem(i, 20));
			}
		}else{
			player.updateInventory();
		}
	}
	
	private void load() {
		file = new File(ItemRemover.getDir(), player.getName() + ".yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		List<String> list = config.getStringList(itemsSection);
		if(list == null) return;
		for(String s : list){
			try {
				Inventory inv = PacketManager.fromString(s);
				for(ItemStack i : inv.getContents()){
					items.add(new RemovedItem(i, 20));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void save(){
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		List<String> stringItems = new ArrayList<>();
		for(RemovedItem i : items){
			try {
				stringItems.add(PacketManager.toString(new ItemStack[]{i.getItemsStack()}));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		config.set(itemsSection, stringItems);
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void delete() {
		file.delete();
	}
	
}
