package ru.mor.iv.mineblocker.managers.privates;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import ru.mor.iv.mineblocker.config.ConfigHolder;
import ru.mor.iv.mineblocker.event.ActionEvent;
import ru.mor.iv.mineblocker.item.EventItem;

public class WorldGuard5Manager implements PrivatePlugin{
	private WorldGuardPlugin wg;
	private IgnoreRegionsMap worldGuardIgnoreRegions;
	
	
	public WorldGuard5Manager() {
		wg = getWorldGuard();
		worldGuardIgnoreRegions = new IgnoreRegionsMap();
	}

	private WorldGuardPlugin getWorldGuard() {
	    Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
	    if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
	        return null;
	    }
	    return (WorldGuardPlugin) plugin;
	}
	@Override
	public boolean canBuild(ActionEvent event) {
		if(event.getActor() == null){
			return !isInRegion(event.getUsedItems());
		}
		int range = event.getCancel().getPrivateRange();
		if(range != 0){
			for(Location loc : event.getActionLocations()){
				if(!canBuild(event.getActor(), loc, range)){
					return false;
				}
			}
		}
		for(Location loc : event.getActionLocations()){
			if(!canBuild(event.getActor(), loc)){
				return false;
			}
		}
		return true;
	}

	private boolean isInRegion(EventItem[] usedItems) {
		if(usedItems != null)
			for(EventItem e : usedItems){
				if(!getRegions(e.getLocation()).isEmpty()){
					return true;
				}
			}
		return false;
	}

	private boolean canBuild(Player player, Location loc) {
		if(ConfigHolder.isCheckOPinPrivate()){
			LocalPlayer lp = wg.wrapPlayer(player);
			ApplicableRegionSet set = wg.getRegionManager(loc.getWorld()).getApplicableRegions(loc);
			Iterator<ProtectedRegion> i = set.iterator();
			while(i.hasNext()){
				ProtectedRegion rg = i.next();
				if(worldGuardIgnoreRegions.contains(loc.getWorld().getName(), rg.getId()) || isMember(rg, lp)){
					return true;
				}
			}
			if(set.size() != 0) return false;
			return true;
		}else{
			ApplicableRegionSet set = wg.getRegionManager(loc.getWorld()).getApplicableRegions(loc);
			Iterator<ProtectedRegion> i = set.iterator();
			while(i.hasNext()){
				ProtectedRegion rg = i.next();
				if(worldGuardIgnoreRegions.contains(loc.getWorld().getName(), rg.getId())){
					return true;
				}
			}
		}
		return wg.canBuild(player, loc);
	}
	
	private boolean canBuild(Player player, Location loc, int range) {
		int yMin = loc.getBlockY() - range;
		int yMax = loc.getBlockY() + range;
		if(yMax > 256){
			yMax = 256;
		}
		if(yMin < 0){
			yMin = 0;
		}
		ProtectedCuboidRegion pcr = new ProtectedCuboidRegion("MineBlockerRangeTest", new BlockVector(loc.getBlockX()-range, yMin, loc.getBlockZ()-range),
				new BlockVector(loc.getBlockX()+range, yMax, loc.getBlockZ()+range));
		ApplicableRegionSet set = wg.getRegionManager(loc.getWorld()).getApplicableRegions(pcr);
		
		if(ConfigHolder.isCheckOPinPrivate()){
			LocalPlayer lp = wg.wrapPlayer(player);
			Iterator<ProtectedRegion> i = set.iterator();
			while(i.hasNext()){
				ProtectedRegion rg = i.next();
				if(worldGuardIgnoreRegions.contains(loc.getWorld().getName(), rg.getId()) || isMember(rg, lp)){
					return true;
				}
			}
			if(set.size() != 0) return false;
			return true;
		}else{
			Iterator<ProtectedRegion> i = set.iterator();
			while(i.hasNext()){
				ProtectedRegion rg = i.next();
				if(worldGuardIgnoreRegions.contains(loc.getWorld().getName(), rg.getId())){
					return true;
				}
			}
		}
		Iterator<ProtectedRegion> i = set.iterator();
		while(i.hasNext()){
			ProtectedRegion rg = i.next();
			if(!wg.canBuild(player, new Location(loc.getWorld(), rg.getMaximumPoint().getBlockX(), rg.getMaximumPoint().getBlockY(), rg.getMaximumPoint().getBlockZ()))) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public boolean outsidePrivate(ActionEvent event) {
		if(event.getActor() == null){
			return isOutsideRegion(event.getActionLocations());
		}else{
			for(Location loc : event.getActionLocations()){
				if(outsidePrivate(event.getActor(), loc)){
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean isOutsideRegion(Location[] locations) {
		if(locations != null)
			for(Location e : locations){
				if(getRegions(e).isEmpty()){
					return true;
				}
			}
		return false;
	}

	private boolean outsidePrivate(Player player, Location loc) {
		ApplicableRegionSet set = wg.getRegionManager(player.getWorld()).getApplicableRegions(loc);
		if (set.size() < 1){
            return true;
        }else{
        	LocalPlayer lp = wg.wrapPlayer(player);
        	Iterator<ProtectedRegion> i = set.iterator();
			while(i.hasNext()){
				ProtectedRegion rg = i.next();
        		if(isMember(rg, lp)){
					return false;
				}
            }
		
		return true;
        }
	}
	
	@Override
	public List<String> getRegions(Location location) {
		List<String> l = new ArrayList<>();
		ApplicableRegionSet set = wg.getRegionManager(location.getWorld()).getApplicableRegions(location);
		Iterator<ProtectedRegion> i = set.iterator();
		while(i.hasNext()){
			ProtectedRegion rg = i.next();
			l.add(rg.getId());
		}
		return l;
	}
	
	private boolean isMember(ProtectedRegion rg, LocalPlayer lp){
		if(rg.isMember(lp) || rg.isOwner(lp)){
			return true;
		}
        return false;
	}
	
	@Override
	public List<String> getRegions() {
		List<String> l = new ArrayList<>();
		for(World w : Bukkit.getServer().getWorlds()){
			l.addAll(wg.getRegionManager(w).getRegions().keySet());
		}
		return l;
	}
	
	
	@Override
	public void reload() {
		
	}

	@Override
	public List<String> getRegions(Location[] locations) {
		List<String> l = new ArrayList<>();
		for(Location loc : locations){
			l.addAll(getRegions(loc));
		}
		return l;
	}

}
