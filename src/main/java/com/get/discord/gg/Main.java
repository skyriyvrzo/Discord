package com.get.discord.gg;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import com.get.discord.gg.gui.LogGUI;
import com.get.discord.gg.util.CheckVersion;
import com.get.discord.gg.util.Reference;
import com.get.discord.gg.util.UnpackAssets;
import com.get.lib.logutils.LogUtil;

public final class Main {

	public final static String directory = "C:\\Users\\"+System.getProperty("user.name")+"\\AppData\\Roaming\\DiscordBot";
	public final static LogUtil log = new LogUtil(directory + "//logs");
	public final static Properties properties = new Properties(); 
	
	public static void main(String[] a) throws IOException {
				
		File dir = new File(directory);
		dir.mkdir();
		
		File config = new File(directory + "\\config.properties");
		if(config.exists());
    	else {
    		FileWriter writer = new FileWriter(directory + "\\config.properties", true);
    		
    		writer.write("version: "+Reference.VERSIONS +"\n");
    		writer.write("tokenId: {token}\n");
    		writer.write("roleId: {role}\n");
    		writer.write("channelIdforCreateRoom: {channel_id}\n");
    		writer.write("categoryIdforNewRoom: {category_id}");
    		writer.close();
    	}
		
		CheckVersion.changeVersionFile();
		UnpackAssets.extractFile();
		
		log.mkdir();
		new LogGUI();
	}
}