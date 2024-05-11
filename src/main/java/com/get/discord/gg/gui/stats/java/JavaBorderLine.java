package com.get.discord.gg.gui.stats.java;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import com.get.discord.gg.gui.GraphicalUserInterface;
import com.get.discord.gg.gui.util.Theme;
import com.get.lib.Logging.Loggy.Level;

public final class JavaBorderLine extends JPanel {

	private static final long serialVersionUID = -7475102986885061679L;

	public JavaBorderLine() {
		Border border = BorderFactory.createLineBorder(Theme.JavaBorderPanelBorder);
        this.setBounds(6,280,450,194);
        this.setBorder(BorderFactory.createTitledBorder(border,"Java", TitledBorder.LEFT, TitledBorder.CENTER, Theme.titleBorder, Theme.JavaBorderPanelTitle));
        this.setLayout(new BorderLayout());
        this.setBackground(Theme.JavaBorderPanelBackground);
        this.setFocusable(false);
        this.add(new JavaInfo());
        
        GraphicalUserInterface.loggy.log(Level.INFO, JavaBorderLine.class.getSimpleName() + " Loaded");
	}
}
