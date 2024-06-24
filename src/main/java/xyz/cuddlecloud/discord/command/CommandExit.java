package xyz.cuddlecloud.discord.command;

import xyz.cuddlecloud.discord.bot.listener.GuildVoiceUpdateListener;

public final class CommandExit {
    public static void exit() {

        GuildVoiceUpdateListener.isShutdown = false;
        GuildVoiceUpdateListener.onShuttingDown();

        System.exit(1);
    }
}
