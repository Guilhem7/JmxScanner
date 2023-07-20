package fr.jrjgjk.shellcmd;

import fr.jrjgjk.helpers.Utils;
import fr.jrjgjk.helpers.Printer;

public class UploadCommand extends AbstractCommand {
	private Object[] args = new Object[2];
	private String[] type = new String[] { byte[].class.getName(), String.class.getName() };


	public UploadCommand()
	{
		super("uploadFile");
	}

	@Override
	protected void parseArgs() throws java.text.ParseException, java.io.IOException {
		String param = this.cmd.replaceAll("\\s+", " ");
		if(param.split(" ").length >= 3 && !param.split(" ")[1].isEmpty() && !param.split(" ")[2].isEmpty()) {
			byte[] fileContent = Utils.readFile(param.split(" ")[1]);
			if(fileContent.length == 0){
				throw new java.io.IOException("Cannot read file " + param.split(" ")[1]);
			}
			String fileDest = param.split(" ")[2];
			this.args[0] = fileContent;
			this.args[1] = fileDest;
		} else {
			throw new java.text.ParseException("Bad Usage: upload <local_file_path> <remote_destination>", 0);
		}
	}

	@Override
	public void execute(Dispatcher dispatch) {
		try {
			this.parseArgs();
			boolean res = (boolean) dispatch.callRemoteMethod(this.methodName, this.args, this.type);
			if(res){
				Printer.log("File has been uploaded");
			} else {
				Printer.err("There was a problem uploading your file...");
			}
		} catch (Exception ex) {
			Printer.err("Exception parsing arguments: " + ex.getMessage());
		}
	}

}
