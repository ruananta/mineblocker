package ru.mor.iv.mineblocker.command;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import ru.mor.iv.mineblocker.config.ConfigHolder;
import ru.mor.iv.mineblocker.config.string.MessageStrings;
import ru.mor.iv.mineblocker.item.EventItem;
import ru.mor.iv.mineblocker.item.EventItemBlock;
import ru.mor.iv.mineblocker.item.EventItemEntity;
import ru.mor.iv.mineblocker.item.EventItemStack;
import ru.mor.iv.mineblocker.managers.VersionsBridge;

public class Info extends Argument {
	public Info() {
		super(new String[] { "info", "i" });
	}

	@Override
	public void commandInit(CommandSender sender, String[] args) {
		if(!isPlayer(sender)){
			messager.sendCommandMessage(sender, plugin.getMessage(MessageStrings.SENDER_ERROR));
			return;
		}
		Player player = (Player) sender;
		sendItemNameToPlayer(player);
		sendBlockAndEntityNameToPlayer(player);
	}

	

	private void sendItemNameToPlayer(Player player) {
		@SuppressWarnings("deprecation")
		ItemStack stack = player.getItemInHand();
		if(stack != null){
			EventItem item = new EventItemStack(stack);
			String commandMessage = plugin.getMessage(MessageStrings.INFORM);
			String itemString = item.toString();
			if(ConfigHolder.isNbtEnabled()){
				itemString = itemString + "§a" + item.getNBT().getStringNBT();
			}
			messager.sendCommandMessage(player, commandMessage, itemString);
		}
	}
	
	private void sendBlockAndEntityNameToPlayer(Player player) {
		Block block = VersionsBridge.getTarget(player, 100);
		if (block != null && !block.getType().equals(Material.AIR)){
			EventItem item = new EventItemBlock(block);
			String commandMessage = plugin.getMessage(MessageStrings.INFORM_BLOCK);
			String blockString = item.toString();
			if(ConfigHolder.isNbtEnabled()){
				blockString = blockString + "§a" + item.getNBT().getStringNBT();
			}
			messager.sendCommandMessage(player, commandMessage, blockString);
			sendEntityNameToPlayer(player, block);
		}
	}

	private void sendEntityNameToPlayer(Player player, Block block) {
		String commandMessage;
		for(Entity e : block.getChunk().getEntities()){
			if(block.getLocation().distanceSquared(e.getLocation()) < 2){
				EventItem entity = new EventItemEntity(e);
				commandMessage = plugin.getMessage(MessageStrings.INFORM_ENTITY);
				messager.sendCommandMessage(player, commandMessage, entity.toString());
			}
		}
	}

}
