package ru.mor.iv.mineblocker.command;

import org.bukkit.command.CommandSender;

import ru.mor.iv.mineblocker.exception.MineBlockerException;

public class PermissionList extends Argument{

	public PermissionList() {
		super(new String[]{"plist", "pl", "permissionslist"});
	}

	@Override
	public void commandInit(CommandSender sender, String[] args) {
		try {
			ListCommand list = new ListCommand(args);
			sender.sendMessage(list.getMessage());
		} catch (MineBlockerException e) {
			sender.sendMessage(e.getMessage());
		}
	}

}
