package xyz.cuddlecloud.discord.util;

import java.io.FileReader;
import java.io.IOException;

import xyz.cuddlecloud.discord.Discord;
import xyz.cuddlecloud.discord.gui.logs.Log;
import com.get.lib.Logging.Loggy.Level;

public final class Utils {

	public static void loadProperties() {
		try {
			Discord.properties.load(new FileReader(Reference.getConfigFile.get()));
		} catch (IOException e) {
			Log.setMessage(Discord.loggy.log(Level.ERROR, Utils.class.getSimpleName(), e.getClass().getSimpleName(), e));
		}
	}
}
