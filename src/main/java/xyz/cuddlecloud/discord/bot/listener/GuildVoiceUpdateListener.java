package xyz.cuddlecloud.discord.bot.listener;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import org.jetbrains.annotations.NotNull;
import xyz.cuddlecloud.discord.Discord;
import xyz.cuddlecloud.discord.bot.JavaDiscordAPI;
import xyz.cuddlecloud.discord.gui.logs.Log;
import xyz.cuddlecloud.discord.json.ConfigFile;
import xyz.cuddlecloud.javax.logging.Loggy.Level;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class GuildVoiceUpdateListener extends ListenerAdapter {

	static Guild guild;
	static Map<String, String> channelId = new ConcurrentHashMap<>();
	Thread worker2;
	public static Boolean isShutdown = true;

	@Override
	public void onGuildVoiceUpdate(@NotNull GuildVoiceUpdateEvent event) {
		super.onGuildVoiceUpdate(event);

		guild = JavaDiscordAPI.jda.getGuildById(event.getGuild().getId());

		if (event.getNewValue() == null) {
			Log.setMessage(Discord.loggy.log(Level.INFO, event.getMember().getEffectiveName() + " left the " + event.getChannelLeft().getName() + " channel"));

			/**
			 * Delete channel(s).
			 */
			worker2 = new Thread(new Runnable() {
				@Override
				public void run() {
					for(String s : channelId.values()) {
						VoiceChannel voiceChannel = guild.getVoiceChannelById(s);

						assert voiceChannel != null;
						if(voiceChannel.getMembers().isEmpty()) {
							String oldName = voiceChannel.getName();
							voiceChannel.getManager().setName(oldName + " [Removing in 5s]").queue();
							for(int i = 5; i >= 0; i--) {
								voiceChannel = guild.getVoiceChannelById(s);
                                assert voiceChannel != null;
                                if(voiceChannel.getMembers().isEmpty()) {
									System.out.println(voiceChannel.getMembers().isEmpty() + " | " + i + " | " + voiceChannel);
									try {
										Thread.sleep(1000L);
									} catch (InterruptedException e) {
										throw new RuntimeException(e);
									}

									if(i == 0) {
										voiceChannel.delete().queue();
										channelId.remove(s);
										Log.setMessage(Discord.loggy.log(Level.INFO, String.format("VoiceChannel[name=%s, id%s] -> removed", voiceChannel.getName(), voiceChannel.getId())));
									}
								}else {
									voiceChannel.getManager().setName(oldName).queue();
								}
							}
						}
					}
				}
			});
			worker2.start();

		} else {
			Log.setMessage(Discord.loggy.log(Level.INFO, event.getMember().getEffectiveName() + " joined the " + event.getChannelJoined().getName() + " channel"));

			/**
			 * Create channel(s).
			 */
			if(!isShutdown) return;
			assert guild != null;
			Category category = guild.getCategoryById(ConfigFile.getCategoryIdForCreateRoom());
			Member member = event.getMember();

			if (event.getChannelJoined().getId().equals(ConfigFile.getChannelIdForCreateRoom())) {
				assert category != null;
				String name = event.getMember().getEffectiveName().isEmpty() ? "null" : event.getMember().getEffectiveName();
				category.createVoiceChannel(name).queue(voiceChannel -> {
					channelId.put(voiceChannel.getId(), voiceChannel.getId());
					Log.setMessage(Discord.loggy.log(Level.INFO, String.format("VoiceChannel[name=%s, id%s] -> created", voiceChannel.getName(), voiceChannel.getId())));
					guild.moveVoiceMember(member, voiceChannel).queue();
				});}
		}
	}

	@Deprecated
	public static void randomRemoveChannelByWorker() {

		while (true) {
			for (String s : channelId.values()) {
				VoiceChannel voiceChannel = guild.getVoiceChannelById(s);

				assert voiceChannel != null;
				if (voiceChannel.getMembers().isEmpty()) {
					String oldName = voiceChannel.getName();
					voiceChannel.getManager().setName(oldName + " [Pending Remove]").queue();
					for (int i = 5; i >= 0; i--) {
						if (voiceChannel.getMembers().isEmpty()) {
							//System.out.println(voiceChannel.getMembers().isEmpty() + " | " + i + " | " + voiceChannel);
							try {
								Thread.sleep(1000L);
							} catch (InterruptedException e) {
								throw new RuntimeException(e);
							}

							if (i == 0) {
								voiceChannel.delete().queue();
								channelId.remove(s);
							}
						} else {
							voiceChannel.getManager().setName(oldName).queue();
						}
					}
				}
			}

            try {
				Discord.loggy.log(Level.TRACE, JavaDiscordAPI.worker1.getName() + " wait");
                Thread.sleep(60000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
	}

	public static void onShuttingDown() {
		for(String s : channelId.values()) {
			VoiceChannel v = guild.getVoiceChannelById(s);
            assert v != null;
            v.delete().queue();
			Log.setMessage(Discord.loggy.log(Level.INFO, String.format("removed VoiceChannel[name=%s, id%s]", v.getName(), v.getId())));
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                Log.setMessage(Discord.loggy.log(Level.ERROR, e));
            }
        }

		if(JavaDiscordAPI.jda != null) {
			JavaDiscordAPI.jda.shutdownNow();
		}
	}
}
