package xyz.cuddlecloud.discord.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class UnpackAssets {

	private static final String directory = Reference.directory.get();

	public static void extractFile() throws IOException {
		//writeConfig();
		extractAssets();
	}

	@Deprecated
	public static void writeConfig() throws IOException {
		
		File dir = new File(Reference.directory.get());
		dir.mkdirs();
		
		File config = new File(Reference.configFile.get());
		if(config.exists());
    	else {
    		FileWriter writer = new FileWriter(Reference.configFile.get(), true);
    		
    		writer.write("version: "+Reference.VERSIONS +"\n");
    		writer.write("tokenId: {token}\n\n");

			writer.write("#Member joins\n");
    		writer.write("roleId: {role}\n\n");

			writer.write("#Create channel\n");
			writer.write("categoryIdForCreateRoom: {category_id}\n");
    		writer.write("channelIdForCreateRoom: {channel_id}");
    		writer.close();
    	}
	}

	public static void extractAssets() {
		try {
			JarFile jarfile = new JarFile(Reference.jarPath.get());
			Enumeration<JarEntry> enu = jarfile.entries();

			while(enu.hasMoreElements()) {
				String targetDir = directory;
				JarEntry je = enu.nextElement();

				if(je.getName().contains("assets")) {

					File fl = new File(targetDir, je.getName());
					if(!fl.exists()) {
						fl.getParentFile().mkdirs();
						fl = new File(targetDir, je.getName());
					}

					if(je.isDirectory()) {
						continue;
					}

					InputStream is = jarfile.getInputStream(je);
					FileOutputStream fo = new FileOutputStream(fl);

					while(is.available() > 0) {
						fo.write(is.read());
					}

					fo.close();
					is.close();
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
