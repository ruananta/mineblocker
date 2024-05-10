package ru.mor.iv.mineblocker.managers;

import ru.mor.iv.mineblocker.MineBlockerPlugin;
import ru.mor.iv.mineblocker.config.ConfigHolder;
import ru.mor.iv.mineblocker.config.string.Strings;
import ru.mor.iv.mineblocker.event.ActionEvent;
import ru.mor.iv.mineblocker.event.Cancel;
import ru.mor.iv.mineblocker.managers.privates.PrivatePlugin;
import ru.mor.iv.mineblocker.permission.StringPermissions;
import ru.mor.iv.mineblocker.permission.list.PermissionsList;

public class PermissionManager {
	private MineBlockerPlugin plugin = MineBlockerPlugin.getInst();
	private PermissionsList permissionsList = plugin.getPermissionsList();
	private PrivatePlugin privatePlugin = plugin.getPrivatePlugin();
	
	public void checkEvent(ActionEvent event) {
		if(isCancelledPlayerPermissions(event)){
			return;
		}
		if(isCancelledPermissionsList(event)){
			return;
		}
	}
	
	private boolean isCancelledPlayerPermissions(ActionEvent event) {
		if(ConfigHolder.isCheckPlayerPermissions() && event.getActor() != null){ 
			Cancel c = event.getCancel();
			for(String perm : event.getStringPermissions()){
				if(!event.getActor().hasPermission(perm)){
					c.setCancelledGlobal(true);
					return true;
				}
				if(!event.getActor().hasPermission(perm + Strings.WORD_DELIMETER + Strings.PRIVATE)){
					if(!privatePlugin.canBuild(event)){
						c.setCancelledPrivate(true);
						return true;
					}
				}
				if(!event.getActor().hasPermission(perm + Strings.WORD_DELIMETER + Strings.OUT_PRIVATE)){
					if(privatePlugin.outsidePrivate(event)){
						c.setCancelledOutPrivate(true);
						return true;
					}
				}
			}
		}
		return false;
	}

	private boolean isCancelledPermissionsList(ActionEvent event) {
		if(event.getActor() != null){
			if(ConfigHolder.isOpBypassPermissionsList() && event.getActor().hasPermission(StringPermissions.BYPASS_CHECK) || 
					ConfigHolder.isOpBypassPermissionsList() && event.getActor().isOp()){
				return false;
			}
		}
		permissionsList.checkEvent(event);
		Cancel c = event.getCancel();
		if(privatePlugin != null){
			if(c.isUnCancelledGlobal()){
				c.setCancelled(false);
			}
			if(c.isUnCancelledPrivate()){
				if(privatePlugin.canBuild(event)){
					c.setCancelled(false);
				}
			}
			if(c.isUnCancelledOutPrivate()){
				if(privatePlugin.outsidePrivate(event)){
					c.setCancelled(false);
				}
			}
		}	
		if(c.isCancelledGlobal()){
			return true;
		}
		if(privatePlugin != null){
			if(c.isCancelledPrivate()){
				if(!privatePlugin.canBuild(event)){
					return true;
					
				}
				c.setCancelledPrivate(false);
			}
			if(c.isCancelledOutPrivate()){
				if(privatePlugin.outsidePrivate(event)){
					return true;
				}
				c.setCancelledOutPrivate(false);
			}
		}
		
		return false;
	}
}
