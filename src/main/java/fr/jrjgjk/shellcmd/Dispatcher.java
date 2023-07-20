package fr.jrjgjk.shellcmd;

import java.util.HashMap;

public class Dispatcher{

	private CommandResult cmdRes = null;
	private RemoteCaller remoteCall = null;
	private HashMap<String, Command> availableCommands = null;
	private boolean outputRes = false;

	public Dispatcher(RemoteCaller caller){
		this.cmdRes = new CommandResult();
		this.remoteCall = caller;
		this.availableCommands = new HashMap<String, Command>();
		this.availableCommands.put("shell", new ShellCommand());
		this.availableCommands.put("upload", new UploadCommand());
		this.availableCommands.put("download", new DownloadCommand());
		this.availableCommands.put("help", new HelpCommand());
	}

	public void dispatch(String command) {
		Command concreteCommand = this.getCommand(command);
		concreteCommand.setCommand(command);
		concreteCommand.execute(this);
	}

	public Object callRemoteMethod(String methodName, Object[] params, String[] type)
	{
		return this.remoteCall.invokeMethod(methodName, params, type);
	}

	private Command getCommand(String command){
		outputRes = false;
		if(command.trim().toLowerCase().startsWith("upload")){

			return this.availableCommands.get("upload");
		} else if(command.trim().toLowerCase().startsWith("download")){
			
			return this.availableCommands.get("download");
		} else {
			outputRes = true;
			this.cmdRes.setRes("");
			if(command.trim().toLowerCase().equals("help")){
				return this.availableCommands.get("help");
			}
			return this.availableCommands.get("shell");
		}
	}

	public String getCwd(){
		return this.cmdRes.getCwd();
	}

	public String getCommandRes(){
		if(outputRes){
			return this.cmdRes.getRes();
		}
		return "";
	}

	public void setCwdAndRes(String[] res){
		this.cmdRes.setBoth(res);
	}

}