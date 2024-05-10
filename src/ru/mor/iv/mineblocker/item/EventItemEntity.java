package ru.mor.iv.mineblocker.item;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

import ru.mor.iv.mineblocker.SignWrapper;
import ru.mor.iv.mineblocker.config.string.Strings;
import ru.mor.iv.mineblocker.full.FullPluginLoader;
import ru.mor.iv.mineblocker.item.part.DataType;
import ru.mor.iv.mineblocker.item.part.ItemData;
import ru.mor.iv.mineblocker.item.part.ItemNBT;
import ru.mor.iv.mineblocker.item.part.ItemName;

public class EventItemEntity extends EventItem {
	
	private Entity entity;

	public EventItemEntity(Entity entity) {
		super(entity);
		this.entity = entity;
		name = new ItemName(entity.getType());
		data = new ItemData(DataType.SKIP);
	}
	
	@Override
	public ItemNBT getNBT() {
		return new ItemNBT(Strings.NULL_NBT);
	}
	
	@Override
	public Entity getEntity() {
		return entity;
	}
	
	@Override
	public Location getLocation() {
		return entity.getLocation();
	}
	
	@Override
	public String getDisplayName() {
		if(FullPluginLoader.isFullPlugin()){
			String name = FullPluginLoader.getFullPlugin().getNameReplacer().getNewName(this);
			if(name != null){
				return name;
			}
		}
		return this.name.getName();
	}
	
	@Override
	public SignWrapper getSign() {
		if(sign == null){
			sign = SignWrapper.searchSing(entity);
		}
		return super.getSign();
	}

	
}
