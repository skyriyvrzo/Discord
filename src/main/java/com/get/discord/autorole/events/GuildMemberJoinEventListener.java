package com.get.discord.autorole.events;

import java.io.FileReader;
import java.io.IOException;

import com.get.discord.autorole.AutoRole;
import com.get.discord.autorole.gui.GPanel;

import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public final class GuildMemberJoinEventListener extends ListenerAdapter{

	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		super.onGuildMemberJoin(event);
		System.out.println(event);
		GPanel.setLog(AutoRole.log.getEvent(event.toString(), event.getMember().toString()));
		
		try {
			AutoRole.properties.load(new FileReader(AutoRole.directory + "\\config.properties"));
		} catch (IOException e) {
			GPanel.setLog(AutoRole.log.getError(GuildMemberJoinEventListener.class, e.getMessage()));
		}
		String roldID = AutoRole.properties.getProperty("roleID");
		
		try {
			event.getGuild().addRoleToMember(event.getMember().getUser(), event.getGuild().getRoleById(roldID)).queue();
		}catch(Exception e) {
			GPanel.setLog(AutoRole.log.getError(GuildMemberJoinEventListener.class, e.getMessage()));
		}
		
	}
	
}
