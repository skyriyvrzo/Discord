package xyz.cuddlecloud.discord.command;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JTextField;

import xyz.cuddlecloud.discord.Discord;
import xyz.cuddlecloud.discord.bot.Discord4J;
import xyz.cuddlecloud.discord.gui.logs.Log;
import xyz.cuddlecloud.discord.util.Reference;
import xyz.cuddlecloud.discord.util.Utils;
import com.get.lib.Logging.Loggy.Level;

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
				else if(tf.getText().equalsIgnoreCase("bot get channelIdList")) {
					Discord4J.getChannelIdList();
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
		Log.setMessage(Discord.loggy.log(Level.INFO, "------ Help Bot ------"));
		Log.setMessage(Discord.loggy.log(Level.INFO, "- bot get"));
		Log.setMessage(Discord.loggy.log(Level.INFO, "- bot set"));
		Log.setMessage(Discord.loggy.log(Level.INFO, "------------------------"));
	}
	
	private static void getGetCMD() {
		Log.setMessage(Discord.loggy.log(Level.INFO, "------ Help Bot ------"));
		Log.setMessage(Discord.loggy.log(Level.INFO, "- bot get botToken"));
		Log.setMessage(Discord.loggy.log(Level.INFO, "- bot get memberJoinRoleId"));
		Log.setMessage(Discord.loggy.log(Level.INFO, "- bot get channelIdforCreateRoom"));
		Log.setMessage(Discord.loggy.log(Level.INFO, "- bot get categoryIdforNewRoom"));
		Log.setMessage(Discord.loggy.log(Level.INFO, "------------------------"));
	}
	
	private static void getSetCMD() {
		Log.setMessage(Discord.loggy.log(Level.INFO, "------ Help Bot ------"));
		Log.setMessage(Discord.loggy.log(Level.INFO, "- bot set botToken <botToken>"));
		Log.setMessage(Discord.loggy.log(Level.INFO, "- bot set memberJoinRoleId <role_id>"));
		Log.setMessage(Discord.loggy.log(Level.INFO, "- bot set channelIdforCreateRoom <channel_id>"));
		Log.setMessage(Discord.loggy.log(Level.INFO, "- bot set categoryIdforNewRoom <category_id>"));
		Log.setMessage(Discord.loggy.log(Level.INFO, "------------------------"));
	}
	
	private static void getBotToken() throws FileNotFoundException, IOException {
		String botToken = Discord.properties.getProperty("tokenId");
		
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(botToken), null);
		Log.setMessage(Discord.loggy.log(Level.INFO, "Bot Token: " + botToken));
		Log.setMessage(Discord.loggy.log(Level.INFO, "Copied to clipboard"));
	}
	
	private static void getMemberJoinRoleID() throws FileNotFoundException, IOException {
		Utils.loadProperties();
		String memberJoinRoleID = Discord.properties.getProperty("roleId");
		
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(memberJoinRoleID), null);
		Log.setMessage(Discord.loggy.log(Level.INFO, "RoleID: " + memberJoinRoleID));
		Log.setMessage(Discord.loggy.log(Level.INFO, "Copied to clipboard"));
	}
	
	private static void getChannelIdforCreateRoom() {
		Utils.loadProperties();
		String channelIdforCreateRoom = Discord.properties.getProperty("channelIdforCreateRoom");
		
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(channelIdforCreateRoom), null);
		Log.setMessage(Discord.loggy.log(Level.INFO, "ChannelIdforCreateRoom: " + channelIdforCreateRoom));
		Log.setMessage(Discord.loggy.log(Level.INFO, "Copied to clipboard"));
	}
	
	private static void getCategoryIdforNewRoom() {
		Utils.loadProperties();
		String categoryIdforNewRoom = Discord.properties.getProperty("categoryIdforNewRoom");
		
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(categoryIdforNewRoom), null);
		Log.setMessage(Discord.loggy.log(Level.INFO, "RoleID: " + categoryIdforNewRoom));
		Log.setMessage(Discord.loggy.log(Level.INFO, "Copied to clipboard"));
	}
	
	private static void setBotToken(String id) throws FileNotFoundException, IOException {
		Utils.loadProperties();
		String oldtokenID = Discord.properties.getProperty("tokenId");
		
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
		
		Log.setMessage(Discord.loggy.log(Level.INFO, "Please restart program."));
	}
	
	private static void setMemberJoinRoleByID(String id) throws IOException {
		Utils.loadProperties();
		String oldRoleID = Discord.properties.getProperty("roleId");
		
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
		
		Log.setMessage(Discord.loggy.log(Level.INFO, "Please restart program."));
		//Bot.buildBot();
	}
	
	private static void setChannelIDforCreateRoom(String id) throws FileNotFoundException, IOException {
		Utils.loadProperties();
		String oldRoleID = Discord.properties.getProperty("channelIdforCreateRoom");
		
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
		
		Log.setMessage(Discord.loggy.log(Level.INFO, "Please restart program."));
	}
	
	private static void setCategoryIdforNewRoom(String id) throws FileNotFoundException, IOException {
		Utils.loadProperties();
		String oldRoleID = Discord.properties.getProperty("categoryIdforNewRoom");
		
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
		
		Log.setMessage(Discord.loggy.log(Level.INFO, "Please restart program."));
	}
}
