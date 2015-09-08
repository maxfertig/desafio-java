package main.java.org.pojoparse.outputformat;

/**
* <p>
* 	Parse POJOS need output file formats, this interface is a model for all
* 	formats that will be implemented on this library. All of them can be
* 	internally designed as their wish, but all need to return the file text
* 	ontent ready to OutputStream.
* </p>
*
* @author  Max Fertig
* @see CsvFormat
*/
public interface FileFormat {
		
	/**
	 * Method called to retrieve file text content processed in the specific
	 * file format class. The text content returned is ready to OutputStream.
	 *
	 * @return String file text content
	 */
	public String getContent();

}
