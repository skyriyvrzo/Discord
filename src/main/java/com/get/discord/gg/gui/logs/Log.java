package com.get.discord.gg.gui.logs;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.text.DefaultCaret;

import com.get.discord.gg.Discord;
import com.get.discord.gg.gui.util.Theme;
import com.get.lib.Logging.Loggy.Level;

public final class Log extends JPanel {

	private static final long serialVersionUID = 763087885358450524L;
	
	private static JTextArea log;
	private JScrollPane pane;
	
	//private static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
	//private static SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
	
	public Log() {
		this.setLayout(new BorderLayout());
        Border border = BorderFactory.createEmptyBorder();
        this.setBounds(470,18,450,100);
        this.setBorder(border);
        this.setFocusable(false);
                
        log = new JTextArea();
        log.setBounds(470,10,0,0);
        log.setEditable(false);
        log.setLineWrap(false);
        log.setFont(Theme.thai);
        log.setBackground(Theme.LogTextAreaBackground);
        log.setForeground(Theme.LogTextAreaForeground);
        log.setFocusable(false);
        this.add(log);
        
        DefaultCaret caret = (DefaultCaret)log.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        
        pane = new JScrollPane(log,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pane.setBackground(Theme.LogScrollPaneBackground);
        pane.setBorder(border);
        this.add(pane);
        
        Discord.loggy.log(Level.INFO, Log.class.getSimpleName() + " Loaded");
	}

	public static void setMessage(String s) {
		log.append(s);
	}
}
