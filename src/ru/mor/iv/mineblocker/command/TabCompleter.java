package ru.mor.iv.mineblocker.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TabCompleter implements org.bukkit.command.TabCompleter {
	private List<String> cmdList = new ArrayList<>();
	
	public TabCompleter() {
		cmdList.add("info");
		cmdList.add("plist");
		cmdList.add("reload");
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
		if(sender instanceof Player){	
			Player player = (Player) sender;
			if(cmd.getName().equalsIgnoreCase("mineblocker") || cmd.getName().equalsIgnoreCase("mb")){
				if(args.length == 1 && args[0].equalsIgnoreCase("")){
					return cmdList;
				}else if(args.length == 1){
					return getCmd(args[0]);
				}else if(args.length > 1)
					if(args[0].equalsIgnoreCase("plist") || args[0].equalsIgnoreCase("permissionslist") || args[0].equalsIgnoreCase("pl")){
					return new ListTabCompleter(player, args).getCoplete();
				}
			}
		}
		return null;
	}

	private List<String> getCmd(String string) {
		List<String> l = new ArrayList<>();
		for(String cmd : cmdList){
			if(cmd.startsWith(string)){
				l.add(cmd);
			}
		}
		if(!l.isEmpty()){
			return l;
		}
		return null;
	}

}
