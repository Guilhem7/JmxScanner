package fr.jrjgjk.actions;

import fr.jrjgjk.parser.SimpleNmapParser;
import fr.jrjgjk.targets.*;
import fr.jrjgjk.helpers.Printer;

import java.util.ArrayList;

public class CheckerMaster implements Action{

	private String nmapFile = null;
	private ArrayList<Target> targets;

	public CheckerMaster(String target, int port){
		Target t = new Target(target);
		t.addPort(port);

		this.targets = new ArrayList<Target>();
		this.targets.add(t);
	}

	public CheckerMaster(String nmapFile){
		this.nmapFile = nmapFile;
	}

	@Override
	public void init()
	{
		if(this.nmapFile != null){
			SimpleNmapParser nmapParser = new SimpleNmapParser(this.nmapFile);
			this.targets = nmapParser.parse();
		}
	}

	@Override
	public void run()
	{
		Checker check = new Checker();
		ArrayList<String> vulnerablesTarget = new ArrayList<String>();
		for(Target t: this.targets)
		{
			System.out.println("\n============[ " + t.getIp() + " ]============");
			for(Integer p: t.getPort()){
				String[] boundNames = check.getRegistryOfRemote(t.getIp(), p);
				if(boundNames != null)
				{
					for(String s: boundNames)
					{
						Printer.vinfo("Checking rmi://" + t.getIp() + ":" + String.valueOf(p) + "/" + s);
						if(check.check(t.getIp(), p, s)){
							vulnerablesTarget.add(t.getIp() + ":" + String.valueOf(p) + "/" + s);
						}
					}
				} else {
					Printer.err("No registry found for remote: " + t.getIp() + ":" + String.valueOf(p) + " ==> " + Printer.color(TargetResult.NOT_VULNERABLE.toString(), Printer.ERROR));
				}
			}
			System.out.println("====================================\n");
		}
		this.displayVuln(vulnerablesTarget);

	}

	private void displayVuln(ArrayList<String> t)
	{
		if(t.size() >= 1)
		{
			Printer.log("Displaying vulnerable jmx endpoints:");
			for(String s: t)
			{
				Printer.info(s);
			}
		}
	}
}