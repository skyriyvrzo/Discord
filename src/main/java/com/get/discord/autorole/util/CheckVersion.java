package com.get.discord.autorole.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.get.discord.autorole.AutoRole;

public final class CheckVersion {

	public static void changeVersionFile() throws FileNotFoundException, IOException {
		AutoRole.properties.load(new FileReader(AutoRole.directory + "\\config.properties"));
		String configFileVersion = AutoRole.properties.getProperty("version");
		String currentVersion = Reference.VERSIONS;
		
		BufferedReader reader = new BufferedReader(new FileReader(AutoRole.directory + "\\config.properties"));
		StringBuilder builder = new StringBuilder();
		String line;
		
		while((line = reader.readLine()) != null) {
			line = line.replace(configFileVersion, currentVersion);
			builder.append(line).append(System.lineSeparator());
		}
		
		reader.close();
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(AutoRole.directory + "\\config.properties"));
		writer.write(builder.toString());
		
		writer.close();
	}
}
