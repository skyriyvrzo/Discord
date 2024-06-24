package xyz.cuddlecloud.discord.gui;

import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import xyz.cuddlecloud.discord.Discord;
import xyz.cuddlecloud.discord.bot.JavaDiscordAPI;
import xyz.cuddlecloud.discord.gui.logs.Log;
import xyz.cuddlecloud.discord.gui.logs.LogMainPanel;
import xyz.cuddlecloud.discord.gui.logs.logandchat.CommandBox;
import xyz.cuddlecloud.discord.gui.stats.StatsMainPanel;
import xyz.cuddlecloud.discord.gui.stats.performance.Performance;
import xyz.cuddlecloud.discord.gui.util.Theme;
import xyz.cuddlecloud.discord.util.Reference;
import xyz.cuddlecloud.javax.logging.Loggy.Level;

public class MainGUI {

private static JFrame frame = new JFrame("Discord (" + Reference.VERSIONS + ")");
		
	private static ImageIcon icon = null;
	
	public MainGUI(String[] args) throws IOException {
		
		frame.setSize(870,520);
		frame.getContentPane().setBackground(Theme.DisplayBackground);
		frame.setLayout(null);

		frame.getContentPane().add(new StatsMainPanel());
		frame.getContentPane().add(new LogMainPanel());
		frame.getContentPane().add(new CommandBox());

		icon = new ImageIcon(Reference.imagesFolder.get() + "discord.png");
		frame.setIconImage(icon.getImage());
		
		TrayIcon tri = new TrayIcon(icon.getImage(), Reference.VERSIONS);
		tri.setImageAutoSize(true);

		tri.addActionListener(e -> {
            frame.setVisible(true);

            try {
                SystemTray.getSystemTray().remove(tri);
            }catch(Exception e1) {
                e1.printStackTrace();
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
		
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);

		if(args.length > 0) {
			if(args[0].equalsIgnoreCase("--nogui") || args[0].equalsIgnoreCase("nogui")) frame.setVisible(false);
		}else{
			frame.setVisible(true);
		}

		Thread setStats = new Thread(() -> {
			while(true) {
				Performance.setText();
			}
		});
		setStats.start();
		
		Log.setMessage(Discord.loggy.log(Level.INFO, "For help, type \"help\" or \"?\""));
		Discord.github.showOptionDialog();
		JavaDiscordAPI.runBot();
	}
}