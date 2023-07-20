package fr.jrjgjk.helpers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Utils {
	
	public static byte[] readFile(String filePath) throws IOException{
		byte[] bytearray;
		Path fileP = Paths.get(filePath);
		if(Files.exists(fileP)){
			bytearray = Files.readAllBytes(fileP);
		} else {
			Printer.err("No such file: " + filePath);
			bytearray = new byte[] {};
		}
		return bytearray;
	}

	public static void writeFile(String dest, byte[] data){
		File outputFile = new File(dest);
		try{
			FileOutputStream outputStream = new FileOutputStream(outputFile);
			outputStream.write(data);
		} catch (Exception ex) {
			Printer.err(ex.getMessage());
		}
	}

}