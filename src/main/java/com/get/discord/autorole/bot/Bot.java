package com.get.discord.autorole.bot;

import java.io.FileReader;
import java.io.IOException;

import com.get.discord.autorole.AutoRole;
import com.get.discord.autorole.events.GuildMemberJoinEventListener;
import com.get.discord.autorole.events.ReadyEventListener;
import com.get.discord.autorole.gui.GPanel;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

public final class Bot {
	
	private static JDABuilder jda;
	
	public static void runBot() {
		try {
			AutoRole.properties.load(new FileReader(AutoRole.directory + "\\config.properties"));
		} catch (IOException e) {
			GPanel.setLog(AutoRole.log.getError(Bot.class, e.getMessage()));
		}
		
		String botToken = (AutoRole.properties.getProperty("tokenID").trim() != null) ? AutoRole.properties.getProperty("tokenID").trim() : "";
		
		System.out.println(botToken);
		jda = JDABuilder.createDefault(botToken);
		
		jda.setActivity(Activity.customStatus("Seeing ʟᴏʙʙʏ"))
		.enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES, GatewayIntent.MESSAGE_CONTENT)
		.addEventListeners(new ReadyEventListener())
		.addEventListeners(new GuildMemberJoinEventListener())
		.build();
	}
	
	public static void buildBot() {
		jda.build();
	}
}
