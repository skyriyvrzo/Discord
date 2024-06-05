package com.get.discord.gg.gui;

import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.get.discord.gg.Discord;
import com.get.discord.gg.bot.JavaDiscordAPI;
import com.get.discord.gg.gui.logs.Log;
import com.get.discord.gg.gui.logs.LogMainPanel;
import com.get.discord.gg.gui.logs.logandchat.CommandBox;
import com.get.discord.gg.gui.stats.StatsMainPanel;
import com.get.discord.gg.gui.stats.performance.Performance;
import com.get.discord.gg.gui.util.Theme;
import com.get.discord.gg.util.Reference;
import com.get.lib.Logging.Loggy.Level;

public class GraphicalUserInterface {

private static JFrame frame = new JFrame("Discord (" + Reference.VERSIONS + ")");
		
	private static ImageIcon icon = null;
	
	public GraphicalUserInterface(String s) throws IOException {
		
		frame.setSize(870,520);
		frame.getContentPane().setBackground(Theme.DisplayBackground);
		frame.setLayout(null);

		frame.getContentPane().add(new StatsMainPanel());
		frame.getContentPane().add(new LogMainPanel());
		frame.getContentPane().add(new CommandBox());

		icon = new ImageIcon(Reference.getImagesFolder.get() + "discord.png");
		frame.setIconImage(icon.getImage());
		
		TrayIcon tri = new TrayIcon(icon.getImage(), Reference.VERSIONS);
		tri.setImageAutoSize(true);
		
		tri.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(true);
				
				try {
					SystemTray.getSystemTray().remove(tri);
				}catch(Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		frame.addWindowListener(new WindowListener() {
			
			
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					SystemTray.getSystemTray().add(tri);
				}catch(Exception e2) {
					e2.printStackTrace();
				}
				frame.setVisible(false);
				//System.out.println(e);
			}

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				//System.out.println(e);
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				//System.out.println(e);
			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				//System.out.println(e);
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				//System.out.println(e);
			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				//System.out.println(e);
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				//System.out.println(e);
			}
		});
		
		//frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		
		if(!s.equalsIgnoreCase("nogui")) frame.setVisible(true);
		
		//Log.setMessage(loggy.log(Level.INFO, "For help, type \"help\" or \"?\""));
		
		//setDelay();
		Thread setStats = new Thread(() -> {
			Discord.loggy.log(Level.TRACE, "setDelay enabled.");
			
			while(true) {
				Performance.setText();
				/*try {
					Thread.sleep(1000);
				}catch(Exception  e) {
					Log.getLogger(log.getError(Display.class, e.getMessage()));
				}*/
			}
		});
		setStats.start();
		
		Log.setMessage(Discord.loggy.log(Level.INFO, "For help, type \"help\" or \"?\""));
		System.out.println("EIEE");
		Discord.github.showOptionDialog();
		JavaDiscordAPI.runBot();
	}
}