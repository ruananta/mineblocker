package ru.mor.iv.mineblocker.command;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import ru.mor.iv.mineblocker.MineBlockerPlugin;
import ru.mor.iv.mineblocker.config.ConfigHolder;
import ru.mor.iv.mineblocker.full.PacketManager;
import ru.mor.iv.mineblocker.item.EventItemBlock;
import ru.mor.iv.mineblocker.item.EventItemEntity;
import ru.mor.iv.mineblocker.item.EventItemStack;
import ru.mor.iv.mineblocker.permission.Permission;
import ru.mor.iv.mineblocker.permission.PermissionHolder;

public class ListTabCompleter {
	private MineBlockerPlugin plugin = MineBlockerPlugin.getInst();
	private String[] args;
	private Player player;
	
	private List<String> complete = null;
	public ListTabCompleter(Player player, String[] args) {
		if(args.length == 2){
			createSections(args[1]);
		}else if(args.length == 3){
			List<String> l = new ArrayList<>();
			l.add("add");
			l.add("remove");
			createCmd(args[2], l);
		}else if(args.length == 4){
			List<String> l = new ArrayList<>();
			l.add("permission");
			l.add("group");
			l.add("gamemode");
			l.add("world");
			l.add("region");
			createCmd(args[3], l);
		}else if(args.length == 5){
			this.args = args;
			this.player = player;
			if(args[2].equalsIgnoreCase("add") || args[2].equalsIgnoreCase("a")){
				createAdd();
			}else if(args[2].equalsIgnoreCase("remove") || args[2].equalsIgnoreCase("r")){
				createRemove();
			}
		}
	}

	
	
	private void createAdd() {
		if(args[3].equalsIgnoreCase("world") || args[3].equalsIgnoreCase("w")){
			List<String> l = new ArrayList<>();
			for(World w : Bukkit.getServer().getWorlds()){
				l.add(w.getName());
			}
			createCmd(args[4], l); 
		}else if(args[3].equalsIgnoreCase("group") || args[3].equalsIgnoreCase("g")){
			if(plugin.getPermissionsPlugin() != null){
				createCmd(args[4], plugin.getPermissionsPlugin().getGroups());
			}
		}else if(args[3].equalsIgnoreCase("region") || args[3].equalsIgnoreCase("r")){
			if(plugin.getPrivatePlugin() != null){
				createCmd(args[4], plugin.getPrivatePlugin().getRegions());
			}
		}else if(args[3].equalsIgnoreCase("gamemode") || args[3].equalsIgnoreCase("gm")){
			createCmd(args[4], GameMode.values());
			
		}else if(args[3].equalsIgnoreCase("permission") || args[3].equalsIgnoreCase("p")){
			createPermission(args[4]);
		}
	}



	@SuppressWarnings("deprecation")
	private void createPermission(String string) {
		if(!string.contains(".")){
			createPermission(string, PermissionHolder.getPermissions());
		}
		/*if(string.endsWith("|")){
			craftList(string, DefaultPermissions.getDefaultPermissions());
		}
		*/
		if(string.endsWith(".")){
			String[] a = string.split("\\.");
			if(a.length == 1){
				if(ConfigHolder.isNbtEnabled()){
					String m = PacketManager.getNbtTags(player.getItemInHand());
					if(!m.equals("{}")){
						player.sendMessage("§a" + m);
					}
				}
				List<String> l = new ArrayList<>();
				l.add(new EventItemStack(player.getItemInHand()).toString());
				Block block = player.getTargetBlock((HashSet<Byte>) null, 20);
				if(block != null){
					l.add(new EventItemBlock(block).toString());
					for(Entity e : block.getChunk().getEntities()){
						if(block.getLocation().distanceSquared(e.getLocation()) < 2){
							l.add(new EventItemEntity(e).toString());
						}
					}
				}
				craftList(string, l);
			}else if(a.length == 2){
				if(ConfigHolder.isNbtEnabled()){
					String m = PacketManager.getNbtTags(player.getTargetBlock((HashSet<Byte>) null, 20));
					if(!m.equals("{}")){
						player.sendMessage("§a" + m);
					}
				}
				List<String> l = new ArrayList<>();
				Block block = player.getTargetBlock((HashSet<Byte>) null, 20);
				if(block != null){
					l.add(new EventItemBlock(block).toString());
					for(Entity e : block.getChunk().getEntities()){
						if(block.getLocation().distanceSquared(e.getLocation()) < 2){
							l.add(new EventItemEntity(e).toString());
						}
					}
				}
				l.add("wg");
				l.add("out");
				craftList(string, l);
			}else if(a.length == 3){
				List<String> l = new ArrayList<>();
				l.add("wg");
				l.add("out");
				craftList(string, l);
			}
		}
	}



	private void createPermission(String string, Permission[] permissions) {
		List<String> l = new ArrayList<>();
		boolean ban = false;
		if(string.startsWith("-")){
			ban = true;
			string = string.substring(1, string.length());
		}
		String perm = string;
		if(string.contains("|")){
			String[] s = string.split("\\|");
			if(!string.endsWith("|")){
				perm = s[s.length-1];
			}else{
				perm = "";
			}
			string = string.substring(0, string.length()-perm.length());
		}else{
			string = "";
		}
		for(Permission cmd : permissions){
			if(cmd.getStringPermission().startsWith(perm)){
				String n = cmd.getStringPermission();
				if(ban){
					n = "-" + string + n;
				}else{
					n = string + n;
				}
				l.add(n);
			}
		}
		if(!l.isEmpty()){
			complete = l;
		}
	}



	private void createRemove() {
		if(args[3].equalsIgnoreCase("world") || args[3].equalsIgnoreCase("w")){
			createCmd(args[4], plugin.getPermissionsList().getPermissionsListLoader().getWorlds(args[1])); 
		}else if(args[3].equalsIgnoreCase("group") || args[3].equalsIgnoreCase("g")){
			createCmd(args[4], plugin.getPermissionsList().getPermissionsListLoader().getGroups(args[1]));
		}else if(args[3].equalsIgnoreCase("region") || args[3].equalsIgnoreCase("r")){
			createCmd(args[4], plugin.getPermissionsList().getPermissionsListLoader().getRegions(args[1]));
		}else if(args[3].equalsIgnoreCase("permission") || args[3].equalsIgnoreCase("p")){
			createCmd(args[4], plugin.getPermissionsList().getPermissionsListLoader().getPermissions(args[1]));
		}else if(args[3].equalsIgnoreCase("gamemode") || args[3].equalsIgnoreCase("gm")){
			createCmd(args[4], plugin.getPermissionsList().getPermissionsListLoader().getGameModes(args[1]));
		}
	}


	private void createSections(String string) {
		createCmd(string, plugin.getPermissionsList().getPermissionsListLoader().getSections());
	}

	private void createCmd(String string, Set<String> sections) {
		List<String> l = new ArrayList<>();
		for(String cmd : sections){
			if(cmd.startsWith(string)){
				l.add(cmd);
			}
		}
		if(!l.isEmpty()){
			complete = l;
		}
	}
	
	private void createCmd(String string, List<String> list) {
		List<String> l = new ArrayList<>();
		for(String cmd : list){
			if(cmd.startsWith(string)){
				l.add(cmd);
			}
		}
		if(!l.isEmpty()){
			complete = l;
		}
	}
	
	private void createCmd(String string, GameMode[] values) {
		List<String> l = new ArrayList<>();
		for(GameMode g : values){
			String cmd = g.toString().toLowerCase();
			if(cmd.startsWith(string)){
				l.add(cmd);
			}
		}
		if(!l.isEmpty()){
			complete = l;
		}
	}
	
	private void craftList(String string, List<String> list){
		List<String> l = new ArrayList<>();
		for(String s : list){
			String ss = string + s;
			l.add(ss);
		}
		complete = l;
	}

/*	private void craftList(String string, String string2) {
		List<String> l = new ArrayList<>();
		String ss = string + string2;
		l.add(ss);
		complete = l;
	}
	*/
	public List<String> getCoplete() {
		if(complete == null || complete.isEmpty()){
			return null;
		}
		return complete;
	}

}
