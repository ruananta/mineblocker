package ru.mor.iv.mineblocker.permission.list;

import java.util.HashMap;
import java.util.Map;

import ru.mor.iv.mineblocker.Debug;
import ru.mor.iv.mineblocker.Matcher;
import ru.mor.iv.mineblocker.MineBlockerPlugin;
import ru.mor.iv.mineblocker.event.ActionEvent;

public class PermissionsList {
	private PermissionsListLoader permissionsListLoader;
	public static String KEY_ALL = "AllGroup";
	public static String KEY_PERM = "permissions";
	private Map<String, PermissionSection> sections = new HashMap<>();
	
	
	public PermissionsList() {
		permissionsListLoader = new PermissionsListLoader(this);
	}
	
	public void load(){
		permissionsListLoader.load();
	}
	
	public void add(String cfg, PermissionSection ps) {
		if(MineBlockerPlugin.debug){
			Debug.send("Added permission section: " + cfg);
			for(PermissionLine pl : ps.getPermissions().getPermissionLines()){
				Debug.send(pl.toString());
			}
		}
		if(!isSection(cfg)){
			sections.put(cfg, ps);
		}
	}
	
	public void checkEvent(ActionEvent event){
		for(PermissionSection ps : sections.values()){	
			Matcher.init(event, ps);
		}
	}
	
	

	public void reload(){
		sections.clear();
		permissionsListLoader.reload();
	}
	
	
	public PermissionsListLoader getPermissionsListLoader() {
		return permissionsListLoader;
	}

	public boolean isSection(String section) {
		return sections.containsKey(section);
	}

	public Map<String, PermissionSection> getSections() {
		return sections;
	}
	
}
