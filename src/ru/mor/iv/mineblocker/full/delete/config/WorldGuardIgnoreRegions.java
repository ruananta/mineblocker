package ru.mor.iv.mineblocker.full.delete.config;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;

import ru.mor.iv.mineblocker.config.ConfigFeatures;
import ru.mor.iv.mineblocker.managers.privates.IgnoreRegionsMap;

public class WorldGuardIgnoreRegions extends ConfigFeatures {
	

	public WorldGuardIgnoreRegions(String fileName) {
		super(fileName);
	}
	
	@Override
	public void load() {
		ConfigurationSection cs = fileConfiguration.getRoot();
		if (!cs.getKeys(false).isEmpty()) {
			for (String w : cs.getKeys(false)) {
				IgnoreRegionsMap.put(w, fileConfiguration.getStringList(w));
			}
		}
	}
	
	@Override
	public void reload() {
		IgnoreRegionsMap.clear();
		load();
	}

	@Override
	public boolean hasEverything() {
		ConfigurationSection cs = fileConfiguration.getRoot();
		return cs.getKeys(false).isEmpty();
	}

	@Override
	public void createDefault() {
		List<String> list = new ArrayList<String>();
		fileConfiguration.addDefault("world", list);
		fileConfiguration.options().copyDefaults(true);
	}

}
