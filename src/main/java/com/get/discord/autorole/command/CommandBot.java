package com.get.discord.autorole.command;

import java.io.IOException;

import javax.swing.JTextField;

import com.get.discord.autorole.AutoRole;
import com.get.discord.autorole.gui.GPanel;

public class CommandBot {

	public static String role_id;
	
	public static void conditional(JTextField tf) throws IOException {
		
		String[] split = tf.getText().split(" ");
		
		if(tf.getText().contains("bot set MemberJoinRole") && split.length > 3) {
			System.out.println("set");
			setMemberJoinRoleByID(split[3]);
		}else {
			System.out.println("help");
			getHelp();
		}
	}
	
	public static void getHelp() {
		GPanel.setLog(AutoRole.log.getInfo("------ Help Bot ------"));
		GPanel.setLog(AutoRole.log.getInfo("- bot set MemberJoinRole <role_id>"));
	}
	
	private static void setMemberJoinRoleByID(String id) throws IOException {
		AutoRole.setRoleID(id);
	}
}
