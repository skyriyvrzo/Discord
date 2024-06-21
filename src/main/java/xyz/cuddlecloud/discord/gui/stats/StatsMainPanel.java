package xyz.cuddlecloud.discord.gui.stats;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import xyz.cuddlecloud.discord.Discord;
import xyz.cuddlecloud.discord.gui.stats.java.JavaBorderLine;
import xyz.cuddlecloud.discord.gui.stats.performance.Performance;
import xyz.cuddlecloud.discord.gui.util.Theme;
import com.get.lib.Logging.Loggy.Level;

public final class StatsMainPanel extends JPanel {

	private static final long serialVersionUID = 8132786352001706183L;

	public StatsMainPanel() {
		this.setLayout(null);
		Border border = BorderFactory.createLineBorder(Theme.StatsMainPanelBorder);
		this.setBounds(1, 0, 462, 480);
        this.setBorder(BorderFactory.createTitledBorder(border, "Stats", TitledBorder.LEFT, TitledBorder.CENTER, Theme.titleBorder, Theme.StatsMainPanelTitle));
        this.setBackground(Theme.StatsMainPanelBackground);
        this.setFocusable(false);
        this.add(new Performance());
        this.add(new JavaBorderLine());

        Discord.loggy.log(Level.INFO, StatsMainPanel.class.getSimpleName() + " Loaded");
	}
}
