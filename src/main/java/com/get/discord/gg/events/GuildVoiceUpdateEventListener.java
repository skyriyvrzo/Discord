package com.get.discord.gg.events;

import com.get.discord.gg.gui.GPanel;
import com.get.lib.logutils.LogUtil;

import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildVoiceUpdateEventListener extends ListenerAdapter{

	@Override
	public void onGuildVoiceUpdate(GuildVoiceUpdateEvent event) {
		super.onGuildVoiceUpdate(event);
		
		if(event.getNewValue() == null) {
			GPanel.setLog(LogUtil.event(GuildVoiceUpdateEvent.class.getSimpleName(), event.getPropertyIdentifier(), event.getMember().getEffectiveName() + " left the " + event.getChannelLeft().getName() + " channel", false));
		}else {
			GPanel.setLog(LogUtil.event(GuildVoiceUpdateEvent.class.getSimpleName(), event.getPropertyIdentifier(), event.getMember().getEffectiveName() + " joined the " + event.getChannelJoined().getName() + " channel", false));
		}
	}
}
