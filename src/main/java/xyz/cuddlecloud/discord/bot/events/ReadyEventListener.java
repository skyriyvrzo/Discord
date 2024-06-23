package xyz.cuddlecloud.discord.bot.events;

import xyz.cuddlecloud.discord.Discord;
import xyz.cuddlecloud.discord.gui.logs.Log;
import xyz.cuddlecloud.javax.logging.Loggy.Level;

import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.StatusChangeEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;

public final class ReadyEventListener implements EventListener{

	@Override
	public void onEvent(GenericEvent event) {
		if(event instanceof ReadyEvent r) {
			Log.setMessage(Discord.loggy.log(Level.INFO, "bot is ready."));
		}
		else if(event instanceof StatusChangeEvent s) {
			Log.setMessage(Discord.loggy.log(Level.INFO, String.valueOf(s.getOldStatus()).toLowerCase() + " -> " + String.valueOf(s.getNewStatus()).toLowerCase()));
		}
	}

}
