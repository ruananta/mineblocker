package ru.mor.iv.mineblocker.permission;

import java.util.ArrayList;
import java.util.List;

import ru.mor.iv.mineblocker.Utils;

public class PermissionHolder {
	private static List<Permission> permissions = new ArrayList<>();
	
	public static boolean addPermission(Permission permission){
		if(!permissions.contains(permission)){
			permissions.add(permission);
			return true;
		}
		return false;
	}
	
	public static boolean removePermission(Permission permission){
		return permissions.remove(permission);
	}
	
	public static Permission[] getPermissions() {
		return Utils.toArrayPermission(permissions);
	}
	
	public static Permission fromString(String stringPermission) {
		for(Permission p : permissions){
			if(p.getStringPermission().equals(stringPermission)){
				return p;
			}
		}
		return null;
	}

	public static void load() {
		for(Permit p : Permit.values()){
			addPermission(p);
		}
	}
	
	public static void reload() {
		permissions.clear();
		load();
	}
}
