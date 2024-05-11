package com.get.discord.gg.gui.logs;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import com.get.discord.gg.gui.GraphicalUserInterface;
import com.get.discord.gg.gui.util.Theme;
import com.get.lib.Logging.Loggy.Level;

public final class LogMainPanel extends JPanel {

	private static final long serialVersionUID = -7495556323278589809L;

	public LogMainPanel() {
		Border border = BorderFactory.createLineBorder(Theme.LogMainPanelBorder);
        this.setLayout(new BorderLayout());
        this.setBounds(466,0,387,452);
        this.setBorder(BorderFactory.createTitledBorder(border, "Log and chat", TitledBorder.LEFT, TitledBorder.CENTER, Theme.titleBorder, Theme.LogMainPanelTitle));
        this.setBackground(Theme.LogMainPanelBackground);
        this.setFocusable(false);
        //this.add(new TextFieldPanel());
        this.add(new Log());
        GraphicalUserInterface.loggy.log(Level.INFO, LogMainPanel.class.getSimpleName() + " Loaded");
	}
}
