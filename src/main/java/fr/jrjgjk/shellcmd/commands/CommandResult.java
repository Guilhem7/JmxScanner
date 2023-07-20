package fr.jrjgjk.shellcmd;

public class CommandResult{
	private String cwd;
	private String res;

	public CommandResult(){
		this.cwd = "";
		this.res = "";
	}

	public CommandResult(String cwd){
		this.cwd = cwd;
		this.res = "";
	}

	public CommandResult(String cwd, String res){
		this.cwd = cwd;
		this.res = res;
	}

	public String getCwd(){
		return this.cwd;
	}

	public String getRes(){
		return this.res;
	}

	public void setCwd(String cwd){
		this.cwd = cwd;
	}

	public void setRes(String res){
		this.res = res;
	}

	public void setBoth(String[] infos)
	{
		this.cwd = infos[0];
		this.res = infos[1];
	}

}