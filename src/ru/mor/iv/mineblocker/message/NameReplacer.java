package ru.mor.iv.mineblocker.message;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import ru.mor.iv.mineblocker.MineBlockerPlugin;
import ru.mor.iv.mineblocker.item.EventItem;
import ru.mor.iv.mineblocker.item.ListItem;

public class NameReplacer {
	private String delimeter = "|";
	private String spliter = "\\" + delimeter;
	private static Map<ListItem, String> objectList = new HashMap<>();

	
	public NameReplacer() {
		createFile();
	}

	public String getNewName(EventItem eventItem) {
		for (Entry<ListItem, String> e : objectList.entrySet()) {
			if (eventItem.equals(e.getKey())) {
				return e.getValue();
			}
		}
		return null;
	}

	public void load() {
		try {
			StringBuilder builder = new StringBuilder(MineBlockerPlugin.getInst().getDataFolder().toString());
			builder.append("/replacer.txt");
			FileInputStream fs = new FileInputStream(builder.toString());
			BufferedReader br = new BufferedReader(new InputStreamReader(new DataInputStream(fs)));
			String line;
			while ((line = br.readLine()) != null) {
				if (line.contains(delimeter)) {
					String[] temp = line.split(spliter);
					if (temp[0] != null && temp[1] != null) {
						ListItem i = new ListItem(temp[0]);
						if (i.isValid()) {
							objectList.put(i, temp[1]);
						}
					}
				}
			}
			fs.close();
			br.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void createFile() {
		File file = new File(MineBlockerPlugin.getInst().getDataFolder(), "replacer.txt");
		if (file.exists()) {
			return;
		}
		try {
			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF8"));
			out.append("stone:0|Камень").append("\r\n");

			out.flush();
			out.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void reload() {
		objectList.clear();
		load();
	}

}
