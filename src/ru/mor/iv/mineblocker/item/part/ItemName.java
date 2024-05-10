package ru.mor.iv.mineblocker.item.part;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import ru.mor.iv.mineblocker.config.ConfigHolder;
import ru.mor.iv.mineblocker.config.string.Strings;

public class ItemName {
	String name;
	Object type;
	
	public ItemName(String name) {
		this.name = name;
	}
	
	public ItemName(Object type) {
		this.type = type;
	}
	
	public ItemName(String name, Object type) {
		this(name);
		this.type = type;
	}
	
	public void setType(Object type) {
		this.type = type;
	}
	
	public Object getType() {
		return type;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		if(name == null){
			createName();
		}
		return name;
	}
	
	@SuppressWarnings("deprecation")
	private void createName() {
		if(type == null) return;
		if(ConfigHolder.isNameEnabled()){
			name = type.toString().toLowerCase();
		}else{
			if(type instanceof Material){
				Material m = (Material) type;
				name = String.valueOf(m.getId());
			}else if(type instanceof EntityType){
				name = type.toString().toLowerCase();
			}
		}
		
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj){
			return true;
		}
		if(obj instanceof ItemName){
			ItemName item = (ItemName) obj;
			if(type != null && item.getType() != null){
				if(type == item.getType()){
					return true;
				}
			}else{
				if(item.getName().equals(Strings.SKIP) || getName().equals(Strings.SKIP) || getName().equalsIgnoreCase(item.getName())){
					return true;
				}
			}
		}
		return false;
	}
}
