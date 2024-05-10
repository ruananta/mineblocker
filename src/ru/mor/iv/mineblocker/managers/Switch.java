package ru.mor.iv.mineblocker.managers;

import ru.mor.iv.mineblocker.MineBlockerPlugin;
import ru.mor.iv.mineblocker.permission.Permission;
import ru.mor.iv.mineblocker.permission.PermissionHolder;
import ru.mor.iv.mineblocker.permission.Permit;
import ru.mor.iv.mineblocker.permission.list.PermissionLine;
import ru.mor.iv.mineblocker.permission.list.PermissionSection;

public class Switch {
	
	private MineBlockerPlugin plugin = MineBlockerPlugin.getInst();
	
	@SuppressWarnings("deprecation")
	public void optimize() {
		clear();
		for(PermissionSection ps : plugin.getPermissionsList().getSections().values()){
			for(PermissionLine p : ps.getPermissions().getPermissionLines()){
				for(Permission s : p.getMultiPerm()){
					setEnabled(s, p.getMultiBlock() != null);
				}
			}
		}
	}


	private void setEnabled(Permission p, boolean forWhat) {
		p.setEnabled(true);
		if(forWhat){
			p.setForWhatEnabled(true);
		}
		if(p.equals(Permit.ALL)){
			for(Permission perm : PermissionHolder.getPermissions()){
				perm.setEnabled(true);
				if(forWhat){
					perm.setForWhatEnabled(true);
				}
			}
		}
	}


	private void clear() {
		for(Permission p : PermissionHolder.getPermissions()){
			p.setEnabled(false);
			p.setForWhatEnabled(false);
		}
	}
	
}
