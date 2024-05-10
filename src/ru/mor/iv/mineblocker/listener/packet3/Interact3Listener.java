package ru.mor.iv.mineblocker.listener.packet3;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;

import ru.mor.iv.mineblocker.MineBlockerPlugin;
import ru.mor.iv.mineblocker.event.ActionEvent;
import ru.mor.iv.mineblocker.event.PlayerActionEvent;
import ru.mor.iv.mineblocker.item.EventItemBlock;
import ru.mor.iv.mineblocker.item.EventItemStack;
import ru.mor.iv.mineblocker.message.Messager;
import ru.mor.iv.mineblocker.permission.Permission;
import ru.mor.iv.mineblocker.permission.Permit;

public class Interact3Listener extends PacketAdapter {

	private Messager messageManager;

	public Interact3Listener(PacketType packetType) {
		super(MineBlockerPlugin.getInst(), ListenerPriority.NORMAL, packetType);
		this.messageManager = MineBlockerPlugin.getInst().getMessager();
	}

	@Override
	public void onPacketReceiving(PacketEvent event) {
		if (event.getPacketType() == PacketType.Play.Client.BLOCK_PLACE) {
			Player player = event.getPlayer();
			WrapperPlayClientBlockPlace packet = new WrapperPlayClientBlockPlace(event.getPacket());
			Location loc = new Location(event.getPlayer().getWorld(), packet.getX(), packet.getY(), packet.getZ());
			Permission perm = null;
			if(player.isSneaking()){
				perm = Permit.SHIFT_RIGHT_CLICK_BLOCK;
			}else{
				perm = Permit.RIGHT_CLICK_BLOCK;
			}
			Block block = loc.getBlock();
			ActionEvent e = new PlayerActionEvent(player, perm, loc, new EventItemStack(packet.getHeldItem()), new EventItemBlock(block));
			plugin.getServer().getPluginManager().callEvent(e);
			if(e.isCancelled()){
				player.sendMessage("OK");
				messageManager.sendMessage(e);
				event.setCancelled(true);
				return;
			}
		}
	}

}
