package com.get.discord.gg.bot.events;

import com.get.discord.gg.gui.GraphicalUserInterface;
import com.get.discord.gg.gui.logs.Log;
import com.get.lib.Logging.Loggy.Level;

import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.StatusChangeEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;

public final class ReadyEventListener implements EventListener{

	@Override
	public void onEvent(GenericEvent event) {
		if(event instanceof ReadyEvent r) {
			Log.setMessage(GraphicalUserInterface.loggy.log(Level.INFO, ReadyEvent.class.getSimpleName(), r.getState(), "bot is ready."));
		}
		else if(event instanceof StatusChangeEvent s) {
			Log.setMessage(GraphicalUserInterface.loggy.log(Level.INFO, StatusChangeEvent.class.getSimpleName(), s.getPropertyIdentifier(), s.getOldStatus() + " -> " + s.getNewStatus()));
		}
	}

}
