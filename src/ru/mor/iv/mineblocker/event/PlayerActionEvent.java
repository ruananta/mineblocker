package ru.mor.iv.mineblocker.event;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import ru.mor.iv.mineblocker.SignWrapper;
import ru.mor.iv.mineblocker.item.EventItem;
import ru.mor.iv.mineblocker.permission.Permission;

public class PlayerActionEvent extends ActionEvent{
	private Player actor;
	
	public PlayerActionEvent(Player actor, Permission[] permissions, Location actionLocation, EventItem[] usedItems) {
		super(permissions, actionLocation, usedItems);
		this.actor = actor;
	}
	
	public PlayerActionEvent(Player actor, Permission[] permissions, Location actionLocation, EventItem usedItem) {
		super(permissions, actionLocation, usedItem);
		this.actor = actor;
	}
	
	public PlayerActionEvent(Player actor, Permission permissions, Location actionLocation, EventItem usedItems) {
		super(permissions, actionLocation, usedItems);
		this.actor = actor;
	}
	
	public PlayerActionEvent(Player actor, Permission permissions, Location actionLocation, EventItem[] usedItems) {
		super(permissions, actionLocation, usedItems);
		this.actor = actor;
	}
	
	public PlayerActionEvent(Player actor, Permission permissions, Location actionLocation, EventItem usedItems,
			EventItem forWhatUsed) {
		super(permissions, actionLocation, usedItems, forWhatUsed);
		this.actor = actor;
	}

	public PlayerActionEvent(Player actor, Permission[] permissions, Location actionLocation, EventItem[] usedItems,
			EventItem[] forWhatUseds) {
		super(permissions, actionLocation, usedItems, forWhatUseds);
		this.actor = actor;
	}
	
	public PlayerActionEvent(Player actor, Permission permissions, Location actionLocation, EventItem[] usedItems,
			EventItem[] forWhatUseds) {
		super(permissions, actionLocation, usedItems, forWhatUseds);
		this.actor = actor;
	}
	
	public PlayerActionEvent(Player actor, Permission[] permissions, Location actionLocation, EventItem usedItems,
			EventItem forWhatUseds) {
		super(permissions, actionLocation, usedItems, forWhatUseds);
		this.actor = actor;
	}
	
//	

	public PlayerActionEvent(Player actor, Permission[] permissions, Location actionLocation, EventItem[] usedItems, SignWrapper sign) {
		super(permissions, actionLocation, usedItems, sign);
		this.actor = actor;
	}
	
	public PlayerActionEvent(Player actor, Permission[] permissions, Location actionLocation, EventItem usedItem, SignWrapper sign) {
		super(permissions, actionLocation, usedItem, sign);
		this.actor = actor;
	}
	
	public PlayerActionEvent(Player actor, Permission permissions, Location actionLocation, EventItem usedItems, SignWrapper sign) {
		super(permissions, actionLocation, usedItems, sign);
		this.actor = actor;
	}
	
	public PlayerActionEvent(Player actor, Permission permissions, Location actionLocation, EventItem[] usedItems, SignWrapper sign) {
		super(permissions, actionLocation, usedItems, sign);
		this.actor = actor;
	}
	
	public PlayerActionEvent(Player actor, Permission permissions, Location actionLocation, EventItem usedItems,
			EventItem forWhatUsed, SignWrapper sign) {
		super(permissions, actionLocation, usedItems, forWhatUsed, sign);
		this.actor = actor;
	}

	public PlayerActionEvent(Player actor, Permission[] permissions, Location actionLocation, EventItem[] usedItems,
			EventItem[] forWhatUseds, SignWrapper sign) {
		super(permissions, actionLocation, usedItems, forWhatUseds, sign);
		this.actor = actor;
	}
	
	public PlayerActionEvent(Player actor, Permission permissions, Location actionLocation, EventItem[] usedItems,
			EventItem[] forWhatUseds, SignWrapper sign) {
		super(permissions, actionLocation, usedItems, forWhatUseds, sign);
		this.actor = actor;
	}
	
	public PlayerActionEvent(Player actor, Permission[] permissions, Location actionLocation, EventItem usedItems,
			EventItem forWhatUseds, SignWrapper sign) {
		super(permissions, actionLocation, usedItems, forWhatUseds, sign);
		this.actor = actor;
	}
	
	@Override
	public Player getActor() {
		return actor;
	}
	
	
	
}
