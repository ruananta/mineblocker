package ru.mor.iv.mineblocker.listener;

import ru.mor.iv.mineblocker.permission.Permission;

public interface MineBlockerListener {
	
	public abstract Permission[] getUsedPermissions();
	
	public abstract boolean isRegisterred();
	
	public abstract void register();
	
	public abstract void unregister();
}
