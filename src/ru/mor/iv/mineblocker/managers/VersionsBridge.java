package ru.mor.iv.mineblocker.managers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.BlockIterator;

import ru.mor.iv.mineblocker.item.EventItem;
import ru.mor.iv.mineblocker.item.EventItemBlock;
import ru.mor.iv.mineblocker.item.EventItemStack;

public class VersionsBridge {
	private static boolean twoItems = false;
	private static boolean moreRetractBlocks = false;
	private static Method getOnlinePlayers;
	private static boolean listType;
	
	public static void init() {
		for(Method m : PlayerInventory.class.getMethods()){
			if(m.getName().equals("getItemInMainHand")){
				twoItems = true;
			}
		}
		for(Method m : BlockPistonRetractEvent.class.getMethods()){
			if(m.getName().equals("getBlocks")){
				moreRetractBlocks = true;
			}
		}
		try {
			getOnlinePlayers = Bukkit.getServer().getClass().getMethod("getOnlinePlayers");
			Class<?> returnType = getOnlinePlayers.getReturnType();
			if(returnType == List.class){
				listType = true;
			}else{
				listType = false;
			}
		} catch (NoSuchMethodException | SecurityException e) {
		}
	}
	
	@SuppressWarnings("deprecation")
	public static EventItem[] getItemsHand(Player player) {
		EventItem[] e;
		if(twoItems){
			e = new EventItem[2];
			e[0] = (new EventItemStack(player.getInventory().getItemInMainHand()));
			e[1] = (new EventItemStack(player.getInventory().getItemInOffHand()));
		}else{
			e = new EventItem[]{new EventItemStack(player.getItemInHand())};
		}
		return e;
	}
	
	@SuppressWarnings("deprecation")
	public static List<EventItem> getRetractBlocks(BlockPistonRetractEvent event) {
		List<EventItem> l = new ArrayList<>();
		if(moreRetractBlocks){
			for(Block b : event.getBlocks()){
				if(b != null){
					l.add(new EventItemBlock(b));
				}
			}
		}else{
			l.add(new EventItemBlock(event.getRetractLocation().getBlock()));
		}
		return l;
	}
	
	public static Block getTarget(Player player, Integer range) {
        BlockIterator iter = new BlockIterator(player, range);
        Block lastBlock = iter.next();
        while (iter.hasNext()) {
            lastBlock = iter.next();
            if (lastBlock.getType() == Material.AIR)
                continue;
            break;
        }
        return lastBlock;
    }
	
	public static String getPlayerOpenInventory(Player player){
		String name = null;
		InventoryView iv = player.getOpenInventory();
		if(iv != null){
			name = iv.getTopInventory().getName();
			if(name != null){
				name = name.replace(".", "_");
				name = name.replace(" ", "_");
			}
		}
		return name;
	}

	public static boolean removeItem(Player player, ItemStack item) {
		if(twoItems){
			if(player.getInventory().getItemInOffHand().equals(item)){
				player.getInventory().setItemInOffHand(null);
				return true;
			}
		}
		return false;
	}

	public static ItemStack getItemInOffHand(PlayerInventory inventory) {
		if(twoItems){
			return inventory.getItemInOffHand();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static List<Player> getOnlinePlayers(){
		List<Player> list = null;
		try {
			if(listType){
					list = (List<Player>) getOnlinePlayers.invoke(Bukkit.getServer());
			}else{
					list = new ArrayList<>();
					Player[] c = (Player[]) getOnlinePlayers.invoke(Bukkit.getServer());
					for(Player p : c){
						list.add(p);
					}
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return list;
	}

	
	
}
