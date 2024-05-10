package ru.mor.iv.mineblocker.permission.list;

public class Content {
	
	private String[] array;
	
	public Content(String[] array) {
		this.array = array;
	}
	
	public String[] getContents(){
		return array;
	}
	
	public boolean isEmpty(){
		return array == null;
	}
	
	public boolean contains(String name){
		if(array == null || name == null){
			return true;
		}
		for(String s : array){
			if(s.equalsIgnoreCase(name)){
				return true;
			}
		}
		return false;
	}

}
