package com.get.discord.autorole.events;

import com.get.discord.autorole.AutoRole;
import com.get.discord.autorole.gui.GPanel;

import net.dv8tion.jda.api.events.GatewayPingEvent;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;

public final class ReadyEventListener implements EventListener{

	@Override
	public void onEvent(GenericEvent event) {
		if(!(event instanceof GatewayPingEvent)) {
			System.out.println(event);
			GPanel.setLog(AutoRole.log.getInfo(event.toString()));
			
			if(event instanceof ReadyEvent) {
				System.out.println("bot is now online.");
				GPanel.setLog(AutoRole.log.getEvent(event.toString(), "bot is now online."));
				GPanel.setLog(AutoRole.log.getInfo("For help, type\"help\" or \"?\""));
			}
		}
	}

}
