package ru.mor.iv.mineblocker.full;

public class FullPluginLoader {
	private static FullPlugin fullPlugin = null;
		
	public static void load() {
		if(fullPlugin()){
			fullPlugin = FullPlugin.getInstance();
		}
		
		
	}
	private static boolean fullPlugin() {
		try {
			Class.forName(getControlClassName());
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
		
	}

	private static String getControlClassName() {
		String thisPackage = FullPluginLoader.class.getPackage().getName();
		thisPackage = thisPackage + ".delete.X";
		return thisPackage;
	}
	
	public static boolean isFullPlugin() {
		return fullPlugin != null;
	}
	
	public static FullPlugin getFullPlugin() {
		return fullPlugin;
	}
	
	public static void reload() {
		if(fullPlugin != null){
			fullPlugin.reload();
		}
	}
}
