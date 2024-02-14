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
		//System.out.println(event);
		if(event instanceof ReadyEvent) {
			System.out.println("bot is now online.");
			LogGUI.setLog(AutoRole.log.getEvent(event.toString(), "bot is now online."));
			LogGUI.setLog(AutoRole.log.getInfo("For help, type\"help\" or \"?\""));
		}
		
		if(!(event instanceof GatewayPingEvent)) {
			System.out.println(event);
			LogGUI.setLog(AutoRole.log.getEvent(event.toString(), event.toString()));
		}
	}

}
