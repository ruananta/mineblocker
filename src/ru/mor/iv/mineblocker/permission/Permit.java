package ru.mor.iv.mineblocker.permission;

import ru.mor.iv.mineblocker.config.ConfigHolder;

public enum Permit implements Permission {
	PLACE
		("place", 
		"block_place", 
		"{Prefix} &aВам запрещено &9ставить &4{ItemName}{EndOffers}",
		"{Prefix} &aYou don't have permission to &9place &athe &4{ItemName}{EndOffers}"), 
	PLACE_NEAR
		("place_near", 
		"block_place_near", 
		"{Prefix} &aВам запрещено &9ставить &4{ItemName} &aвозле &4{BlockName}{EndOffers}", 
		"{Prefix} &aYou don't have permission to &9place &athe &4{ItemName} &anear the &4{BlockName}{EndOffers}"), 
	BREAK
		("break", 
		"block_break", 
		"{Prefix} &aВам запрещено &9ломать &4{ItemName}{EndOffers}", 
		"{Prefix} &aYou don't have permission to &9break &athe &4{ItemName}{EndOffers}"), 
	HAVE
		("have", 
		"item_have",
		"{Prefix} &aВам запрещено &9хранить &4{ItemName}{EndOffers}",
		"{Prefix} &aYou don't have permission to &9have &athe &4{ItemName}{EndOffers}"), 
	HAVE_R
		("have_r",
		"item_have_and_remove",
		"{Prefix} &aВам запрещено &9хранить &4{ItemName}{EndOffers}",
		"{Prefix} &aYou don't have permission to &9have &athe &4{ItemName}{EndOffers}"), 
	PACKUP
		("packup",
		"item_packup", 
		"{Prefix} &aВам запрещено &9подбирать &4{ItemName}{EndOffers}", 
		"{Prefix} &aYou don't have permission to &9have &athe &4{ItemName}{EndOffers}"), 
	DROP
		("drop", 
		"item_drop", 
		"{Prefix} &aВам запрещено &9выбрасывать &4{ItemName}{EndOffers}", 
		"{Prefix} &aYou don't have permission to &9drop &athe &4{ItemName}{EndOffers}"), 
	LEFT_CLICK_AIR
		("left_click_air", 
		"left_click_air", 
		"{Prefix} &aВам запрещено использовать &9левую &aкнопку c &4{ItemName}{EndOffers}", 
		"{Prefix} &aYou don't have permission to click the &9left &amouse button with the &4{ItemName}{EndOffers}"), 
	SHIFT_LEFT_CLICK_AIR
		("shift_left_click_air", 
		"shift_left_click_air", 
		"{Prefix} &aВам запрещено использовать &9левую &aкнопку + &9shift&a c &4{ItemName}{EndOffers}", 
		"{Prefix} &aYou don't have permission to click the &9left &amouse button + &9shift&a with the &4{ItemName}{EndOffers}"), 
	LEFT_CLICK_BLOCK
		("left_click_block", 
		"left_click_on_block", 
		"{Prefix} &aВам запрещено использовать &9левую &aкнопку c &4{ItemName} &aна &4{BlockName}{EndOffers}", 
		"{Prefix} &aYou don't have permission to click the &9left &amouse button with a &4{ItemName} &aon the &4{BlockName}{EndOffers}"), 
	SHIFT_LEFT_CLICK_BLOCK
		("shift_left_click_block", 
		"shift_left_click_on_block", 
		"{Prefix} &aВам запрещено использовать &9левую &aкнопку + &9shift &a c &4{ItemName} &aна &4{BlockName}{EndOffers}", 
		"{Prefix} &aYou don't have permission to click the &9left &amouse button + &9shift&a with a &4{ItemName} &aon the &4{BlockName}{EndOffers}"), 
	RIGHT_CLICK_AIR
		("right_click_air", 
		"right_click_air", 
		"{Prefix} &aВам запрещено использовать &9правую &aкнопку c &4{ItemName}{EndOffers}", 
		"{Prefix} &aYou don't have permission to click the &9right &amouse button with the &4{ItemName}{EndOffers}"), 
	SHIFT_RIGHT_CLICK_AIR
		("shift_right_click_air", 
		"shift_right_click_air", 
		"{Prefix} &aВам запрещено использовать &9правую &aкнопку + &9shift&a c &4{ItemName}{EndOffers}", 
		"{Prefix} &aYou don't have permission to click the &9right &amouse button + &9shift&a with the &4{ItemName}{EndOffers}"),  
	RIGHT_CLICK_BLOCK
		("right_click_block", 
		"right_click_on_block", 
		"{Prefix} &aВам запрещено использовать &9правую &aкнопку c &4{ItemName} &aна &4{BlockName}{EndOffers}", 
		"{Prefix} &aYou don't have permission to click the &9right &amouse button with a &4{ItemName} &aon the &4{BlockName}{EndOffers}"), 
	SHIFT_RIGHT_CLICK_BLOCK
		("shift_right_click_block", 
		"shift_right_click_on_block", 
		"{Prefix} &aВам запрещено использовать &9правую &aкнопку + &9shift &a c &4{ItemName} &aна &4{BlockName}{EndOffers}", 
		"{Prefix} &aYou don't have permission to click the &9right &amouse button + &9shift&a with a &4{ItemName} &aon the &4{BlockName}{EndOffers}"),
	PHYSICAL
		("physical", 
		"physical", 
		"{Prefix} &aВам запрещено &9наступать &aна &4{ItemName}{EndOffers}", 
		"{Prefix} &aYou don't have permission to &9step on &athe &4{ItemName}{EndOffers}"), 	
	OPENING
		("opening", 
		"opening", 
		"{Prefix} &aВам запрещено &9открывать &4{BlockName}{EndOffers}", 
		"{Prefix} &aYou don't have permission to &9open &athe &4{BlockName}{EndOffers}"), 		
	DAMAGE		
		("damage", 
		"damage_entity", 
		"{Prefix} &aВам запрещено &9бить &4{ItemName}{EndOffers}", 
		"{Prefix} &aYou don't have permission to &9damage &athe &4{ItemName}{EndOffers}"), 
	INVCLICK_CREATIVE
		("invclick_creative", 
		"invclick_creative", 
		"{Prefix} &aВам запрещено &9перемещать &4{ItemName} &aв творческом режиме игры{EndOffers}", 
		"{Prefix} &aYou don't have permission to &9move &4the {ItemName} &ain the creative game mode {EndOffers}"), 
	INVCLICK_LEFT	
		("invclick_left", 
		"invclick_left", 
		"{Prefix} &aВам запрещено использовать &9левую &aкнопку мыши на &4{ItemName}{EndOffers}", 
		"{Prefix} &aYou don't have permission to click the &9left &amouse button on the &4{ItemName}{EndOffers}"), 
	INVCLICK_SHIFT_LEFT
		("invclick_shift_left", 
		"invclick_shift_left", 
		"{Prefix} &aВам запрещено использовать &9левую &aкнопку мыши + &9shift &aна &4{ItemName}{EndOffers}", 
		"{Prefix} &aYou don't have permission to click the &9left &amouse button + &9shift&a on the &4{ItemName}{EndOffers}"), 
	INVCLICK_RIGHT	
		("invclick_right", 
		"invclick_right", 
		"{Prefix} &aВам запрещено использовать &9правую &aкнопку мыши на &4{ItemName}{EndOffers}", 
		"{Prefix} &aYou don't have permission to click the &9right &amouse button on the &4{ItemName}{EndOffers}"), 
	INVCLICK_SHIFT_RIGHT	
		("invclick_shift_right", 
		"invclick_shift_right", 
		"{Prefix} &aВам запрещено использовать &9правую &aкнопку мыши + &9shift &aна &4{ItemName}{EndOffers}", 
		"{Prefix} &aYou don't have permission to click the &9right &amouse button + &9shift&a on the &4{ItemName}{EndOffers}"),
	INVCLICK_MIDDLE			
		("invclick_middle",
		"invclick_middle", 
		"{Prefix} &aВам запрещено использовать &9среднюю &aкнопку мыши на &4{ItemName}{EndOffers}", 
		"{Prefix} &aYou don't have permission to click the &9middle &amouse button on the &4{ItemName}{EndOffers}"),
	INVCLICK_NUMBER_KEY	
		("invclick_number_key", 
		"invclick_number_key", 
		"{Prefix} &aВам запрещено использовать &9цифры &aна &4{ItemName}{EndOffers}", 
		"{Prefix} &aYou don't have permission to use a &9number-key &aon the &4{ItemName}{EndOffers}"),
	INVCLICK_DOUBLE_CLICK	
		("invclick_double", 
		"invclick_double", 
		"{Prefix} &aВам запрещено использовать &9двойной клик &aмыши на &4{ItemName}{EndOffers}", 
		"{Prefix} &aYou don't have permission to use a &9double-click &amouse button on the &4{ItemName}{EndOffers}"),
	INVCLICK_DROP			
		("invclick_drop", 
		"invclick_drop", 
		"{Prefix} &aВам запрещено &9выбрасывать &4{ItemName}{EndOffers}", 
		"{Prefix} &aYou don't have permission to &9drop &athe &4{ItemName}{EndOffers}"),
	INVCLICK_CONTROL_DROP
		("invclick_control_drop", 
		"invclick_control_drop", 
		"{Prefix} &aВам запрещено &9выбрасывать с зажатым Ctrl &4{ItemName}{EndOffers}", 
		"{Prefix} &aYou don't have permission to &9ctrl-drop &athe &4{ItemName}{EndOffers}"),
	INVCLICK_UNKNOWN
		("invclick_unknown",
		"invclick_unknown", 
		"{Prefix} &aВам запрещено делать это c &4{ItemName}{EndOffers}", 
		"{Prefix} &aYou don't have permission to do it with &4{ItemName}{EndOffers}"),
	DOUBLE_OPENING	
		("double_opening", 
		"inventory_double_opening",
		"{Prefix} &aВам запрещено &9открывать инвентарь &4{ItemName} &aсовместно с другим игроком &4{EndOffers}",
		"{Prefix} &aYou don't have permission to &9open &4{ItemName} &atogether with another player &4{EndOffers}"),
	CRAFT			
		("craft", 
		"craft",
		"{Prefix} &aВам запрещено &9крафтить &4{ItemName}{EndOffers}",
		"{Prefix} &aYou don't have permission to &9craft &4{ItemName}{EndOffers}"), 
	BUCKET_FILL		
		("bucket_fill", 
		"bucket_fill", 
		"{Prefix} &aВам запрещено &9наполнять &aведро &4{ItemName}{EndOffers}", 
		"{Prefix} &aYou don't have permission to &9draw &4{ItemName}{EndOffers}"), 
	BUCKET_EMPTY	
		("bucket_empty", 
		"bucket_empty",
		"{Prefix} &aВам запрещено &9опустошать &aведро с &4{ItemName}{EndOffers}",
		"{Prefix} &aYou don't have permission to &9pour &4{ItemName}{EndOffers}"),
	PISTON_EXTEND		
		("piston_extend", 
		"piston_extend", 
		null, 
		null), 
	PISTON_RETRACT	
		("piston_retract", 
		"piston_retract",
		null, 
		null), 
	ALL	
		("*", 
		"permission_all",
		null,
		null);
	
	
	
	
	private String stringPermission;
	private String configSection;
	private String ruMessage;
	private String enMessage;
	private String message;
	private boolean enabled;
	private boolean forWhatEnabled;
	
	private Permit(String defaultPermission, String configPerm, String ruMessage, String enMessage) {
		this.configSection = configPerm;
		this.stringPermission = defaultPermission;
		this.ruMessage = ruMessage;
		this.enMessage = enMessage;
		this.message = null;
		this.enabled = false;
	}
	
	@Override
	public boolean isEnabled() {
		return enabled;
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	@Override
	public boolean isForWhatEnabled() {
		return forWhatEnabled;
	}
	@Override
	public void setForWhatEnabled(boolean forWhatEnabled) {
		this.forWhatEnabled = forWhatEnabled;
	}
	@Override
	public String getStringPermission() {
		return stringPermission;
	}
	@Override
	public void setStringPermission(String stringPermission) {
		this.stringPermission = stringPermission;
	}
	@Override
	public String getConfigSection() {
		return configSection;
	}
	
	@Override
	public String getRuMessage() {
		return ruMessage;
	}
	
	@Override
	public String getEnMessage() {
		return enMessage;
	}
	
	@Override
	public String getMessage() {
		if(message == null){
			if(ConfigHolder.getMessageLanguage().equalsIgnoreCase("ru")){
				return ruMessage;
			}else{
				return enMessage;
			}
		}
		return message;
	}
	
	@Override
	public void setMessage(String message) {
		this.message = message;
	}
	
	@Deprecated
	public String toString(){
		return getStringPermission();
	}
	
	public static class Strings {
		public static String 
		ALL = "*",
		BYPASS_CHECK = "MineBlockerUtils.bypass",
		MINEBLOCKER = "mineblocker.",
		COMMAND = "MineBlockerUtils.operator";
	
	}
}
