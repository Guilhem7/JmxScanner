package fr.jrjgjk;

import fr.jrjgjk.helpers.Printer;
import fr.jrjgjk.helpers.Help;
import fr.jrjgjk.builders.*;
import fr.jrjgjk.actions.Action;
import fr.jrjgjk.sockets.CustRMISockerFactory;
import org.apache.commons.cli.*;

/**
 * Main class for the program JmxScanner
 * Inspired by already existing tools
 *  - rmg ==> resources/templates/SampleTemplate.java <3 remote-method-guesser is incredible :D
 * 
 * To query the MBeans: https://docs.oracle.com/javase/8/docs/api/javax/management/MBeanServerConnection.html#queryMBeans-javax.management.ObjectName-javax.management.QueryExp-
 * Not needed for now
 * 
 * Other tools that I looked at a lot:
 *  - https://github.com/siberas/sjet
 *  - https://github.com/flubshi/jmx-exploiter
 * 
 */

public class JmxScanner{

	public static void main(String[] argv) {
		CommandLineParser parser = new DefaultParser();
		CommandLine cmdLine = null;

		Options opts = Help.getOptions();
        String action = "";

		try {
            cmdLine = parser.parse(opts, argv);
            action = cmdLine.getOptionValue("action", "");
            Help.checkCli(cmdLine);
        } catch (ParseException e) {
            Printer.err(e.getMessage());
     		Help.displayHelp(action);
            System.exit(1);
        }

        Printer.setVerbosity(cmdLine.hasOption("verbose"));
    	Printer.vinfo("Turning verbosity on");
        
        CustRMISockerFactory.setRedirect(cmdLine.hasOption("follow-redirect"));

        BuilderFromCli builderCli = new BuilderFromCli(cmdLine);
        Builder builder = null;

        if(action.equalsIgnoreCase("uninstall")){
            builder = new UninstallerBuilder();
            builderCli.buildUninstaller(builder);
        } else if(action.equalsIgnoreCase("interactive")) {
            builder = new InteractiveBuilder();
            builderCli.buildInteractive(builder);
        } else if(action.equalsIgnoreCase("scan")) {
            builder = new CheckBuilder();
            builderCli.buildCheck(builder);
        } else {
            System.exit(1);
        }

        Action concreteAction = builder.getAction();
        concreteAction.init();
        concreteAction.run();

	}

}
