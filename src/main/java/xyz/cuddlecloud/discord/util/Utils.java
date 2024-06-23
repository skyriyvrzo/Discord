package xyz.cuddlecloud.discord.util;

import java.io.FileReader;
import java.io.IOException;

import xyz.cuddlecloud.discord.Discord;
import xyz.cuddlecloud.discord.gui.logs.Log;
import xyz.cuddlecloud.javax.logging.Loggy;

public final class Utils {

	public static void loadProperties() {
		try {
			Discord.properties.load(new FileReader(Reference.configFile.get()));
		} catch (IOException e) {
			Log.setMessage(Discord.loggy.log(Loggy.Level.ERROR, Utils.class.getSimpleName(), e.getClass().getSimpleName(), e));
		}
	}
}
