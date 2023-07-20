package fr.jrjgjk.mbeanhelpers;

import fr.jrjgjk.LocalServer;
import fr.jrjgjk.helpers.Printer;
import fr.jrjgjk.Config;

import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;

public class MBeanServer {
	
	private int port;
	private String endpoint;
	private HttpServer server;

	private LocalServer lServer;

	private static final String serverListener = "0.0.0.0";

	public MBeanServer(LocalServer localServer){
		this.lServer = localServer;
	}

	public void startHttpServer(){
		try{
			server = HttpServer.create(new InetSocketAddress(serverListener, Integer.valueOf(this.lServer.getPort())), 0);
		} catch(IOException ex){
			Printer.err("Failed creating server...");
			ex.printStackTrace();
			System.exit(1);
		}

		server.createContext("/" + this.lServer.getEndpoint(), new MBeanServerHandler(
														Config.packageName,
														Config.objectName,
														Config.jarPath,
														Config.jarName,
														this.lServer
														));
		server.setExecutor(null);
		server.start();
		Printer.log("Server started on " + serverListener + " " + this.lServer.getPort());

	}

	public String getUrl(){
		return this.lServer.getUrl();
	}

	public void stopHttpServer(){
		this.server.stop(0);
		Printer.log("Server http stoped");
	}
}