package fr.jrjgjk.shellcmd;

import fr.jrjgjk.helpers.Printer;
import fr.jrjgjk.helpers.Utils;
import java.io.File;

public class DownloadCommand extends AbstractCommand {
	private Object[] args = new Object[] { null };
	private String fileDest = "";
	private String[] type = new String[] { String.class.getName() };

	public DownloadCommand()
	{
		super("downloadFile");
	}

	@Override
	protected void parseArgs() throws java.text.ParseException, java.io.IOException {
		String param = this.cmd.replaceAll("\\s+", " ");
		if(param.split(" ").length >= 2 && !param.split(" ")[1].isEmpty()) {
			String filePath = param.split(" ")[1];
			this.args[0] = filePath;
			this.fileDest = new File(filePath).getName();
		} else {
			throw new java.text.ParseException("Bad Usage: download <file_path>", 0);
		}

	}

	@Override
	public void execute(Dispatcher dispatch) {
		try {
			this.parseArgs();
			byte[] res = (byte[]) dispatch.callRemoteMethod(this.methodName, this.args, this.type);
			if(res != null){
				Utils.writeFile(this.fileDest, res);
				Printer.log("File downloaded: " + Printer.color(this.fileDest, Printer.ORANGE));
			}
		} catch (Exception ex) {
			Printer.err("Exception parsing arguments: " + ex.getMessage());
		}
	}

}
