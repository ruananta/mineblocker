package ru.mor.iv.mineblocker.item.part;

import ru.mor.iv.mineblocker.config.string.Strings;

public class ItemNBT {
	String stringNBT;
	String[] arrayStringNBT;;
	
	public ItemNBT() {}
	
	public ItemNBT(String stringNBT) {
		this.stringNBT = stringNBT;
	}

	public void setStringNBT(String nbt) {
		this.stringNBT = nbt;
	}
	
	public String getStringNBT() {
		return stringNBT;
	}
	
	public void setMultiNbt(String[] multiNbt) {
		this.arrayStringNBT = multiNbt;
	}
	
	public String[] getMultiNbt() {
		return arrayStringNBT;
	}
	
	public boolean isEmpty() {
		return stringNBT == null && arrayStringNBT == null;
	}
	
	public int size() {
		if(arrayStringNBT == null)
			return 0;
		return arrayStringNBT.length;
	}

	
//	@Override
//	public boolean equals(Object obj) {
//		if(this == obj){
//			return true;
//		}
//		if(obj == null){
//			return contains((String) null);
//		}
//		if(obj instanceof ItemNBT){
//			ItemNBT itemNbt = (ItemNBT) obj;
//			if(itemNbt.isListNbt()){
//				if(itemNbt.getNbt() != null){
//					return contains(itemNbt.getNbt());
//				}else{
//					return contains(itemNbt.getMultiNbt());
//				}
//			}else{
//				if(nbt != null){
//					return itemNbt.contains(nbt);
//				}else{
//					return itemNbt.contains(multiNbt);
//				}
//			}
//		}
//		return false;
//	}

	public boolean containsAll(String[] array){
		if(array == null){
			return true;
		}
		boolean b = true;
		for(String s :array){
			if(!contains(s)){
				b = false;
			}
		}
		return b;
	}
	
	
	public boolean contains(String[] array) {
		if(array == null){
			return true;
		}
		for(String s :array){
			if(contains(s)){
				return true;
			}
		}
		return false;
	}

	public boolean contains(String s){
		if(s == null){
			return true;
		}
		if(s.equals(Strings.NULL_NBT) && stringNBT.length() == 2){
			return true;
		}
		return stringNBT.contains(s);
	}

	

	
}
