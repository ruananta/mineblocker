package ru.mor.iv.mineblocker.listener.packet;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.comphenix.packetwrapper.WrapperPlayClientUseItem;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;

import ru.mor.iv.mineblocker.MineBlockerPlugin;
import ru.mor.iv.mineblocker.SignWrapper;
import ru.mor.iv.mineblocker.event.ActionEvent;
import ru.mor.iv.mineblocker.event.PlayerActionEvent;
import ru.mor.iv.mineblocker.item.EventItem;
import ru.mor.iv.mineblocker.item.EventItemBlock;
import ru.mor.iv.mineblocker.managers.VersionsBridge;
import ru.mor.iv.mineblocker.message.Messager;
import ru.mor.iv.mineblocker.permission.Permission;
import ru.mor.iv.mineblocker.permission.Permit;

public class InteractListener extends PacketAdapter {

	private Messager messageManager;

	public InteractListener(PacketType packetType) {
		super(MineBlockerPlugin.getInst(), ListenerPriority.NORMAL, packetType);
		this.messageManager = MineBlockerPlugin.getInst().getMessager();
	}

	@Override
	public void onPacketReceiving(PacketEvent event) {
		if (event.getPacketType() == PacketType.Play.Client..USE_ITEM) {
			Player player = event.getPlayer();
			WrapperPlayClientUseItem packet = new WrapperPlayClientUseItem(event.getPacket());
			Location loc = packet.getLocation().toVector().toLocation(event.getPlayer().getWorld());
			Permission perm = null;
			if(player.isSneaking()){
				perm = Permit.SHIFT_RIGHT_CLICK_BLOCK;
			}else{
				perm = Permit.RIGHT_CLICK_BLOCK;
			}
			Block block = loc.getBlock();
			ActionEvent e = new PlayerActionEvent(player, perm, loc, VersionsBridge.getItemsHand(player), new EventItem[]{new EventItemBlock(block)}, SignWrapper.searchSing(block));
			plugin.getServer().getPluginManager().callEvent(e);
			if(e.isCancelled()){
				messageManager.sendMessage(e);
				event.setCancelled(true);
				return;
			}
		}
	}

}
