package xyz.cuddlecloud.discord.command;

import xyz.cuddlecloud.discord.bot.events.GuildVoiceUpdateEventListener;

public final class CommandExit {
    public static void exit() {

        GuildVoiceUpdateEventListener.isShutdown = false;
        GuildVoiceUpdateEventListener.onShuttingDown();

        System.exit(1);
    }
}
