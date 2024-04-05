package com.get.discord.gg.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.border.Border;

import com.get.discord.gg.Discord;
import com.get.discord.gg.bot.JavaDiscordAPI;
import com.get.discord.gg.command.CommandBot;
import com.get.discord.gg.util.Reference;
import com.get.lib.logutils.LogUtil;

public final class LogGUI implements KeyListener {

	private JFrame f;
	private static JTextField tf;
	private static String split[];
	
	private ArrayList<String> commandList = new ArrayList<String>();
	private static int command_location = 0;
	
	public LogGUI() throws IOException {
		Border lineBorder = BorderFactory.createLineBorder(Color.black);
		f = new JFrame("Discord " + "(" + Reference.VERSIONS + ")");
		f.setSize(802, 501);
		f.setLayout(null);
		f.setFocusable(false);
		
		f.add(new GPanel());
		
		tf = new JTextField();
		tf.setLayout(null);
		tf.setBounds(2, 436, 782, 24);
		tf.setBorder(lineBorder);
		tf.setBackground(Color.LIGHT_GRAY);
		tf.setFont(new Font("Tahoma", Font.BOLD, 14));
		tf.setFocusable(true);
		tf.addKeyListener(this);
		f.add(tf);
		
		f.setLocationRelativeTo(null);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ImageIcon icon = new ImageIcon(Reference.getImagesFolder.get() + "discord.png");
		f.setIconImage(icon.getImage());
		
		f.setVisible(true);
		
		Discord.github.showOptionDialog();
		
		JavaDiscordAPI.runBot();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		//System.out.println(e.getKeyCode());
		if(e.getKeyCode() == 10) {
			//System.out.println("TF: " + tf.getText());
			split = tf.getText().split(" ");
			if(tf.getText().equalsIgnoreCase("")) return;
			
			if(commandList.size() != 0) {
				if(!(tf.getText().equalsIgnoreCase(commandList.get(commandList.size() - 1)))) {
					commandList.add(tf.getText());
					command_location = commandList.size();
				}
			}else {
				commandList.add(tf.getText());
				command_location = commandList.size();
			}
			
			if(tf.getText().equalsIgnoreCase("help") || tf.getText().equalsIgnoreCase("?")) {
				CommandBot.getHelp();
			}
			else if(tf.getText().contains("bot")) {
				try {
					//System.out.println("run conditional");
					CommandBot.conditional(tf, split);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					GPanel.setLog(LogUtil.error(LogUtil.getEnclosingMethod(new Object() {}), LogGUI.class.getSimpleName(), e, true, true));
				}
			}
			else {
				CommandBot.getHelp();
			}
			tf.setText("");
		}
		
		else if(e.getKeyCode() == 38) {
			if(tf.getText().equalsIgnoreCase("")) command_location = commandList.size();
			
			try {
				tf.setText(commandList.get(--command_location));
			}
			catch(Exception e1) {
				tf.setText(commandList.get(++command_location));
			}
		}
		else if(e.getKeyCode() == 40) {
			if(tf.getText().equalsIgnoreCase("")) return;
			
			try {
				tf.setText(commandList.get(++command_location));
			}
			catch(Exception e1) {
				command_location = commandList.size();
				tf.setText("");
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
