package xyz.cuddlecloud.discord.bot;

import net.dv8tion.jda.api.JDA;
import xyz.cuddlecloud.discord.Discord;
import xyz.cuddlecloud.discord.bot.listener.GuildMemberJoinListener;
import xyz.cuddlecloud.discord.bot.listener.GuildVoiceUpdateListener;
import xyz.cuddlecloud.discord.bot.listener.ReadyListener;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import xyz.cuddlecloud.discord.bot.listener.ShutdownListener;
import xyz.cuddlecloud.discord.json.ConfigFile;

public final class JavaDiscordAPI {
	
	public static JDABuilder jdaBuilder;
	public static JDA jda;
	public static Thread worker1;

	@Deprecated public static Discord4J discord4j;
	
	public static void runBot() {
		
		String botToken = ConfigFile.getBotToken();

		jdaBuilder = JDABuilder.createDefault(botToken);

		jdaBuilder.setActivity(Activity.customStatus(ConfigFile.getActivity()))
				.enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES, GatewayIntent.MESSAGE_CONTENT)
				.addEventListeners(new ReadyListener())
				.addEventListeners(new GuildMemberJoinListener())
				.addEventListeners(new GuildVoiceUpdateListener())
				.addEventListeners(new ShutdownListener());

		{
			worker1 = new Thread(new Runnable() {
				@Override
				public void run() {
					GuildVoiceUpdateListener.randomRemoveChannelByWorker();
				}
			});
			//worker1.start();
		}

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
