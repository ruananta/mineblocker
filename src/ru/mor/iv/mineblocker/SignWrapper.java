package ru.mor.iv.mineblocker;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;

public class SignWrapper {

	private Sign sign;

	private SignWrapper(Sign sign) {
		this.sign = sign;
	}

	public boolean contains(String text) {
		for (String t : sign.getLines()) {
			if (t.equalsIgnoreCase(text)) {
				return true;
			}
		}
		return false;
	}
	
	public String[] getLines(){
		return sign.getLines();
	}

	public static SignWrapper searchSing(Block center) {
		Sign sign = searchSignAroundBlock(center);
		if (sign != null) {
			return new SignWrapper(sign);
		}
		return null;
	}

	public static Sign searchSignAroundBlock(Block center){
	    Block side = null;
	    side = center.getRelative(0, 1, 0);
	    if (isSign(side)) {
	      return (Sign)side.getState();
	    }
	    side = center.getRelative(1, 0, 0);
	    if (isSign(side) && blockProtectedSign(center, side)) {
	      return (Sign)side.getState();
	    }
	    side = center.getRelative(0, 0, 1);
	    if (isSign(side) && blockProtectedSign(center, side)) {
	      return (Sign)side.getState();
	    }
	    side = center.getRelative(-1, 0, 0);
	    if (isSign(side) && blockProtectedSign(center, side)) {
	      return (Sign)side.getState();
	    }
	    side = center.getRelative(0, 0, -1);
	    if (isSign(side) && blockProtectedSign(center, side)) {
	      return (Sign)side.getState();
	    }
	    side = center.getRelative(0, -1, 0);
	    if (isSign(side)) {
	      return (Sign)side.getState();
	    }
	    side = center.getRelative(0, -2, 0);
	    if (isSign(side)) {
	      return (Sign)side.getState();
	    }
	    return null;
	  }

	

	public static boolean isSign(Block b) {
		if ((b == null) || (b.getType() != Material.WALL_SIGN) && (b.getType() != Material.SIGN_POST)) {
			return false;
		}
		BlockState state = b.getState();
		if ((state == null) || (!(state instanceof Sign))) {
			return false;
		}
		return true;
	}
	
	private static boolean blockProtectedSign(Block block, Block sign) {
		if(sign.getType() == Material.SIGN_POST){
			return true;
		}
		Block protect = getProtectedBlockBehindSign(sign);
		if(protect != null){
			return block.equals(protect);
		}
		return true;
	}

	@SuppressWarnings("deprecation")
	public static Block getProtectedBlockBehindSign(Block sign) {
		switch (sign.getData()) {
		case 2:
			return sign.getRelative(BlockFace.SOUTH);
		case 3:
			return sign.getRelative(BlockFace.NORTH);
		case 4:
			return sign.getRelative(BlockFace.EAST);
		case 5:
			return sign.getRelative(BlockFace.WEST);
		}
		return null;
	}
	
	public static SignWrapper searchSing(Entity center) {
		Sign sign = searchSignAroundEntity(center);
		if (sign != null) {
			return new SignWrapper(sign);
		}
		return null;
	}

	private static Sign searchSignAroundEntity(Entity center) {
		Block centerBlock = center.getLocation().getBlock();
		Block side = null;
		side = centerBlock.getRelative(0, 1, 0);
		if (isSign(side)) {
			return (Sign) side.getState();
		}
		side = centerBlock.getRelative(0, 0, 0);
		if (isSign(side)) {
			return (Sign) side.getState();
		}
		side = centerBlock.getRelative(1, 0, 0);
		if (isSign(side)) {
			return (Sign) side.getState();
		}
		side = centerBlock.getRelative(0, 0, 1);
		if (isSign(side)) {
			return (Sign) side.getState();
		}
		side = centerBlock.getRelative(-1, 0, 0);
		if (isSign(side)) {
			return (Sign) side.getState();
		}
		side = centerBlock.getRelative(0, 0, -1);
		if (isSign(side)) {
			return (Sign) side.getState();
		}
		side = centerBlock.getRelative(0, -2, 0);
		if (isSign(side)) {
			return (Sign) side.getState();
		}
		return null;
	}
}
