package xyz.cuddlecloud.discord.gui.logs.logandchat;

import javax.swing.JTextField;

import xyz.cuddlecloud.discord.Discord;
import xyz.cuddlecloud.discord.gui.logs.Log;
import com.get.lib.Logging.Loggy.Level;

public final class CommandException {

	public static void unknowCommandException(JTextField tf) {
		Log.setMessage(Discord.loggy.log(Level.WARN, "Unknown or incomplete command, see below for error"));
		Log.setMessage(Discord.loggy.log(Level.WARN, tf.getText() + "<--[Here]"));
	}
}
