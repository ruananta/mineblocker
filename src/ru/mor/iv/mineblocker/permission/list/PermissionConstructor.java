package ru.mor.iv.mineblocker.permission.list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import ru.mor.iv.mineblocker.Utils;
import ru.mor.iv.mineblocker.config.string.Strings;
import ru.mor.iv.mineblocker.event.CancelType;
import ru.mor.iv.mineblocker.exception.MineBlockerException;
import ru.mor.iv.mineblocker.full.FullPluginLoader;
import ru.mor.iv.mineblocker.item.ListItem;
import ru.mor.iv.mineblocker.permission.Permission;
import ru.mor.iv.mineblocker.permission.PermissionHolder;
import ru.mor.iv.mineblocker.permission.StringPermissions;

public class PermissionConstructor {
	private String str;
	private MineBlockerException mineBlockerException = null;
	private boolean ban;
	private boolean mute;
	private List<Permission> multiPerm;
	private List<ListItem> multiItem;
	private List<ListItem> multiBlock;
	private CancelType cancellType;
	private int privateRange;
	private int removeTicks;
	private String custom_message;
	private String signLine;
	
	
	public PermissionLine getPermission() {
		return new PermissionLine(Utils.toArrayPermission(multiPerm), Utils.toArrayItem(multiItem),
				Utils.toArrayItem(multiBlock), cancellType, privateRange, ban, removeTicks,
				custom_message, signLine, mute);
	}

	public PermissionConstructor(String line) {
		this.str = line;
	}
	
	public void construct() {
		try {
			constructStart();
		} catch (MineBlockerException e) {
			mineBlockerException = e;
		}
	}
	
	public boolean constructed() {
		return mineBlockerException == null;
	}
	
	public String getErrorMessage() {
		return mineBlockerException.getMessage();
	}
	
	private void constructStart() throws MineBlockerException {
		checkForWordSeparator();
		initBan();
		initNbt();
		// делим на составляющие
		String[] args = str.split("\\.");

		// проверка количества составляющих
		if (args.length < 2 || args.length > 8) {
			throw MineBlockerException.getNewException("in line too many arguments.");
		}

		init();
		// новый конструктор
		for (int x = 0; x < args.length; x++) {
			if (args[x].equalsIgnoreCase(StringPermissions.MINEBLOCKER)) {
				continue;
				// перм
			} else if (x == 0) {
				if (args[x].contains("|")) {
					for (String p : args[x].split("\\|")) {
						addPermissions(getPermissionsFromString(p));
					}
				} else {
					addPermissions(getPermissionsFromString(args[x]));
				}
				// предмет
			} else if (x == 1) {
				if (args[x].contains("|")) {
					for (String p : args[x].split("\\|")) {
						multiItem.addAll(getListItemsListFromString(p));
					}
				} else {
					multiItem.addAll(getListItemsListFromString(args[x]));
				}
				// блок || wg || remove
			} else {
				// wg
				if (args[x].startsWith("wg") || args[x].startsWith("private")) {
					cancellType = CancelType.PRIVATE;
					if (FullPluginLoader.isFullPlugin() && args[x].contains(Strings.ITEM_DELIMETER)) {
						String s = args[x].split(Strings.ITEM_DELIMETER)[1];
						if (!isInteger(s)) {
							throw MineBlockerException.getNewException("the argument " + args[x] + " is not valid.");
						}
						privateRange = Integer.valueOf(trimSpace(s));
					}
					// remove
				} else if (FullPluginLoader.isFullPlugin() && args[x].startsWith("remove") || 
						FullPluginLoader.isFullPlugin() && args[x].startsWith("rm")) {
					if (args[x].contains(Strings.ITEM_DELIMETER)) {
						String s = args[x].split(Strings.ITEM_DELIMETER)[1];
						if (!isInteger(s)) {
							throw MineBlockerException.getNewException("the argument " + args[x] + " is not valid.");
						}
						removeTicks = Integer.valueOf(trimSpace(s));
					} else {
						removeTicks = 1;
					}
					// out
				} else if (FullPluginLoader.isFullPlugin() && args[x].startsWith("out")) {
					cancellType = CancelType.OUTSIDE_PRIVATE;
					// custom message
				} else if (FullPluginLoader.isFullPlugin() && args[x].startsWith("message:")
						|| FullPluginLoader.isFullPlugin() && args[x].startsWith("msg:")) {
					custom_message = trimSpace(args[x].split(Strings.ITEM_DELIMETER)[1]);
					// sign line
				} else if (FullPluginLoader.isFullPlugin() && args[x].startsWith("sign:")) {
					signLine = trimSpace(args[x].split(Strings.ITEM_DELIMETER)[1]);
					// тихий режим
				} else if (FullPluginLoader.isFullPlugin() && args[x].startsWith("mute")) {
					mute = true;
					// block если это 3й аргумент
				} else if (x == 2) {
					if (args[x].contains("|")) {
						for (String p : args[x].split("\\|")) {
							multiBlock.addAll(getListItemsListFromString(p));
						}
					} else {
						multiBlock.addAll(getListItemsListFromString(args[x]));
					}
					// ошибка
				} else {
					throw MineBlockerException.getNewException("the argument " + args[x] + " is not valid.");
				}
			}
		}
		for (ListItem listItem : multiItem) {
			listItem.setRemoveTicks(removeTicks);
		}
		if(cancellType == null){
			cancellType = CancelType.GLOBAL;
		}
	}

	private void initNbt() {
		// подготовка NBT
		str = nbtReplace(str);
	}

	private void initBan() {
		// бан или нет
		if (str.startsWith("-")) {
			str = str.substring(1, str.length());
			ban = true;
		} else {
			ban = false;
		}
	}

	private void checkForWordSeparator() throws MineBlockerException {
		// проверка на наличие разделителя
		if (!str.contains(".")) {
			throw MineBlockerException.getNewException("in line missing '.'");
		}
	}

	private String nbtReplace(String str) {
		StringBuilder sb = new StringBuilder();
		boolean nbt = false;
		boolean nullNbt = false;
		for (char c : str.toCharArray()) {
			String s = Character.toString(c);
			if (nbt && s.equals(".")) {
				s = s.replace(".", "MB_Point");
			}
			if (!nbt && !nullNbt && s.equals("{")) {
				nbt = true;
			}
			if (nbt && !nullNbt && s.equals("{")) {
				nullNbt = true;
			}
			if (nbt && nullNbt && s.equals("}")) {
				nullNbt = false;
			}
			if (nbt && !nullNbt && s.equals("}")) {
				nbt = false;
			}
			sb.append(s);
		}
		return sb.toString();
	}

	private Map<String, Permission> getPermissionsFromString(String stringPermission) throws MineBlockerException {
		Map<String, Permission> name_permission = new HashMap<>();
		if (FullPluginLoader.isFullPlugin()) {
			List<String> perms = FullPluginLoader.getFullPlugin().getCustomPermissionsLoader()
					.getCustomPermissions(stringPermission);
			if (perms != null) {
				for (String c : perms) {
					Permission perm = PermissionHolder.fromString(c);
					name_permission.put(c, perm);
				}
			}
		}
		if(name_permission.isEmpty()){
			Permission perm = PermissionHolder.fromString(stringPermission);
			name_permission.put(stringPermission, perm);
		}
		return name_permission;
	}

	private void addPermissions(Map<String, Permission> name_permission) throws MineBlockerException {
		for(Entry<String, Permission> e : name_permission.entrySet()){
			if (e.getValue() == null) {
				throw MineBlockerException.getNewException("error adding permission " + e.getKey() + ".");
			} else {
				multiPerm.add(e.getValue());
			}
		}
	}

	private List<ListItem> getListItemsListFromString(String p) throws MineBlockerException {
		List<String> list = null;
		if (FullPluginLoader.isFullPlugin()) {
			list = FullPluginLoader.getFullPlugin().getCustomItemsLoader().getCustomItems(p);
		}
		if (list == null) {
			list = new ArrayList<>();
			list.add(p);
		}
		List<ListItem> itemsList = new ArrayList<>();
		for (String name : list) {
			ListItem i = new ListItem(name);
			if (!i.isValid()) {
				throw MineBlockerException.getNewException("error adding item " + name + ".");
			}
			itemsList.add(i);
		}
		return itemsList;
	}

	private void init() {
		multiPerm = new ArrayList<>();
		multiItem = new ArrayList<>();
		multiBlock = new ArrayList<>();
		privateRange = 0;
		removeTicks = 0;
	}

	// private void debug() {
	// if(MineBlockerPlugin.debug){
	//
	// MineBlockerPlugin.debug("-- perm --");
	// for(Permission s : multiPerm){
	// MineBlockerPlugin.debug(s.getStringPermission());
	// }
	//
	// MineBlockerPlugin.debug("-- item --");
	// for(ListItem i : multiItem){
	// MineBlockerPlugin.debug(i.toString());
	// }
	//
	// MineBlockerPlugin.debug("-- block --");
	// for(ListItem i : multiBlock){
	// MineBlockerPlugin.debug(i.toString());
	// }
	//
	// MineBlockerPlugin.debug("-- WorldGuard --");
	// MineBlockerPlugin.debug("isWorldGuard: " + isPrivate);
	// MineBlockerPlugin.debug("WorldGuard range: " + privateRange);
	// MineBlockerPlugin.debug("isOutWorldGuard: " + isOutsidePrivate);
	// MineBlockerPlugin.debug("remove ticks: " + removeTicks);
	// MineBlockerPlugin.debug("isBan: " + isBan);
	// MineBlockerPlugin.debug("=============================");
	// }
	//
	// }

	private boolean isInteger(String s) {
		s = trimSpace(s);
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}

	}

	private String trimSpace(String s) {
		while (s.startsWith(" ")) {
			s = s.substring(1, s.length());
		}
		while (s.endsWith(" ")) {
			s = s.substring(0, s.length() - 1);
		}
		return s;
	}

	public String getLine() {
		return str;
	}

}
