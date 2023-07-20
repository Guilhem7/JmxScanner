package fr.jrjgjk.helpers;

import fr.jrjgjk.mbeanhelpers.MBeanOpManager;
import fr.jrjgjk.shellcmd.*;
import fr.jrjgjk.sockets.CustRMISockerFactory;
import javax.management.MBeanServerConnection;

public class InteractiveShell{

	private Dispatcher dispatch;
	private String cwd;
	private String cmdResult;

	private final int timeout = 5000;
	
	public InteractiveShell(RemoteCaller remCall){
		this.dispatch = new Dispatcher(remCall);
	}

	private void showHelp(){
		String help = 	"\n============================================================" +
						"\nThis pseudo shell execute command on the host by default" +
						"\nBut it provides other command" +
						"\nUse the Help command to show help (case insensitive)" +
						"\n============================================================";
		System.out.println(help);
	}

	public void startRoutine(){
		CustRMISockerFactory.setTimeout(this.timeout);
		Shell shell = new Shell();
		String cmd = "";
		this.showHelp();
		while(true){
			this.dispatch.dispatch(cmd);
			System.out.println(this.dispatch.getCommandRes());

			cmd = shell.getCommand(this.dispatch.getCwd());

			if(cmd.equalsIgnoreCase("exit")){
				Printer.log("Exiting shell..");
				break;
			}
		}
	}

}
