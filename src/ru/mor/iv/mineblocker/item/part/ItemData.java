package ru.mor.iv.mineblocker.item.part;

public class ItemData {
	int intData;
	int[] multiData;
	
	public ItemData() {
		intData = DataType.SKIP;
	}
	
	public ItemData(int data) {
		this.intData = data;
	}
	
	public ItemData(int data, int[] multiData) {
		this(data);
		this.multiData = multiData;
	}
	
	public void setData(int intData) {
		this.intData = intData;
	}
	
	public int getData() {
		return intData;
	}
	
	public void setMultiData(int[] multiData) {
		this.multiData = multiData;
	}
	
	public int[] getMultiData() {
		return multiData;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
            return true;
        }
        if (obj instanceof ItemData) {
        	ItemData data = (ItemData) obj;
        	if(intData == DataType.MULTI_FLAG){
    			if(data.getData() != DataType.MULTI_FLAG){
    				for(int i : multiData){
    					if(equals(i, data.getData())){
							return true;
						}
    				}
    			}else{
    				for(int i : multiData){
    					for(int j : data.getMultiData()){
    						if(equals(i, j)){
    							return true;
    						}
    					}
    				}
    			}
    		}else{
    			if(data.getData() != DataType.MULTI_FLAG){
    				return equals(intData, data.getData());
    			}else{
    				for(int j : data.getMultiData()){
    					if(equals(intData, j)){
							return true;
						}
    				}
    			}
    			
    		}
        }
        return false;
	}
	
	private boolean equals(int data1, int data2){
		if( data1 == data2 || data1 == DataType.SKIP || data2 == DataType.SKIP){
			return true;
		}
		return false;
	}
}
