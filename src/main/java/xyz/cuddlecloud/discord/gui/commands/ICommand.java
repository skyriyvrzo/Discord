package xyz.cuddlecloud.discord.gui.commands;

import java.awt.Desktop;
import java.util.Properties;

import javax.swing.JTextField;

public interface ICommand {

	String getName();
	
	void getHelp();
		
	default void run() {};
	
	default void run(JTextField tf, String[] split){};

	default void run(JTextField tf, String[] split, Desktop desktop) {};
	
	default void run(JTextField tf, Properties properties, Desktop desktop, String[] split) {}
}