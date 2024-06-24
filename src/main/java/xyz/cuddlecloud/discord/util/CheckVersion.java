package xyz.cuddlecloud.discord.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import xyz.cuddlecloud.discord.Discord;
import xyz.cuddlecloud.discord.json.ConfigFile;

@Deprecated
public final class CheckVersion {

	public static void changeVersionFile() throws FileNotFoundException, IOException {
		String configFileVersion = ConfigFile.getVersion();
		String currentVersion = Reference.VERSIONS;
		
		BufferedReader reader = new BufferedReader(new FileReader(Reference.configFile.get()));
		StringBuilder builder = new StringBuilder();
		String line;
		
		while((line = reader.readLine()) != null) {
			line = line.replace(configFileVersion, currentVersion);
			builder.append(line).append(System.lineSeparator());
		}
		
		reader.close();
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(Reference.configFile.get()));
		writer.write(builder.toString());
		
		writer.close();
	}
}
