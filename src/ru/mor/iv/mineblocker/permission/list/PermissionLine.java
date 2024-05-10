package ru.mor.iv.mineblocker.permission.list;

import ru.mor.iv.mineblocker.config.string.Strings;
import ru.mor.iv.mineblocker.event.CancelType;
import ru.mor.iv.mineblocker.item.ListItem;
import ru.mor.iv.mineblocker.permission.Permission;


public class PermissionLine {
	private Permission[] multiPerm;
	private ListItem[]	multiItem;
	private ListItem[]	multiBlock;
	private CancelType cancellType;
	private int privateRange;
	private boolean ban;
	private int removeTicks;
	private String custom_message;
	private String signLine;
	private boolean mute;
	
	
	
	public PermissionLine(Permission[] multiPerm, ListItem[] multiItem, ListItem[] multiBlock, CancelType cancellType,
			int privateRange, boolean ban, int removeTicks, String custom_message, String signLine, boolean mute) {
		this.multiPerm = multiPerm;
		this.multiItem = multiItem;
		this.multiBlock = multiBlock;
		this.cancellType = cancellType;
		this.privateRange = privateRange;
		this.ban = ban;
		this.removeTicks = removeTicks;
		this.custom_message = custom_message;
		this.signLine = signLine;
		this.mute = mute;
	}
	
	@Deprecated
	public Permission[] getMultiPerm() {
		return multiPerm;
	}
	
	public ListItem[] getMultiItem() {
		return multiItem;
	}
	
	public ListItem[] getMultiBlock() {
		return multiBlock;
	}
	
	public boolean isBan() {
		return ban;
	}
	
	public CancelType getCancellType() {
		return cancellType;
	}
	
	public boolean isPrivateRange(){
		return privateRange == 0;
	}
	
	public int getPrivateRange() {
		return privateRange;
	}
	
	public int getRemoveTicks() {
		return removeTicks;
	}
	
	public String getCustoMessage() {
		return custom_message;
	}
	
	public String getSignLine() {
		return signLine;
	}
	
	public boolean isMute() {
		return mute;
	}
	
	@Deprecated
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int i = multiPerm.length;
		for(Permission s : multiPerm){
			sb.append(s.getStringPermission());
			i--;
			if(i!=0){
				sb.append("|");
			}
		}
		sb.append(Strings.WORD_DELIMETER);
		int i2 = multiItem.length;
		for(ListItem s : multiItem){
			sb.append(s.toString());
			i2--;
			if(i2 != 0){
				sb.append("|");
			}
		}
		if(multiBlock != null){
			sb.append(Strings.WORD_DELIMETER);
			int i3 = multiBlock.length;
			for(ListItem s : multiBlock){
				sb.append(s.toString());
				i3--;
				if(i3 != 0){
					sb.append("|");
				}
			}
		}
		if(cancellType == CancelType.PRIVATE){
			sb.append(".wg");
			if(privateRange != 0){
				sb.append("|" + privateRange);
			}
		}
		if(cancellType == CancelType.OUTSIDE_PRIVATE){
			sb.append(".out");
		}
		return sb.toString();
	}

}
