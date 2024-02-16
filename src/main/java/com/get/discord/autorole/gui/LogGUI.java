package com.get.discord.autorole.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.border.Border;

import com.get.discord.autorole.AutoRole;
import com.get.discord.autorole.bot.Bot;
import com.get.discord.autorole.command.CommandBot;
import com.get.discord.autorole.util.Reference;

public final class LogGUI implements KeyListener {

	private JFrame f;
	private static JTextField tf;
	public LogGUI() {
		Border lineBorder = BorderFactory.createLineBorder(Color.black);
		f = new JFrame("[Discord] AutoRole (" + Reference.VERSIONS + ")" + "     For help, type\"help\" or \"?\"");
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
		
		//DefaultCaret caret = (DefaultCaret)ta.getCaret();
        //caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        
		f.setVisible(true);
		
		Bot.runBot();
		
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
			if(tf.getText().equalsIgnoreCase("")) return;
			
			if(tf.getText().equalsIgnoreCase("help") || tf.getText().equalsIgnoreCase("?")) {
				CommandBot.getHelp();
			}
			else if(tf.getText().contains("bot")) {
				try {
					System.out.println("run conditional");
					CommandBot.conditional(tf);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					GPanel.setLog(AutoRole.log.getError(LogGUI.class, e1.getMessage()));
				}
			}
			tf.setText("");
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
