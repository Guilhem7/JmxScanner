package fr.jrjgjk.actions;

import fr.jrjgjk.mbeanhelpers.MBeanConnect;
import fr.jrjgjk.targets.TargetResult;
import fr.jrjgjk.sockets.SocketHelp;
import fr.jrjgjk.helpers.Printer;
import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.RemoteException;
import java.lang.SecurityException;

public class Checker extends MBeanConnect {

	public Checker(){
		super(null, 0, null);
	}

	public TargetResult connectWithEmptyCredz(){
		try {
			this.connect("", "");
		} catch (IOException ex) {
			if( (ex.getCause() instanceof java.net.SocketTimeoutException || ex.getCause() instanceof java.net.NoRouteToHostException)){
				return TargetResult.UNKNOWN;
			} else {
				return TargetResult.NOT_VULNERABLE;
			}
		} catch(SecurityException ex){
			return TargetResult.REQUIRE_CREDENTIALS;
		} catch(Exception ex) {
			return TargetResult.NOT_VULNERABLE;
		}
		return TargetResult.VULNERABLE;
	}

	public boolean check(String target, int port, String boundName){
		this.setTarget(target, port, boundName);
		TargetResult isTargetVuln = this.connectWithEmptyCredz();
		Printer.info(this.formatMessage(isTargetVuln));
		return getBoolFromRes(isTargetVuln);
	}

	private boolean getBoolFromRes(TargetResult res)
	{
		if(res == TargetResult.VULNERABLE){
			return true;
		}
		return false;
	}

	private String formatMessage(TargetResult res){
		String targetState;
		if(res == TargetResult.VULNERABLE){
			targetState = Printer.color(res.toString(), Printer.LOG);
		} else if(res == TargetResult.REQUIRE_CREDENTIALS){
			targetState = String.format("%s because: %s", Printer.color(TargetResult.NOT_VULNERABLE.toString(), Printer.ERROR), Printer.color(res.toString(), Printer.ORANGE));
		} else {
			targetState = Printer.color(res.toString(), Printer.ERROR);
		}
		return String.format("Target %s:%s/%s is %s", this.target, String.valueOf(this.port), this.boundName, targetState);
	}

	public static String[] getRegistryOfRemote(String host, int port){
		SocketHelp.changeDefaultSocket(host);
		String[] boundNames = null;
		try{
			Registry registry = LocateRegistry.getRegistry(host, port);
			boundNames = registry.list();
		} catch(RemoteException ex){
			Printer.err("Failed to catch remote registry");
			Printer.verr("Error was: " + ex.getMessage());
		}
		return boundNames;
	}

}