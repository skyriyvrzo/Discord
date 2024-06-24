package xyz.cuddlecloud.discord;

import java.io.IOException;
import java.util.Properties;

import xyz.cuddlecloud.discord.gui.MainGUI;
import xyz.cuddlecloud.discord.json.ConfigFile;
import xyz.cuddlecloud.discord.util.CheckOS;
import xyz.cuddlecloud.discord.util.Reference;
import xyz.cuddlecloud.discord.util.UnpackAssets;
import xyz.cuddlecloud.javax.github.Github;
import xyz.cuddlecloud.javax.logging.Loggy;

public final class Discord {

	//public final static Properties properties = new Properties();
	public static final Github github = new Github("skyriyvrzo", "Discord", Reference.VERSIONS);
	public static Loggy loggy;

	public static void main(String[] args) throws IOException {

		CheckOS.checkOperatingSystem();
		
		loggy = Loggy.getLoggy(Reference.directory.get() + "/logs", true, true, true);

		System.out.println("Starting " + Discord.class.getCanonicalName());
		
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			//CheckVersion.changeVersionFile();
			ConfigFile.setup();
		}catch(Exception e) {
			loggy.log(Loggy.Level.ERROR, "main", e.getClass().getSimpleName(), e);
		}
		UnpackAssets.extractFile();
		
		new MainGUI(args);
	}
}