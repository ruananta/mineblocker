package ru.mor.iv.mineblocker.item;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import ru.mor.iv.mineblocker.Utils;
import ru.mor.iv.mineblocker.config.ConfigHolder;
import ru.mor.iv.mineblocker.config.string.Strings;
import ru.mor.iv.mineblocker.item.part.DataType;
import ru.mor.iv.mineblocker.item.part.ItemData;
import ru.mor.iv.mineblocker.item.part.ItemNBT;
import ru.mor.iv.mineblocker.item.part.ItemName;

public class ListItem extends Item {

	private ItemNBT includeNBT = new ItemNBT();
	private ItemNBT includeAllNBT = new ItemNBT();
	private ItemNBT notIncludeNBT = new ItemNBT();

	private boolean isValid;
	private int range;

	public ListItem(String string) {
		super(string);
		if(string.startsWith("!")){
			not = true;
			string = string.substring(1, string.length());
		}
		isValid = false;
		range = 0;
		string = purgeNBT(string);
		if (string == null)
			return;
		data = new ItemData();
		if (string.contains(Strings.ITEM_DELIMETER)) {
			String[] args = string.split(Strings.ITEM_DELIMETER);
			if (args.length == 2) {
				name = new ItemName(args[0]);
				isValid = true;
				data.setData(getData(args[1]));
			} else if (args.length > 2) {
				name = new ItemName(args[0]);
				isValid = true;
				data.setData(DataType.MULTI_FLAG);
				int[] multiData = new int[args.length - 1];
				for (int i = 1; i < args.length; i++) {
					multiData[i - 1] = getData(args[i]);
				}
				data.setMultiData(multiData);
			}
		} else {
			name = new ItemName(string);
			data.setData(DataType.SKIP);
			isValid = true;
		}
		if (isValid)
			loadMaterial();
	}

	@SuppressWarnings("deprecation")
	private void loadMaterial() {
		String s = new String(name.getName());
		s = s.toUpperCase();
		if (ConfigHolder.isNameEnabled()) {
			try {
				name.setType(Material.valueOf(s));
			} catch (IllegalArgumentException e) {
			}

		} else {
			try {
				name.setType(Material.getMaterial(Integer.parseInt(name.getName())));
			} catch (NumberFormatException e) {
			}
		}

		if (name.getType() != null)
			return;
		try {
			name.setType(EntityType.valueOf(s));
		} catch (IllegalArgumentException e) {
		}
	}

	private int getData(String string) {
		if (string.equals(Strings.SKIP)) {
			return DataType.SKIP;
		} else {
			try {
				return Integer.parseInt(string);
			} catch (NumberFormatException e) {
				isValid = false;
			}
		}
		return 0;
	}

	private String purgeNBT(String p) {
		if (p.contains("{")) {
			if (p.endsWith("}")) {
				p = p.substring(0, p.length() - 1);
			}
			// p = p.replace(" ", "");
			p = p.replace("MB_Point", ".");
			String[] args = p.split("\\{", 2);
			if (args.length != 2 || args[1].equals(""))
				return null;
			p = args[0];
			String nbt = args[1];
			if (nbt.contains(",")) {
				String[] args_n = nbt.split("\\,");
				for (String n : args_n) {
					addNBT(n);
				}
			} else {
				addNBT(nbt);
			}
		}
		return p;
	}

	private void addNBT(String n) {
		if (!n.startsWith("!") && !n.startsWith("+")) {
			includeNBT.setMultiNbt(Utils.addString(includeNBT.getMultiNbt(), n));
		} else if (n.startsWith("+")) {
			includeAllNBT.setMultiNbt(Utils.addString(includeAllNBT.getMultiNbt(), n.replace("+", "")));
		} else if (n.startsWith("!")) {
			notIncludeNBT.setMultiNbt(Utils.addString(notIncludeNBT.getMultiNbt(), n.replace("!", "")));
		}
	}

	public ItemNBT getIncludeNBT() {
		return includeNBT;
	}

	public ItemNBT getIncludeAllNBT() {
		return includeAllNBT;
	}

	public ItemNBT getNotIncludeNBT() {
		return notIncludeNBT;
	}

	public boolean isValid() {
		return isValid;
	}

	public boolean isEmptyNbt() {
		if (includeNBT.isEmpty() && includeAllNBT.isEmpty() && notIncludeNBT.isEmpty()) {
			return true;
		}
		return false;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public int getRange() {
		return range;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getName().getName());
		sb.append(Strings.ITEM_DELIMETER);
		sb.append(getData().getData());

		if (!isEmptyNbt()) {
			sb.append(Strings.LEFT_NBT);
			int i1 = includeNBT.size();
			int i2 = includeAllNBT.size();
			int i3 = notIncludeNBT.size();
			int i = i1 + i2 + i3;
			if (includeNBT.getMultiNbt() != null) {
				for (String n : includeNBT.getMultiNbt()) {
					i--;
					sb.append(n);
					if (i != 0)
						sb.append(",");
				}
			}
			if (includeAllNBT.getMultiNbt() != null) {
				for (String n : includeAllNBT.getMultiNbt()) {
					i--;
					sb.append("+");
					sb.append(n);
					if (i != 0)
						sb.append(",");
				}
			}
			if (notIncludeNBT.getMultiNbt() != null) {
				for (String n : notIncludeNBT.getMultiNbt()) {
					i--;
					sb.append("!");
					sb.append(n);
					if (i != 0)
						sb.append(",");
				}
			}
			sb.append(Strings.RIGHT_NBT);
		}
		return sb.toString();
	}

}
