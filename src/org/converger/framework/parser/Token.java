package org.converger.framework.parser;

/**
 * Represents a typed token: a string which is part of an expression.
 * @author Dario Pavllo
 */
public class Token {

	/** Represents a left parenthesis token. */
	public static final Token LEFT_PARENTHESIS = new Token("(", Type.LEFT_PARENTHESIS);
	/** Represents a right parenthesis token. */
	public static final Token RIGHT_PARENTHESIS = new Token(")", Type.RIGHT_PARENTHESIS);
	
	private final String content;
	private final Type type;
	
	/**
	 * Initializes this token.
	 * @param tokenContent the string contained in this token
	 * @param tokenType the type of this token
	 */
	public Token(final String tokenContent, final Type tokenType) {
		this.content = tokenContent;
		this.type = tokenType;
	}
	
	/**
	 * Returns the content of this token.
	 * @return the string contained in this token
	 */
	public String getContent() {
		return this.content;
	}
	
	/**
	 * Returns the type of this token.
	 * @return the type of this token
	 */
	public Type getType() {
		return this.type;
	}
	
	@Override
	public String toString() {
		return this.getContent();
	}
	
	/**
	 * This enumeration represents all the possible token types.
	 */
	public enum Type {
		/** A function token (sin, cos, ln...). */
		FUNCTION,
		/** An operator token (+, -, *, /, ...). */
		OPERATOR,
		/** A numeric/constant token (5, 3, -2, ...). */
		NUMBER,
		/** A named variable token (x, y, z, ...). */
		VARIABLE,
		/** A left parenthesis token "(". */
		LEFT_PARENTHESIS,
		/** A right parenthesis token ")". */
		RIGHT_PARENTHESIS;
	}
}
