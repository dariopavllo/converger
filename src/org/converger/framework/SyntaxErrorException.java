package org.converger.framework;

/**
 * This exception represents a syntax error.
 * It is thrown by the parser when it is given an invalid expression.
 * @author Dario Pavllo
 */
public class SyntaxErrorException extends Exception {

	private static final long serialVersionUID = 2469747959620043906L;
	
	private final String expression;
	
	/**
	 * Constructs a syntax error exception with the specified message and
	 * the invalid expression.
	 * @param message a description of the error
	 * @param parsedExpression the expression that caused the error
	 */
	public SyntaxErrorException(final String message, final String parsedExpression) {
		super(message);
		this.expression = parsedExpression;
	}
	
	/**
	 * Constructs a syntax error exception with the specified message,
	 * the invalid expression, and the cause of the exception.
	 * @param message a description of the error
	 * @param parsedExpression the expression that caused the error
	 * @param cause the cause of the exception
	 */
	public SyntaxErrorException(final String message, final String parsedExpression,
			final Throwable cause) {
		super(message, cause);
		this.expression = parsedExpression;
	}
	
	/**
	 * Returns the expression that caused the syntax error.
	 * @return the parsed expression
	 */
	public String getExpression() {
		return this.expression;
	}
}
