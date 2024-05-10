package ru.mor.iv.mineblocker.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import ru.mor.iv.mineblocker.ListenerHolder;
import ru.mor.iv.mineblocker.MineBlockerPlugin;
import ru.mor.iv.mineblocker.config.ConfigHolder;

public class HaveTaskTimer implements Runnable{
	private MineBlockerPlugin plugin = MineBlockerPlugin.getInst();
	private boolean isEnable = false;
	private boolean isEnd;
	
	public HaveTaskTimer() {
		start();
	}

	public void start() {
		long time = ConfigHolder.getHaveTaskTimerTime() * 20;
		if(!isEnable && time != 0){
			isEnd = true;
			isEnable = true;
			Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, this, time, time);
		}
	}

	@Override
	public void run() {
		if(isEnd){
			isEnd = false;
			for(Player p :  VersionsBridge.getOnlinePlayers()){
				check(p);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			isEnd = true;
		}
	}
	

	private void check(final Player p) {
		Bukkit.getScheduler().runTask(plugin, new Runnable() {	
			@Override
			public void run() {
				if(p != null && p.isOnline()){
					ListenerHolder.getHaveListener().checkPlayerInventory(p);
				}
			}
		});
	}
}
