package fr.jrjgjk.builders;

import fr.jrjgjk.actions.ActionType;
import fr.jrjgjk.actions.Action;
import fr.jrjgjk.actions.Uninstaller;
import fr.jrjgjk.LocalServer;

public class UninstallerBuilder implements Builder{
	private ActionType type;
	private String username;
	private String password;
	private String target;
	private String boundName;
	private int port;

	@Override
	public void setActionType(ActionType type)
	{
		this.type = type;
	}

	@Override
	public void setCredz(String username, String password)
	{
		this.username = username;
		this.password = password;
	}

	@Override
	public void setLocalServer(LocalServer lServer){}

	@Override
	public void setTarget(String target, String port)
	{
		this.target = target;
		this.port = Integer.valueOf(port);
	}

	@Override
	public void setTargetFile(String filename){}

	@Override
	public void setBoundName(String name)
	{
		this.boundName = name;
	}

	@Override
	public Action getAction()
	{
		return new Uninstaller(this.target, this.port, this.username, this.password, this.boundName);
	}

}