package fr.jrjgjk.helpers;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Shell{

	private BufferedReader csl;
	private String cmd;

	public Shell(){
		this.csl = new BufferedReader(new InputStreamReader(System.in));
		this.cmd = "";
	}

	public String getCommand(String cwd){
		String prompt = String.format("[%s]\n$ ", Printer.color(cwd, Printer.DEBUG));
		System.out.print(prompt);
		try{
			this.cmd = this.csl.readLine();
		} catch(java.io.IOException ex) {
			Printer.err("Failed..");
			ex.printStackTrace();
			System.exit(1);
		}
		return this.cmd;
	}

}
