package fr.jrjgjk;

import fr.jrjgjk.LocalServer;
import fr.jrjgjk.Config;
import fr.jrjgjk.actions.ActionType;
import fr.jrjgjk.builders.*;

import org.apache.commons.cli.*;

public class BuilderFromCli {

	private CommandLine cli;
	
	public BuilderFromCli(CommandLine cli){
		this.cli = cli;
		this.setStaticConfig();
	}

	public void buildUninstaller(Builder builder)
	{
		builder.setActionType(ActionType.UNINSTALL_ACTION);
		builder.setTarget(
				this.cli.getOptionValue("target"),
				this.cli.getOptionValue("port", Config.defaultTargetPort)
			);
		builder.setCredz(
				this.cli.getOptionValue("username", ""),
				this.cli.getOptionValue("password", "")
			);
		builder.setBoundName(this.cli.getOptionValue("bound-name", Config.defaultBoundName));
	}

	public void buildCheck(Builder builder)
	{
		builder.setActionType(ActionType.CHECK_ACTION);
		if(this.cli.hasOption("nmap-file") && !this.cli.getOptionValue("nmap-file", "").isEmpty()){
			builder.setTargetFile(this.cli.getOptionValue("nmap-file"));
		} else {
			builder.setTarget(
					this.cli.getOptionValue("target"),
					this.cli.getOptionValue("port", Config.defaultTargetPort)
				);
		}
	}

	public void buildInteractive(Builder builder)
	{
		builder.setActionType(ActionType.INTERACTIVE_ACTION);
		builder.setTarget(
				this.cli.getOptionValue("target"),
				this.cli.getOptionValue("port", Config.defaultTargetPort)
			);
		builder.setCredz(
				this.cli.getOptionValue("username", ""),
				this.cli.getOptionValue("password", "")
			);
		builder.setBoundName(this.cli.getOptionValue("bound-name", Config.defaultBoundName));

		LocalServer lServer = new LocalServer(
                                this.cli.getOptionValue("server-host"),
                                this.cli.getOptionValue("server-port", Config.defaultServerPort),
                                Config.defaultLocalServerEndpoint
                                );

		builder.setLocalServer(lServer);
	}

	private void setStaticConfig()
	{
		String mLetName = this.cli.getOptionValue("mlet-name", "");
        if(!mLetName.isEmpty()){
            Config.setMletName(mLetName);
        }
	}

}