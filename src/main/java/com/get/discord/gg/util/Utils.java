package com.get.discord.gg.util;

import java.io.FileReader;
import java.io.IOException;

import com.get.discord.gg.Discord;
import com.get.discord.gg.command.CommandBot;
import com.get.discord.gg.gui.GPanel;
import com.get.lib.logutils.LogUtil;

public final class Utils {

	public static void loadProperties() {
		try {
			Discord.properties.load(new FileReader(Reference.getConfigFile.get()));
		} catch (IOException e) {
			GPanel.setLog(LogUtil.error(LogUtil.getEnclosingMethod(new Object() {}), CommandBot.class.getSimpleName(), e.getMessage(), true, true));
		}
	}
}
