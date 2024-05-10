package ru.mor.iv.mineblocker.managers.privates;

import java.util.ArrayList;
import java.util.List;

import net.sacredlabyrinth.Phaed.PreciousStones.PreciousStones;
import net.sacredlabyrinth.Phaed.PreciousStones.vectors.Field;
import ru.mor.iv.mineblocker.event.ActionEvent;

import org.bukkit.Location;
import org.bukkit.entity.Player;



public class PreciousStonesManager implements PrivatePlugin{

	@Override
	public List<String> getRegions(Location[] locations) {
		List<String> l = new ArrayList<>();
		for(Location loc : locations){
			l.addAll(getRegions(loc));
		}
		return l;
	}
	
	@Override
	public List<String> getRegions(Location loc) {
		List<String> l = new ArrayList<>();
		
		List<Field> list = PreciousStones.getInstance().getForceFieldManager().getFields("*", loc.getWorld());
		for(Field f : list){
			if(f.envelops(loc)){
				l.add(f.getName());
			}
		}
		return l;
	}
	
	public boolean canBuild(Player player, Location loc) {
		List<Field> list = PreciousStones.getInstance().getForceFieldManager().getFields("*", loc.getWorld());
		if(!list.isEmpty()){
			for(Field f : list){
				if(f.envelops(loc) && !isMember(f, player)){
					return false;
				}
			}
		}

		return true;
	}

	private boolean isMember(Field f, Player player) {
		if(f.isOwner(player.getName()) || f.isAllowed(player.getName())){
			return true;
		}
		return false;
	}

	public boolean outsidePrivate(Player player, Location loc) {
		List<Field> list = PreciousStones.getInstance().getForceFieldManager().getFields("*", loc.getWorld());
		if(!list.isEmpty()){
			for(Field f : list){
				if(f.envelops(loc) && isMember(f, player)){
					return false;
				}
			}
		}

		return true;
	}

	public boolean canBuild(ActionEvent event) {
		for(Location loc : event.getActionLocations()){
			if(!canBuild(event.getActor(), loc)){
				return false;
			}
		}
		return true;
	}

	public boolean outsidePrivate(ActionEvent event) {
		for(Location loc : event.getActionLocations()){
			if(outsidePrivate(event.getActor(), loc)){
				return true;
			}
		}
		return false;
	}

	@Override
	public void reload() {
		
	}

	@Override
	public List<String> getRegions() {
		
		return new ArrayList<>();
	}

	

	
}
