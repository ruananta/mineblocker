package ru.mor.iv.mineblocker.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import ru.mor.iv.mineblocker.MineBlockerPlugin;

public abstract class ConfigFeatures implements Config {
	protected MineBlockerPlugin plugin = MineBlockerPlugin.getInst();
	protected File file;
	protected FileConfiguration fileConfiguration;

	public ConfigFeatures(String fileName) {
		if(fileName != null){
			this.file = new File(plugin.getDataFolder(), fileName);
		}
	}

	public void loadFile() {
		fileConfiguration = YamlConfiguration.loadConfiguration(file);
	}

	public abstract void load();
	
	public abstract boolean hasEverything();

	public abstract void createDefault();

	public abstract void reload();
	
	public void save() {
		try {
			fileConfiguration.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
