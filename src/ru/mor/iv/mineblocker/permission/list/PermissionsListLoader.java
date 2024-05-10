package ru.mor.iv.mineblocker.permission.list;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import ru.mor.iv.mineblocker.MineBlockerPlugin;
import ru.mor.iv.mineblocker.Utils;
import ru.mor.iv.mineblocker.full.FullPluginLoader;

public class PermissionsListLoader {
	private MineBlockerPlugin plugin = MineBlockerPlugin.getInst();
	private File permissionsListFile = new File(plugin.getDataFolder(), "PermissionsList.yml");
	private FileConfiguration permissionsListConfig;
	
	private PermissionsList permissionsList;
	
	public PermissionsListLoader(PermissionsList permissionsList) {
		this.permissionsList = permissionsList;
		loadDefConfig();
	}
	
	
	public void load(){
		permissionsListConfig = YamlConfiguration.loadConfiguration(permissionsListFile);	
		for(String cfg : permissionsListConfig.getRoot().getKeys(false)){
			if(permissionsList.isSection(cfg)){
				plugin.getLogger().info("[MineBlocker] section of the loading error. the name <" + cfg + "> is repeated.");
				continue;
			}
			List<String> worlds = permissionsListConfig.getStringList(cfg + ".worlds");
			List<String> groups = permissionsListConfig.getStringList(cfg + ".groups");
			List<String> regions = null;
			List<String> gamemodes = null;
			List<String> signLines = null;
			if(FullPluginLoader.isFullPlugin()){
				regions = permissionsListConfig.getStringList(cfg + ".regions");
				gamemodes = permissionsListConfig.getStringList(cfg + ".gamemodes");
				signLines = permissionsListConfig.getStringList(cfg + ".sign");
			}
			List<String> string_permissions = permissionsListConfig.getStringList(cfg + ".permissions");
			Permissions permissions = new Permissions(string_permissions);
			PermissionSection ps = new PermissionSection(Utils.toArray(worlds), Utils.toArray(groups), Utils.toArray(regions), Utils.toArray(gamemodes), Utils.toArray(signLines), permissions);
			permissionsList.add(cfg, ps);
		}
	}
	
	private void loadDefConfig() {
		if(!permissionsListFile.exists()){
			permissionsListConfig = YamlConfiguration.loadConfiguration(permissionsListFile);
			String cfg = "sample";
			ArrayList<String> all = new ArrayList<String>();
			all.add("place|packup.sample:0{nbt,nbt}|sample:1|megasample:*{!nbt}.wg");
			all.add("it.is.section.for.all.groups.and.worlds.and.regions");
			permissionsListConfig.addDefault(cfg + ".permissions", all);
			permissionsListConfig.options().copyDefaults(true);
			savePermissionList();
		}
	}
	
	public void reload() {
		load();
	}

	private void savePermissionList(){
		try {
			permissionsListConfig.save(permissionsListFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void add(String section, String type, String value) {
		List<String> l = permissionsListConfig.getStringList(section + "." + type);
		if(l == null){
			l = new ArrayList<>();
		}
		l.add(value);
		permissionsListConfig.set(section + "." + type, l);
		savePermissionList();
		permissionsList.reload();
	}
	
	public int remove(String section, String type, String value) {
		List<String> l = permissionsListConfig.getStringList(section + "." + type);
		if(l == null){
			return 0;
		}
		if(!l.contains(value)){
			return 1;
		}
		l.remove(value);
		if(l.isEmpty()){
			l = null;
		}
		permissionsListConfig.set(section + "." + type, l);
		savePermissionList();
		permissionsList.reload();
		return 3;
	}
	
	public List<String> getPermissions(String section){
		List<String> l = permissionsListConfig.getStringList(section + ".permissions");
		if(l == null){
			l = new ArrayList<>();
		}
		return l;
	}
	
	public List<String> getWorlds(String section){
		List<String> l = permissionsListConfig.getStringList(section + ".worlds");
		if(l == null){
			l = new ArrayList<>();
		}
		return l;
	}
	
	public List<String> getGroups(String section){
		List<String> l = permissionsListConfig.getStringList(section + ".groups");
		if(l == null){
			l = new ArrayList<>();
		}
		return l;
	}

	public List<String> getRegions(String section){
		List<String> l = permissionsListConfig.getStringList(section + ".regions");
		if(l == null){
			l = new ArrayList<>();
		}
		return l;
	}
	
	public List<String> getGameModes(String section){
		List<String> l = permissionsListConfig.getStringList(section + ".gamemodes");
		if(l == null){
			l = new ArrayList<>();
		}
		return l;
	}


	public Set<String> getSections() {
		return permissionsListConfig.getRoot().getKeys(false);
	}
}
