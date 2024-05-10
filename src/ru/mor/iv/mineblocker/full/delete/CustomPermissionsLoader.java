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
import ru.mor.iv.mineblocker.permission.Permit;

public class CustomPermissionsLoader {
	private MineBlockerPlugin plugin = MineBlockerPlugin.getInst();
	private File customPermissionsFile = new File(plugin.getDataFolder(), "custom_permissions.yml");
	private FileConfiguration customPermissionsConf;
	private Map<String, List<String>> customPermissionsList = new HashMap<>();
	
	public CustomPermissionsLoader() {
		createDefaultConf();
	}
	
	public List<String> getCustomPermissions(String name){
		return customPermissionsList.get(name);
	}
	
	
	public void load() {
		customPermissionsConf = YamlConfiguration.loadConfiguration(customPermissionsFile);
		for(String name : customPermissionsConf.getKeys(false)){
			List<String> perms = customPermissionsConf.getStringList(name);
			if(perms != null && !perms.isEmpty()){
				customPermissionsList.put(name, perms);
			}
		}
	}

	private void createDefaultConf() {
		customPermissionsConf = YamlConfiguration.loadConfiguration(customPermissionsFile);
		if(customPermissionsConf.getKeys(false).isEmpty()){
			List<String> list0 = new ArrayList<>();
			list0.add(Permit.INVCLICK_CONTROL_DROP.getStringPermission());
			list0.add(Permit.INVCLICK_CREATIVE.getStringPermission());
			list0.add(Permit.INVCLICK_DOUBLE_CLICK.getStringPermission());
			list0.add(Permit.INVCLICK_DROP.getStringPermission());
			list0.add(Permit.INVCLICK_LEFT.getStringPermission());
			list0.add(Permit.INVCLICK_MIDDLE.getStringPermission());
			list0.add(Permit.INVCLICK_NUMBER_KEY.getStringPermission());
			list0.add(Permit.INVCLICK_RIGHT.getStringPermission());
			list0.add(Permit.INVCLICK_SHIFT_LEFT.getStringPermission());
			list0.add(Permit.INVCLICK_SHIFT_RIGHT.getStringPermission());
			list0.add(Permit.INVCLICK_UNKNOWN.getStringPermission());	
			customPermissionsConf.addDefault("invclick", list0);
			List<String> list = new ArrayList<>();
			list.add(Permit.RIGHT_CLICK_AIR.getStringPermission());
			list.add(Permit.RIGHT_CLICK_BLOCK.getStringPermission());
			list.add(Permit.SHIFT_RIGHT_CLICK_AIR.getStringPermission());
			list.add(Permit.SHIFT_RIGHT_CLICK_BLOCK.getStringPermission());
			customPermissionsConf.addDefault("правый_клик", list);
			list = new ArrayList<>();
			list.add(Permit.LEFT_CLICK_AIR.getStringPermission());
			list.add(Permit.LEFT_CLICK_BLOCK.getStringPermission());
			list.add(Permit.SHIFT_LEFT_CLICK_AIR.getStringPermission());
			list.add(Permit.SHIFT_LEFT_CLICK_BLOCK.getStringPermission());
			customPermissionsConf.addDefault("левый_клик", list);
			list = new ArrayList<>();
			list.add(Permit.PACKUP.getStringPermission());
			list.add(Permit.HAVE.getStringPermission());
			customPermissionsConf.addDefault("custom_packup", list);
			list = new ArrayList<>();
			list.add(Permit.PLACE.getStringPermission());
			list.add(Permit.BREAK.getStringPermission());
			list.add(Permit.DROP.getStringPermission());
			list.add(Permit.PACKUP.getStringPermission());
			list.add(Permit.HAVE.getStringPermission());
			list.add(Permit.DAMAGE.getStringPermission());
			list.add(Permit.CRAFT.getStringPermission());
			list.add(Permit.LEFT_CLICK_AIR.getStringPermission());
			list.add(Permit.LEFT_CLICK_BLOCK.getStringPermission());
			list.add(Permit.SHIFT_LEFT_CLICK_AIR.getStringPermission());
			list.add(Permit.SHIFT_LEFT_CLICK_BLOCK.getStringPermission());
			list.add(Permit.RIGHT_CLICK_AIR.getStringPermission());
			list.add(Permit.RIGHT_CLICK_BLOCK.getStringPermission());
			list.add(Permit.SHIFT_RIGHT_CLICK_AIR.getStringPermission());
			list.add(Permit.SHIFT_RIGHT_CLICK_BLOCK.getStringPermission());
			customPermissionsConf.addDefault("full_ban", list);
			customPermissionsConf.options().copyDefaults(true);
			try {
				customPermissionsConf.save(customPermissionsFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void reload(){
		customPermissionsList.clear();
		load();
	}

	
}
