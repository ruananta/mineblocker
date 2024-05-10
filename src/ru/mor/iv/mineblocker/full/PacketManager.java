package ru.mor.iv.mineblocker.full;

import java.io.IOException;

import org.bukkit.Bukkit;

import ru.mor.iv.mineblocker.MineBlockerPlugin;
import ru.mor.iv.mineblocker.config.string.Strings;
import ru.mor.iv.mineblocker.full.delete.packet.PacketUtilsInterface;

public class PacketManager {

	private static PacketUtilsInterface packetUtils;

	public static boolean init() {
		String nmspackageversion = getNmsPackageVersion();
		if (!initPacketUtils(nmspackageversion)) {
			MineBlockerPlugin.debug("[MineBlocker] server version " + nmspackageversion + " is not supported.");
			return false;
		}
		MineBlockerPlugin.debug("[MineBlocker] found the server version " + nmspackageversion + ".");
		return true;
	}

	private static boolean initPacketUtils(String nmspackageversion) {
		try {
			String versioned = PacketManager.class.getPackage().getName() + ".delete.packet." + nmspackageversion + ".";
			packetUtils = (PacketUtilsInterface) Class.forName(versioned + "PacketUtils").newInstance();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private static String getNmsPackageVersion() {
		String packageName = Bukkit.getServer().getClass().getPackage().getName();
		String nmspackageversion = packageName.substring(packageName.lastIndexOf('.') + 1);
		return nmspackageversion;
	}

	public static String getNbtTags(org.bukkit.inventory.ItemStack item) {
		if (packetUtils == null) {
			return Strings.NULL_NBT;
		}
		return packetUtils.getTags(item);
	}

	public static String getNbtTags(org.bukkit.block.Block block) {
		if (packetUtils == null) {
			return Strings.NULL_NBT;
		}
		return packetUtils.getTags(block);
	}

	public static String getOpenInventoryName(org.bukkit.entity.Player player) {
		if (packetUtils == null) {
			return Strings.NULL_NBT;
		}
		String name = packetUtils.getOpenInventoryName(player);
		name = name.replace(".", "_");
		name = name.replace(" ", "_");
		return name;
	}

	public static boolean hasInventory(org.bukkit.block.Block b) {
		if (packetUtils == null) {
			return false;
		}
		return packetUtils.hasInventory(b);
	}

	public static boolean hasInventory(org.bukkit.entity.Entity e) {
		if (packetUtils == null) {
			return false;
		}
		return packetUtils.hasInventory(e);
	}

	public static org.bukkit.inventory.Inventory fromString(String s) throws IOException {
		if (packetUtils == null) {
			return MineBlockerPlugin.getInst().getServer().createInventory(null, 9);
		}
		return packetUtils.fromString(s);
	}

	public static String toString(org.bukkit.inventory.ItemStack[] itemStacks) throws IOException {
		if (packetUtils == null) {
			return Strings.NULL_NBT;
		}
		return packetUtils.toString(itemStacks);
	}
}
