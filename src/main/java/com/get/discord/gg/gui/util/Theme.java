package com.get.discord.gg.gui.util;

import java.awt.Color;
import java.awt.Font;

import com.get.lib.colorlib.ColorLib;

public final class Theme {
	
	public static final Font head = new Font(null, 0, 0);
	public static final Font body = new Font(null, 0, 0);
	public static final Font thai = new Font("Tahoma", Font.PLAIN, 14);
	public static final Font titleBorder = new Font("Tahoma", Font.PLAIN, 12);
	public static final Font unpackFont = new Font("Tahoma", Font.PLAIN, 14);
	//Display Main
	public static final Color DisplayBackground = ColorLib.White;
	
	//StatsPanel
	public static final Color StatsMainPanelBackground = ColorLib.White;
	public static final Color StatsMainPanelTitle = ColorLib.Black;
	public static final Color StatsMainPanelBorder = Color.gray;
	
	public static final Color PerformanceBackground = ColorLib.White;
	public static final Color PerformanceuseLabel = ColorLib.Black;
	public static final Color PerformancefreeLabel = ColorLib.Black;
	public static final Color PerformancemaxLabel = ColorLib.Black;
	public static final Color PerformancecpuLabel = ColorLib.Black;
	public static final Color PerformancegpuLabel = ColorLib.Black;
	public static final Color PerformanceboardManufacturerLabel = ColorLib.Black;
	public static final Color PerformanceboardProductLabel = ColorLib.Black;
	public static final Color PerformancebiosVersionLabel = ColorLib.Black;
	public static final Color PerformancedateLabel = ColorLib.Black;
	public static final Color ShutdownRemainningLabel = ColorLib.Black;

	//Java
	public static final Color JavaBorderPanelBackground = ColorLib.White;
	public static final Color JavaBorderPanelTitle = ColorLib.Black;
	public static final Color JavaBorderPanelBorder = Color.gray;
	
	public static final Color JavaInfoBackground = ColorLib.White;
	public static final Color JavaInfoForeground = ColorLib.Black;
	
	//Log
	public static final Color LogMainPanelBackground = ColorLib.White;
	public static final Color LogMainPanelTitle = ColorLib.Black;
	public static final Color LogMainPanelBorder = Color.gray;

	public static final Color LogTextAreaBackground = ColorLib.White;
	public static final Color LogTextAreaForeground = ColorLib.Black;

	public static final Color LogScrollPaneBackground = ColorLib.White;
	//Log Command Panel
	public static final Color TextFieldPanelBorder = Color.gray; // unuse
	public static final Color CommandBoxBorder = ColorLib.White;
	public static final Color CommandBoxTextfieldBorder = Color.gray;
	public static final Color CommandBoxTextfieldBackground = ColorLib.White;
	public static final Color CommandBoxTextfieldForeground = ColorLib.Black;
	
}
