package ru.mor.iv.mineblocker.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import ru.mor.iv.mineblocker.permission.StringPermissions;

public class CommandHandler implements CommandExecutor {
	private Argument[] arguments;
	
	public CommandHandler() {
		arguments = new Argument[]{
			new Info(),
			new Inventory(),
			new PermissionList(),
			new Reload(),
			new Test()
		};
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!sender.hasPermission(StringPermissions.COMMAND)){
			sender.sendMessage(ChatColor.RED + "you don't have permission");
			return true;
		}
		if(args.length == 0){
			new Help(sender);
		}else{
			for(Argument a : arguments){
				if(a.containsArgument(args[0])){
					a.commandInit(sender, args);
				}
			}
		}
		return true;
	}
}
