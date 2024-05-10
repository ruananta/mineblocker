package ru.mor.iv.mineblocker.full.delete;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import ru.mor.iv.mineblocker.MineBlockerPlugin;
import ru.mor.iv.mineblocker.item.EventItem;
import ru.mor.iv.mineblocker.item.ListItem;

public class RangeItemManager {
	private String delimeter = "|";
	private String spliter = "\\" + delimeter;
	private File file = new File(MineBlockerPlugin.getInst().getDataFolder(), "range_items.txt");
	private List<ListItem> items = new ArrayList<>();
	
	public RangeItemManager() {
		createDefaultConfig();
	}
	public void load() {
		if(!file.exists()){
			return;
		}
		try{
	          FileInputStream fs = new FileInputStream(file);
	          BufferedReader br = new BufferedReader(new InputStreamReader(new DataInputStream(fs)));
	          String line;
	          while ((line = br.readLine()) != null)
	          {
	          	if(line.contains(delimeter)){
	          		String[] temp = line.split(spliter);
	          		if (temp[0] != null && temp[1] != null) {
	          			int range = Integer.valueOf(temp[1]);
	        			ListItem item = new ListItem(temp[0]);
	        			item.setRange(range);
	        			items.add(item);
	              	}
	          	}
	          }
	          fs.close();
	          br.close();
	      }catch(Exception ex){
	          ex.printStackTrace();
	      }
	}
	private void createDefaultConfig() {
		if(file.exists()){
			return;
		}
		try {
			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF8"));
			out.append("sample|10").append("\r\n");
			out.append("sample:0|20").append("\r\n");
			out.append("sample:0{nbt}|30").append("\r\n");
			out.flush();
			out.close();
		        
		    } 
		   catch (Exception e)
		   {
			System.out.println(e.getMessage());
		   } 
	}
	
	
	public ListItem getRangeItem(EventItem eventItem){
		for(ListItem listItem : items){
			if(eventItem.equals(listItem)){
				return listItem;
			}
		}
		return null;
	}
	
	public void reload(){
		items.clear();
		load();
	}
}
