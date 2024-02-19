package com.get.discord.autorole.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultCaret;

import com.get.discord.autorole.AutoRole;

public final class GPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 104347075198952397L;
	private static JTextArea ta;
	
	private static DefaultCaret caret;
	
	public GPanel() {
		this.setLayout(new BorderLayout());
		this.setBounds(0, 1, 786, 435);
		Border lineBorder = BorderFactory.createLineBorder(Color.black);
		this.setBorder(BorderFactory.createTitledBorder(lineBorder, "Log and Chat", TitledBorder.LEFT, TitledBorder.CENTER, getFont().deriveFont(12f), Color.DARK_GRAY));
	
		ta = new JTextArea();
		ta.setLayout(null);
		//ta.setBounds(0, 1, 800, 435);
		ta.setBackground(Color.WHITE);
		ta.setFont(new Font("Tahoma", Font.PLAIN, 14));
		ta.setFocusable(false);
		ta.setEditable(false);
		
		caret = (DefaultCaret)ta.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		this.add(ta);
		
		JScrollPane scrollPane = new JScrollPane(ta);
		this.add(scrollPane);
	}
	
	public static void setLog(String s) {
		ta.append(s);
		System.out.println(s);
		
		fileWriter(s);
	}
	
	public static void fileWriter(String s) { 
		AutoRole.log.writeLog(s);
	}
}
