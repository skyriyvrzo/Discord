package xyz.cuddlecloud.discord.command;

import xyz.cuddlecloud.discord.Discord;
import xyz.cuddlecloud.discord.gui.logs.Log;
import xyz.cuddlecloud.discord.util.Reference;
import xyz.cuddlecloud.javax.logging.Loggy;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public final class CommandOpen {

    public static void conditional(JTextField tf, String[] s) {
        if(s[0].equalsIgnoreCase("open")){
            if(tf.getText().trim().equalsIgnoreCase("open")){
                getOpenCMD();
            }else if(s[1].equalsIgnoreCase("configFile")) {
                openConfigFile();
            }else {
                getOpenCMD();
            }
        }else {
            getOpenCMD();
        }
    }

    private static void getOpenCMD() {
        Log.setMessage(Discord.loggy.log(Loggy.Level.INFO, "------ Help Open ------"));
        Log.setMessage(Discord.loggy.log(Loggy.Level.INFO, "- open configFile"));
        Log.setMessage(Discord.loggy.log(Loggy.Level.INFO, "------------------------"));
    }

    private static void openConfigFile() {
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.open(new File(Reference.configFile.get()));
        } catch (IOException e) {
            Log.setMessage(Discord.loggy.log(Loggy.Level.ERROR, e));
        }
    }
}
