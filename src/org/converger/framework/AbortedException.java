package org.converger.framework;

/**
 * This exception is thrown when a framework calculation is stopped
 * from a different thread (using the specific method).
 * Note that this exception is unchecked, as its usage depends
 * on the threading model used: in a single threaded environment
 * the user should not worry about handling this exception.
 * @author Dario Pavllo
 *
 */
public class AbortedException extends RuntimeException {

	private static final long serialVersionUID = -2335747219967466242L;

}
