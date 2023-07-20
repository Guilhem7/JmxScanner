package fr.jrjgjk.sockets;

import fr.jrjgjk.helpers.Printer;
import fr.jrjgjk.Config;
import java.rmi.server.RMISocketFactory;

public class SocketHelp {
	public static void changeDefaultSocket(String target){
		try{
            if(CustRMISockerFactory.isNotDefined()){
                RMISocketFactory.setSocketFactory(new CustRMISockerFactory(target, Config.timeout));
            } else {
                CustRMISockerFactory.setGTarget(target);
            }
        } catch(Exception ex){
            Printer.err("Cannot redeclare socket factory...");
            ex.printStackTrace();
            System.exit(1);
        }
	}
}