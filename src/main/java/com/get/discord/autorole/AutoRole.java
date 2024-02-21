package com.get.discord.autorole;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import com.get.discord.autorole.gui.LogGUI;
import com.get.discord.autorole.util.CheckVersion;
import com.get.discord.autorole.util.Reference;
import com.get.lib.logutils.LogUtil;

public final class AutoRole {

	public final static String directory = "C:\\Users\\"+System.getProperty("user.name")+"\\AppData\\Roaming\\DiscordAutoRole";
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
    		writer.write("roleID: 00000000000\n");
    		writer.write("tokenID: MTIwMTg1NDM3MjI4NDQ4MTU0Ng.GWuk80.iYPf8XeM740pHvdIC3imBGU5xuPjdm2gAPWyV0");
    		writer.close();
    	}
		
		CheckVersion.changeVersionFile();
		
		log.mkdir();
		new LogGUI();
	}
}