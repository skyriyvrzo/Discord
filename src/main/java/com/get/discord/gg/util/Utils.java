package com.get.discord.gg.util;

import java.io.FileReader;
import java.io.IOException;

import com.get.discord.gg.Main;
import com.get.discord.gg.command.CommandBot;
import com.get.discord.gg.gui.GPanel;

public final class Utils {

	public static void loadProperties() {
		try {
			Main.properties.load(new FileReader(Reference.getConfigFile.get()));
		} catch (IOException e) {
			GPanel.setLog(Main.log.getError(CommandBot.class, e.getMessage()));
		}
	}
}
