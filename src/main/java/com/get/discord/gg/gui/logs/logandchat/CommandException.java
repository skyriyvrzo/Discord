package com.get.discord.gg.gui.logs.logandchat;

import javax.swing.JTextField;

import com.get.discord.gg.gui.GraphicalUserInterface;
import com.get.discord.gg.gui.logs.Log;
import com.get.lib.Logging.Loggy.Level;

public final class CommandException {

	public static void unknowCommandException(JTextField tf) {
		Log.setMessage(GraphicalUserInterface.loggy.log(Level.WARN, "Unknown or incomplete command, see below for error"));
		Log.setMessage(GraphicalUserInterface.loggy.log(Level.WARN, tf.getText() + "<--[Here]"));
	}
}
