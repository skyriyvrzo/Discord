package com.get.discord.gg.bot;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

import com.get.discord.gg.Main;
import com.get.discord.gg.gui.GPanel;

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
	
	public Discord4J() {
		
		DiscordClient client = DiscordClient.create((Main.properties.getProperty("tokenId") != null) ? Main.properties.getProperty("tokenId").trim() : "");
		
		@SuppressWarnings("deprecation")
		Mono<Void> login = client.withGateway((GatewayDiscordClient gateway) -> {
			Mono<Void> printOnLogin = gateway.on(ReadyEvent.class, event ->
				Mono.fromRunnable(() -> {
					final User seft = event.getSelf();
					GPanel.setLog(Main.log.getDiscord(String.format("Logged in as %s#%s", seft.getUsername(), seft.getDiscriminator())));
					GPanel.setLog(Main.log.getDiscord("bot is now online."));
					GPanel.setLog(Main.log.getInfo("For help, type \"help\" or \"?\""));
				}))
				.then();
			
			Mono<Void> handleCommand = gateway.on(MessageCreateEvent.class, event -> {
				Message message = event.getMessage();
				Optional<User> user = event.getMessage().getAuthor();
				
				if(message.getContent().equalsIgnoreCase("hi bot")) {
					System.out.println(message.getContent());
					return message.getChannel().flatMap(channel -> channel.createMessage("Hi " + user.get().getGlobalName().get()));
				}
				else if(message.getContent().equalsIgnoreCase("myuserdata")) {
					return message.getChannel().flatMap(channel -> channel.createMessage(String.valueOf(user.get().getUserData())));
				}
				
				return Mono.empty();
			}).then();
			
			Mono<Void> voiceJoinEvent = gateway.on(VoiceStateUpdateEvent.class, event -> {
				//System.out.println("Voice State Update Event [Join]");
				GPanel.setLog(Main.log.getInfo(String.valueOf(event)));
				userID = event.getCurrent().getUserId();
				VoiceState voiceState = event.getCurrent();
				Random random = new Random();
				globalName = voiceState.getData().member().get().user().globalName().isEmpty() ? Optional.ofNullable("Room " + random.nextInt(100) + ((char) (random.nextInt(26) + 65))) : voiceState.getData().member().get().user().globalName();
				guildID = voiceState.getGuildId();
				
				//System.out.println("Global Name: " + globalName);
				//System.out.println(guildID);
				
				Mono<Guild> guildMono = event.getClient().getGuildById(guildID);
				//System.out.println(guildMono);
				//System.out.println("User ID: " + voiceState.getData().member().get().user().id() + "\n" + "Global Name: " + globalName.get());
				
				//System.out.println("Voice State: " + voiceState);
				//System.out.println("is Bot: " + voiceState.getData().member().get().user().bot());
				//System.out.println("is Empty: " + (voiceState.getData().channelId().isEmpty()));
				
				if(voiceState.getData().channelId().isEmpty() == false) {
					if(voiceState.getData().channelId().get().toString().equalsIgnoreCase(Main.properties.getProperty("channelIdforCreateRoom"))) {
					guildMono.flatMap(guild -> {
							return guild.createVoiceChannel(spec -> {
							spec.setParentId(Snowflake.of(Main.properties.getProperty("categoryIdforNewRoom")));
							spec.setName(globalName.get());
						});
					  }).subscribe(voiceChannel -> {
						System.out.println("Voice channel created: " + voiceChannel.getName() + ":" + voiceChannel.getId());
						
						if(!voiceChannelIDList.contains(voiceChannel.getId())) {
							voiceChannelIDList.add(voiceChannel.getId());
						}	
					  });
					}
				}
				
				return Mono.empty();
				
			}).then();
			
			Flux<Void> voiceChannelCreateEvent = gateway.on(VoiceChannelCreateEvent.class, event -> {
				GPanel.setLog(Main.log.getInfo(String.valueOf(event)));
			    //System.out.println("Voice Create Event");
			    
			    Snowflake voiceChannelID = event.getChannel().getId();
			    //System.out.println("Channel ID: " + voiceChannelID.asLong());
			    
			    Mono<Guild> guildMono = event.getClient().getGuildById(guildID);
			    //System.out.println(guildMono);

			    return guildMono.flatMap(guild -> {
			        System.out.println("Step 0");
			        Mono<Member> memberMono = guild.getMemberById(userID);
			        //System.out.println("Step 1");

			        return memberMono.flatMap(member -> {
			            Mono<VoiceChannel> targetChannelMono = guild.getChannelById(voiceChannelID).ofType(VoiceChannel.class);
			            //System.err.println("Step 2");

			            return targetChannelMono.flatMap(targetChannel -> {
			                //System.out.println("Step 3");
			                return member.edit(spec -> spec.setNewVoiceChannel(targetChannel.getId())).then();
			            });
			        });
			    }).then();
			});
			
			Flux<Void> voiceStateUpdateEvent = gateway.on(VoiceStateUpdateEvent.class, event -> {
				GPanel.setLog(Main.log.getInfo(String.valueOf(event)));
				
				//System.out.println("Voice State Update Event");
				
			    Optional<Snowflake> channelIdBefore = event.getOld().map(VoiceState::getChannelId).orElse(null);
			    
			    if(channelIdBefore != null) {
			    	if(voiceChannelIDList.contains(channelIdBefore.get())) {
			    		Mono<Guild> guildMono = event.getClient().getGuildById(guildID);
					    Mono<VoiceChannel> channelMono = guildMono.flatMap(guild -> guild.getChannelById(channelIdBefore.get()).ofType(VoiceChannel.class));

				    	return channelMono.flatMap(channel -> channel.getVoiceStates()
				                .count()
				                .flatMap(memberCount -> {
				                    if (memberCount == 0) {
				                        return channel.delete()
				                                .doOnError(error -> {
				                                    System.err.println("Error deleting voice channel: " + error.getMessage());
				                                })
				                                .onErrorResume(error -> Mono.empty());
				                    } else {
				                        return Mono.empty();
				                    }
				                }))
				                .then()
				                .doOnError(error -> {
				                    System.err.println("Error handling VoiceStateUpdateEvent: " + error.getMessage());
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
}
