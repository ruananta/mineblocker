package ru.mor.iv.mineblocker.config;

import ru.mor.iv.mineblocker.config.string.MessageStrings;
import ru.mor.iv.mineblocker.permission.Permission;
import ru.mor.iv.mineblocker.permission.PermissionHolder;

public class MessageEN extends Message {
	
	public MessageEN(String fileName) {
		super(fileName);
	}

	@Override
	public void createDefault() {
		fileConfiguration.addDefault(MessageStrings.PREFIX, "&f[&2MineBlocker&f]&4");
		fileConfiguration.addDefault(MessageStrings.INFORM, "{Prefix} &aYou are holding the &4{Name}");
		fileConfiguration.addDefault(MessageStrings.INFORM_BLOCK, "{Prefix} &aYou look at the &4{Name}");
		fileConfiguration.addDefault(MessageStrings.INFORM_ENTITY, "{Prefix} &aNear the block stands a entity &4{Name}");
		fileConfiguration.addDefault(MessageStrings.INFORM_INVENTORY, "{Prefix} &aYou open the inventory &4{Name}");
		fileConfiguration.addDefault(MessageStrings.RELOAD, "{Prefix} &aPlugin reloaded!");
		fileConfiguration.addDefault(MessageStrings.END_REGION, " &ain this region");
		fileConfiguration.addDefault(MessageStrings.END_RANGE_REGION, " &ain or near the region");
		fileConfiguration.addDefault(MessageStrings.END_OUTSIDE, " &aoutside the your region");
		fileConfiguration.addDefault(MessageStrings.END_WORLD, " &ain this world");
		fileConfiguration.addDefault(MessageStrings.END_GAMEMODE, " &ain this gamemode");
		fileConfiguration.addDefault(MessageStrings.SENDER_ERROR, "{Prefix} This command is only for the player");
		fileConfiguration.addDefault(MessageStrings.INVENTORY_OPEN, "{Prefix} &aYou have enabled display of open inventory name.");
		fileConfiguration.addDefault(MessageStrings.INVENTORY_CLOSE, "{Prefix} &aYou have disabled display of open inventory name.");
		for (Permission p : PermissionHolder.getPermissions()) {
			fileConfiguration.addDefault(p.getStringPermission(), p.getEnMessage());
		}
		fileConfiguration.options().copyDefaults(true);
	}

}
