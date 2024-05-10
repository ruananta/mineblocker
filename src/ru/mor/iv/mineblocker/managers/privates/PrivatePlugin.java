package ru.mor.iv.mineblocker.managers.privates;

import java.util.List;

import org.bukkit.Location;

import ru.mor.iv.mineblocker.event.ActionEvent;

public interface PrivatePlugin {
	//public boolean canBuild(Player player, Location loc);

	//public boolean outsidePrivate(Player player, Location loc);

	public boolean canBuild(ActionEvent event);

	public boolean outsidePrivate(ActionEvent event);
	
	public List<String> getRegions(Location location);
	
	public List<String> getRegions(Location[] locations);
	
	public void reload();

	public List<String> getRegions();

	
}
