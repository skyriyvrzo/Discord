package xyz.cuddlecloud.discord.util;

import java.util.function.Supplier;

import xyz.cuddlecloud.discord.Discord;

public final class Reference {

	public static final String VERSIONS = "1.5.3";
	
	public static Supplier<String> getDirectory = () -> CheckOS.directory;
	public static Supplier<String> getClassPath = () -> Discord.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	public static Supplier<String> getImagesFolder = () -> CheckOS.imagesFolder;
	public static Supplier<String> getJarPath = () -> CheckOS.jarPath;
	public static Supplier<String> getConfigFile = () -> CheckOS.configFile;
}