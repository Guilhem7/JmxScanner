package fr.jrjgjk.shellcmd;

import fr.jrjgjk.helpers.Printer;

public class HelpCommand extends AbstractCommand {
	private Object[] args = new Object[] { null };
	private String[] type = new String[] { String.class.getName() };

	public HelpCommand()
	{
		super("");
	}

	@Override
	protected void parseArgs() throws java.text.ParseException, java.io.IOException {}

	@Override
	public void execute(Dispatcher dispatch) {
		String msg = 	"help for this shell:" +
						"\n" +
						"\n - Download <file_to_download>\tDownload an existing file from the remote target" +
						"\n - Upload   <local_file> <name>\tUpload an existing file on the remote target with the name <name>" +
						"\n - Help\tShow this help";
		Printer.log(msg);
	}

}