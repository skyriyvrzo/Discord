package com.get.discord.gg;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import com.get.discord.gg.gui.LogGUI;
import com.get.discord.gg.util.CheckOS;
import com.get.discord.gg.util.CheckVersion;
import com.get.discord.gg.util.Reference;
import com.get.discord.gg.util.UnpackAssets;
import com.get.lib.github.Github;
import com.get.lib.logutils.LogUtil;

public final class Main {

	public final static Properties properties = new Properties(); 
	public static LogUtil log;
	public static Github github = new Github("skyriyvrzo", "Discord", Reference.VERSIONS);
	
	public static void main(String[] a) throws IOException {
		
		CheckOS.checkOperatingSystem();
		
		log = new LogUtil(Reference.getDirectory.get());
		
		File dir = new File(Reference.getDirectory.get());
		dir.mkdir();
		
		File config = new File(Reference.getConfigFile.get());
		if(config.exists());
    	else {
    		FileWriter writer = new FileWriter(Reference.getConfigFile.get(), true);
    		
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