package com.get.discord.gg.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.get.discord.gg.Main;

public final class CheckVersion {

	public static void changeVersionFile() throws FileNotFoundException, IOException {
		Main.properties.load(new FileReader(Main.directory + "\\config.properties"));
		String configFileVersion = Main.properties.getProperty("version");
		String currentVersion = Reference.VERSIONS;
		
		BufferedReader reader = new BufferedReader(new FileReader(Main.directory + "\\config.properties"));
		StringBuilder builder = new StringBuilder();
		String line;
		
		while((line = reader.readLine()) != null) {
			line = line.replace(configFileVersion, currentVersion);
			builder.append(line).append(System.lineSeparator());
		}
		
		reader.close();
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(Main.directory + "\\config.properties"));
		writer.write(builder.toString());
		
		writer.close();
	}
}
