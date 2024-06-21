package xyz.cuddlecloud.discord.gui.logs.logandchat;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import xyz.cuddlecloud.discord.Discord;
import xyz.cuddlecloud.discord.command.CommandBot;
import xyz.cuddlecloud.discord.gui.commands.RegisterCommand;
import xyz.cuddlecloud.discord.gui.util.Theme;
import com.get.lib.Logging.Loggy.Level;
import xyz.cuddlecloud.discord.gui.logs.Log;

public final class CommandBox extends JPanel implements KeyListener {

	private ArrayList<String> commandList = new ArrayList<String>();
	
	private static final long serialVersionUID = 7644992812327912248L;
	private static int command_location = 0;
	
	private JTextField tf;
	//private FileReader reader = new FileReader(Reference.getConfigFile.get());
	//private Properties properties = new Properties();

    public CommandBox() throws IOException{
        Border border = BorderFactory.createLineBorder(Theme.CommandBoxBorder);
        this.setLayout(new BorderLayout());
        this.setBounds(467,454,385,25);
        this.setBorder(border);
        this.setFocusable(false);
        //this.add(new TextFieldPanel());
        
        //this.add(new LogPanel());
        tf = new JTextField();
        tf.setFocusable(true);
        tf.setFont(Theme.thai);
        tf.addKeyListener(this);
        tf.setBackground(Theme.CommandBoxTextfieldBackground);
        tf.setForeground(Theme.CommandBoxTextfieldForeground);
        Border tfBorder = BorderFactory.createLineBorder(Theme.CommandBoxTextfieldBorder);
        tf.setBorder(tfBorder);
        this.add(tf);
        //properties.load(reader);
        
        Discord.loggy.log(Level.INFO, CommandBox.class.getSimpleName() + " Loaded");
    }
    
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
	
	@SuppressWarnings("unused")
	@Override
	public void keyPressed(KeyEvent e) {
		Desktop desktop = Desktop.getDesktop();
		
		//System.out.println(e.getKeyCode());
		
		if(e.getKeyCode() == 10) {
			
			if(tf.getText().equalsIgnoreCase("")) return;
			
			String[] split = tf.getText().split(" ");
			
			Discord.loggy.log(Level.TRACE, tf.getText());
			
			if(commandList.size() != 0) {
				
				/*
				 * System.out.println(command_location); System.out.println(tf.getText() + ":" +
				 * commandList.get(commandList.size() - 1));
				 * System.out.println(command_location);
				 */
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
					Log.setMessage(Discord.loggy.log(Level.ERROR, CommandBox.class.getSimpleName(), e1.getClass().getSimpleName(), e1));
				}
			}
			
			else if(tf.getText().equalsIgnoreCase("close") || tf.getText().equalsIgnoreCase("exit")) {
				RegisterCommand.exit.run();
			}
			
			else {
				CommandException.unknowCommandException(tf);
			}
			
			tf.setText(null);
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
