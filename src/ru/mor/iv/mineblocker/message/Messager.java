package ru.mor.iv.mineblocker.message;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.mor.iv.mineblocker.MineBlockerPlugin;
import ru.mor.iv.mineblocker.config.ConfigHolder;
import ru.mor.iv.mineblocker.config.MessageContainer;
import ru.mor.iv.mineblocker.config.string.MessageStrings;
import ru.mor.iv.mineblocker.event.ActionEvent;
import ru.mor.iv.mineblocker.event.Cancel;
import ru.mor.iv.mineblocker.item.EventItem;

public class Messager {

private MineBlockerPlugin plugin;
	private static long
	CHECK_COOLDOWN_tick = 		30;
	private Map<ActionEvent, Long> cooldown = new HashMap<>();
	
	public Messager() {
		this.plugin = MineBlockerPlugin.getInst();
		runCleaner();
	}

	public void sendMessage(ActionEvent event) {
		if(ConfigHolder.isMessageEnabled() && event.getActor() != null){
			if(event.isMute() || cooldown(event)){
				return;
			}
			
			String message;
			if(event.getCustomMessage() != null){
				message = event.getCustomMessage();
			}else{
				message = event.getBannedPermissions()[0].getMessage();
			}
			String end_offers = getEndOffers(event.getCancel());
			
			if(message != null && end_offers != null){
				message = message.replace("{EndOffers}", end_offers);
				for(EventItem usedItem : event.getBannedUsedItems()){
					String item = usedItem.getDisplayName();
					String block = event.getBannedForWhatUsed() != null ? event.getBannedForWhatUsed()[0].getDisplayName() : null;
					if(block == null){
						block = event.getBannedNearBlocks() != null ? event.getBannedNearBlocks()[0].getDisplayName() : "";
					}
					sendMessage(event.getActor(), message,  item, block);
				}
			}
		}
	}
	
	public void sendCommandMessage(CommandSender sender, String message) {
		message = message.replace("{Prefix}", MessageContainer.getMessage(MessageStrings.PREFIX));
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
	}
	
	public void sendCommandMessage(CommandSender sender, String message, String itemName) {
		message = message.replace("{Prefix}", MessageContainer.getMessage(MessageStrings.PREFIX));
		message = message.replace("{Name}", itemName);
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
	}

	public void sendMessage(Player player, String msg, String cancelItem, String cancelBlock) {
			player.sendMessage(craftMessage(msg, cancelItem, cancelBlock));
	}

	private  String craftMessage(String message, String cancelItem, String cancelBlock) {
		message = message.replace("{Prefix}", MessageContainer.getMessage(MessageStrings.PREFIX));
		message = message.replace("{ItemName}", cancelItem);
		if(!cancelBlock.equals("")){
			message = message.replace("{BlockName}", cancelBlock);
		}
		message = ChatColor.translateAlternateColorCodes('&', message);
		return message;
	}

	private String getEndOffers(Cancel c) {
		String end_offers = "";
		if(c.isCancelledPrivate() && c.getPrivateRange() == 0){
			end_offers = plugin.getMessage(MessageStrings.END_REGION);
		}else if(c.isCancelledPrivate() && c.getPrivateRange() != 0){
			end_offers = plugin.getMessage(MessageStrings.END_RANGE_REGION);
		}else if(c.isCancelledOutPrivate()){
			end_offers = plugin.getMessage(MessageStrings.END_OUTSIDE);
		}else if(c.isCancelledWorld()){
			end_offers = plugin.getMessage(MessageStrings.END_WORLD);
		}else if(c.isCancelledGamemode()){
			end_offers = plugin.getMessage(MessageStrings.END_GAMEMODE);
		}
		return end_offers;
	}



	private boolean cooldown(ActionEvent event) {
		long cooldown_ms = ConfigHolder.getMessageCooldown();
		if (cooldown_ms == 0) {
			return false;
		}
		for (Entry<ActionEvent, Long> e : new HashMap<ActionEvent, Long>(cooldown).entrySet()) {
			ActionEvent list_event = e.getKey();
			if (list_event.getActor().equals(event.getActor())) {
				if (list_event.getBannedPermissions()[0].equals(event.getBannedPermissions()[0])
						&& list_event.getBannedUsedItems()[0].toString().equals(event.getBannedUsedItems()[0].toString())) {
					if (list_event.getBannedForWhatUsed() == null && event.getBannedForWhatUsed() == null
							|| list_event.getBannedForWhatUsed() != null && event.getBannedForWhatUsed() != null
									&& list_event.getBannedForWhatUsed()[0].toString()
											.equals(event.getBannedForWhatUsed()[0].toString())) {
						if (e.getValue() < new Date().getTime()) {
							cooldown.remove(list_event);
							cooldown.put(event, new Date().getTime() + cooldown_ms);
							return false;
						} else {
							return true;
						}
					}
				}
			}
		}
		cooldown.put(event, new Date().getTime() + cooldown_ms);
		return false;
	}
	
	private void runCleaner() {
		Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
			@Override
			public void run() {
				long time = new Date().getTime();
				for(Entry<ActionEvent, Long> e : new HashMap<>(cooldown).entrySet()){
					if(e.getValue() < time){
						cooldown.remove(e.getKey());
					}
				}
			}
		}, CHECK_COOLDOWN_tick, CHECK_COOLDOWN_tick);
	}

	

}
