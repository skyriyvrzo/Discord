package com.get.discord.gg.bot.events;

import com.get.discord.gg.Discord;
import com.get.discord.gg.gui.logs.Log;
import com.get.lib.Logging.Loggy.Level;

import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildVoiceUpdateEventListener extends ListenerAdapter{

	@Override
	public void onGuildVoiceUpdate(GuildVoiceUpdateEvent event) {
		super.onGuildVoiceUpdate(event);
		
		if(event.getNewValue() == null) {
			Log.setMessage(Discord.loggy.log(Level.INFO, GuildVoiceUpdateEvent.class.getSimpleName(), event.getPropertyIdentifier(), event.getMember().getEffectiveName() + " left the " + event.getChannelLeft().getName() + " channel"));
		}else {
			Log.setMessage(Discord.loggy.log(Level.INFO, GuildVoiceUpdateEvent.class.getSimpleName(), event.getPropertyIdentifier(), event.getMember().getEffectiveName() + " joined the " + event.getChannelJoined().getName() + " channel"));
		}
	}
}
