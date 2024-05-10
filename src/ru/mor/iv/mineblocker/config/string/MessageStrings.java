package ru.mor.iv.mineblocker.config.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MessageStrings {
	public static String
	PREFIX = 			"prefix",
	INFORM =			"inform",
	INFORM_BLOCK = 		"informBlock",
	INFORM_ENTITY = 	"informEntity",
	INFORM_INVENTORY = 	"informInventory",
	RELOAD = 			"reload",
	END_WORLD = 		"world_end_offers",
	END_GAMEMODE = 		"gamemode_end_offers",
	END_REGION = 		"region_end_offers",
	END_RANGE_REGION = 	"range_region_end_offers",
	END_OUTSIDE = 		"outside_end_offers",
	SENDER_ERROR = 		"sender_error",
	INVENTORY_OPEN = 	"inventory_open",
	INVENTORY_CLOSE= 	"inventory_close";
	
	
	public static List<String> values() {
		return new ArrayList<>(Arrays.asList(new String[] { 
				PREFIX, 
				INFORM, 
				INFORM_BLOCK, 
				INFORM_ENTITY,
				INFORM_INVENTORY, 
				RELOAD, 
				END_WORLD,
				END_GAMEMODE, 
				END_RANGE_REGION, 
				END_REGION, 
				END_OUTSIDE,
				SENDER_ERROR,
				INVENTORY_OPEN,
				INVENTORY_CLOSE
				}));
	}
}
