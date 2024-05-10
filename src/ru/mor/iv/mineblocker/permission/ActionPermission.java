package ru.mor.iv.mineblocker.permission;

public class ActionPermission {
	private Permission permission;
	private boolean banned;
	
	public ActionPermission(Permission permission) {
		this.permission = permission;
		banned = false;
	}
	
	public Permission getPermission() {
		return permission;
	}
	
	public boolean isBanned() {
		return banned;
	}
	
	public void setBanned(boolean banned) {
		this.banned = banned;
	}
}
