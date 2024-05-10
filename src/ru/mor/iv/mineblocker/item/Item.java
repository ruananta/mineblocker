package ru.mor.iv.mineblocker.item;

import ru.mor.iv.mineblocker.config.ConfigHolder;
import ru.mor.iv.mineblocker.config.string.Strings;
import ru.mor.iv.mineblocker.item.part.DataType;
import ru.mor.iv.mineblocker.item.part.ItemData;
import ru.mor.iv.mineblocker.item.part.ItemName;

public abstract class Item {
	
	ItemName name;
	ItemData data;

	
	int removeTicks;
	
	boolean not;
	
	public Item(Object o) {
		
	}
	
	public ItemName getName() {
		return name;
	}
	
	public ItemData getData() {
		return data;
	}
	
	public String getDisplayName() {
		return toString();
	}
	
	public int getRemoveTicks() {
		return removeTicks;
	}
	
	public void setRemoveTicks(int removeTicks) {
		this.removeTicks = removeTicks;
	}
	
	public boolean isNot() {
		return not;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(name.getName());
		if(ConfigHolder.isMetadataEnabled()){
			sb.append(Strings.ITEM_DELIMETER);
			if(data.getData() == DataType.SKIP){
				sb.append(Strings.SKIP);
			}else if(data.getData() == DataType.MULTI_FLAG){
				int j = 0;
				for(int i : data.getMultiData()){
					if(j != 0) sb.append(Strings.ITEM_DELIMETER);
					if(i == DataType.SKIP){
						sb.append(Strings.SKIP);
					}else{
						sb.append(data.getData());
					}
				}
			}
					else{
				sb.append(data.getData());
			}
		}
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj){
			return true;
		}
		if(obj instanceof Item){
			Item item = (Item) obj;
			return name.equals(item.getName()) && data.equals(item.getData());
		}
		return false;
	}
	
}
