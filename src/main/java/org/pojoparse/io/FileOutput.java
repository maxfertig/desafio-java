package main.java.org.pojoparse.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.logging.Logger;

/**
 * Output class to return a OutputStream instance of the parsed POJO
 *
 * @author  Max Fertig
 */
public class FileOutput {
	
	private OutputStream file = null;
	private final Logger logger = Logger.getLogger("main.java.org.util.FileOutput");

	public FileOutput(String fileContent) {
		createOutputStream(fileContent);
	}
	
	private void createOutputStream(String fileContent) {
		this.file = new ByteArrayOutputStream();
		try {
			this.file.write(fileContent.getBytes(Charset.forName("UTF-8")));
		} catch (IOException e) {
			logger.warning(e.getMessage());
		}
	}
	
	public OutputStream getContent() {
		return this.file;
	}

}
