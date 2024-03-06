package com.get.discord.gg.command;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JTextField;

import com.get.discord.gg.Main;
import com.get.discord.gg.gui.GPanel;
import com.get.discord.gg.util.Reference;
import com.get.discord.gg.util.Utils;
import com.get.lib.logutils.LogUtil;

public final class CommandBot {

	//private static String role_id;
	
	public static void conditional(JTextField tf, String split[]) throws IOException {
		
		try {
			if(tf.getText().equalsIgnoreCase("bot")) {
				getHelp();
			}else if(split[1].equalsIgnoreCase("get")) {
				if(tf.getText().equalsIgnoreCase("bot get botToken")) {
					getBotToken();
				}
				else if(tf.getText().equalsIgnoreCase("bot get memberJoinRoleId")) {
					getMemberJoinRoleID();
				}
				else if(tf.getText().equalsIgnoreCase("bot get channelIdforCreateRoom")) {
					getChannelIdforCreateRoom();
				}
				else if(tf.getText().equalsIgnoreCase("bot get categoryIdforNewRoom")) {
					getCategoryIdforNewRoom();
				}
				else {
					getGetCMD();
				}
			}else if(split[1].equalsIgnoreCase("set")) {
				if(tf.getText().trim().equalsIgnoreCase("bot set")) {
					getSetCMD();
				}
				else if(split[2].equalsIgnoreCase("botToken") && split.length == 4) {
					setBotToken(split[3]);
				}
				else if(split[2].equalsIgnoreCase("memberJoinRoleId") && split.length == 4){
					setMemberJoinRoleByID(split[3]);
				}
				else if(split[2].equalsIgnoreCase("channelIdforCreateRoom") && split.length == 4) {
					setChannelIDforCreateRoom(split[3]);
				}
				else if(split[2].equalsIgnoreCase("categoryIdforNewRoom") && split.length == 4) {
					setCategoryIdforNewRoom(split[3]);
				}
				else {
					getSetCMD();
				}
			}else {
				//getHelp();
			}
		}catch(Exception e) {
			e.printStackTrace();
			getHelp();
		}
	}
	
	public static void getHelp() {
		GPanel.setLog(LogUtil.info("Command", "Discord","------ Help Bot ------", false));
		GPanel.setLog(LogUtil.info("Command", "Discord","- bot get", false));
		GPanel.setLog(LogUtil.info("Command", "Discord","- bot set", false));
		GPanel.setLog(LogUtil.info("Command", "Discord","------------------------", false));
	}
	
	private static void getGetCMD() {
		GPanel.setLog(LogUtil.info("Command", "Discord","------ Help Bot ------", false));
		GPanel.setLog(LogUtil.info("Command", "Discord","- bot get botToken", false));
		GPanel.setLog(LogUtil.info("Command", "Discord","- bot get memberJoinRoleID", false));
		GPanel.setLog(LogUtil.info("Command", "Discord","- bot get channelIdforCreateRoom", false));
		GPanel.setLog(LogUtil.info("Command", "Discord","- bot get categoryIdforNewRoom", false));
		GPanel.setLog(LogUtil.info("Command", "Discord","------------------------", false));
	}
	
	private static void getSetCMD() {
		GPanel.setLog(LogUtil.info("Command", "Discord","------ Help Bot ------", false));
		GPanel.setLog(LogUtil.info("Command", "Discord","- bot set botToken <botToken>", false));
		GPanel.setLog(LogUtil.info("Command", "Discord","- bot set memberJoinRole <role_id>", false));
		GPanel.setLog(LogUtil.info("Command", "Discord","- bot set channelIdforCreateRoom <channel_id>", false));
		GPanel.setLog(LogUtil.info("Command", "Discord","- bot set categoryIdforNewRoom <category_id>", false));
		GPanel.setLog(LogUtil.info("Command", "Discord","------------------------", false));
	}
	
	private static void getBotToken() throws FileNotFoundException, IOException {
		String botToken = Main.properties.getProperty("tokenId");
		
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(botToken), null);
		GPanel.setLog(LogUtil.info("Command", "Discord","Bot Token: " + botToken, false));
		GPanel.setLog(LogUtil.info("Command", "Discord","Copied to clipboard", false));
	}
	
	private static void getMemberJoinRoleID() throws FileNotFoundException, IOException {
		Utils.loadProperties();
		String memberJoinRoleID = Main.properties.getProperty("roleId");
		
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(memberJoinRoleID), null);
		GPanel.setLog(LogUtil.info("Command", "Discord","RoleID: " + memberJoinRoleID, false));
		GPanel.setLog(LogUtil.info("Command", "Discord","Copied to clipboard", false));
	}
	
	private static void getChannelIdforCreateRoom() {
		Utils.loadProperties();
		String channelIdforCreateRoom = Main.properties.getProperty("channelIdforCreateRoom");
		
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(channelIdforCreateRoom), null);
		GPanel.setLog(LogUtil.info("Command", "Discord","ChannelIdforCreateRoom: " + channelIdforCreateRoom, false));
		GPanel.setLog(LogUtil.info("Command", "Discord","Copied to clipboard", false));
	}
	
	private static void getCategoryIdforNewRoom() {
		Utils.loadProperties();
		String categoryIdforNewRoom = Main.properties.getProperty("categoryIdforNewRoom");
		
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(categoryIdforNewRoom), null);
		GPanel.setLog(LogUtil.info("Command", "Discord","RoleID: " + categoryIdforNewRoom, false));
		GPanel.setLog(LogUtil.info("Command", "Discord","Copied to clipboard", false));
	}
	
	private static void setBotToken(String id) throws FileNotFoundException, IOException {
		Utils.loadProperties();
		String oldtokenID = Main.properties.getProperty("tokenId");
		
		BufferedReader reader = new BufferedReader(new FileReader(Reference.getConfigFile.get()));
		StringBuilder builder = new StringBuilder();
		String line;
		
		while((line = reader.readLine()) != null) {
			line = line.replace(oldtokenID, id);
			builder.append(line).append(System.lineSeparator());
		}
		
		reader.close();
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(Reference.getConfigFile.get()));
		writer.write(builder.toString());
		
		writer.close();
		
		GPanel.setLog(LogUtil.warn("Command", "Discord", "Please restart program.", false));
	}
	
	private static void setMemberJoinRoleByID(String id) throws IOException {
		Utils.loadProperties();
		String oldRoleID = Main.properties.getProperty("roleId");
		
		BufferedReader reader = new BufferedReader(new FileReader(Reference.getConfigFile.get()));
		StringBuilder builder = new StringBuilder();
		String line;
		
		while((line = reader.readLine()) != null) {
			line = line.replace(oldRoleID, id);
			builder.append(line).append(System.lineSeparator());
		}
		
		reader.close();
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(Reference.getConfigFile.get()));
		writer.write(builder.toString());
		
		writer.close();
		
		GPanel.setLog(LogUtil.warn("Command", "Discord", "Please restart program.", false));
		//Bot.buildBot();
	}
	
	private static void setChannelIDforCreateRoom(String id) throws FileNotFoundException, IOException {
		Utils.loadProperties();
		String oldRoleID = Main.properties.getProperty("channelIdforCreateRoom");
		
		BufferedReader reader = new BufferedReader(new FileReader(Reference.getConfigFile.get()));
		StringBuilder builder = new StringBuilder();
		String line;
		
		while((line = reader.readLine()) != null) {
			line = line.replace(oldRoleID, id);
			builder.append(line).append(System.lineSeparator());
		}
		
		reader.close();
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(Reference.getConfigFile.get()));
		writer.write(builder.toString());
		
		writer.close();
		
		GPanel.setLog(LogUtil.warn("Command", "Discord", "Please restart program.", false));
	}
	
	private static void setCategoryIdforNewRoom(String id) throws FileNotFoundException, IOException {
		Utils.loadProperties();
		String oldRoleID = Main.properties.getProperty("categoryIdforNewRoom");
		
		BufferedReader reader = new BufferedReader(new FileReader(Reference.getConfigFile.get()));
		StringBuilder builder = new StringBuilder();
		String line;
		
		while((line = reader.readLine()) != null) {
			line = line.replace(oldRoleID, id);
			builder.append(line).append(System.lineSeparator());
		}
		
		reader.close();
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(Reference.getConfigFile.get()));
		writer.write(builder.toString());
		
		writer.close();
		
		GPanel.setLog(LogUtil.warn("Command", "Discord", "Please restart program.", false));
	}
}
