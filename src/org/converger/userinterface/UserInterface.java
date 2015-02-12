package org.converger.userinterface;

/**
 * This interface represent the user interface of the CAS software. 
 * The controller can call only the functions of this interface. 
 * @author Gabriele Graffieti
 */
public interface UserInterface {
	
	/**
	 * Print in the user interface the given expression.
	 * @param exp the mathematical expression provided by the cas framework.
	 */
	void printExpression(String exp);
	
	/**
	 * Manage an error throws by the framework, for example an invalid expression or an invalid symbol.
	 */
	void manageError(); // da vedere con eh
	
}
