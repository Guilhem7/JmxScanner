package fr.jrjgjk.shellcmd;

import fr.jrjgjk.mbeanhelpers.MBeanOpManager;
import javax.management.MBeanServerConnection;

public class RemoteCaller{
	private MBeanOpManager mBeanOpManager;
	private MBeanServerConnection conn;

	public RemoteCaller(MBeanOpManager m, MBeanServerConnection conn){
		this.mBeanOpManager = m;
		this.conn = conn;
	}

	public Object invokeMethod(String methodName, Object[] params, String[] type){
		return this.mBeanOpManager.invokeMBeanMethodNoFail(this.conn, methodName, params, type);
	}

}