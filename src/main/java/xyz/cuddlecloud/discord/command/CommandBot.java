package xyz.cuddlecloud.discord.command;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.*;

import javax.swing.JTextField;

import xyz.cuddlecloud.discord.Discord;
import xyz.cuddlecloud.discord.bot.Discord4J;
import xyz.cuddlecloud.discord.gui.logs.Log;
import xyz.cuddlecloud.discord.json.ConfigFile;
import xyz.cuddlecloud.javax.logging.Loggy.Level;

public final class CommandBot {
	
	public static void conditional(JTextField tf, String[] split) throws IOException {
		
		try {
			if(split[0].equalsIgnoreCase("bot")) {
				if(tf.getText().equalsIgnoreCase("bot")){
					getHelp();
				}
				else if(split[1].equalsIgnoreCase("get")) {
					if(tf.getText().equalsIgnoreCase("bot get botToken")) {
						getBotToken();
					}
					else if(tf.getText().equalsIgnoreCase("bot get memberJoinRoleId")) {
						getMemberJoinRoleID();
					}
					else if(tf.getText().equalsIgnoreCase("bot get channelIdForCreateRoom")) {
						getChannelIdForCreateRoom();
					}
					else if(tf.getText().equalsIgnoreCase("bot get categoryIdForCreateRoom")) {
						getCategoryIdForCreateRoom();
					}
					else if(tf.getText().equalsIgnoreCase("bot get channelIdList")) {
						Discord4J.getChannelIdList();
					}
					else if(tf.getText().equalsIgnoreCase("bot get activity")) {
						getActivity();
					}
					else {
						getGetCMD();
					}
				}
				else if(split[1].equalsIgnoreCase("set")) {
					if(tf.getText().trim().equalsIgnoreCase("bot set")) {
						getSetCMD();
					}
					else if(split[2].equalsIgnoreCase("botToken") && split.length == 4) {
						ConfigFile.setBotToken(split[3]);
					}
					else if(split[2].equalsIgnoreCase("memberJoinRoleId") && split.length == 4){
						ConfigFile.setRoleId(split[3]);
					}
					else if(split[2].equalsIgnoreCase("channelIdForCreateRoom") && split.length == 4) {
						ConfigFile.setChannelIdForCreateRoom(split[3]);
					}
					else if(split[2].equalsIgnoreCase("categoryIdForCreateRoom") && split.length == 4) {
						ConfigFile.setCategoryIdForCreateRoom(split[3]);
					}
					else if(split[2].equalsIgnoreCase("activity") && split.length == 4) {
						ConfigFile.setActivity(split[3]);
					}
					else {
						getSetCMD();
					}
				}
			}else {
				getHelp();
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
		Log.setMessage(Discord.loggy.log(Level.INFO, "- bot get activity"));
		Log.setMessage(Discord.loggy.log(Level.INFO, "- bot get botToken"));
		Log.setMessage(Discord.loggy.log(Level.INFO, "- bot get categoryIdForCreateRoom"));
		Log.setMessage(Discord.loggy.log(Level.INFO, "- bot get channelIdForCreateRoom"));
		Log.setMessage(Discord.loggy.log(Level.INFO, "- bot get memberJoinRoleId"));
		Log.setMessage(Discord.loggy.log(Level.INFO, "------------------------"));
	}
	
	private static void getSetCMD() {
		Log.setMessage(Discord.loggy.log(Level.INFO, "------ Help Bot ------"));
		Log.setMessage(Discord.loggy.log(Level.INFO, "- bot set activity <activity>"));
		Log.setMessage(Discord.loggy.log(Level.INFO, "- bot set botToken <botToken>"));
		Log.setMessage(Discord.loggy.log(Level.INFO, "- bot set categoryIdForCreateRoom <category_id>"));
		Log.setMessage(Discord.loggy.log(Level.INFO, "- bot set channelIdForCreateRoom <channel_id>"));
		Log.setMessage(Discord.loggy.log(Level.INFO, "- bot set memberJoinRoleId <role_id>"));
		Log.setMessage(Discord.loggy.log(Level.INFO, "------------------------"));
	}
	
	private static void getBotToken() throws FileNotFoundException, IOException {
		String botToken = ConfigFile.getBotToken();
		
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(botToken), null);
		Log.setMessage(Discord.loggy.log(Level.INFO, "Bot Token: " + botToken));
		Log.setMessage(Discord.loggy.log(Level.INFO, "Copied to clipboard"));
	}
	
	private static void getMemberJoinRoleID() throws FileNotFoundException, IOException {
		String memberJoinRoleID = ConfigFile.getRoleId();
		
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(memberJoinRoleID), null);
		Log.setMessage(Discord.loggy.log(Level.INFO, "RoleID: " + memberJoinRoleID));
		Log.setMessage(Discord.loggy.log(Level.INFO, "Copied to clipboard"));
	}
	
	private static void getChannelIdForCreateRoom() {
		String channelIdForCreateRoom = ConfigFile.getChannelIdForCreateRoom();
		
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(channelIdForCreateRoom), null);
		Log.setMessage(Discord.loggy.log(Level.INFO, "channelIdForCreateRoom: " + channelIdForCreateRoom));
		Log.setMessage(Discord.loggy.log(Level.INFO, "Copied to clipboard"));
	}
	
	private static void getCategoryIdForCreateRoom() {
		String categoryIdForCreateRoom = ConfigFile.getCategoryIdForCreateRoom();
		
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(categoryIdForCreateRoom), null);
		Log.setMessage(Discord.loggy.log(Level.INFO, "RoleID: " + categoryIdForCreateRoom));
		Log.setMessage(Discord.loggy.log(Level.INFO, "Copied to clipboard"));
	}

	private static void getActivity() {
		String activity = ConfigFile.getActivity();

		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(activity), null);
		Log.setMessage(Discord.loggy.log(Level.INFO, "Activity: " + activity));
		Log.setMessage(Discord.loggy.log(Level.INFO, "Copied to clipboard"));
	}
}
