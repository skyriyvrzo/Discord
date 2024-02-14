package com.get.discord.autorole.bot;

import com.get.discord.autorole.events.GuildMemberJoinEventListener;
import com.get.discord.autorole.events.ReadyEventListener;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Bot {
	
	static JDABuilder jda;
	
	public static void runBot() {
		jda = JDABuilder.createDefault("MTIwNzMxNDY2NjY0MTAzNTI5NA.G1MYGB.tdBtJIF7oYV3Y9EEjsojmfQlJUcnn_CeH4UvRg");
		
		jda.setActivity(Activity.playing("Eclipse IDE"))
		.enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES, GatewayIntent.MESSAGE_CONTENT)
		.addEventListeners(new ReadyEventListener())
		.addEventListeners(new GuildMemberJoinEventListener())
		.build();
	}
	
	public static void buildBot() {
		jda.build();
	}
}
