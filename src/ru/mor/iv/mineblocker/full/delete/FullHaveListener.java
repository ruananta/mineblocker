package ru.mor.iv.mineblocker.full.delete;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import ru.mor.iv.mineblocker.MineBlockerPlugin;
import ru.mor.iv.mineblocker.config.ConfigHolder;
import ru.mor.iv.mineblocker.item.EventItem;

public class FullHaveListener {
	public void checkMaxStackSize(EventItem[] stacks) {
		if(ConfigHolder.isHaveEventStackSizeCheck()){
			for(EventItem i : stacks){
				int size = getAmount(i.getItemStack());
				if(i.getItemStack().getAmount() > size){
					i.getItemStack().setAmount(size);
				}
			}
			
		}
	}
	
	private int getAmount(ItemStack stack) {
		ItemStack i = new ItemStack(stack);
		i.setAmount(1);
		Inventory inv = MineBlockerPlugin.getInst().getServer().createInventory(null, 9);
		inv.addItem(i);
		return inv.getItem(0).getMaxStackSize();
	}
}
