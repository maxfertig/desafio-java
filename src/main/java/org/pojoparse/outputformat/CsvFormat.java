package main.java.org.pojoparse.outputformat;

import java.io.StringWriter;
import java.util.List;
import java.util.Map;

/**
 * Implementation of FileFormat to create a CSV format of POJO
 *
 * @author  Max Fertig
 */
public class CsvFormat implements FileFormat {

	private String file = null;

	/**
	 * Constructs a new <tt>CSV</tt> text file with the getContent
	 * method specified at FileFormat interface
	 *
	 * @param data
	 *            a map of POJO parameters and their list of values
	 */
	public CsvFormat(Map<String, List<String>> data) {
		this.create(data);
	}

	/**
	 * Called by the constructor, create a csv text ready to OutputStream
	 * The text created are stored in file instance parameter
	 *
	 * @param data
	 *            a map of POJO parameters and their list of values
	 * @return void no return
	 */
	private void create(Map<String, List<String>> data) {

		StringWriter returnString = new StringWriter();
		Integer maxKeySize = data.size(), maxValueSize = 0, keyIndex = 1;

		for (Map.Entry<String, List<String>> entry : data.entrySet()) {
			if (keyIndex < maxKeySize)
				returnString.append(entry.getKey()).append(",");
			else
				returnString.append(entry.getKey());

			if (entry.getValue().size() > maxValueSize)
				maxValueSize = entry.getValue().size();

			keyIndex++;
		}
		returnString.append("\r\n");

		for (Integer i = 0; i < maxValueSize; i++) {
			keyIndex = 1;
			for (Map.Entry<String, List<String>> entry : data.entrySet()) {
				if (keyIndex < maxKeySize)
					returnString.append(entry.getValue().get(i)).append(",");
				else
					returnString.append(entry.getValue().get(i));
				keyIndex++;
			}
			if (i < (maxValueSize - 1))
				returnString.append("\r\n");
		}

		this.file = returnString.toString();
	}

	/**
	 * Returns the file text content.
	 * The text returned is ready to OutputStream.
	 *
	 * @return String file text content
	 */
	@Override
	public String getContent() {
		return this.file;
	}

}
