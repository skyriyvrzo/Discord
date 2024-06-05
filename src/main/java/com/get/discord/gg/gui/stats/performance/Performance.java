package com.get.discord.gg.gui.stats.performance;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTextArea;

import com.get.discord.gg.Discord;
import com.get.discord.gg.gui.util.Theme;
import com.get.lib.Logging.Loggy.Level;
import com.get.lib.colorlib.ColorLib;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.ComputerSystem;
import oshi.hardware.GraphicsCard;
import oshi.hardware.HardwareAbstractionLayer;

public final class Performance extends JTextArea {

	private static final long serialVersionUID = 5182496210265635913L;

	private static String use;
	private static String free ;
	private static String total;
	private static String max;
	private static String cpu;
	private static String cputhread;
	private static String gpu = "";
    private static String boardManufacturer;
    private static String boardProduct;
    private static String biosVersion;
    private static String programFree;
    private static String programTotal;
    private static String programUse;
    
    //private static String shutdownRemainning;

    private static String usePercentage;
    private static String freePercentage;
    
    private static String date;
    
    private static JLabel useLabel;
    private static JLabel freeLabel;
    //public static JLabel totalLabel;
    private static JLabel maxLabel;
    private static JLabel cpuLabel;
    private static JLabel gpuLabel;
    private static JLabel boardManufacturerLabel;
    private static JLabel boardProductLabel;
    private static JLabel biosVersionLabel;
    
    private static JLabel shutdownRemainningLabel;
    
    private static JLabel dateLabel;
    
    //public static SimpleDateFormat dateFormat = new SimpleDateFormat("EEEEE, dd MMMM yyyy, k:mm:ss z");
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("EEEEE, dd MMMM yyyy, hh:mm:ss a z");
    
    private static SystemInfo si = new SystemInfo();
    private static HardwareAbstractionLayer hal = si.getHardware();

    public Performance() {
        this.setBounds(5, 16, 450, 264);
        
        this.setBackground(Theme.PerformanceBackground);
        
        useLabel = new JLabel();
        useLabel.setBounds(5, 0, 250, 16);
        useLabel.setFont(Theme.thai);
        useLabel.setFocusable(false);
        useLabel.setText("Mem: N/A");
        this.add(useLabel);

        freeLabel = new JLabel();
        freeLabel.setBounds(5, 15, 430, 16);
        freeLabel.setFont(Theme.thai);
        freeLabel.setFocusable(false);
        freeLabel.setText("Allocated: N/A");
        this.add(freeLabel);

        maxLabel = new JLabel();
        maxLabel.setBounds(5, 30, 430, 16);
        maxLabel.setFont(Theme.thai);
        maxLabel.setFocusable(false);
        maxLabel.setText("Max: N/A");
        this.add(maxLabel);

        cpuLabel = new JLabel();
        cpuLabel.setBounds(5,60,430,17);
        cpuLabel.setFont(Theme.thai);
        cpuLabel.setFocusable(false);
        cpuLabel.setText("CPU: N/A");
        this.add(cpuLabel);

        gpuLabel = new JLabel();
        gpuLabel.setBounds(5,76,430,17);
        gpuLabel.setFont(Theme.thai);
        gpuLabel.setFocusable(false);
        gpuLabel.setText("GPU: N/A");
        this.add(gpuLabel);

        boardManufacturerLabel = new JLabel();
        boardManufacturerLabel.setBounds(5,106,430,17);
        boardManufacturerLabel.setFont(Theme.thai);
        boardManufacturerLabel.setFocusable(false);
        boardManufacturerLabel.setText("Board Manufacturer: N/A");
        this.add(boardManufacturerLabel);

        boardProductLabel = new JLabel();
        boardProductLabel.setBounds(5,122,430,17);
        boardProductLabel.setFont(Theme.thai);
        boardProductLabel.setFocusable(false);
        boardProductLabel.setText("Board Product: N/A");
        this.add(boardProductLabel);

        biosVersionLabel = new JLabel();
        biosVersionLabel.setBounds(5,138,430,17);
        biosVersionLabel.setFont(Theme.thai);
        biosVersionLabel.setFocusable(false);
        biosVersionLabel.setText("Bios Version: N/A");
        this.add(biosVersionLabel);
        
        dateLabel = new JLabel();
        dateLabel.setBounds(5,170,430,17);
        dateLabel.setFont(Theme.thai);
        dateLabel.setFocusable(false);
        dateLabel.setText("Current date is: N/A");
        this.add(dateLabel);
        
        shutdownRemainningLabel = new JLabel();
        shutdownRemainningLabel.setBounds(5, 200, 430, 17);
        shutdownRemainningLabel.setFont(Theme.thai);
        shutdownRemainningLabel.setFocusable(false);
        //shutdownRemainningLabel.setText("Computer will be shutdown in: 00:00:00");
        this.add(shutdownRemainningLabel);
        
        this.setEditable(false);
        this.setFocusable(false);
        
        setCentralProcessingUnitName();
        setGraphicsProcessingUnitName();
        setBoard();
        setTextColor();

        Discord.loggy.log(Level.INFO, Performance.class.getSimpleName() + " Loaded");
        setVisible(true);
    }
    
    public static void setText() {
    	try {
    		free = String.valueOf(hal.getMemory().getAvailable() / (1024 * 1024));
    		total = String.valueOf(hal.getMemory().getTotal() / (1024 * 1024));
    		max = String.valueOf(hal.getMemory().getTotal() / (1024 * 1024));
    		use = String.valueOf((Integer.parseInt(total) - (Integer.parseInt(free))));
    		usePercentage = String.valueOf((Integer.parseInt(use) * 100) / Integer.parseInt(total));
        	freePercentage = String.valueOf((Integer.parseInt(free) *100) / Integer.parseInt(total));    	
        	
        	programFree = String.valueOf(Runtime.getRuntime().freeMemory() / (1024 * 1024));
            programTotal = String.valueOf(Runtime.getRuntime().totalMemory() / (1024 * 1024));
            programUse = String.valueOf(Integer.parseInt(programTotal) - Integer.parseInt(programFree));
            
            useLabel.setText("Mem:  " + usePercentage +"% " + String.format("%03d", Integer.parseInt(programUse)) + "/" + use + "/" + total + "MB");
            //useLabel.setText("Mem:  " + usePercentage +"% " + use + "/" + total + "MB");
            freeLabel.setText("Allocated:  " + freePercentage +"% " + free + "MB");
            maxLabel.setText("Max:  " + max + "MB");
            
            if(Integer.parseInt(usePercentage) >= 70 && Integer.parseInt(usePercentage) < 80) {
            	useLabel.setForeground(ColorLib.DarkOrange);;
            }
            else if(Integer.parseInt(usePercentage) >= 80) {
            	useLabel.setForeground(ColorLib.Red);
            }
            else {
            	useLabel.setForeground(ColorLib.Black);
            }
            
            if(Integer.parseInt(freePercentage) <= 30 && Integer.parseInt(freePercentage) > 20) {
            	freeLabel.setForeground(ColorLib.DarkOrange);
            }
            else if(Integer.parseInt(freePercentage) <= 20) {
            	freeLabel.setForeground(ColorLib.Red);
            }
            else {
            	freeLabel.setForeground(ColorLib.Black);
            }
    	}catch (Exception e) {
            //useLabel.setText("Mem:  " + usePercentage +"% " + programUse + "/" + use + "/" + total + "MB");
            freeLabel.setText("Allocated:  " + freePercentage +"% " + free + "MB");
            maxLabel.setText("Max:  " + max + "MB");
		}
    	
		date = dateFormat.format(Calendar.getInstance().getTime());
		dateLabel.setText("Current date is: " + date);
        
		return;
    }
    private static void setCentralProcessingUnitName() {
    	CentralProcessor cpu_info = hal.getProcessor();
    	cpu = cpu_info.getProcessorIdentifier().getName();
    	
    	cputhread = String.valueOf(Runtime.getRuntime().availableProcessors());
    	cpuLabel.setText("CPU: " + cputhread + "x " + cpu);
    }
    
    private static void setGraphicsProcessingUnitName() {
    	List<GraphicsCard> graphicsCard = hal.getGraphicsCards();
    	//String gpu_name_str = gpu_name.toString();
    	//gpu_name_str = gpu_name_str.replace("[", "");
    	//gpu_name_str = gpu_name_str.replace("]", "");
    	
    	//gpu = String.valueOf(gpu_name_str);
    	//String a = null;
    	for(int i = 0; i < graphicsCard.size(); i++) {
    		
    		if(graphicsCard.size() < 2) {
    			gpu += graphicsCard.get(i).getName();
    		}else {
    			gpu += graphicsCard.get(i).getName() + " ";
    		}
    	}
    	
    	gpuLabel.setText("GPU: " + gpu);
    }
    
    private static void setBoard() {
    	ComputerSystem computerSystem = hal.getComputerSystem();
    	boardManufacturer = computerSystem.getBaseboard().getManufacturer();
    	boardProduct = computerSystem.getModel();
    	biosVersion = computerSystem.getFirmware().getName();
    	
    	boardManufacturerLabel.setText("Board Manufacturer: " + boardManufacturer);
        boardProductLabel.setText("Board Product: " + boardProduct);
        biosVersionLabel.setText("Bios Version: " + biosVersion);
    }
    
    private static void setTextColor() {
    	useLabel.setForeground(Theme.PerformanceuseLabel);
    	freeLabel.setForeground(Theme.PerformancefreeLabel);
        maxLabel.setForeground(Theme.PerformancemaxLabel);
    	
    	cpuLabel.setForeground(Theme.PerformancecpuLabel);
    	gpuLabel.setForeground(Theme.PerformancegpuLabel);
    	boardManufacturerLabel.setForeground(Theme.PerformanceboardManufacturerLabel);
    	boardProductLabel.setForeground(Theme.PerformanceboardProductLabel);
    	biosVersionLabel.setForeground(Theme.PerformancebiosVersionLabel);
    	
    	shutdownRemainningLabel.setForeground(Theme.ShutdownRemainningLabel);
    	
    	dateLabel.setForeground(Theme.PerformancedateLabel);
    }
}
