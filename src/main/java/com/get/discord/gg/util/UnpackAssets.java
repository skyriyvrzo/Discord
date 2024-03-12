package com.get.discord.gg.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class UnpackAssets {

	private static String directory = Reference.getDirectory.get();
	
	public static void extractFile() throws IOException {
		
		File dir = new File(Reference.getDirectory.get());
		dir.mkdir();
		
		File config = new File(Reference.getConfigFile.get());
		if(config.exists());
    	else {
    		FileWriter writer = new FileWriter(Reference.getConfigFile.get(), true);
    		
    		writer.write("version: "+Reference.VERSIONS +"\n");
    		writer.write("tokenId: {token}\n");
    		writer.write("roleId: {role}\n");
    		writer.write("channelIdforCreateRoom: {channel_id}\n");
    		writer.write("categoryIdforNewRoom: {category_id}");
    		writer.close();
    	}
		
		try {
			JarFile jarfile = new JarFile(Reference.getJarPath.get());
			Enumeration<JarEntry> enu = jarfile.entries();
		
			while(enu.hasMoreElements()) {
				String targetDir = directory;
				JarEntry je = enu.nextElement();

				if(je.getName().contains("resources")) {
										
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
