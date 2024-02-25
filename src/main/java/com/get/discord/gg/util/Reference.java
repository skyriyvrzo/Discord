package com.get.discord.gg.util;

import java.util.function.Supplier;

import com.get.discord.gg.Main;

public final class Reference {

	public static final String VERSIONS = "1.3.1";
	
	public static Supplier<String> getDirectory = () -> Main.directory;
	public static Supplier<String> getClassPath = () -> Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	public static Supplier<String> getImagesFolder = () -> Main.directory + "\\resources\\assets\\images\\";
}