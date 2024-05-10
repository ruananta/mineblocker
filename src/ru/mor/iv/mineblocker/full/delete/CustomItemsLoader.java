package ru.mor.iv.mineblocker.full.delete;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import ru.mor.iv.mineblocker.MineBlockerPlugin;

public class CustomItemsLoader {
	private MineBlockerPlugin plugin = MineBlockerPlugin.getInst();
	private File customItemsFile = new File(plugin.getDataFolder(), "custom_items.yml");
	private FileConfiguration customItemsConf;
	private Map<String, List<String>> customItemsList = new HashMap<>();
	
	public CustomItemsLoader() {
		createDefaultConf();
	}
	
	public List<String> getCustomItems(String name){
		return customItemsList.get(name);
	}
	
	
	public void load() {
		customItemsConf = YamlConfiguration.loadConfiguration(customItemsFile);
		for(String name : customItemsConf.getKeys(false)){
			List<String> items = customItemsConf.getStringList(name);
			if(items != null && !items.isEmpty()){
				customItemsList.put(name, items);
			}
		}
	}

	private void createDefaultConf() {
		customItemsConf = YamlConfiguration.loadConfiguration(customItemsFile);
		if(customItemsConf.getKeys(false).isEmpty()){
			List<String> list = new ArrayList<>();
			list.add("wood_sword");
			list.add("stone_sword");
			list.add("iron_sword");
			list.add("gold_sword");
			list.add("diamond_sword");
			customItemsConf.addDefault("all_swords", list);
			list = new ArrayList<>();
			list.add("wood_pickaxe");
			list.add("stone_pickaxe");
			list.add("iron_pickaxe");
			list.add("gold_pickaxe");
			list.add("diamond_pickaxe");
			customItemsConf.addDefault("all_pickaxe", list);
			list = new ArrayList<>();
			list.add("piston_base");
			list.add("piston_sticky_base");
			customItemsConf.addDefault("поршень", list);
			customItemsConf.options().copyDefaults(true);
			try {
				customItemsConf.save(customItemsFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void reload(){
		customItemsList.clear();
		load();
	}

	
}
