package xyz.cuddlecloud.discord.util;

import java.io.File;
import java.io.IOException;

public final class CheckOS {

	public static String osType;
	public static String directory;
	public static String jarPath;
	public static String configFile;
	public static String imagesFolder;
	
	public static void checkOperatingSystem() throws IOException {
		if(System.getProperty("os.name").contains("Windows")) {
			osType = "windows";
			directory = "C:\\Users\\"+System.getProperty("user.name")+"\\AppData\\Roaming\\DiscordBot";
			jarPath = new File(Reference.getClassPath.get()).getCanonicalPath() + "\\Discord-" + Reference.VERSIONS + ".jar";
			configFile = directory + "\\config.properties";
			imagesFolder = directory + "\\resources\\assets\\images\\";
		}
		else if(System.getProperty("os.name").equalsIgnoreCase("Linux")) {
			osType = "linux";
			directory = "/home/" + System.getProperty("user.name") + "/Documents/DiscordBot";
			jarPath = new File(Reference.getClassPath.get()).getCanonicalPath() + "/Discord-" + Reference.VERSIONS + ".jar";
			configFile = directory + "/config.properties";
			imagesFolder = directory + "/resources/assets/images/";
		}
		else if(System.getProperty("os.name").contains("mac")) {
			osType = "mac";
		}
		else {
			System.err.println(System.getProperty("os.name"));
		}
	}
}
