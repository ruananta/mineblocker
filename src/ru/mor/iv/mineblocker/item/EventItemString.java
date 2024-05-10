package ru.mor.iv.mineblocker.item;

import ru.mor.iv.mineblocker.config.string.Strings;
import ru.mor.iv.mineblocker.item.part.DataType;
import ru.mor.iv.mineblocker.item.part.ItemData;
import ru.mor.iv.mineblocker.item.part.ItemNBT;
import ru.mor.iv.mineblocker.item.part.ItemName;

public class EventItemString extends EventItem{
	
	public EventItemString(String string) {
		super(string);
		name = new ItemName(string);
		data = new ItemData(DataType.SKIP);
	}

	@Override
	public ItemNBT getNBT() {
		return new ItemNBT(Strings.NULL_NBT);
	}

}
