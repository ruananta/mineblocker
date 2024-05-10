package ru.mor.iv.mineblocker.config;

import ru.mor.iv.mineblocker.config.string.MessageStrings;
import ru.mor.iv.mineblocker.permission.Permission;
import ru.mor.iv.mineblocker.permission.PermissionHolder;

public class MessageRU extends Message {
	
	public MessageRU(String fileName) {
		super(fileName);
	}

	@Override
	public void createDefault() {
		fileConfiguration.addDefault(MessageStrings.PREFIX, "&f[&2MineBlocker&f]&4");
		fileConfiguration.addDefault(MessageStrings.INFORM, "{Prefix} &aУ Вас в руках &4{Name}");
		fileConfiguration.addDefault(MessageStrings.INFORM_BLOCK, "{Prefix} &aВы смотрите на &4{Name}");
		fileConfiguration.addDefault(MessageStrings.INFORM_ENTITY, "{Prefix} &aТак же рядом с блоком ентити &4{Name}");
		fileConfiguration.addDefault(MessageStrings.INFORM_INVENTORY, "{Prefix} &aУ вас открыт инвентарь с именем &4{Name}");
		fileConfiguration.addDefault(MessageStrings.RELOAD, "{Prefix} &aПлагин перезагружен!");
		fileConfiguration.addDefault(MessageStrings.END_REGION, " &aв этом регионе");
		fileConfiguration.addDefault(MessageStrings.END_RANGE_REGION, " &aв этом регионе или возле него");
		fileConfiguration.addDefault(MessageStrings.END_OUTSIDE, " &aза пределами своего региона");
		fileConfiguration.addDefault(MessageStrings.END_WORLD, " &aв этом мире");
		fileConfiguration.addDefault(MessageStrings.END_GAMEMODE, " &aв этом режиме игры");
		fileConfiguration.addDefault(MessageStrings.SENDER_ERROR, "{Prefix} This command is only for the player");
		fileConfiguration.addDefault(MessageStrings.INVENTORY_OPEN, "{Prefix} &aВы включили информацию об открытом инвентаре.");
		fileConfiguration.addDefault(MessageStrings.INVENTORY_CLOSE, "{Prefix} &aВы отключили информацию об открытом инвентаре.");
		for (Permission p : PermissionHolder.getPermissions()) {
			fileConfiguration.addDefault(p.getStringPermission(), p.getRuMessage());
		}
		fileConfiguration.options().copyDefaults(true);
	}

	

}
