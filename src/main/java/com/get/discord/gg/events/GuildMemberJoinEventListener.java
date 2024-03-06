package com.get.discord.gg.events;

import java.io.FileReader;
import java.io.IOException;

import com.get.discord.gg.Main;
import com.get.discord.gg.gui.GPanel;
import com.get.discord.gg.util.Reference;
import com.get.discord.gg.util.Utils;
import com.get.lib.logutils.LogUtil;

import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public final class GuildMemberJoinEventListener extends ListenerAdapter{

	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		super.onGuildMemberJoin(event);
		Utils.loadProperties();
		
		//GPanel.setLog(LogUtil.event(event, event, null, false));
		
		try {
			Main.properties.load(new FileReader(Reference.getConfigFile.get()));
		} catch (IOException e) {
			GPanel.setLog(LogUtil.error(LogUtil.getEnclosingMethod(new Object() {}), GuildMemberJoinEventListener.class.getSimpleName(), e, false));
		}
		
		String roldID = Main.properties.getProperty("roleID");
		
		try {
			event.getGuild().addRoleToMember(event.getMember().getUser(), event.getGuild().getRoleById(roldID)).queue();
		}catch(Exception e) {
			GPanel.setLog(LogUtil.error(LogUtil.getEnclosingMethod(new Object() {}), GuildMemberJoinEventListener.class.getSimpleName(), e, false));
		}
	}
}
