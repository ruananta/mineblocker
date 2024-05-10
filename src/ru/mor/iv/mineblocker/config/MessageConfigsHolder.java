package ru.mor.iv.mineblocker.config;

public class MessageConfigsHolder implements Config{
	
	private String ru = "message_ru.yml";
	private String en = "message_en.yml";
	
	private Message messageConfig;
	
	@Override
	public void load() {
		messageConfig.load();
	}

	@Override
	public boolean hasEverything() {
		return messageConfig.hasEverything();
	}

	@Override
	public void createDefault() {
		messageConfig.createDefault();
	}

	@Override
	public void reload() {
		messageConfig.reload();
	}

	@Override
	public void loadFile() {
		String lang = ConfigHolder.getMessageLanguage();
		if(lang.equalsIgnoreCase("ru")){
			messageConfig = new MessageRU(ru);
		}else if(lang.equalsIgnoreCase("en")){
			messageConfig = new MessageEN(en);
		}else{
			String fileName = "message_" + lang + ".yml";
			messageConfig = new MessageXX(fileName);
		}
		messageConfig.loadFile();
	}

	@Override
	public void save() {
		messageConfig.save();
	}

	
}
