package fr.jrjgjk.helpers;

import org.apache.commons.cli.*;

public class Help{
	
    public static Options getOptions(){
		Options opts = new Options();

		Option user = new Option("u", "user", true, "Username if needed");
        user.setRequired(false);
        user.setArgName("username");
        opts.addOption(user);

        Option password = new Option("P", "password", true, "Password if needed");
        password.setRequired(false);
        password.setArgName("password");
        opts.addOption(password);

        Option port = new Option("p", "port", true, "Port for jmx (default: 1099)");
        port.setRequired(false);
        port.setArgName("port");
        opts.addOption(port);

        Option bound = new Option("bn", "bound-name", true, "Bound name for jmx (default: jmxrmi)");
        bound.setRequired(false);
        bound.setArgName("bound-name");
        opts.addOption(bound);

        Option verbose = new Option("v", "verbose", false, "Increase verbosity");
        verbose.setRequired(false);
        opts.addOption(verbose);

        Option followRedirect = new Option("f", "follow-redirect", false, "Whether to follow server redirect or not");
        followRedirect.setRequired(false);
        opts.addOption(followRedirect);

        Option serverHost = new Option("sh", "server-host", true, "Listener IP used for serving payload (exemple: 192.168.22.6)");
        serverHost.setRequired(false);
        serverHost.setArgName("server-host");
        opts.addOption(serverHost);

        Option action = new Option("a", "action", true, "The action to execute (uninstall, install, interactive, command)");
        action.setRequired(false);
        action.setArgName("action");
        opts.addOption(action);

        Option serverPort = new Option("sp", "server-port", true, "Listener PORT used for serving payload (default: 80)");
        serverPort.setRequired(false);
        serverPort.setArgName("server-port");
        opts.addOption(serverPort);

        Option nmapFile = new Option("nf", "nmap-file", true, "The nmap file to read targets from");
        nmapFile.setRequired(false);
        nmapFile.setArgName("nmap-file");
        opts.addOption(nmapFile);

        Option mletName = new Option("mn", "mlet-name", true, "The nmap file to read targets from");
        mletName.setRequired(false);
        mletName.setArgName("mlet-name");
        opts.addOption(mletName);

        Option target = new Option("t", "target", true, "Target to scan");
        target.setRequired(false);
        target.setArgName("target");
        opts.addOption(target);

        return opts;
	}

    public static void checkCli(CommandLine cmd) throws ParseException{
        if(!cmd.hasOption("action")){
            throw new ParseException("No action specified");
        } else {
            if(cmd.getOptionValue("action", "").equalsIgnoreCase("scan")){
                if(!cmd.hasOption("target") && !cmd.hasOption("nmap-file")){
                    throw new ParseException("Target or nmap file must be specified");
                }
            } else if(cmd.getOptionValue("action", "").equalsIgnoreCase("interactive")){
                if(!cmd.hasOption("target")){
                    throw new ParseException("Target must be specified");
                } else if(!cmd.hasOption("server-host")){
                    throw new ParseException("Server host needs to be specified");
                }
            } else if(cmd.getOptionValue("action", "").equalsIgnoreCase("uninstall")){
                if(!cmd.hasOption("target")){
                    throw new ParseException("Target must be specified");
                }
            } else {
                throw new ParseException("Bad action specified");
            }
        }
    }

	public static void displayHelp(String action){
		HelpFormatter formatter = new HelpFormatter();
		System.out.println(getMessage(action));
	}

    public static String getMessage(String action){
        String msg = "JmxScanner --action ";
        switch (action.toLowerCase()) {
            case "interactive":
                msg += action + "\n\t-t, --target <ip_adress>\tTarget to scan" +
                                "\n\t-p,--port <port>\tPort to connect to (default: 1099)" +
                                "\n\t-sh, --server-host <ip_address>\tListener IP used for serving payload" +
                                "\n\t-sp, --server-port <port>\tListener PORT used for serving payload (default: 80)" +
                                "\n\t-u,--username <username>\tUsername to use to connect if needed" +
                                "\n\t-P,--password <password>\tPassword to use to connect if needed";
                break;
            case "uninstall":
                msg += action + "\n\t-t, --target <ip_adress>\tTarget to scan" +
                                "\n\t-p,--port <port>\tPort to connect to (default: 1099)" +
                                "\n\t-u,--username <username>\tUsername to use to connect if needed" +
                                "\n\t-P,--password <password>\tPassword to use to connect if needed";
                break;
            case "scan":
                msg += action + "\n\t-nf, --nmap-file <path_to_nmap_file>\tThe nmap file to read targets from" +
                                "\n\t-t, --target <ip_adress>\tTarget to scan" +
                                "\n\t-p,--port <port>\tPort to connect to (default: 1099)";
                break;
            default:
                msg += "{scan, interactive, uninstall}";
                break;
        }

        msg +=  "\n\t-f,--follow-redirect\tWhether to follow server redirection or not (default: Nope)";
        msg +=  "\n\t-v,--verbose\tIncrease verbosity";
        msg +=  "\n\t-mn,--mlet-name <mlet-name>\tSet the mletName to target (default: DefaultDomain:type=MLet / but for jonas: jonas:type=MLet)";

        return msg;
    }
}
