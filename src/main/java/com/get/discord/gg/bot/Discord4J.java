package com.get.discord.gg.bot;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

import com.get.discord.gg.Discord;
import com.get.discord.gg.gui.GPanel;
import com.get.discord.gg.util.Reference;
import com.get.discord.gg.util.Utils;
import com.get.lib.logutils.LogUtil;

import discord4j.common.util.Snowflake;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.VoiceStateUpdateEvent;
import discord4j.core.event.domain.channel.VoiceChannelCreateEvent;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.VoiceState;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import discord4j.core.object.entity.channel.VoiceChannel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public final class Discord4J {

	private static Snowflake userID;
	private static Optional<String> globalName;
	private static Snowflake guildID;
	private static ArrayList<Snowflake> voiceChannelIDList = new ArrayList<>();
	public DiscordClient client;
	
	public Discord4J() {
		Utils.loadProperties();
		client = DiscordClient.create((Discord.properties.getProperty("tokenId") != null) ? Discord.properties.getProperty("tokenId").trim() : "");
		
		@SuppressWarnings("deprecation")
		Mono<Void> login = client.withGateway((GatewayDiscordClient gateway) -> {
			Mono<Void> printOnLogin = gateway.on(ReadyEvent.class, event ->
				Mono.fromRunnable(() -> {
					final User seft = event.getSelf();
					GPanel.setLog(LogUtil.discord("Server thread", Discord4J.class.getSimpleName(), String.format("Logged in as %s#%s", seft.getUsername(), seft.getDiscriminator()), true, true));
					GPanel.setLog(LogUtil.discord("Server thread", Discord4J.class.getSimpleName(), "bot is now online.", true, true));
					GPanel.setLog(LogUtil.info("For help, type \"help\" or \"?\"", true, true));
				}))
				.then();
			
			Mono<Void> handleCommand = gateway.on(MessageCreateEvent.class, event -> {
				Message message = event.getMessage();
				Optional<User> user = event.getMessage().getAuthor();
				
				if(message.getContent().equalsIgnoreCase("hi bot")) {
					return message.getChannel().flatMap(channel -> channel.createMessage("Hi " + user.get().getGlobalName().get()));
				}
				else if(message.getContent().equalsIgnoreCase("myuserdata")) {
					return message.getChannel().flatMap(channel -> channel.createMessage(String.valueOf(user.get().getUserData())));
				}
				else if(message.getContent().equalsIgnoreCase("bot version") || message.getContent().equalsIgnoreCase("(b) -> b.ver")) {
					return message.getChannel().flatMap(channel -> channel.createMessage(Reference.VERSIONS));
				}
				else if(message.getContent().equalsIgnoreCase("(b) -> exit")){
					System.exit(0);
				}
				
				return Mono.empty();
			}).then();
			
			Mono<Void> voiceJoinEvent = gateway.on(VoiceStateUpdateEvent.class, event -> {
				userID = event.getCurrent().getUserId();
				VoiceState voiceState = event.getCurrent();
				Random random = new Random();
				globalName = voiceState.getData().member().get().user().globalName().isEmpty() ? Optional.ofNullable("Room " + random.nextInt(100) + ((char) (random.nextInt(26) + 65))) : voiceState.getData().member().get().user().globalName();
				guildID = voiceState.getGuildId();
	
				Mono<Guild> guildMono = event.getClient().getGuildById(guildID);
				if(voiceState.getData().channelId().isEmpty() == false) {
					Utils.loadProperties();
					if(voiceState.getData().channelId().get().toString().equalsIgnoreCase(Discord.properties.getProperty("channelIdforCreateRoom"))) {
					guildMono.flatMap(guild -> {
							return guild.createVoiceChannel(spec -> {
							spec.setParentId(Snowflake.of(Discord.properties.getProperty("categoryIdforNewRoom")));
							spec.setName(globalName.get());
						});
					  }).subscribe(voiceChannel -> {
						GPanel.setLog(LogUtil.event(Discord4J.class.getSimpleName(), "channel-create", voiceChannel.getName() + "(" + voiceChannel.getId().asString() + ") has been created", true, true));
						if(!voiceChannelIDList.contains(voiceChannel.getId())) {
							voiceChannelIDList.add(voiceChannel.getId());
						}	
					  });
					}
				}
				return Mono.empty();
			}).then();
			
			Flux<Void> voiceChannelCreateEvent = gateway.on(VoiceChannelCreateEvent.class, event -> {
			    Snowflake voiceChannelID = event.getChannel().getId();
			    Mono<Guild> guildMono = event.getClient().getGuildById(guildID);

			    return guildMono.flatMap(guild -> {
			        Mono<Member> memberMono = guild.getMemberById(userID);

			        return memberMono.flatMap(member -> {
			            Mono<VoiceChannel> targetChannelMono = guild.getChannelById(voiceChannelID).ofType(VoiceChannel.class);

			            return targetChannelMono.flatMap(targetChannel -> {
			                return member.edit(spec -> spec.setNewVoiceChannel(targetChannel.getId())).then();
			            });
			        });
			    }).then();
			});
			
			Flux<Void> voiceStateUpdateEvent = gateway.on(VoiceStateUpdateEvent.class, event -> {
			    Optional<Snowflake> channelIdBefore = event.getOld().map(VoiceState::getChannelId).orElse(null);
			    LogUtil.event("Server thread", "voiceStateUpdateEvent", "ChannelIdBefore: " + channelIdBefore.get().asString(), true, true);
			    
			    if(channelIdBefore != null) {
			    	if(voiceChannelIDList.contains(channelIdBefore.get())) {
			    		Mono<Guild> guildMono = event.getClient().getGuildById(guildID);
					    Mono<VoiceChannel> channelMono = guildMono.flatMap(guild -> guild.getChannelById(channelIdBefore.get()).ofType(VoiceChannel.class));

				    	return channelMono.flatMap(channel -> channel.getVoiceStates()
				                .count()
				                .flatMap(memberCount -> {
				                	LogUtil.event("Server thread", "voiceStateUpdateEvent", "Member Count: " + memberCount, true, true);
				                    if (memberCount == 0) {				
				                    	GPanel.setLog(LogUtil.event("Server thread", "channel-delete", "try to delete " + channel.getName(), true, true));
				                        return channel.delete()
				                                .doOnError(error -> {
				                                	GPanel.setLog(LogUtil.warn("Server thread", "voiceStateUpdateEvent", "Error deleting voice channel: " + error.getMessage(), true, true));
				                                })
				                                .onErrorResume(error -> Mono.empty());
				                    } else {
	                                	LogUtil.event("Server thread", "voiceStateUpdateEvent", "return Mono.empty() | Member : " + memberCount, true, true);
				                        return Mono.empty();
				                    }
				                }))
				                .then()
				                .doOnError(error -> {
                                	LogUtil.warn("Server thread", "voiceStateUpdateEvent", "Error handling VoiceStateUpdateEvent: " + error.getMessage(), true, true);
				                })
				                .onErrorResume(error -> Mono.empty());
			    	}
			    }
			    
			    return Mono.empty();
			});
			
			return printOnLogin.and(handleCommand).and(voiceJoinEvent).and(voiceChannelCreateEvent).and(voiceStateUpdateEvent);
		});
		
		login.block();
	}
	
	public static void getChannelIdList() {
		for(var a : voiceChannelIDList) {
			GPanel.setLog(LogUtil.info(LogUtil.getEnclosingMethod(new Object() {}), Discord4J.class.getSimpleName(), a.asString(), true, true));
		}
	}
}
