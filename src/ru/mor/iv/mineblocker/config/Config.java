package ru.mor.iv.mineblocker.config;

public interface Config {
	
	public abstract void loadFile();
	
	public abstract void load();
	
	public abstract boolean hasEverything();

	public abstract void createDefault();

	public abstract void reload();

	public abstract void save();

	
}
