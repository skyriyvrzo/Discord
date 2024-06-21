package xyz.cuddlecloud.discord.gui.commands;

import xyz.cuddlecloud.discord.Discord;
import xyz.cuddlecloud.discord.gui.logs.Log;
import com.get.lib.Logging.Loggy.Level;

public final class CommandHelp implements ICommand{

	@Override
	public String getName() {
		return "help";
	}

	@Override
	public void getHelp() {
		Log.setMessage(Discord.loggy.log(Level.INFO, "[--- help ---]"));
		Log.setMessage(Discord.loggy.log(Level.INFO, "- " + RegisterCommand.exit.getName()));
		Log.setMessage(Discord.loggy.log(Level.INFO, "[-------------]"));
	}
	
	@Override
	public void run() {
		getHelp();
	}
}
