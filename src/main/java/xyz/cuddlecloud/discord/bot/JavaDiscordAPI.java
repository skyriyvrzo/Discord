package xyz.cuddlecloud.discord.bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import xyz.cuddlecloud.discord.Discord;
import xyz.cuddlecloud.discord.bot.events.GuildMemberJoinEventListener;
import xyz.cuddlecloud.discord.bot.events.GuildVoiceUpdateEventListener;
import xyz.cuddlecloud.discord.bot.events.ReadyEventListener;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

public final class JavaDiscordAPI {
	
	public static JDABuilder jdaBuilder;
	public static JDA jda;
	public static Thread worker1;

	@Deprecated public static Discord4J discord4j;
	
	public static void runBot() {
		
		String botToken = (Discord.properties.getProperty("tokenId") != null) ? Discord.properties.getProperty("tokenId").trim() : "";
		//String botToken = "MTIwNzMxNDY2NjY0MTAzNTI5NA.GuzQNY.p01Aty4m86D_AeDXjZKYm-07CbbsnnDAMWoHUU";
		
		//System.out.println(botToken);
		jdaBuilder = JDABuilder.createDefault(botToken);

		jdaBuilder.setActivity(Activity.customStatus("Seeing ʟᴏʙʙʏ"))
		.enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES, GatewayIntent.MESSAGE_CONTENT)
		.addEventListeners(new ReadyEventListener())
		.addEventListeners(new GuildMemberJoinEventListener())
		.addEventListeners(new GuildVoiceUpdateEventListener());

		worker1 = new Thread(new Runnable() {
			@Override
			public void run() {
				GuildVoiceUpdateEventListener.randomRemoveChannelByWorker();
			}
		});
		//worker1.start();

        try {
            jda = jdaBuilder.build().awaitReady();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //discord4j = new Discord4J();
	}

	@Deprecated
	public static void buildBot() {
		jdaBuilder.build();
		discord4j.client.login().block();
	}
}
