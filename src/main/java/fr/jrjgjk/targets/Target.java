package fr.jrjgjk.targets;

import fr.jrjgjk.helpers.Printer;
import java.util.ArrayList;

public class Target{

	private String ip;
	private ArrayList<Integer> port;

	public Target(String ip){
		this.ip = ip;
		this.port = new ArrayList<Integer>();
	}


	public String getIp(){
		return this.ip;
	}

	public ArrayList<Integer> getPort(){
		return this.port;
	}

	public void addPort(int port){
		this.port.add(port);
	}

	public boolean isPortEmpty(){
		if(this.port == null || this.port.size() == 0){
			return true;
		}
		return false;
	}

	public String toString(){
		String display = "";
		for(Integer p: this.port){
			display += Printer.color("\t[+]", Printer.DEBUG) + " RMI Target -- " + this.ip + ":" + p + "\n";
		}
		return display;
	}

}