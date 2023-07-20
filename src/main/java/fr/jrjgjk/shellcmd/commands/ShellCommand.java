package fr.jrjgjk.shellcmd;

import fr.jrjgjk.helpers.Printer;

public class ShellCommand extends AbstractCommand {
	private Object[] args = new Object[] { null };
	private String[] type = new String[] { String.class.getName() };

	public ShellCommand()
	{
		super("startCmd");
	}

	@Override
	protected void parseArgs() throws java.text.ParseException, java.io.IOException {
		args[0] = this.cmd;
	}

	@Override
	public void execute(Dispatcher dispatch) {
		try{
			this.parseArgs();
			String res = (String) dispatch.callRemoteMethod(this.methodName, this.args, this.type);
			dispatch.setCwdAndRes(res.split("\n", 2));
		} catch (Exception ex) {
			Printer.err("Exception parsing arguments: " + ex.getMessage());
		}
	}

}