package ru.mor.iv.mineblocker.listener.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

public class ProtocolLibManager {
	
	private static ProtocolManager protocolManager;

	public static  void init(){
		protocolManager = ProtocolLibrary.getProtocolManager();
		protocolManager.addPacketListener(new InteractListener(PacketType.Play.Client.USE_ITEM));
	}
	
	public static ProtocolManager getProtocolManager() {
		return protocolManager;
	}
	
	
}
