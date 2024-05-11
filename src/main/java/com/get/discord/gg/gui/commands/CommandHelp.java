package com.get.discord.gg.gui.commands;

import com.get.discord.gg.gui.GraphicalUserInterface;
import com.get.discord.gg.gui.logs.Log;
import com.get.lib.Logging.Loggy.Level;

public final class CommandHelp implements ICommand{

	@Override
	public String getName() {
		return "help";
	}

	@Override
	public void getHelp() {
		Log.setMessage(GraphicalUserInterface.loggy.log(Level.INFO, "[--- help ---]"));
		Log.setMessage(GraphicalUserInterface.loggy.log(Level.INFO, "- " + RegisterCommand.exit.getName()));
		Log.setMessage(GraphicalUserInterface.loggy.log(Level.INFO, "[-------------]"));
	}
	
	@Override
	public void run() {
		getHelp();
	}
}
