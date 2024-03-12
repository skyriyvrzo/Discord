package com.get.discord.gg;

import java.io.IOException;
import java.util.Properties;

import com.get.discord.gg.gui.LogGUI;
import com.get.discord.gg.util.CheckOS;
import com.get.discord.gg.util.CheckVersion;
import com.get.discord.gg.util.Reference;
import com.get.discord.gg.util.UnpackAssets;
import com.get.lib.github.Github;
import com.get.lib.logutils.LogUtil;

public final class Main {

	public final static Properties properties = new Properties(); 
	public static LogUtil log;
	public static final Github github = new Github("skyriyvrzo", "Discord", Reference.VERSIONS);
	
	public static void main(String[] a) throws IOException {
		
		CheckOS.checkOperatingSystem();
		
		log = new LogUtil(Reference.getDirectory.get());
			
		try {
			CheckVersion.changeVersionFile();
		}catch(Exception e) {
			LogUtil.error(LogUtil.getEnclosingMethod(new Object() {}), Main.class.getSimpleName(), e, true, true);
		}
		UnpackAssets.extractFile();
		
		log.mkdir();
		new LogGUI();
	}
}