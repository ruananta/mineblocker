package ru.mor.iv.mineblocker.item;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import ru.mor.iv.mineblocker.SignWrapper;
import ru.mor.iv.mineblocker.full.PacketManager;
import ru.mor.iv.mineblocker.item.part.ItemData;
import ru.mor.iv.mineblocker.item.part.ItemNBT;
import ru.mor.iv.mineblocker.item.part.ItemName;

public class EventItemBlock extends EventItem{
	private Block block;
	private EventItem[] nearList;
	
	@SuppressWarnings("deprecation")
	public EventItemBlock(Block block) {
		super(block);
		this.block = block;
		name = new ItemName(block.getType());
		data = new ItemData(block.getData());
		getData();
	}
	
	@Override
	public ItemNBT getNBT() {
		if(nbt == null){
			nbt = new ItemNBT(PacketManager.getNbtTags(block));
		}
		return nbt;
	}
	
	@Override
	public Block getBlock() {
		return block;
	}
	
	@Override
	public Location getLocation() {
		return block.getLocation();
	}
	
	@Override
	public EventItem[] getNearBlocks() {
		if(nearList == null){
			nearList = new EventItem[6];
			BlockFace[] blockFace = getBlockFace();
			for(int i = 0 ; i < nearList.length ; i++){
				Block b = block.getRelative(blockFace[i]);
				if(b.getType() != Material.AIR){
					nearList[i] = new EventItemBlock(b);
				}
			}
		}
		return nearList;
	}
	
	private BlockFace[] getBlockFace() {
		return new BlockFace[]{
				BlockFace.UP, 
				BlockFace.DOWN, 
				BlockFace.EAST, 
				BlockFace.SOUTH, 
				BlockFace.WEST, 
				BlockFace.NORTH
				};
	}
	
	@Override
	public EventItem getBannedNearBlock() {
		if(nearList != null){
			for(EventItem i : nearList){
				if(i != null && i.isBanned()){
					return i;
				}
			}
		}
		return null;
	}
	
	@Override
	public SignWrapper getSign() {
		if(sign == null){
			sign = SignWrapper.searchSing(block);
		}
		return super.getSign();
	}
}
