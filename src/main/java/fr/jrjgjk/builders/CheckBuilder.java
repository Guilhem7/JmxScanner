package fr.jrjgjk.builders;

import fr.jrjgjk.actions.ActionType;
import fr.jrjgjk.actions.Action;
import fr.jrjgjk.actions.CheckerMaster;
import fr.jrjgjk.LocalServer;

public class CheckBuilder implements Builder{
	private ActionType type;
	private String target;
	private int port;
	private String nmapFile = null;

	@Override
	public void setCredz(String username, String password){}

	@Override
	public void setLocalServer(LocalServer lServer){}

	@Override
	public void setTargetFile(String filename)
	{
		this.nmapFile = filename;
	}

	@Override
	public void setActionType(ActionType type)
	{
		this.type = type;
	}

	@Override
	public void setTarget(String target, String port)
	{
		this.target = target;
		this.port = Integer.valueOf(port);
	}

	@Override
	public void setBoundName(String name){}


	@Override
	public Action getAction()
	{
		if(this.nmapFile == null){
			return new CheckerMaster(this.target, this.port);
		}
		return new CheckerMaster(this.nmapFile);
	}

}