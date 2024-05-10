package ru.mor.iv.mineblocker.item;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

public class EventItemConstructor {
	private EventItem eventItem;
	
	public EventItemConstructor(Block block) {
		eventItem = new EventItemBlock(block);
	}
	
	public EventItemConstructor(ItemStack itemStack) {
		eventItem = new EventItemStack(itemStack);
	}
	
	public EventItemConstructor(Entity entity) {
		eventItem = new EventItemEntity(entity);
	}
	
	public EventItemConstructor(String string) {
		eventItem = new EventItemString(string);
	}
	
	
	public EventItem getEventItem() {
		return eventItem;
	}
}


