package ru.mor.iv.mineblocker.command;

import org.bukkit.command.CommandSender;

public class Help {
	public Help(CommandSender sender) {
		sender.sendMessage("§6<command> §b[§6<aliases>§b]");
		sender.sendMessage("§6/mineblocker §b[§6mb§b] §a- gives access to all commands");
		sender.sendMessage("§7/mb §6info §b[§6i§b] §a- information about an item and block");
		sender.sendMessage("§7/mb §6permissionslist §b[§6plist §b| §6pl§b] §a- edit Permissions list");
		sender.sendMessage("§7/mb §6reload §b[§6r§b] §a- reload MineBlocker");
	}
}
