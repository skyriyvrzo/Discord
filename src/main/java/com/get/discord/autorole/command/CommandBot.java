package com.get.discord.autorole.command;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JTextField;

import com.get.discord.autorole.AutoRole;
import com.get.discord.autorole.gui.GPanel;

public final class CommandBot {

	//private static String role_id;
	
	public static void conditional(JTextField tf, String split[]) throws IOException {
		
		try {
			if(tf.getText().equalsIgnoreCase("bot")) {
				getHelp();
			}else if(split[1].equalsIgnoreCase("get")) {
				if(tf.getText().equalsIgnoreCase("bot get botToken")) {
					getBotToken();
				}else if(tf.getText().equalsIgnoreCase("bot get MemberJoinRoleID")) {
					getMemberJoinRoleID();
				}else {
					getGetCMD();
				}
			}else if(split[1].equalsIgnoreCase("set")) {
				if(tf.getText().equalsIgnoreCase("bot set")) {
					getSetCMD();
				}
				else if(split[2].equalsIgnoreCase("botToken") && split.length == 4) {
					setBotToken();
				}else if(split[2].equalsIgnoreCase("MemberJoinRole") && split.length == 4){
					setMemberJoinRoleByID(split[3]);
				}else {
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
		GPanel.setLog(AutoRole.log.getInfo("------ Help Bot ------"));
		GPanel.setLog(AutoRole.log.getInfo("- bot get"));
		GPanel.setLog(AutoRole.log.getInfo("- bot set"));
	}
	
	private static void getGetCMD() {
		GPanel.setLog(AutoRole.log.getInfo("- bot get botToken"));
		GPanel.setLog(AutoRole.log.getInfo("- bot get MemberJoinRoleID"));
	}
	
	private static void getSetCMD() {
		GPanel.setLog(AutoRole.log.getInfo("- bot set botToken <botToken>"));
		GPanel.setLog(AutoRole.log.getInfo("- bot set MemberJoinRole <role_id>"));
	}
	
	private static void getBotToken() {
		
	}
	
	private static void getMemberJoinRoleID() {
		
	}
	
	private static void setBotToken() {
		
	}
	
	private static void setMemberJoinRoleByID(String id) throws IOException {
		AutoRole.properties.load(new FileReader(AutoRole.directory + "\\config.properties"));
		String oldRoleID = AutoRole.properties.getProperty("roleID");
		
		BufferedReader reader = new BufferedReader(new FileReader(AutoRole.directory + "\\config.properties"));
		StringBuilder builder = new StringBuilder();
		String line;
		
		while((line = reader.readLine()) != null) {
			line = line.replace(oldRoleID, id);
			builder.append(line).append(System.lineSeparator());
		}
		
		reader.close();
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(AutoRole.directory + "\\config.properties"));
		writer.write(builder.toString());
		
		writer.close();
		
		GPanel.setLog(AutoRole.log.getWarning("Please restart program."));
		//Bot.buildBot();
	}
}
