package ru.mor.iv.mineblocker.config;

import ru.mor.iv.mineblocker.full.FullPluginLoader;

public class ConfigHolder {
	protected static String generalConfigName = "config.yml";
	protected static String switchName = "switch_listeners.yml";
	protected static String defaultPermissionsName = "default_permissions.yml";
	protected static String worldGuardIgnoreRegionsName = "WorldGuard_ignore_regions.yml";
	protected static String pistonBlackListName = "piston_black_list.yml";
	
	private static Config[] configs;
	
	
	
	public static void init(){
		if(FullPluginLoader.isFullPlugin()){
			configs = FullPluginLoader.getFullPlugin().getFullConfigHolder().getConfigs();
		}else{
			configs = new Config[]{
					new General(generalConfigName),
					new MessageConfigsHolder(),
					new SwitchConfig(switchName)
			};
		}
	}
	
	public static void load(){
		for(Config c : configs){
			c.loadFile();
			if(!c.hasEverything()){
				c.createDefault();
				c.save();
			}
			c.load();
		}
	}

	public static void reload(){
		for(Config c : configs){
			c.loadFile();
			if(!c.hasEverything()){
				c.createDefault();
				c.save();
			}
			c.reload();
		}
	}
	
	
	public static boolean messageEnabled;
	public static String messageLanguage;
	public static boolean nameEnabled;
	public static boolean metadataEnabled;
	public static boolean nbtEnabled;
	public static boolean useWorldGuard;
	public static boolean checkOPinPrivate;
	public static boolean checkPlayerPermissions;
	public static boolean permissionsListEnabled;
	public static boolean opBypassPermissionsList;
	public static boolean haveEventStackSizeCheck;
	public static boolean blockBreakEventHaveCheck;
	public static boolean blockPlaceEventHaveCheck;
	public static boolean dropEventHaveCheck;
	public static boolean packupEventHaveCheck;
	public static boolean craftHaveCheck;
	public static boolean entityDamageEventHaveCheck;
	public static boolean inventoryEventHaveCheck;
	public static boolean leftInteractEventHaveCheck;
	public static boolean rightInteractEvent_HaveCheck;
	public static boolean bucketEventEmpty_HaveCheck;
	public static boolean bucketEventFill_HaveCheck;
	public static boolean pistonBlackList;
	public static int haveTaskTimerTime = 0;
	public static long messageCooldown = 1000;
	
	public static boolean isMessageEnabled() {
		return messageEnabled;
	}
	
	public static String getMessageLanguage() {
		return messageLanguage;
	}
	
	public static boolean isNameEnabled() {
		return nameEnabled;
	}
	public static boolean isMetadataEnabled() {
		return metadataEnabled;
	}
	public static boolean isNbtEnabled() {
		return nbtEnabled;
	}
	public static boolean isUseWorldGuard() {
		return useWorldGuard;
	}
	public static boolean isCheckOPinPrivate() {
		return checkOPinPrivate;
	}
	public static boolean isCheckPlayerPermissions() {
		return checkPlayerPermissions;
	}
	public static boolean isPermissionsListEnabled() {
		return permissionsListEnabled;
	}
	public static boolean isOpBypassPermissionsList() {
		return opBypassPermissionsList;
	}
	public static int getHaveTaskTimerTime() {
		return haveTaskTimerTime;
	}
	public static boolean isHaveEventStackSizeCheck() {
		return haveEventStackSizeCheck;
	}
	public static boolean isBlockBreakEventHaveCheck() {
		return blockBreakEventHaveCheck;
	}
	public static boolean isBlockPlaceEventHaveCheck() {
		return blockPlaceEventHaveCheck;
	}
	public static boolean isDropEventHaveCheck() {
		return dropEventHaveCheck;
	}
	public static boolean isPackupEventHaveCheck() {
		return packupEventHaveCheck;
	}
	public static boolean isCraftHaveCheck() {
		return craftHaveCheck;
	}
	public static boolean isEntityDamageEventHaveCheck() {
		return entityDamageEventHaveCheck;
	}
	public static boolean isInventoryEventHaveCheck() {
		return inventoryEventHaveCheck;
	}
	public static boolean isLeftInteractEventHaveCheck() {
		return leftInteractEventHaveCheck;
	}
	public static boolean isRightInteractEvent_HaveCheck() {
		return rightInteractEvent_HaveCheck;
	}
	public static boolean isBucketEventEmpty_HaveCheck() {
		return bucketEventEmpty_HaveCheck;
	}
	public static boolean isBucketEventFill_HaveCheck() {
		return bucketEventFill_HaveCheck;
	}
	public static boolean isPistonBlackList() {
		return pistonBlackList;
	}
	
	public static long getMessageCooldown() {
		return messageCooldown;
	}
}
