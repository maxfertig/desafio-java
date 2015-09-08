package main.java.org.pojoparse.exception;

/**
 * An ComplexTypeException is thrown when an application tries
 * to pass a POJO object that has parameters with complex types.
 *
 * @author  Max Fertig
 */
public class ComplexTypeException extends Exception {

	private static final long serialVersionUID = 1L;

	public ComplexTypeException() {
		super();
	}

	public ComplexTypeException(String message) {
		super(message);
	}

	public ComplexTypeException(String message, Throwable cause) {
		super(message, cause);
	}

	public ComplexTypeException(Throwable cause) {
		super(cause);
	}
}