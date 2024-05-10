package ru.mor.iv.mineblocker.event;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import ru.mor.iv.mineblocker.Debug;
import ru.mor.iv.mineblocker.SignWrapper;
import ru.mor.iv.mineblocker.Utils;
import ru.mor.iv.mineblocker.config.string.Strings;
import ru.mor.iv.mineblocker.exception.NotNullException;
import ru.mor.iv.mineblocker.item.EventItem;
import ru.mor.iv.mineblocker.permission.ActionPermission;
import ru.mor.iv.mineblocker.permission.Permission;
import ru.mor.iv.mineblocker.permission.StringPermissions;

public class ActionEvent extends Event implements Cancellable {
	private static HandlerList handlers = new HandlerList();
	private Cancel cancel = new Cancel();
	private ActionPermission[] permissions;
	private Location location;
	private EventItem[] usedItems;
	private EventItem[] forWhatUsed;
	private String customMessage;
	private boolean mute;
	private SignWrapper signWrapper;

	public ActionEvent(Permission[] permissions, EventItem[] usedItems) {
		if (!contentNull(permissions))
			initPermissions(permissions);
		if (!contentNull(usedItems))
			this.usedItems = usedItems;
	}

	public ActionEvent(Permission[] permissions, Location actionLocation, EventItem[] usedItems) {
		this(permissions, usedItems);
		this.location = actionLocation;
	}

	public ActionEvent(Permission permission, Location actionLocation, EventItem[] usedItems) {
		this(getArray(permission), actionLocation, usedItems);
	}

	public ActionEvent(Permission permission, Location actionLocation, EventItem usedItem) {
		this(getArray(permission), actionLocation, getArray(usedItem));
	}

	public ActionEvent(Permission[] permissions, Location actionLocation, EventItem usedItem) {
		this(permissions, actionLocation, getArray(usedItem));
	}

	public ActionEvent(Permission[] permissions, EventItem[] usedItems, EventItem[] forWhatUseds) {
		this(permissions, usedItems);
		if (!contentNull(forWhatUseds))
			this.forWhatUsed = forWhatUseds;
	}

	public ActionEvent(Permission[] permissions, Location actionLocation, EventItem[] usedItems,
			EventItem[] forWhatUseds) {
		this(permissions, actionLocation, usedItems);
		if (!contentNull(forWhatUseds))
			this.forWhatUsed = forWhatUseds;
	}

	public ActionEvent(Permission permission, Location actionLocation, EventItem usedItem, EventItem forWhatUsed) {
		this(getArray(permission), actionLocation, getArray(usedItem), getArray(forWhatUsed));
	}

	public ActionEvent(Permission[] permissions, Location actionLocation, EventItem usedItem, EventItem forWhatUsed) {
		this(permissions, actionLocation, getArray(usedItem), getArray(forWhatUsed));
	}

	public ActionEvent(Permission permission, Location actionLocation, EventItem[] usedItems,
			EventItem[] forWhatUseds) {
		this(getArray(permission), actionLocation, usedItems, forWhatUseds);
	}
	
//	
	public ActionEvent(Permission[] permissions, EventItem[] usedItems, SignWrapper sign) {
		if (!contentNull(permissions))
			initPermissions(permissions);
		if (!contentNull(usedItems))
			this.usedItems = usedItems;
		this.signWrapper = sign;
	}

	public ActionEvent(Permission[] permissions, Location actionLocation, EventItem[] usedItems, SignWrapper sign) {
		this(permissions, usedItems, sign);
		this.location = actionLocation;
	}

	public ActionEvent(Permission permission, Location actionLocation, EventItem[] usedItems, SignWrapper sign) {
		this(getArray(permission), actionLocation, usedItems, sign);
	}

	public ActionEvent(Permission permission, Location actionLocation, EventItem usedItem, SignWrapper sign) {
		this(getArray(permission), actionLocation, getArray(usedItem), sign);
	}

	public ActionEvent(Permission[] permissions, Location actionLocation, EventItem usedItem, SignWrapper sign) {
		this(permissions, actionLocation, getArray(usedItem), sign);
	}

	public ActionEvent(Permission[] permissions, EventItem[] usedItems, EventItem[] forWhatUseds, SignWrapper sign) {
		this(permissions, usedItems, sign);
		if (!contentNull(forWhatUseds))
			this.forWhatUsed = forWhatUseds;
	}

	public ActionEvent(Permission[] permissions, Location actionLocation, EventItem[] usedItems,
			EventItem[] forWhatUseds, SignWrapper sign) {
		this(permissions, actionLocation, usedItems, sign);
		if (!contentNull(forWhatUseds))
			this.forWhatUsed = forWhatUseds;
	}

	public ActionEvent(Permission permission, Location actionLocation, EventItem usedItem, EventItem forWhatUsed, SignWrapper sign) {
		this(getArray(permission), actionLocation, getArray(usedItem), getArray(forWhatUsed), sign);
	}

	public ActionEvent(Permission[] permissions, Location actionLocation, EventItem usedItem, EventItem forWhatUsed, SignWrapper sign) {
		this(permissions, actionLocation, getArray(usedItem), getArray(forWhatUsed), sign);
	}

	public ActionEvent(Permission permission, Location actionLocation, EventItem[] usedItems,
			EventItem[] forWhatUseds, SignWrapper sign) {
		this(getArray(permission), actionLocation, usedItems, forWhatUseds, sign);
	}

	private static Permission[] getArray(Permission permission) {
		if (permission != null)
			return new Permission[] { permission };
		return null;
	}

	private static EventItem[] getArray(EventItem eventItem) {
		if (eventItem != null)
			return new EventItem[] { eventItem };
		return null;
	}

	private void initPermissions(Permission[] permissions) {
		if (this.permissions == null) {
			this.permissions = new ActionPermission[permissions.length];
		}
		for (int i = 0; i < permissions.length; i++) {
			this.permissions[i] = new ActionPermission(permissions[i]);
		}
	}

	/**
	 * Return Actor of this event.
	 * 
	 * @return Player or null.
	 */
	public Player getActor() {
		return null;
	}

	/**
	 * Return array of permissions.
	 * 
	 * @return ActionPermission[] or null;
	 */
	public ActionPermission[] getPermissions() {
		return permissions;
	}

	/**
	 * Return banned permissions.
	 * 
	 * @return Permission[] or null;
	 */
	public Permission[] getBannedPermissions() {
		if (permissions == null) {
			return null;
		}
		Permission[] p = null;
		for (ActionPermission actionPermission : permissions) {
			if (actionPermission.isBanned()) {
				p = addPermission(p, actionPermission.getPermission());
			}
		}
		return p;
	}

	private Permission[] addPermission(Permission[] array, Permission p) {
		return Utils.addPermission(array, p);
	}

	// Action Location
	/**
	 * Return the Locations of the event.
	 * 
	 * @return Location[] or null.
	 */
	public Location[] getActionLocations() {
		if(location != null){
			return new Location[]{location};
		}
		if(forWhatUsed != null){
			return getLocations(forWhatUsed);
		}
		if(usedItems != null){
			Location[] locations = getLocations(usedItems);
			if(locations != null){
				return locations;
			}
		}
		if(getActor() != null){
			return new Location[]{getActor().getLocation()};
		}
		return null;
	}

	private Location[] getLocations(EventItem[] eventItems) {
		Location[] locations = null;
		for(EventItem eventItem : eventItems){
			if(eventItem.getLocation() != null){
				Utils.addLocation(locations, eventItem.getLocation());
			}
		}
		return locations;
	}
	
	

	// UsedItems
	/**
	 * Returns the items that were used.
	 * 
	 * @return EventItem[] or null.
	 */
	public EventItem[] getUsedItems() {
		return usedItems;
	}

	public EventItem[] getBannedUsedItems() {
		return getBannedItems(usedItems);
	}

	public EventItem[] getBannedNearBlocks() {
		EventItem[] items = null;
		for (EventItem i : getBannedUsedItems()) {
			if (i.getNearBlocks() != null) {
				for (EventItem b : i.getNearBlocks()) {
					if (b != null && b.isBanned()) {
						items = addItem(items, b);
					}
				}
			}
		}
		return items;
	}

	public EventItem[] getRemoveUsedItems() {
		EventItem[] i = null;
		if (usedItems != null) {
			for (EventItem item : usedItems) {
				if (item.getRemoveTicks() != 0) {
					i = addItem(i, item);
				}
			}
		}
		return i;
	}

	// ForWhatUsed
	public EventItem[] getForWhatUsed() {
		return forWhatUsed;
	}

	public EventItem[] getBannedForWhatUsed() {
		return getBannedItems(forWhatUsed);
	}

	// Utils
	private EventItem[] addItem(EventItem[] array, EventItem eventItem) {
		return Utils.addItem(array, eventItem);
	}

	private EventItem[] getBannedItems(EventItem[] items) {
		EventItem[] i = null;
		if (items != null) {
			for (EventItem item : items) {
				if (item.isBanned()) {
					i = addItem(i, item);
				}
			}
		}
		return i;
	}

	// Cancell
	public Cancel getCancel() {
		return cancel;

	}

	@Override
	public boolean isCancelled() {
		return cancel.isCancelled();
	}

	/**
	 * @deprecated use getCancell();
	 */
	@Override
	@Deprecated
	public void setCancelled(boolean b) {
		cancel.setCancelled(b);
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	// Message
	public void setCustomMessage(String customMessage) {
		this.customMessage = customMessage;
	}

	public String getCustomMessage() {
		return customMessage;
	}
	
	//Mute
	public boolean isMute() {
		return mute;
	}
	
	public void setMute(boolean mute) {
		this.mute = mute;
	}

	public SignWrapper getSign() {
		return signWrapper;
	}
	
	// Exception
	protected boolean arrayNull(Object[] obj) {
		if (obj == null) {
			arrayNotNullException();
			return true;
		}
		return false;
	}

	private void arrayNotNullException() {
		try {
			throw new NotNullException("Array can not be null!!!");
		} catch (NotNullException e) {
			Debug.send(e.getMessage());
			e.printStackTrace();
		}
	}

	protected boolean contentNull(Object[] obj) {
		if (obj != null) {
			for (Object o : obj) {
				if (o == null) {
					contentNotNullException();
					return true;
				}
			}
		}
		return false;
	}

	private void contentNotNullException() {
		try {
			throw new NotNullException("Content can not be null!!!");
		} catch (NotNullException e) {
			Debug.send(e.getMessage());
			e.printStackTrace();
		}
	}

	public String getStringWorld() {
		if (location != null) {
			return location.getWorld().getName();
		}
		return null;
	}

	public List<String> getStringPermissions() {
		List<String> list = new ArrayList<>();
		if (permissions != null) {
			if (usedItems != null) {
				if (forWhatUsed != null) {
					for (ActionPermission p : permissions) {
						for (EventItem u : usedItems) {
							for (EventItem f : forWhatUsed) {
								list.add(convertToString(p.getPermission(), u, f));
							}
						}
					}
				} else {
					for (ActionPermission p : permissions) {
						for (EventItem u : usedItems) {
							list.add(convertToString(p.getPermission(), u));
						}
					}
				}
			}
		}
		return list;
	}

	private String convertToString(Permission p, EventItem i) {
		StringBuilder sb = new StringBuilder();
		sb.append(StringPermissions.MINEBLOCKER);
		sb.append(p.getStringPermission());
		sb.append(Strings.WORD_DELIMETER);
		sb.append(i.toString());
		return sb.toString();
	}

	private String convertToString(Permission p, EventItem i, EventItem b) {
		StringBuilder sb = new StringBuilder();
		sb.append(StringPermissions.MINEBLOCKER);
		sb.append(p.getStringPermission());
		sb.append(Strings.WORD_DELIMETER);
		sb.append(i.toString());
		sb.append(Strings.WORD_DELIMETER);
		sb.append(b.toString());
		return sb.toString();
	}

}
