package fr.jrjgjk.actions;

import fr.jrjgjk.Config;
import fr.jrjgjk.mbeanhelpers.MBeanConnect;
import fr.jrjgjk.mbeanhelpers.MBeanServer;
import fr.jrjgjk.mbeanhelpers.MBeanOpManager;
import fr.jrjgjk.helpers.Printer;

/**
 * Uninstaller class used to uninstall the backdoor
 * This class implements the action interface
 * 
 * It also implements the MBeanConnect class, so it can connect to the remote server
 */
public class Uninstaller extends MBeanConnect implements Action{
	private MBeanOpManager mBeanOpManager = null;
	private MBeanServer mBeanServer = null;
	private String username;
	private String password;

	public Uninstaller(String target, int port, String username, String password, String boundName) {
		super(target, port, boundName);
		this.username = username;
		this.password = password;
	}

	private void initMBeanServerConnection()
	{
		try{
			this.connect(this.username, this.password);
		} catch (Exception ex){
			Printer.err("Connection to target failed...");
			Printer.verr("Url: " + this.getUrl());
			ex.printStackTrace();
			System.exit(1);
		}
	}

	private void initMBeanOperationsManager()
	{
		try{
			this.mBeanOpManager = new MBeanOpManager(Config.mLetName, Config.objectName);
		} catch (Exception ex){
			Printer.err("An exception occured while initilizing object name...");
			ex.printStackTrace();
			System.exit(1);
		}
	}

	@Override
	public void init(){
		this.initMBeanServerConnection();
		this.initMBeanOperationsManager();
	}

	@Override
	public void run(){
		Printer.vinfo("Removing backdoor..");
		this.mBeanOpManager.cleanAll(this.getConnection());
	}

}