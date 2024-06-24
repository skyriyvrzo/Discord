package xyz.cuddlecloud.discord.bot.listener;

import org.jetbrains.annotations.NotNull;
import xyz.cuddlecloud.discord.Discord;
import xyz.cuddlecloud.discord.gui.logs.Log;
import xyz.cuddlecloud.javax.logging.Loggy.Level;

import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.StatusChangeEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;

public final class ReadyListener implements EventListener{

	@Override
	public void onEvent(@NotNull GenericEvent event) {
		if(event instanceof ReadyEvent r) {
			Log.setMessage(Discord.loggy.log(Level.INFO, "bot is ready."));
		}
		else if(event instanceof StatusChangeEvent s) {
			Log.setMessage(Discord.loggy.log(Level.INFO, String.valueOf(s.getOldStatus()).toLowerCase() + " -> " + String.valueOf(s.getNewStatus()).toLowerCase()));
		}
	}

}
