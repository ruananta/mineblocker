package ru.mor.iv.mineblocker.full;

import ru.mor.iv.mineblocker.full.delete.CustomItemsLoader;
import ru.mor.iv.mineblocker.full.delete.CustomPermissionsLoader;
import ru.mor.iv.mineblocker.full.delete.FullConfigHolder;
import ru.mor.iv.mineblocker.full.delete.FullHaveListener;
import ru.mor.iv.mineblocker.full.delete.FullInventoryDoubleOpeningListener;
import ru.mor.iv.mineblocker.full.delete.FullPlaceNearListener;
import ru.mor.iv.mineblocker.full.delete.ItemRemover;
import ru.mor.iv.mineblocker.full.delete.PistonBlackList;
import ru.mor.iv.mineblocker.full.delete.RangeItemManager;
import ru.mor.iv.mineblocker.message.NameReplacer;

public class FullPlugin {
	private static FullPlugin instance;
	private CustomItemsLoader customItemsLoader;
	private CustomPermissionsLoader customPermissionsLoader;
	private FullPlaceNearListener fullPlaceNearListener;
	private FullInventoryDoubleOpeningListener fullInventoryDoubleOpeningListener;
	private FullHaveListener fullHaveListener;
	private RangeItemManager rangeItemManager;
	private NameReplacer nameReplacer;
	private ItemRemover itemRemover;
	private FullConfigHolder fullConfigHolder;
	private PistonBlackList pistonBlackList;
	
	private FullPlugin() {
		initialize();
	}

	public void initialize() {
		customItemsLoader = new CustomItemsLoader();
		customItemsLoader.load();
		customPermissionsLoader = new CustomPermissionsLoader();
		customPermissionsLoader.load();
		fullPlaceNearListener = new FullPlaceNearListener();
		fullHaveListener = new FullHaveListener();
		rangeItemManager = new RangeItemManager();
		rangeItemManager.load();
		PacketManager.init();
		nameReplacer = new NameReplacer();
		nameReplacer.load();
		itemRemover = new ItemRemover();
		fullConfigHolder = new FullConfigHolder();
		pistonBlackList = new PistonBlackList();
		fullInventoryDoubleOpeningListener = new FullInventoryDoubleOpeningListener();
	}
	
	public CustomItemsLoader getCustomItemsLoader() {
		return customItemsLoader;
	}
	
	public CustomPermissionsLoader getCustomPermissionsLoader() {
		return customPermissionsLoader;
	}
	
	public FullPlaceNearListener getFullPlaceNearListener() {
		return fullPlaceNearListener;
	}
	
	public FullInventoryDoubleOpeningListener getFullInventoryDoubleOpeningListener() {
		return fullInventoryDoubleOpeningListener;
	}
	
	public FullHaveListener getFullHaveListener() {
		return fullHaveListener;
	}
	
	public RangeItemManager getRangeItemManager() {
		return rangeItemManager;
	}
	
	public NameReplacer getNameReplacer() {
		return nameReplacer;
	}
	
	public ItemRemover getItemRemover() {
		return itemRemover;
	}
	
	public FullConfigHolder getFullConfigHolder(){
		return fullConfigHolder;
	}
	
	public PistonBlackList getPistonBlackList() {
		return pistonBlackList;
	}
	
	public void reload() {
		customItemsLoader.reload();
		customPermissionsLoader.reload();
		rangeItemManager.reload();
		nameReplacer.reload();
	}
	
	static FullPlugin getInstance() {
		if(instance == null)
			instance = new FullPlugin();
		return instance;
	}

	
}
