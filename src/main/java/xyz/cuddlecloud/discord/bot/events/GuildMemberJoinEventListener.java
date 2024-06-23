package xyz.cuddlecloud.discord.bot.events;

import java.io.FileReader;
import java.io.IOException;

import xyz.cuddlecloud.discord.Discord;
import xyz.cuddlecloud.discord.gui.logs.Log;
import xyz.cuddlecloud.discord.util.Reference;
import xyz.cuddlecloud.discord.util.Utils;
import xyz.cuddlecloud.javax.logging.Loggy.Level;

import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public final class GuildMemberJoinEventListener extends ListenerAdapter{

	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		super.onGuildMemberJoin(event);
		Utils.loadProperties();
		
		//Log.setMessage(LogUtil.event(event, event, null, false));
		
		try {
			Discord.properties.load(new FileReader(Reference.configFile.get()));
		} catch (IOException e) {
			Log.setMessage(Discord.loggy.log(Level.INFO, e));
		}
		
		String roldID = Discord.properties.getProperty("roleID");
		
		try {
			event.getGuild().addRoleToMember(event.getMember().getUser(), event.getGuild().getRoleById(roldID)).queue();
		}catch(Exception e) {
			Log.setMessage(Discord.loggy.log(Level.INFO, e));
		}
	}
}
