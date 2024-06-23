package xyz.cuddlecloud.discord.bot.events;

import xyz.cuddlecloud.discord.Discord;
import xyz.cuddlecloud.discord.gui.logs.Log;
import xyz.cuddlecloud.javax.logging.Loggy.Level;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildVoiceUpdateEventListener extends ListenerAdapter{

	@Override
	public void onGuildVoiceUpdate(GuildVoiceUpdateEvent event) {
		super.onGuildVoiceUpdate(event);
		
		if(event.getNewValue() == null) {
			Log.setMessage(Discord.loggy.log(Level.INFO,event.getMember().getEffectiveName() + " left the " + event.getChannelLeft().getName() + " channel"));
		}else {
			Log.setMessage(Discord.loggy.log(Level.INFO,event.getMember().getEffectiveName() + " joined the " + event.getChannelJoined().getName() + " channel"));
		}
	}
}
