package com.get.discord.gg.util;

import java.util.function.Supplier;

import com.get.discord.gg.Discord;

public final class Reference {

	public static final String VERSIONS = "1.5.0";
	
	public static Supplier<String> getDirectory = () -> CheckOS.directory;
	public static Supplier<String> getClassPath = () -> Discord.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	public static Supplier<String> getImagesFolder = () -> CheckOS.imagesFolder;
	public static Supplier<String> getJarPath = () -> CheckOS.jarPath;
	public static Supplier<String> getConfigFile = () -> CheckOS.configFile;
}