package ru.mor.iv.mineblocker;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import ru.mor.iv.mineblocker.command.CommandHandler;
import ru.mor.iv.mineblocker.command.TabCompleter;
import ru.mor.iv.mineblocker.config.ConfigHolder;
import ru.mor.iv.mineblocker.config.MessageContainer;
import ru.mor.iv.mineblocker.full.FullPluginLoader;
import ru.mor.iv.mineblocker.listener.packet.ProtocolLibManager;
import ru.mor.iv.mineblocker.listener.packet3.ProtocolLib3Manager;
import ru.mor.iv.mineblocker.managers.HaveTaskTimer;
import ru.mor.iv.mineblocker.managers.Metrics;
import ru.mor.iv.mineblocker.managers.PermissionManager;
import ru.mor.iv.mineblocker.managers.Switch;
import ru.mor.iv.mineblocker.managers.VersionsBridge;
import ru.mor.iv.mineblocker.managers.permissions.PermissionsExManager;
import ru.mor.iv.mineblocker.managers.permissions.PermissionsPlugin;
import ru.mor.iv.mineblocker.managers.permissions.VaultPermissionsManager;
import ru.mor.iv.mineblocker.managers.permissions.rscPermissionsManager;
import ru.mor.iv.mineblocker.managers.privates.PreciousStonesManager;
import ru.mor.iv.mineblocker.managers.privates.PrivatePlugin;
import ru.mor.iv.mineblocker.managers.privates.WorldGuard5Manager;
import ru.mor.iv.mineblocker.managers.privates.WorldGuard6Manager;
import ru.mor.iv.mineblocker.message.Messager;
import ru.mor.iv.mineblocker.permission.PermissionHolder;
import ru.mor.iv.mineblocker.permission.list.PermissionsList;

public class MineBlockerPlugin extends JavaPlugin {
	private static MineBlockerPlugin inst;
	public static boolean debug = false;
	private CommandHandler mineBlockerCommand;
	private PermissionManager permManager;
	private Messager messager;
	private PrivatePlugin privatePlugin;
	private PermissionsPlugin permissionsPlugin;
	private PermissionsList permissionsList;
	private HaveTaskTimer haveTaskTimer;
	private Switch optimizer;
	
	@Override
	public void onEnable()
	  {
		inst = this;
		messager = new Messager();
		
		FullPluginLoader.load();
		PermissionHolder.load();
		ConfigHolder.init();
		ConfigHolder.load();
		loadPrivatePlugin();
		loadPermissionsPlugin();
		loadProtocolLibPlugin();
		
		VersionsBridge.init();
		
		permissionsList = new PermissionsList();
		permissionsList.load();
		
		permManager = new PermissionManager();
		
		haveTaskTimer = new HaveTaskTimer();
		optimizer = new Switch();
	  	mineBlockerCommand = new CommandHandler();
	  	
	  	getCommand("mineblocker").setExecutor(mineBlockerCommand);
	  	getCommand("mineblocker").setTabCompleter(new TabCompleter());
	  	
	  	optimizer.optimize();
	  	ListenerHolder.init();
	  	ListenerHolder.load();
	  	try
	    {
	      Metrics metrics = new Metrics(this);
	      metrics.start();
	    }
	    catch (IOException e)
	    {
	    	Bukkit.getLogger().info("[MineBlocker] failed to submit the statistic.");
	    }
	  	
	  }
	
	@Override
	public void onDisable(){
		if(FullPluginLoader.isFullPlugin()){
			FullPluginLoader.getFullPlugin().getItemRemover().shutdown();
		}
	}
	
	private void loadPrivatePlugin() {
		Plugin wg = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
		Plugin ps = Bukkit.getServer().getPluginManager().getPlugin("PreciousStones");
		if(wg != null && ConfigHolder.isUseWorldGuard()){
			if(wg.getDescription().getVersion().startsWith("5")){
				privatePlugin = new WorldGuard5Manager();
				Bukkit.getLogger().info("[MineBlocker] WorldGuard 5 found.");
			}else{
				privatePlugin = new WorldGuard6Manager();
				Bukkit.getLogger().info("[MineBlocker] WorldGuard 6 found.");
			}
		}else if(ps != null && !ConfigHolder.isUseWorldGuard()){
			privatePlugin = new PreciousStonesManager();
			Bukkit.getLogger().info("[MineBlocker] PreciousStones found.");
		}
		
		if(privatePlugin == null){
			Bukkit.getLogger().info("---------------------------------------------------------------");
			Bukkit.getLogger().info("[MineBlocker] Error start plugin. Private plugin is not found. ");
			Bukkit.getLogger().info("---------------------------------------------------------------");
		}
	}
	
	private void loadPermissionsPlugin() {
		Plugin permissionsEx = Bukkit.getServer().getPluginManager().getPlugin("PermissionsEx");
		Plugin vault = Bukkit.getServer().getPluginManager().getPlugin("Vault");
		Plugin rscPermissions = Bukkit.getServer().getPluginManager().getPlugin("rscPermissions");
		if(rscPermissions != null){
			permissionsPlugin = new rscPermissionsManager();
			Bukkit.getLogger().info("[MineBlocker] rscPermissions found.");
		}else if(permissionsEx != null){
			permissionsPlugin = new PermissionsExManager();
			Bukkit.getLogger().info("[MineBlocker] PermissionsEx found.");
		}else if(vault != null){
			permissionsPlugin = new VaultPermissionsManager();
			Bukkit.getLogger().info("[MineBlocker] Vault found.");
		}
	}
	
	private void loadProtocolLibPlugin() {
		Plugin protocolLib = Bukkit.getServer().getPluginManager().getPlugin("ProtocolLib");
		if(protocolLib != null){
			if(protocolLib.getDescription().getVersion().startsWith("3")){
				Bukkit.getLogger().info("[MineBlocker] ProtocolLib 3 found.");
				ProtocolLib3Manager.init();
			}else{
				Bukkit.getLogger().info("[MineBlocker] ProtocolLib 4 found.");
				ProtocolLibManager.init();
			}
		}
		
	}
	
	public static MineBlockerPlugin getInst() {
		return inst;
	}
	
	public PermissionManager getPermissionManager() {
		return this.permManager;
	}
	
	public Messager getMessager() {
		return this.messager;
	}
	
	public PrivatePlugin getPrivatePlugin() {
		return privatePlugin;
	}
	
	public PermissionsPlugin getPermissionsPlugin(){
		return permissionsPlugin;
	}
	
	public PermissionsList getPermissionsList() {
		return permissionsList;
	}
	
	public void reloadPlugin(){
		PermissionHolder.reload();
		ConfigHolder.reload();
		FullPluginLoader.reload();
		if(privatePlugin != null)
			privatePlugin.reload();
		permissionsList.reload();
		haveTaskTimer.start();
		optimizer.optimize();
		ListenerHolder.load();
	}
	
	
	public static void debug(String msg) {
		Bukkit.getLogger().info(msg);
	}

	public static String getVersion() {
		return getInst().getDescription().getVersion();
	}

	public String getMessage(String name) {
		return MessageContainer.getMessage(name);
	}

}
