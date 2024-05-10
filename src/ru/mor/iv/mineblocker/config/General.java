package ru.mor.iv.mineblocker.config;

import ru.mor.iv.mineblocker.config.string.Strings;
import ru.mor.iv.mineblocker.full.FullPluginLoader;

public class General extends ConfigFeatures{
	
	public General(String fileName) {
		super(fileName);
	}
	
	
	@Override
	public void load() {
		ConfigHolder.messageEnabled = fileConfiguration.getBoolean(Strings.enableMessage);
		ConfigHolder.messageLanguage = fileConfiguration.getString(Strings.messageLanguage);
		ConfigHolder.messageCooldown = fileConfiguration.getLong(Strings.messageCooldown);
		ConfigHolder.nameEnabled = fileConfiguration.getBoolean(Strings.enableItemName);
		ConfigHolder.metadataEnabled = fileConfiguration.getBoolean(Strings.enableMetadata);
		if(FullPluginLoader.isFullPlugin())
			ConfigHolder.nbtEnabled = fileConfiguration.getBoolean(Strings.enableNBT);
		ConfigHolder.useWorldGuard = fileConfiguration.getBoolean(Strings.useWorldGuard);
		ConfigHolder.checkOPinPrivate = fileConfiguration.getBoolean(Strings.enableCheckOPinPrivate);
		ConfigHolder.checkPlayerPermissions = fileConfiguration.getBoolean(Strings.enableCheckPlayerPermissions);
		ConfigHolder.permissionsListEnabled = fileConfiguration.getBoolean(Strings.enablePermissionsList);
		ConfigHolder.opBypassPermissionsList = fileConfiguration.getBoolean(Strings.opBypassPermissionsList);
		if(FullPluginLoader.isFullPlugin())
			ConfigHolder.pistonBlackList = fileConfiguration.getBoolean(Strings.enablePistonBlackList);
		ConfigHolder.haveTaskTimerTime = fileConfiguration.getInt(Strings.haveTimerTime);
		ConfigHolder.blockBreakEventHaveCheck = fileConfiguration.getBoolean(Strings.enableBlockBreakEventHaveCheck);
		ConfigHolder.blockPlaceEventHaveCheck = fileConfiguration.getBoolean(Strings.enableBlockPlaceEventHaveCheck);
		ConfigHolder.dropEventHaveCheck = fileConfiguration.getBoolean(Strings.enableDropEventHaveCheck);
		ConfigHolder.packupEventHaveCheck = fileConfiguration.getBoolean(Strings.enablePackupEventHaveCheck);
		ConfigHolder.craftHaveCheck = fileConfiguration.getBoolean(Strings.enableCraftHaveCheck);
		if(FullPluginLoader.isFullPlugin())
			ConfigHolder.haveEventStackSizeCheck = fileConfiguration.getBoolean(Strings.enableHaveEventStackSizeCheck);
		ConfigHolder.entityDamageEventHaveCheck = fileConfiguration.getBoolean(Strings.enableEntityDamageEventHaveCheck);
		ConfigHolder.inventoryEventHaveCheck = fileConfiguration.getBoolean(Strings.enableInventoryEventHaveCheck);
		ConfigHolder.leftInteractEventHaveCheck = fileConfiguration.getBoolean(Strings.enableLeftInteractEventHaveCheck);
		ConfigHolder.rightInteractEvent_HaveCheck = fileConfiguration.getBoolean(Strings.enableRightInteractEvent_HaveCheck);
		ConfigHolder.bucketEventEmpty_HaveCheck = fileConfiguration.getBoolean(Strings.enableBucketEventEmpty_HaveCheck);
		ConfigHolder.bucketEventFill_HaveCheck = fileConfiguration.getBoolean(Strings.enableBucketEventFill_HaveCheck);
	}

	
	@Override
	public boolean hasEverything() {
		String[] strings = new String[]{
				Strings.enableMessage,
				Strings.messageLanguage,
				Strings.messageCooldown,
				Strings.enableItemName,
				Strings.enableMetadata,
				Strings.enableNBT,
				Strings.useWorldGuard,
				Strings.enableCheckOPinPrivate,
				Strings.enableCheckPlayerPermissions,
				Strings.enablePermissionsList, 
				Strings.opBypassPermissionsList,
				Strings.enablePistonBlackList,
				Strings.haveTimerTime,
				Strings.enableBlockBreakEventHaveCheck,
				Strings.enableBlockPlaceEventHaveCheck,
				Strings.enableDropEventHaveCheck,
				Strings.enablePackupEventHaveCheck,
				Strings.enableCraftHaveCheck,
				Strings.enableHaveEventStackSizeCheck,
				Strings.enableEntityDamageEventHaveCheck,
				Strings.enableInventoryEventHaveCheck,
				Strings.enableLeftInteractEventHaveCheck,
				Strings.enableRightInteractEvent_HaveCheck,
				Strings.enableBucketEventFill_HaveCheck,
				Strings.enableBucketEventEmpty_HaveCheck
				
		};
		int i = strings.length;
		for(String s : fileConfiguration.getRoot().getKeys(true)){
			if(contains(strings, s)){
				i--;
			}
		}
		
		if(!FullPluginLoader.isFullPlugin()){
			i -= 3;
		}
		
		
		return i == 0;
	}

	private boolean contains(String[] strings, String s) {
		for(String str : strings){
			if(str.equals(s)){
				return true;
			}
		}
		return false;
	}


	@Override
	public void reload() {
		load();
	}
	
	@Override
	public void createDefault() {
		fileConfiguration.addDefault(Strings.enableMessage, true);
		fileConfiguration.addDefault(Strings.messageLanguage, "en");
		fileConfiguration.addDefault(Strings.messageCooldown, 1000);
		fileConfiguration.addDefault(Strings.enableItemName, true);
		fileConfiguration.addDefault(Strings.enableMetadata, true);
		if(FullPluginLoader.isFullPlugin())
			fileConfiguration.addDefault(Strings.enableNBT, false);
		fileConfiguration.addDefault(Strings.useWorldGuard, true);
		fileConfiguration.addDefault(Strings.enableCheckOPinPrivate, true);
		fileConfiguration.addDefault(Strings.enableCheckPlayerPermissions, false);
		fileConfiguration.addDefault(Strings.enablePermissionsList, true);
		fileConfiguration.addDefault(Strings.opBypassPermissionsList, false);
		if(FullPluginLoader.isFullPlugin())
			fileConfiguration.addDefault(Strings.enablePistonBlackList, true);
		fileConfiguration.addDefault(Strings.haveTimerTime, 0);
		fileConfiguration.addDefault(Strings.enableBlockBreakEventHaveCheck, false);
		fileConfiguration.addDefault(Strings.enableBlockPlaceEventHaveCheck, false);
		fileConfiguration.addDefault(Strings.enableDropEventHaveCheck, false);
		fileConfiguration.addDefault(Strings.enablePackupEventHaveCheck, false);
		fileConfiguration.addDefault(Strings.enableCraftHaveCheck, false);
		if(FullPluginLoader.isFullPlugin())
			fileConfiguration.addDefault(Strings.enableHaveEventStackSizeCheck, true);
		fileConfiguration.addDefault(Strings.enableEntityDamageEventHaveCheck, false);
		fileConfiguration.addDefault(Strings.enableInventoryEventHaveCheck, false);
		fileConfiguration.addDefault(Strings.enableLeftInteractEventHaveCheck, false);	
		fileConfiguration.addDefault(Strings.enableRightInteractEvent_HaveCheck, false);		
		fileConfiguration.addDefault(Strings.enableBucketEventEmpty_HaveCheck, false);		
		fileConfiguration.addDefault(Strings.enableBucketEventFill_HaveCheck, false);
		fileConfiguration.options().copyDefaults(true);
	}

}
