package fr.jrjgjk.parser;

import fr.jrjgjk.targets.Target;
import fr.jrjgjk.helpers.Printer;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;

public class SimpleNmapParser {

	private String filename;
	private final Pattern patternIp = Pattern.compile("Nmap scan report for .*?(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})", Pattern.CASE_INSENSITIVE);
	private final Pattern patternPort = Pattern.compile("(\\d+)/tcp\\s+open\\s+java-rmi.*Java", Pattern.CASE_INSENSITIVE);

	private Target target = null;
	private ArrayList<Target> targets;

	public SimpleNmapParser(String filename){
			this.filename = filename;
	}

	public ArrayList<Target> parse(){
		targets = new ArrayList<Target>();
		Printer.info("Parsing nmap file: " + this.filename);
		try{
			Scanner scanner = new Scanner(new File(this.filename));

			while (scanner.hasNextLine()) {
				this.parseLine(scanner.nextLine());
			}

			scanner.close();
		} catch(FileNotFoundException ex){
			Printer.err("No such file or directory: " + this.filename);
			System.exit(1);
		}
		return targets;
	}

	private void parseLine(String line){
		Matcher matcherIp = patternIp.matcher(line);
		if(matcherIp.find()){
			this.switchTarget();

			String ipTarget = matcherIp.group(1);
			this.initPotentialTarget(ipTarget);
		} else {
			Matcher matcherPort = patternPort.matcher(line);
			if(matcherPort.find()){
				int port = Integer.valueOf(matcherPort.group(1));
				this.addPortTarget(port);
			}
		}
	}

	private void initPotentialTarget(String ip){
		this.target = new Target(ip);
	}

	private void addPortTarget(int port){
		if(this.target != null){
			this.target.addPort(port);
		}
	}

	private void switchTarget(){
		if(this.target != null && !this.target.isPortEmpty()){
			this.targets.add(target);
			System.out.print(target);
		}
	}

}