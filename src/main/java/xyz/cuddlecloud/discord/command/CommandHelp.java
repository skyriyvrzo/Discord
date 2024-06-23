package xyz.cuddlecloud.discord.command;

import xyz.cuddlecloud.javax.logging.Loggy;
import xyz.cuddlecloud.discord.Discord;
import xyz.cuddlecloud.discord.gui.logs.Log;

public final class CommandHelp {
    public static void sendHelp() {
        Log.setMessage(Discord.loggy.log(Loggy.Level.INFO, "------ Help ------"));
        Log.setMessage(Discord.loggy.log(Loggy.Level.INFO, "- bot"));
        Log.setMessage(Discord.loggy.log(Loggy.Level.INFO, "- open"));
        Log.setMessage(Discord.loggy.log(Loggy.Level.INFO, "- exit"));
        Log.setMessage(Discord.loggy.log(Loggy.Level.INFO, "------------------------"));
    }
}
