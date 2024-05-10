package ru.mor.iv.mineblocker.command;

import org.bukkit.command.CommandSender;

import ru.mor.iv.mineblocker.config.string.MessageStrings;

public class Reload extends Argument{
	
	public Reload() {
		super(new String[] { "reload", "r" });
	}

	@Override
	public void commandInit(CommandSender sender, String[] args) {
		plugin.reloadPlugin();
		messager.sendCommandMessage(sender, plugin.getMessage(MessageStrings.RELOAD));
	}

}
