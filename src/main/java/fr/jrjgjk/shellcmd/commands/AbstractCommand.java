package fr.jrjgjk.shellcmd;

abstract class AbstractCommand implements Command{
	protected String cmd;
	protected final String methodName;

	abstract protected void parseArgs() throws java.text.ParseException, java.io.IOException;

	public AbstractCommand(String methodName){
		this.methodName = methodName;
	}

	@Override
	public void setCommand(String cmd){
		this.cmd = cmd;
	}

}