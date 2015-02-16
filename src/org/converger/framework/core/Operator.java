package org.converger.framework.core;

/**
 * This class represents an operator.
 * @author Dario Pavllo
 */
public interface Operator {
	
	/**
	 * Returns the symbol associated with this operator.
	 * @return a string containing the symbol
	 */
	String getSymbol();
	
	
	/**
	 * Returns the precedence of this operator, useful for parsing.
	 * @return an integer value representing the precedence of this operator
	 */
	int getPrecedence();
	
	/**
	 * Returns the associativity of this operator, useful for parsing.
	 * @return the associativity of this operator
	 */
	Associativity getAssociativity();
	
	/**
	 * Represents the associativity of an operator.
	 */
	enum Associativity {
		LEFT, RIGHT;
	}
}
