package ru.mor.iv.mineblocker.full.delete;

import ru.mor.iv.mineblocker.config.Config;
import ru.mor.iv.mineblocker.config.ConfigHolder;
import ru.mor.iv.mineblocker.config.General;
import ru.mor.iv.mineblocker.config.MessageConfigsHolder;
import ru.mor.iv.mineblocker.config.SwitchConfig;
import ru.mor.iv.mineblocker.full.delete.config.DefaultPermissions;
import ru.mor.iv.mineblocker.full.delete.config.PistonBlackListManager;
import ru.mor.iv.mineblocker.full.delete.config.WorldGuardIgnoreRegions;

public class FullConfigHolder extends ConfigHolder {
	
	
	
	public Config[] getConfigs(){
		return new Config[]{
				new General(generalConfigName),
				new MessageConfigsHolder(),
				new DefaultPermissions(defaultPermissionsName),
				new SwitchConfig(switchName),
				new WorldGuardIgnoreRegions(worldGuardIgnoreRegionsName),
				new PistonBlackListManager(pistonBlackListName),
		};
	}
}
