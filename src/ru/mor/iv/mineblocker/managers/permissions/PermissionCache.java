package ru.mor.iv.mineblocker.managers.permissions;

import java.util.List;

public class PermissionCache {
	private List<String> groups;
	private long time;
	
	public PermissionCache(List<String> groups) {
		this.groups = groups;
		this.time = System.currentTimeMillis();
	}

	public List<String> getGroups() {
		return groups;
	}
	
	public long getTime() {
		return time;
	}
}
