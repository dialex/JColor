package print.exception;

/**
 * This exception is thrown whenever the arguments received do not comply with
 * the ones expected.
 * @version 1.15
 * @author Diogo Nunes
 */
public class InvalidArgumentsException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Simple constructor.
	 */
	public InvalidArgumentsException() {
		//empty on purpose
	}

	@Override
	public final String toString() {
		return "Invalid arguments.";
	}

}
