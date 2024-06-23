package xyz.cuddlecloud.discord.bot;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

import xyz.cuddlecloud.discord.Discord;
import xyz.cuddlecloud.discord.gui.logs.Log;
import xyz.cuddlecloud.discord.util.Reference;
import xyz.cuddlecloud.discord.util.Utils;
import xyz.cuddlecloud.javax.logging.Loggy.Level;

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
					Log.setMessage(Discord.loggy.log(Level.INFO, String.format("Logged in as %s#%s", seft.getUsername(), seft.getDiscriminator())));
					Log.setMessage(Discord.loggy.log(Level.INFO, "bot is now online."));
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
				else if(message.getContent().equalsIgnoreCase("() -> exit")){
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
				if(!voiceState.getData().channelId().isEmpty()) {
					Utils.loadProperties();
					if(voiceState.getData().channelId().get().toString().equalsIgnoreCase(Discord.properties.getProperty("channelIdforCreateRoom"))) {
					guildMono.flatMap(guild -> {
							return guild.createVoiceChannel(spec -> {
							spec.setParentId(Snowflake.of(Discord.properties.getProperty("categoryIdforNewRoom")));
							spec.setName(globalName.get());
						});
					  }).subscribe(voiceChannel -> {
						Log.setMessage(Discord.loggy.log(Level.INFO, voiceChannel.getName() + "(" + voiceChannel.getId().asString() + ") has been created"));
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
			    Discord.loggy.log(Level.TRACE, "Server", "voiceStateUpdateEvent", "ChannelIdBefore: " + channelIdBefore.get().asString());
			    
			    if(channelIdBefore != null) {
			    	if(voiceChannelIDList.contains(channelIdBefore.get())) {
			    		Mono<Guild> guildMono = event.getClient().getGuildById(guildID);
					    Mono<VoiceChannel> channelMono = guildMono.flatMap(guild -> guild.getChannelById(channelIdBefore.get()).ofType(VoiceChannel.class));

				    	return channelMono.flatMap(channel -> channel.getVoiceStates()
				                .count()
				                .flatMap(memberCount -> {
				                	Discord.loggy.log(Level.TRACE, "Server", "voiceStateUpdateEvent", "Member Count: " + memberCount);
				                    if (memberCount == 0) {				
				                    	Log.setMessage(Discord.loggy.log(Level.INFO, "try to delete " + channel.getName()));
				                        return channel.delete()
				                                .doOnError(error -> {
				                                	Log.setMessage(Discord.loggy.log(Level.INFO, "Error deleting voice channel: " + error.getMessage()));
				                                })
				                                .onErrorResume(error -> Mono.empty());
				                    } else {
	                                	Discord.loggy.log(Level.TRACE,"return Mono.empty() | Member : " + memberCount);
				                        return Mono.empty();
				                    }
				                }))
				                .then()
				                .doOnError(error -> {
                                	Discord.loggy.log(Level.TRACE, "Error handling VoiceStateUpdateEvent: " + error.getMessage());
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
			Log.setMessage(Discord.loggy.log(Level.INFO, a.asString()));
		}
	}
}
