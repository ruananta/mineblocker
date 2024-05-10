package ru.mor.iv.mineblocker.full.delete;

import org.bukkit.inventory.ItemStack;

public class RemovedItem {
	private ItemStack stack;
	private int ticks;
	public RemovedItem(ItemStack stack, int ticks) {
		this.stack = stack;
		this.ticks = ticks;
	}
	
	public ItemStack getItemsStack() {
		return stack;
	}
	
	public int getTicks() {
		return ticks;
	}
	
	public void setTicks(int ticks) {
		this.ticks = ticks;
	}
}
