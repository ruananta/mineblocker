package ru.mor.iv.mineblocker.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.mor.iv.mineblocker.MineBlockerPlugin;
import ru.mor.iv.mineblocker.message.Messager;

public abstract class Argument {
	MineBlockerPlugin plugin = MineBlockerPlugin.getInst();
	Messager messager = plugin.getMessager();
	
	private String[] args;
	
	
	public Argument(String[] args) {
		this.args = args;
	}
	
	public boolean containsArgument(String arg) {
		return contains(args, arg);
	}
	
	public abstract void commandInit(CommandSender sender, String[] args);
	
	
	boolean contains(String[] args, String arg){
		for(String s : args){
			if(s.equalsIgnoreCase(arg)){
				return true;
			}
		}
		return false;
	}
	
	boolean isPlayer(CommandSender sender){
		return sender instanceof Player;
	}
}
