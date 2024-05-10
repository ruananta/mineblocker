package ru.mor.iv.mineblocker.permission.list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ru.mor.iv.mineblocker.Debug;
import ru.mor.iv.mineblocker.permission.Permission;
import ru.mor.iv.mineblocker.permission.PermissionHolder;
import ru.mor.iv.mineblocker.permission.Permit;

public class Permissions {

	private Map<Permission, List<PermissionLine>> permissions = new HashMap<>();
	
	public Permissions(List<String> permissions){
		if(permissions != null){
			for(String s : permissions){
				PermissionConstructor pc = new PermissionConstructor(s);
				pc.construct();
				if(pc.constructed()){
					addPermission(pc.getPermission());
				}else{
					log(pc.getLine(), pc.getErrorMessage());
				}
			}
		}
	}
	
	private void log(String line, String errorMessage) {
		Debug.send("line loading error: " + line);
		Debug.send(errorMessage);
	}

	@Deprecated
	public Permissions() {
		
	}

	public Set<Permission> getPermissions(){
		return permissions.keySet();
	}
	
	@Deprecated
	public List<PermissionLine> getPermissionLines() {
		List<PermissionLine> list = new ArrayList<>();
		for(List<PermissionLine> l : permissions.values()){
			for(PermissionLine pl : l){
				if(!list.contains(pl)){
					list.add(pl);
				}
			}
		}
		return list;
	}
	
	public List<PermissionLine> getPermissionLines(Permission p) {
		return permissions.get(p);
	}
	
	@SuppressWarnings("deprecation")
	public void addPermission(PermissionLine pl){
		if(pl != null){
			for(Permission p : pl.getMultiPerm()){
				if(p.equals(Permit.ALL)){
					for(Permission ps : PermissionHolder.getPermissions()){
						if(!ps.equals(Permit.ALL)) 
							addPermission(ps, pl);
					}
				}else{
					addPermission(p, pl);
				}
			}
		}
	}
	
	private void addPermission(Permission p, PermissionLine pl) {
		List<PermissionLine> l = permissions.get(p);
		if(l == null) l = new ArrayList<>();	
		if(!l.contains(pl)){
			l.add(pl);
		}
		permissions.put(p, l);
	}
	
	public void clear(){
		permissions.clear();
	}
}
