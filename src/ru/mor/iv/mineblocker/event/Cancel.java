package ru.mor.iv.mineblocker.event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Cancel {
	private List<CancelType> cancelTypes;
	private List<CancelType> unCancelTypes;
	
	private int privateRange;
	private int outsideRange;
	
	public Cancel() {
		privateRange = 0;
		outsideRange = 0;
	}
	
	public void addCancel(CancelType cancelType) {
		initCancelTypes();
		cancelTypes.add(cancelType);
	}
	
	public void addAllCancelIfNotNull(List<CancelType> cancelTypes) {
		if(cancelTypes != null && !cancelTypes.isEmpty()){
			initCancelTypes();
			this.cancelTypes.addAll(cancelTypes);
		}
	}
	
	public List<CancelType> getCancelTypes() {
		return cancelTypes;
	}

	private void initCancelTypes() {
		if(cancelTypes == null){
			cancelTypes = new ArrayList<>();
		}
	}
	
	public void addUnCancel(CancelType unCancelType) {
		initUnCancelTypes();
		unCancelTypes.add(unCancelType);
	}
	
	public List<CancelType> getUnCancelTypes() {
		return unCancelTypes;
	}

	private void initUnCancelTypes() {
		if(unCancelTypes == null){
			unCancelTypes = new ArrayList<>();
		}
	}
	
	public void setPrivateRange(int privateRange) {
		this.privateRange = privateRange;
	}
	public int getPrivateRange() {
		return privateRange;
	}
	
	public void setOutsideRange(int outsideRange) {
		this.outsideRange = outsideRange;
	}
	
	public int getOutsideRange() {
		return outsideRange;
	}

	public boolean isCancelled() {
		return cancelTypes != null && !cancelTypes.isEmpty();
	}

	public void setCancelled(boolean cancelled) {
		if(cancelled){
			addAllCancelTypes();
		}else{
			cancelTypes = null;
		}
	}
	
	public void setCancelledGlobal(boolean cancelled) {
		if(cancelled){
			addCancel(CancelType.GLOBAL);
		}else{
			if(cancelTypes != null){
				cancelTypes.remove(CancelType.GLOBAL);
			}
		}
	}

	private void addAllCancelTypes() {
		initCancelTypes();
		cancelTypes.addAll(Arrays.asList(CancelType.values()));
	}

	public boolean isUnCancelledGlobal() {
		return unCancelTypes != null && unCancelTypes.contains(CancelType.GLOBAL);
	}

	public boolean isUnCancelledPrivate() {
		return unCancelTypes != null && unCancelTypes.contains(CancelType.PRIVATE);
	}

	public boolean isUnCancelledOutPrivate() {
		return unCancelTypes != null && unCancelTypes.contains(CancelType.OUTSIDE_PRIVATE);
	}
	
	public boolean isCancelledPrivate() {
		return cancelTypes != null && cancelTypes.contains(CancelType.PRIVATE);
	}

	public void setCancelledPrivate(boolean cancelled) {
		if(cancelled){
			addCancel(CancelType.PRIVATE);
		}else{
			if(cancelTypes != null){
				cancelTypes.remove(CancelType.PRIVATE);
			}
		}
	}
	
	public boolean isCancelledGlobal() {
		return cancelTypes != null && cancelTypes.contains(CancelType.GLOBAL);
	}
	

	public boolean isCancelledOutPrivate() {
		return cancelTypes != null && cancelTypes.contains(CancelType.OUTSIDE_PRIVATE);
	}

	public void setCancelledOutPrivate(boolean cancelled) {
		if(cancelled){
			addCancel(CancelType.OUTSIDE_PRIVATE);
		}else{
			if(cancelTypes != null){
				cancelTypes.remove(CancelType.OUTSIDE_PRIVATE);
			}
		}
	}
	
	public boolean isCancelledWorld() {
		return cancelTypes != null && cancelTypes.contains(CancelType.WORLD);
	}

	public void setCancelledWorld(boolean cancelled) {
		if(cancelled){
			addCancel(CancelType.WORLD);
		}else{
			if(cancelTypes != null){
				cancelTypes.remove(CancelType.WORLD);
			}
		}
	}
	
	public boolean isCancelledGamemode() {
		return cancelTypes != null && cancelTypes.contains(CancelType.GAMEMODE);
	}

	public void setCancelledGamemode(boolean cancelled) {
		if(cancelled){
			addCancel(CancelType.GAMEMODE);
		}else{
			if(cancelTypes != null){
				cancelTypes.remove(CancelType.GAMEMODE);
			}
		}
	}

	
	
}
