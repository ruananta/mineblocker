package ru.mor.iv.mineblocker.full.delete.config;

import ru.mor.iv.mineblocker.config.ConfigFeatures;
import ru.mor.iv.mineblocker.permission.Permission;
import ru.mor.iv.mineblocker.permission.PermissionHolder;

public class DefaultPermissions extends ConfigFeatures{
	
	public DefaultPermissions(String fileName) {
		super(fileName);
	}
	
	
	@Override
	public void load(){
		for(Permission p : PermissionHolder.getPermissions()){
			p.setStringPermission(fileConfiguration.getString(p.getConfigSection()));
		}
	}
	
	@Override
	public boolean hasEverything() {
		for(Permission p : PermissionHolder.getPermissions()){
			if (fileConfiguration.getString(p.getConfigSection()) == null
					|| fileConfiguration.getString(p.getConfigSection()).equals("")) {
				return false;
			}
		}
		return true;
	}
	
	
	@Override
	public void createDefault() {
		for(Permission p : PermissionHolder.getPermissions()){
			fileConfiguration.addDefault(p.getConfigSection(), p.getStringPermission());
		}
		fileConfiguration.options().copyDefaults(true);
	}


	@Override
	public void reload() {
		load();
	}


}
