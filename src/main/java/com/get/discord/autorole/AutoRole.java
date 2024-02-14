package com.get.discord.autorole;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import com.get.discord.autorole.log.LogGUI;
import com.get.discord.autorole.util.CheckVersion;
import com.get.discord.autorole.util.Reference;
import com.get.lib.logutils.LogUtil;

public class AutoRole {

	public static String directory = "C:\\Users\\"+System.getProperty("user.name")+"\\AppData\\Roaming\\DiscordAutoRole";
	public static LogUtil log = new LogUtil(directory);
	
	public static Properties properties = new Properties(); 
	
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
	
	public static void setRoleID(String id) throws IOException {
		properties.load(new FileReader(directory + "\\config.properties"));
		String oldRoleID = properties.getProperty("roleID");
		
		BufferedReader reader = new BufferedReader(new FileReader(directory + "\\config.properties"));
		StringBuilder builder = new StringBuilder();
		String line;
		
		while((line = reader.readLine()) != null) {
			line = line.replace(oldRoleID, id);
			builder.append(line).append(System.lineSeparator());
		}
		
		reader.close();
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(directory + "\\config.properties"));
		writer.write(builder.toString());
		
		writer.close();
		
		LogGUI.setLog(AutoRole.log.getWarning("Please restart program."));
		//Bot.buildBot();
	}
}