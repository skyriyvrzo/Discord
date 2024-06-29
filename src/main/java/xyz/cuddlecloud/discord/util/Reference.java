package xyz.cuddlecloud.discord.util;

import java.util.function.Supplier;

import xyz.cuddlecloud.discord.Discord;

public final class Reference {

	public static final String VERSIONS = "1.5.8c";
	
	public static Supplier<String> directory = () -> CheckOS.directory;
	public static Supplier<String> classPath = () -> Discord.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	public static Supplier<String> imagesFolder = () -> CheckOS.imagesFolder;
	public static Supplier<String> jarPath = () -> CheckOS.jarPath;
	public static Supplier<String> configFile = () -> CheckOS.configFile;

}