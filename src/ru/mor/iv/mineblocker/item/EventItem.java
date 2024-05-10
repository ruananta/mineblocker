package ru.mor.iv.mineblocker.item;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import ru.mor.iv.mineblocker.SignWrapper;
import ru.mor.iv.mineblocker.config.ConfigHolder;
import ru.mor.iv.mineblocker.full.FullPluginLoader;
import ru.mor.iv.mineblocker.item.part.ItemNBT;

public abstract class EventItem extends Item {

	ItemNBT nbt;
	SignWrapper sign;
	boolean banned;

	public EventItem(Object o) {
		super(o);
	}

	public abstract ItemNBT getNBT();

	public ItemStack getItemStack() {
		return null;
	}

	public Block getBlock() {
		return null;
	}

	public Entity getEntity() {
		return null;
	}

	public Location getLocation() {
		return null;
	}

	public boolean isBanned() {
		return banned;
	}

	public void setBanned(boolean banned) {
		this.banned = banned;
	}

	public EventItem[] getNearBlocks() {
		return null;
	}

	public EventItem getBannedNearBlock() {
		return null;
	}

	public SignWrapper getSign() {
		return sign;
	}

	@Override
	public String getDisplayName() {
		if (FullPluginLoader.isFullPlugin()) {
			String name = FullPluginLoader.getFullPlugin().getNameReplacer().getNewName(this);
			if (name != null) {
				return name;
			}
		}
		return toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (super.equals(obj)) {
			if (obj instanceof ListItem) {
				ListItem item = (ListItem) obj;
				if (!ConfigHolder.isNbtEnabled()) {
					return true;
				}
				return matchNBT(item);
			}
			return true;
		}
		return false;
	}

	private boolean matchNBT(ListItem item){
		if(item.isEmptyNbt()){
			return true;
		}
		getNBT();
		boolean in = nbt.contains(item.getIncludeNBT().getMultiNbt());
		boolean inAll = nbt.containsAll(item.getIncludeAllNBT().getMultiNbt());
		boolean notIn = item.getNotIncludeNBT().getMultiNbt() == null ? true : !nbt.contains(item.getNotIncludeNBT().getMultiNbt());
		return in && inAll && notIn;
	}
}
