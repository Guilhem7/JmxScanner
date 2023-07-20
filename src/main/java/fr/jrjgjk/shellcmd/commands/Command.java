package fr.jrjgjk.shellcmd;

public interface Command{
	public void execute(Dispatcher dispatch);
	public void setCommand(String cmd);
}