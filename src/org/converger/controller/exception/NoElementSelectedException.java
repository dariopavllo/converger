package org.converger.controller.exception;

/**
 * A checked exception occurred when an expression is required but no expression is selected from the user interface.
 * @author Gabriele Graffieti
 *
 */
public class NoElementSelectedException extends Exception {

	private static final long serialVersionUID = -1321103466815769854L;
	
	/**
	 * Create the Exception.
	 * @param message the message ot the exception
	 */
	public NoElementSelectedException(final String message) {
		super(message);
	}

}
