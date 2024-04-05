package com.get.discord.gg.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.get.discord.gg.Discord;

public final class CheckVersion {

	public static void changeVersionFile() throws FileNotFoundException, IOException {
		Discord.properties.load(new FileReader(Reference.getConfigFile.get()));
		String configFileVersion = Discord.properties.getProperty("version");
		String currentVersion = Reference.VERSIONS;
		
		BufferedReader reader = new BufferedReader(new FileReader(Reference.getConfigFile.get()));
		StringBuilder builder = new StringBuilder();
		String line;
		
		while((line = reader.readLine()) != null) {
			line = line.replace(configFileVersion, currentVersion);
			builder.append(line).append(System.lineSeparator());
		}
		
		reader.close();
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(Reference.getConfigFile.get()));
		writer.write(builder.toString());
		
		writer.close();
	}
}
