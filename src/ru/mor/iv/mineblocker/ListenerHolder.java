package ru.mor.iv.mineblocker;

import ru.mor.iv.mineblocker.listener.BreakListener;
import ru.mor.iv.mineblocker.listener.BucketListener_empty;
import ru.mor.iv.mineblocker.listener.BucketListener_fill;
import ru.mor.iv.mineblocker.listener.CraftListener;
import ru.mor.iv.mineblocker.listener.DropListener;
import ru.mor.iv.mineblocker.listener.EntityAtListener;
import ru.mor.iv.mineblocker.listener.EntityDamageListener;
import ru.mor.iv.mineblocker.listener.EntityInteractListener;
import ru.mor.iv.mineblocker.listener.HaveListener;
import ru.mor.iv.mineblocker.listener.InteractListener;
import ru.mor.iv.mineblocker.listener.InventoryDoubleOpeningListener;
import ru.mor.iv.mineblocker.listener.InventoryListener;
import ru.mor.iv.mineblocker.listener.ItemRemoverListener;
import ru.mor.iv.mineblocker.listener.MineBlockerListener;
import ru.mor.iv.mineblocker.listener.PickupListener;
import ru.mor.iv.mineblocker.listener.PistonListener;
import ru.mor.iv.mineblocker.listener.PlaceListener;
import ru.mor.iv.mineblocker.listener.PlaceNearListener;
import ru.mor.iv.mineblocker.listener.ActionListener;
import ru.mor.iv.mineblocker.permission.Permission;

public class ListenerHolder {
	
	private static HaveListener haveListener;
	private static MineBlockerListener[] listeners;
	
	public static void init(){
		haveListener = new HaveListener();
		MineBlockerListener entityInteractListener = null;
		try {
			Class.forName("org.bukkit.event.player.PlayerInteractAtEntityEvent");
			entityInteractListener = new EntityAtListener();
		} catch (ClassNotFoundException e) {
			entityInteractListener = new EntityInteractListener();
		}
		listeners = new MineBlockerListener[] {
				new BreakListener(),
				new BucketListener_empty(),
				new BucketListener_fill(),
				new CraftListener(),
				new DropListener(),
				entityInteractListener,
				new EntityDamageListener(),
				new HaveListener(),
				new InteractListener(),
				new InventoryDoubleOpeningListener(),
				new InventoryListener(),
				new ItemRemoverListener(),
				new PickupListener(),
				new PistonListener(),
				new PlaceListener(),
				new PlaceNearListener(),
				new ActionListener()
		};
	}
	
	public static void load() {
		for(MineBlockerListener f : listeners){
			boolean e = false;
			for(Permission p : f.getUsedPermissions()){
				if(p.isEnabled()){
					e = true;
				}
			}
			if(e && !f.isRegisterred()){
				f.register();
			}
			if(!e && f.isRegisterred()){
				f.unregister();
			}
		}
	}
	
	public static HaveListener getHaveListener() {
		return haveListener;
	}
	
	public MineBlockerListener[] getListeners(){
		return listeners;
	}
}
