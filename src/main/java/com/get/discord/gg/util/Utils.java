package com.get.discord.gg.util;

import java.io.FileReader;
import java.io.IOException;

import com.get.discord.gg.Discord;
import com.get.discord.gg.gui.logs.Log;
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
