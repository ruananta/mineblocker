package ru.mor.iv.mineblocker.permission.list;

public class PermissionSection {
	private Content worlds;
	private Content groups;
	private Content regions;
	private Content gamemodes;
	private Content signLines;
	
	@SuppressWarnings("deprecation")
	private Permissions permissions = new Permissions();
	
	public PermissionSection(String[] worlds, String[] groups, String[] regions, String[] gamemodes, String[] signLines, Permissions permissions) {
		this.worlds = new Content(worlds);
		this.groups = new Content(groups);
		this.regions = new Content(regions);
		this.gamemodes = new Content(gamemodes);
		this.signLines = new Content(signLines);
		this.permissions = permissions;
	}
	
	public Content getWorlds() {
		return worlds;
	}
	
	public Content getGroups() {
		return groups;
	}
	
	public Content getRegions() {
		return regions;
	}
	
	public Content getGamemodes() {
		return gamemodes;
	}
	
	public Content getSignLines() {
		return signLines;
	}
	
	public Permissions getPermissions() {
		return permissions;
	}

	
	
}
