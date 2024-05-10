package ru.mor.iv.mineblocker.config;

import ru.mor.iv.mineblocker.permission.Permission;
import ru.mor.iv.mineblocker.permission.PermissionHolder;

public class SwitchConfig extends ConfigFeatures {
	private static String this_enabled = "this-enable";
	
	public SwitchConfig(String fileName) {
		super(fileName);
	}

	@Override
	public void load() {
		if(fileConfiguration.getBoolean(this_enabled)){
			for(Permission p : PermissionHolder.getPermissions()){
				p.setEnabled(fileConfiguration.getBoolean(p.getConfigSection()  + ".what"));
				p.setForWhatEnabled(fileConfiguration.getBoolean(p.getConfigSection()  + ".forWhat"));
			}
		}
	}

	@Override
	public boolean hasEverything() {
		
		return false;
	}

	@Override
	public void createDefault() {
		fileConfiguration.addDefault(this_enabled, false);
		for(Permission p : PermissionHolder.getPermissions()){
			fileConfiguration.addDefault(p.getConfigSection() + ".what", false);
			fileConfiguration.addDefault(p.getConfigSection() + ".forWhat", false);
		}
		fileConfiguration.options().copyDefaults(true);
	}

	@Override
	public void reload() {
		load();
	}
}
