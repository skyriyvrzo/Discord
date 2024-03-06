package com.get.discord.gg.events;

import com.get.discord.gg.gui.GPanel;
import com.get.lib.logutils.LogUtil;

import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.StatusChangeEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;

public final class ReadyEventListener implements EventListener{

	@Override
	public void onEvent(GenericEvent event) {
		if(event instanceof ReadyEvent r) {
			GPanel.setLog(LogUtil.event(ReadyEvent.class.getSimpleName(), r.getState(), "bot is ready.", false));
		}
		else if(event instanceof StatusChangeEvent s) {
			GPanel.setLog(LogUtil.event(StatusChangeEvent.class.getSimpleName(), s.getPropertyIdentifier(), s.getOldStatus() + " -> " + s.getNewStatus(), false));
		}
	}

}
