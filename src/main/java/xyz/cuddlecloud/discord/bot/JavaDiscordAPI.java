package xyz.cuddlecloud.discord.bot;

import xyz.cuddlecloud.discord.Discord;
import xyz.cuddlecloud.discord.bot.events.GuildMemberJoinEventListener;
import xyz.cuddlecloud.discord.bot.events.GuildVoiceUpdateEventListener;
import xyz.cuddlecloud.discord.bot.events.ReadyEventListener;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

public final class JavaDiscordAPI {
	
	public static JDABuilder jda;
	public static Discord4J discord4j;
	
	public static void runBot() {
		
		String botToken = (Discord.properties.getProperty("tokenId") != null) ? Discord.properties.getProperty("tokenId").trim() : "";
		//String botToken = "MTIwNzMxNDY2NjY0MTAzNTI5NA.GuzQNY.p01Aty4m86D_AeDXjZKYm-07CbbsnnDAMWoHUU";
		
		//System.out.println(botToken);
		jda = JDABuilder.createDefault(botToken);
		
		jda.setActivity(Activity.customStatus("Seeing ʟᴏʙʙʏ"))
		.enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES, GatewayIntent.MESSAGE_CONTENT)
		.addEventListeners(new ReadyEventListener())
		.addEventListeners(new GuildMemberJoinEventListener())
		.addEventListeners(new GuildVoiceUpdateEventListener())
		.build();
		
		discord4j = new Discord4J();
	}
	
	public static void buildBot() {
		jda.build();
		discord4j.client.login().block();
	}
}
