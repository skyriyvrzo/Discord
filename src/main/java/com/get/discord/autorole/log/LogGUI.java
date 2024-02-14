package com.get.discord.autorole.log;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;

import com.get.discord.autorole.AutoRole;
import com.get.discord.autorole.bot.Bot;
import com.get.discord.autorole.command.CommandBot;
import com.get.discord.autorole.util.Reference;

public class LogGUI implements KeyListener {

	public JFrame f;
	private static JTextArea ta;
	private static JTextField tf;
	public LogGUI() {
		f = new JFrame("[Discord] AutoRole (" + Reference.VERSIONS + ")");
		f.setSize(802, 501);
		f.setLayout(null);
		f.setFocusable(false);
		
		ta = new JTextArea();
		ta.setLayout(null);
		ta.setBounds(0, 1, 800, 435);
		ta.setBackground(Color.WHITE);
		ta.setFont(new Font("Tahoma", Font.PLAIN, 14));
		ta.setFocusable(false);
		ta.setEditable(false);
		DefaultCaret caret = (DefaultCaret)ta.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		f.add(ta);
		
		tf = new JTextField();
		tf.setLayout(null);
		tf.setBounds(1, 436, 785, 26);
		tf.setBackground(Color.LIGHT_GRAY);
		tf.setFont(new Font("Tahoma", Font.BOLD, 14));
		tf.setFocusable(true);
		tf.addKeyListener(this);
		f.add(tf);
		
		f.setLocationRelativeTo(null);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		
		Bot.runBot();
		
		LogGUI.setLog(AutoRole.log.getInfo("For help, type\"help\" or \"?\""));
	}
	
	public static void setLog(String s) {
		ta.append(s);
		System.out.println(s);
		
		fileWriter(s);
	}
	
	public static void fileWriter(String s) { 
		try {
			AutoRole.log.writeLog(s);
		} catch (IOException e) {
			LogGUI.setLog(AutoRole.log.getError(LogGUI.class, e.getMessage()));
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		//System.out.println(e.getKeyCode());
		if(e.getKeyCode() == 10) {
			System.out.println("TF: " + tf.getText());
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
					LogGUI.setLog(AutoRole.log.getError(LogGUI.class, e1.getMessage()));
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
