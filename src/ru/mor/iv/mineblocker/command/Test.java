package ru.mor.iv.mineblocker.command;

import java.util.Date;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import ru.mor.iv.mineblocker.MineBlockerPlugin;
import ru.mor.iv.mineblocker.event.ActionEvent;
import ru.mor.iv.mineblocker.event.CancelType;
import ru.mor.iv.mineblocker.event.PlayerActionEvent;
import ru.mor.iv.mineblocker.item.EventItemBlock;
import ru.mor.iv.mineblocker.item.EventItemStack;
import ru.mor.iv.mineblocker.item.ListItem;
import ru.mor.iv.mineblocker.managers.VersionsBridge;
import ru.mor.iv.mineblocker.permission.Permission;
import ru.mor.iv.mineblocker.permission.PermissionHolder;
import ru.mor.iv.mineblocker.permission.Permit;
import ru.mor.iv.mineblocker.permission.list.PermissionLine;
import ru.mor.iv.mineblocker.permission.list.PermissionSection;
import ru.mor.iv.mineblocker.permission.list.Permissions;
import ru.mor.iv.mineblocker.permission.list.PermissionsList;

public class Test extends Argument {
	private PermissionsList permissionsList;
	private Player player;
	private int fakeLine = 1000;
	private boolean testFromPermissionsPlugin;
	private double version = 0.2;

	public Test() {
		super(new String[] { "test" });
	}

	@Override
	public void commandInit(CommandSender sender, String[] args) {
		int steps = 1;
		boolean b = false;
		if (args.length == 2) {
			try {
				steps = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
			}
		}
		if (args.length == 3) {
			try {
				steps = Integer.parseInt(args[1]);
				b = true;
			} catch (NumberFormatException e) {
			}
		}
		this.player = (Player) sender;
		if(steps > 1){
			steps = steps / PermissionHolder.getPermissions().length;
		}
		test(steps, b);
	}

	public void test(int steps, boolean b) {
		testFromPermissionsPlugin = b;
		load();
		long time = new Date().getTime();
		boolean mute = steps > PermissionHolder.getPermissions().length ? true : false;
		String permPlugin = b ? "&cWith&9 Permissions plugin." : "&cWithout &9Permissions plugin.";
		player.sendMessage(ChatColor.translateAlternateColorCodes('&',
				"&9Plugin version &a" + MineBlockerPlugin.getVersion() + "&9 New test version &a"
						+ String.valueOf(version) + "&9 START in &a" + String.valueOf(steps * PermissionHolder.getPermissions().length) + "&9 steps "
						+ permPlugin));
		while (steps > 0) {
			steps--;
			step(mute);
		}
		player.sendMessage(ChatColor.BLUE + "Test end: " + ChatColor.GREEN + String.valueOf(new Date().getTime() - time)
				+ ChatColor.BLUE + " ms.");
		long lineJump = permissionsList.getSections().get("name").getPermissions().getPermissionLines().size();
		player.sendMessage(
				ChatColor.BLUE + "Lines in list: " + ChatColor.GREEN + String.valueOf(lineJump) + ChatColor.BLUE + ".");
	}

	private void load() {
		permissionsList = new PermissionsList();
		@SuppressWarnings("deprecation")
		Permissions pss = new Permissions();
		addFakeLine(pss);
		for (Permission p : PermissionHolder.getPermissions()) {
			pss.addPermission(getLine(p));
		}
		PermissionSection ps = new PermissionSection(null, null, null, null, null, pss);
		permissionsList.add("name", ps);
	}

	private void addFakeLine(Permissions pss) {
		int f = fakeLine;
		while (f > 0) {
			for (Permission p : PermissionHolder.getPermissions()) {
				pss.addPermission(
						new PermissionLine(new Permission[] { p }, new ListItem[] { new ListItem("sand:0{{}}") }, null,
								CancelType.GLOBAL, 0, true, 0, null, null, false));
				f--;
				if (f == 0)
					continue;
			}
		}
	}

	private PermissionLine getLine(Permission p) {
		if (p == Permit.PLACE_NEAR) {
			return new PermissionLine(new Permission[] { p },
					new ListItem[] {
							new ListItem(new EventItemBlock(VersionsBridge.getTarget(player, 100)).toString()) },
					null, CancelType.GLOBAL, 0, true, 0, null, null, false);
		}
		return new PermissionLine(new Permission[] { p }, new ListItem[] { new ListItem("stone:0{{}}") }, null,
				CancelType.GLOBAL, 0, true, 0, null, null, false);
	}

	private void step(boolean mute) {
		for (Permission p : PermissionHolder.getPermissions()) {
			ActionEvent e = getEvent(p);
			permissionsList.checkEvent(e);
			if (!mute && e.isCancelled()) {
				String m = MineBlockerPlugin.getInst()
						.getMessage(e.getBannedPermissions()[0].getStringPermission()) != null ? "§e message §a OK "
								: "§e message §c NO ";
				player.sendMessage(ChatColor.YELLOW + e.getPermissions()[0].getPermission().getStringPermission()
						+ ChatColor.GREEN + " OK " + m + ChatColor.GRAY + e.getStringPermissions());
			} else if (!mute && !e.isCancelled()) {
				player.sendMessage(ChatColor.YELLOW + e.getPermissions()[0].getPermission().getStringPermission()
						+ ChatColor.RED + " NO " + ChatColor.GRAY + e.getStringPermissions());
			}
			if (testFromPermissionsPlugin) {
				for (String perm : e.getStringPermissions()) {
					player.hasPermission(perm);
				}
			}
		}
	}

	private ActionEvent getEvent(Permission p) {
		if (p == Permit.PLACE_NEAR) {
			return new PlayerActionEvent(player, p, player.getLocation(),
					new EventItemBlock(VersionsBridge.getTarget(player, 100)),
					new EventItemStack(new ItemStack(Material.STONE)));
		}
		return new PlayerActionEvent(player, p, player.getLocation(), new EventItemStack(new ItemStack(Material.STONE)),
				new EventItemStack(new ItemStack(Material.STONE)));
	}

}
