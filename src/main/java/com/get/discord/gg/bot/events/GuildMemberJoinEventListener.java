package com.get.discord.gg.bot.events;

import java.io.FileReader;
import java.io.IOException;

import com.get.discord.gg.Discord;
import com.get.discord.gg.gui.GraphicalUserInterface;
import com.get.discord.gg.gui.logs.Log;
import com.get.discord.gg.util.Reference;
import com.get.discord.gg.util.Utils;
import com.get.lib.Logging.Loggy.Level;

import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public final class GuildMemberJoinEventListener extends ListenerAdapter{

	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		super.onGuildMemberJoin(event);
		Utils.loadProperties();
		
		//Log.setMessage(LogUtil.event(event, event, null, false));
		
		try {
			Discord.properties.load(new FileReader(Reference.getConfigFile.get()));
		} catch (IOException e) {
			Log.setMessage(GraphicalUserInterface.loggy.log(Level.INFO, "onGuildMemberJoin", GuildMemberJoinEventListener.class.getSimpleName(), e));
		}
		
		String roldID = Discord.properties.getProperty("roleID");
		
		try {
			event.getGuild().addRoleToMember(event.getMember().getUser(), event.getGuild().getRoleById(roldID)).queue();
		}catch(Exception e) {
			Log.setMessage(GraphicalUserInterface.loggy.log(Level.INFO, "onGuildMemberJoin", GuildMemberJoinEventListener.class.getSimpleName(), e));
		}
	}
}
