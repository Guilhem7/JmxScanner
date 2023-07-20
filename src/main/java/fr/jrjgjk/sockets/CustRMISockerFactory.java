package fr.jrjgjk.sockets;

import fr.jrjgjk.helpers.Printer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.server.RMISocketFactory;

public class CustRMISockerFactory extends RMISocketFactory{

	private String host;
	private static int timeoutMillis = 2000;
	private static String target = null;
	private static boolean followRedirect = false;

	public CustRMISockerFactory(String host, int timeout){

		target = host;
		timeoutMillis = timeout;
	}

	public Socket createSocket(String host, int port) throws IOException {
		if(!followRedirect && !target.equals(host)) {
			Printer.vinfo(String.format("Server trynna redirect us to %s:%s", host, port));
			Printer.vinfo(String.format("  Continue anyway to %s", target));
			host = target;
		}
        Socket socket = new Socket();
        socket.setSoTimeout(timeoutMillis);
        socket.setSoLinger(false, 0);
        socket.connect(new InetSocketAddress(host, port), timeoutMillis);
        return socket;
    }

    public ServerSocket createServerSocket(int port) throws IOException {
        return new ServerSocket(port);
    }

    public static void setGTarget(String newTarget)
    {
    	target = newTarget;
    }

    public static void setTimeout(int time){
    	timeoutMillis = time;
    }

    public static boolean isNotDefined()
    {
    	return target == null;
    }

    public static void setRedirect(boolean redirect)
    {
    	followRedirect = redirect;
    }
}

