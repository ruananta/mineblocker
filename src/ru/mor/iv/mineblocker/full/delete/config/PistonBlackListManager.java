package ru.mor.iv.mineblocker.full.delete.config;

import java.util.ArrayList;
import java.util.List;

import ru.mor.iv.mineblocker.config.ConfigFeatures;
import ru.mor.iv.mineblocker.config.string.Strings;
import ru.mor.iv.mineblocker.full.FullPluginLoader;
import ru.mor.iv.mineblocker.full.delete.PistonBlackList;
import ru.mor.iv.mineblocker.item.ListItem;
import ru.mor.iv.mineblocker.permission.Permit;

public class PistonBlackListManager extends ConfigFeatures{
	private PistonBlackList pistonBlackList;
	
	public PistonBlackListManager(String fileName) {
		super(fileName);
		pistonBlackList = FullPluginLoader.getFullPlugin().getPistonBlackList();
	}
	
	@Override
	public void load() {
		for(String cfg : fileConfiguration.getRoot().getKeys(false)){
			List<ListItem> list = new ArrayList<>();
			for(String s : fileConfiguration.getStringList(cfg)){
				ListItem li = new ListItem(s);
				if(li.isValid()){
					list.add(li);
				}	
			}
			if(!list.isEmpty()){
				pistonBlackList.put(cfg, list);
			}
		}
		Permit.PISTON_EXTEND.setEnabled(!pistonBlackList.isEmpty());
		Permit.PISTON_RETRACT.setEnabled(!pistonBlackList.isEmpty());
		
	}
	
	@Override
	public void createDefault() {
		List<String> l = new ArrayList<>();
		l.add("sample");
		l.add("sample:0");
		l.add("sample:0{nbt}");
		fileConfiguration.set(Strings.ALL_WORLDS, l);
		l = new ArrayList<>();
		l.add("sample");
		l.add("sample:0");
		l.add("sample:0{nbt}");
		fileConfiguration.set("world", l);
	}
	
	@Override
	public void reload(){
		pistonBlackList.clear();
		load();
	}

	@Override
	public boolean hasEverything() {
		return file.exists();
	}
}
