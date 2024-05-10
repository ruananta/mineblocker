package ru.mor.iv.mineblocker.item;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import ru.mor.iv.mineblocker.full.FullPluginLoader;
import ru.mor.iv.mineblocker.full.PacketManager;
import ru.mor.iv.mineblocker.item.part.ItemData;
import ru.mor.iv.mineblocker.item.part.ItemNBT;
import ru.mor.iv.mineblocker.item.part.ItemName;

public class EventItemStack extends EventItem{
	private ItemStack stack;
	
	@SuppressWarnings("deprecation")
	public EventItemStack(ItemStack stack) {
		super(stack);
		this.stack = stack;
		data = new ItemData(stack.getData().getData());
		name = new ItemName(stack.getType());
	}
	
	@Override
	public String getDisplayName() {
		if(FullPluginLoader.isFullPlugin()){
			String name = FullPluginLoader.getFullPlugin().getNameReplacer().getNewName(this);
			if(name != null){
				return name;
			}
		}
		if(stack != null){
			ItemMeta im = stack.getItemMeta();
			if(im != null){
				String displayName = im.getDisplayName();
				if(displayName != null){
					return displayName;
				}
			}
		}
		return super.getDisplayName();
	}

	@Override
	public ItemNBT getNBT() {
		if(nbt == null){
			nbt = new ItemNBT(PacketManager.getNbtTags(stack));
		}
		return nbt;
	}
	
	@Override
	public ItemStack getItemStack() {
		return stack;
	}
}
