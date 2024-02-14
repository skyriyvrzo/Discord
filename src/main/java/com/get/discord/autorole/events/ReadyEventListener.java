package com.get.discord.autorole.events;

import com.get.discord.autorole.AutoRole;
import com.get.discord.autorole.log.LogGUI;

import net.dv8tion.jda.api.events.GatewayPingEvent;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;

public class ReadyEventListener implements EventListener{

	@Override
	public void onEvent(GenericEvent event) {
		System.out.println(event);
		if(event instanceof ReadyEvent && !(event instanceof GatewayPingEvent)) {
			LogGUI.setLog(AutoRole.log.getEvent(event.toString(), "bot is ready."));
		}
	}

}
