package ru.mor.iv.mineblocker.config;

import ru.mor.iv.mineblocker.config.string.MessageStrings;
import ru.mor.iv.mineblocker.permission.Permission;
import ru.mor.iv.mineblocker.permission.PermissionHolder;

public abstract class Message extends ConfigFeatures {

	public Message(String fileName) {
		super(fileName);
	}

	@Override
	public void load() {
		loadMessage();
	}

	private void loadMessage() {
		for (Permission p : PermissionHolder.getPermissions()) {
			String s = fileConfiguration.getString(p.getStringPermission());
			if (s != null) {
				p.setMessage(s);
			}
		}
		for (String value : MessageStrings.values()) {
			MessageContainer.addMessage(value, fileConfiguration.getString(value));
		}

	}

	@Override
	public boolean hasEverything() {
		for (String value : MessageStrings.values()) {
			if (fileConfiguration.getString(value) == null || fileConfiguration.getString(value).equals("")) {
				return false;
			}
		}
		for (Permission p : PermissionHolder.getPermissions()) {
			if (p.getMessage() != null) {
				if (fileConfiguration.getString(p.getStringPermission()) == null
						|| fileConfiguration.getString(p.getStringPermission()).equals("")) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public abstract void createDefault();

	@Override
	public void reload() {
		MessageContainer.clear();
		load();
	}

}
