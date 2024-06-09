package com.get.discord.gg;

import java.io.IOException;
import java.util.Properties;

import com.get.discord.gg.gui.GraphicalUserInterface;
import com.get.discord.gg.util.CheckOS;
import com.get.discord.gg.util.CheckVersion;
import com.get.discord.gg.util.Reference;
import com.get.discord.gg.util.UnpackAssets;
import com.get.lib.Logging.Loggy;
import com.get.lib.Logging.Loggy.Level;
import com.get.lib.github.Github;

public final class Discord {

	public final static Properties properties = new Properties(); 
	public static final Github github = new Github("skyriyvrzo", "Discord", Reference.VERSIONS);
	public static Loggy loggy;

	public static void main(String[] args) throws IOException {
		
		CheckOS.checkOperatingSystem();
		
		loggy = Loggy.getLoggy(Reference.getDirectory.get() + "/logs", true, true, true);
		
		System.out.println("Starting " + Discord.class.getCanonicalName());
		
		try {
			Thread.sleep(5000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			CheckVersion.changeVersionFile();
		}catch(Exception e) {
			loggy.log(Level.ERROR, "main", e.getClass().getSimpleName(), e);
		}
		UnpackAssets.extractFile();
		
		new GraphicalUserInterface("args");
	}
}