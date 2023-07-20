package fr.jrjgjk.builders;

import fr.jrjgjk.actions.ActionType;
import fr.jrjgjk.actions.Action;
import fr.jrjgjk.LocalServer;

public interface Builder{
	public void setActionType(ActionType type);
	public void setCredz(String username, String password);
	public void setLocalServer(LocalServer lServer);
	public void setTarget(String target, String port);
	public void setTargetFile(String filename);
	public void setBoundName(String name);
	public Action getAction();
}