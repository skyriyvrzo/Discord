package com.get.discord.gg.events;

import java.io.FileReader;
import java.io.IOException;

import com.get.discord.gg.Main;
import com.get.discord.gg.gui.GPanel;
import com.get.discord.gg.util.Reference;

import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public final class GuildMemberJoinEventListener extends ListenerAdapter{

	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		super.onGuildMemberJoin(event);
		System.out.println(event);
		GPanel.setLog(Main.log.getEvent(event.toString(), event.getMember().toString()));
		
		try {
			Main.properties.load(new FileReader(Reference.getConfigFile.get()));
		} catch (IOException e) {
			GPanel.setLog(Main.log.getError(GuildMemberJoinEventListener.class, e.getMessage()));
		}
		String roldID = Main.properties.getProperty("roleID");
		
		try {
			event.getGuild().addRoleToMember(event.getMember().getUser(), event.getGuild().getRoleById(roldID)).queue();
		}catch(Exception e) {
			GPanel.setLog(Main.log.getError(GuildMemberJoinEventListener.class, e.getMessage()));
		}
		
	}
	
}
