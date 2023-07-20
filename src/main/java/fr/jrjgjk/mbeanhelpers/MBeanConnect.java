package fr.jrjgjk.mbeanhelpers;

import fr.jrjgjk.sockets.SocketHelp;
import fr.jrjgjk.helpers.Printer;
import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.util.HashMap;

public abstract class MBeanConnect {

	protected String target;
	protected String boundName;
	protected int port;

	public MBeanServerConnection conn = null;

	public MBeanConnect(String target, int port, String boundName){
		this.target = target;
		this.port = port;
		this.boundName = boundName;
	}

	public void setTarget(String target, int port, String boundName){
		this.target = target;
		this.port = port;
		this.boundName = boundName;
	}

	public void connect(String username, String password) throws Exception {
		SocketHelp.changeDefaultSocket(this.target);

		HashMap<String, String[]> environment = new HashMap<String, String[]>();
		String[] credentials = new String[] {username, password};
		environment.put(JMXConnector.CREDENTIALS, credentials);

		JMXServiceURL jmxUrl = new JMXServiceURL(this.getUrl());

		Printer.vlog("Connecting to jmx server");
		Printer.vlog("Url used rmi://" + this.target + ":" + String.valueOf(this.port) +  "/" + this.boundName);

		JMXConnector jmxConnector = JMXConnectorFactory.connect(jmxUrl, environment);
		Printer.vlog("Connection successful");

		this.conn = jmxConnector.getMBeanServerConnection();

	}

	public MBeanServerConnection getConnection(){
		return this.conn;
	}

	protected void setBoundName(String boundName){
		this.boundName = boundName;
	}

	protected String getUrl(){
		return "service:jmx:rmi:///jndi/rmi://" + this.target + ":" + String.valueOf(this.port) +  "/" + this.boundName;
	}

}