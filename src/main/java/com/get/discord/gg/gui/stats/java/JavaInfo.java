package com.get.discord.gg.gui.stats.java;

import java.awt.BorderLayout;

import javax.swing.JTextArea;

import com.get.discord.gg.Discord;
import com.get.discord.gg.gui.util.Theme;
import com.get.lib.Logging.Loggy.Level;

public final class JavaInfo extends JTextArea {

	private static final long serialVersionUID = 5885562020695857813L;

	public JavaInfo() {
        this.setBounds(10, 240, 430, 180);
        this.setLayout(new BorderLayout());
        this.setFont(Theme.thai);;
        this.setText("Java Vendor: " + System.getProperty("java.vendor")
                + "\nJava Version: " + System.getProperty("java.version")
                + "\nJava Runtime: " + System.getProperty("java.runtime.name")
                + "\nJava Runtime Version: " + System.getProperty("java.runtime.version")
                + "\nJava Class Version: " + System.getProperty("java.class.version")
                + "\nJava Home: " + System.getProperty("java.home")
                + "\nJava Version Date: " + System.getProperty("java.version.date"));
        this.setEditable(false);
        this.setFocusable(false);
        this.setLineWrap(true);
        
        this.setBackground(Theme.JavaInfoBackground);
        this.setForeground(Theme.JavaInfoForeground);
        
        Discord.loggy.log(Level.INFO, JavaInfo.class.getSimpleName() + " Loaded");
	}
}
