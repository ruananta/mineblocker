package ru.mor.iv.mineblocker.command;

import ru.mor.iv.mineblocker.MineBlockerPlugin;
import ru.mor.iv.mineblocker.exception.MineBlockerException;

public class ListCommand {
	private MineBlockerPlugin plugin = MineBlockerPlugin.getInst();
	private String command = "/mineblocker plist";
	
	private String section;
	private String add$remove;
	private boolean add;
	
	private String type;
	
	private String value;
	private boolean newSection = false;

	
	public ListCommand(String[] args) throws MineBlockerException  {
		if(args.length == 1){
			throw new MineBlockerException("§4you have not entered: " + command + " §6<section>");
		}
		section = args[1];
		if(args.length == 2){
			throw new MineBlockerException("§4you have not entered: " + command + " " + section + " §6<add | remove>");
		}
		add$remove = args[2];
		
		if(args.length == 3){
			throw new MineBlockerException("§4you have not entered: " + command + " " + section + " " + add$remove + " §6<world | group | gamemode | region | permission>");
		}
		type = args[3];
		
		if(args.length == 4){
			throw new MineBlockerException("§4you have not entered: " + command + " " + section + " " + add$remove +  " " + type + " §6<value>");
		}
		value = args[4];
		init();
	}

	private void init() throws MineBlockerException {
		if(add$remove.equalsIgnoreCase("add") || add$remove.equalsIgnoreCase("a")){
			this.add = true;
		}else if(add$remove.equalsIgnoreCase("remove") || add$remove.equalsIgnoreCase("r")){
			this.add = false;
		}else{
			throw new MineBlockerException("§4the key: §6" + add$remove +  " §4is not known.");
		}
		
		if(type.equalsIgnoreCase("world") || type.equalsIgnoreCase("w")){
			type = "worlds";
		}else if(type.equalsIgnoreCase("group") || type.equalsIgnoreCase("g")){
			type = "groups";
		}else if(type.equalsIgnoreCase("region") || type.equalsIgnoreCase("r")){
			type = "regions";
		}else if(type.equalsIgnoreCase("permission") || type.equalsIgnoreCase("p")){
			type = "permissions";
		}else if(type.equalsIgnoreCase("gamemode") || type.equalsIgnoreCase("gm")){
			type = "gamemodes";
		}else{
			throw new MineBlockerException("§4the key: §6" + type +  " §4is not known.");
		}
		
		if(add){
			add();
		}else{
			remove();
		}
	}

	
	private void add() {
		if(!plugin.getPermissionsList().isSection(section)){
			newSection = true;
		}
		plugin.getPermissionsList().getPermissionsListLoader().add(section, type, value);
	}

	private void remove() throws MineBlockerException {
		if(!plugin.getPermissionsList().isSection(section)){
			throw new MineBlockerException("§4section: §6" + section +  " §4can not be found in the PermissionList.");
		}
		int i = plugin.getPermissionsList().getPermissionsListLoader().remove(section, type, value);
		if(i == 0){
			throw new MineBlockerException("§4list: §6" + type +  " §4can not be found in the section: §6" + section);
		}else if(i == 1){
			throw new MineBlockerException("§4value: §6" + value +  " §4can not be found in the section: §6" + section + " §4and in the list: §6" + type);
		}
	}

	
	public String getMessage() {
		String s;
		String n = newSection ? " §6new§a" : "";
		if(add){
			s = "§ayou add a " + type.substring(0, type.length() -1) + ":§6 " + value + "§a in the" + n + " section: §6" + section;
		}else{
			s = "§ayou remove a " + type.substring(0, type.length() -1) + ":§6 " + value + "§a in the section: §6" + section;
		}
		return s;
	}

}
