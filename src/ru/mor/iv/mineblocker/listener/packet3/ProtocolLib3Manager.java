package ru.mor.iv.mineblocker.listener.packet3;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

public class ProtocolLib3Manager {
	
	private static ProtocolManager protocolManager;

	public static  void init(){
		protocolManager = ProtocolLibrary.getProtocolManager();
		protocolManager.addPacketListener(new Interact3Listener(PacketType.Play.Client.BLOCK_PLACE));
	}
	
	public static ProtocolManager getProtocolManager() {
		return protocolManager;
	}
	
	
}
