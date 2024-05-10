package ru.mor.iv.mineblocker.full.delete.packet;

import java.io.IOException;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface PacketUtilsInterface {
	
	public String getTags(org.bukkit.block.Block block);
	
	public String getTags(org.bukkit.inventory.ItemStack item);
	
	public String getOpenInventoryName(org.bukkit.entity.Player player);
	
	public boolean hasInventory(org.bukkit.block.Block b);
	
	public boolean hasInventory(org.bukkit.entity.Entity e);

	public String toString(ItemStack[] inventory) throws IOException;

	public Inventory fromString(String data) throws IOException;
	
}
