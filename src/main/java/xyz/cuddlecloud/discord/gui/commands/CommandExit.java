package xyz.cuddlecloud.discord.gui.commands;

public final class CommandExit implements ICommand{

	@Override
	public String getName() {
		return "exit";
	}

	@Override
	public void getHelp() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void run() {
		System.exit(0);
	}
}
