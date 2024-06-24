package xyz.cuddlecloud.discord.bot.listener;

import java.io.FileReader;
import java.io.IOException;

import org.jetbrains.annotations.NotNull;
import xyz.cuddlecloud.discord.Discord;
import xyz.cuddlecloud.discord.gui.logs.Log;
import xyz.cuddlecloud.discord.json.ConfigFile;
import xyz.cuddlecloud.discord.util.Reference;
import xyz.cuddlecloud.discord.util.Utils;
import xyz.cuddlecloud.javax.logging.Loggy.Level;

import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public final class GuildMemberJoinListener extends ListenerAdapter{

	@Override
	public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
		super.onGuildMemberJoin(event);
		
		String roleId = ConfigFile.getRoleId();
		
		try {
			event.getGuild().addRoleToMember(event.getMember().getUser(), event.getGuild().getRoleById(roleId)).queue();
		}catch(Exception e) {
			Log.setMessage(Discord.loggy.log(Level.INFO, e));
		}
	}
}
