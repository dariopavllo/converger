package org.converger.framework;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * This interface defines the operations which can be done on this CAS.
 * @author Dario Pavllo
 */
public interface CasFramework {
	
	/**
	 * Parses an expression and returns its internal representation.
	 * @param input	the expression string in infix notation
	 * @return the parsed expression tree
	 * @throws SyntaxErrorException if the expression contains syntax errors
	 */
	Expression parse(String input) throws SyntaxErrorException;
	
	/**
	 * Simplifies (algebraically) the supplied expression.
	 * @param input the expression to simplify
	 * @return the simplified expression
	 */
	Expression simplify(Expression input);
	
	/**
	 * Substitutes the supplied variables with the corresponding subexpressions.
	 * @param input the target expression
	 * @param subexpressions a Variable-Subexpression map
	 * @return the processed expression
	 */
	Expression substitute(Expression input, Map<String, Expression> subexpressions);
	
	/**
	 * Returns a list of the variables contained in the expression.
	 * @param input the target expression
	 * @return a set of variables
	 */
	Set<String> enumerateVariables(Expression input);
	
	/**
	 * Differentiates the supplied function with respect to the supplied variable.
	 * @param input the function to differentiate
	 * @param variable the independent variable
	 * @return the input function's derivative, with respect to "variable"
	 */
	Expression differentiate(Expression input, String variable);
	
	/**
	 * Evaluates (numerically) a function, using the supplied map of values.
	 * @param input the function to evaluate
	 * @param values a map containing correspondences between variables and values
	 * @return a real number representing the final result
	 * @throws NoSuchElementException if a mapping is not found for a certain variable
	 */
	double evaluate(Expression input, Map<String, Double> values);
	
	
	/**
	 * Solves numerically the given equation. It must contain
	 * only one variable, and it is deduced automatically.
	 * @param input the equation to solve
	 * @return a set containing the solutions of the equation
	 * @throws IllegalArgumentException either if the expression is not an equation
	 * or does not contain only one variable
	 */
	Set<Double> solveNumerically(Expression input);
	
	/**
	 * Integrates numerically the given function. It must contain
	 * only one variable, and it is deduced automatically.
	 * @param input the function to integrate
	 * @param lowerBound the lower bound of the integral
	 * @param upperBound the upper bound of the integral
	 * @return the approximate definite integral of the function
	 * @throws IllegalArgumentException if the function does not contain only one variable
	 */
	double integrateNumerically(Expression input, double lowerBound, double upperBound);
	
	/**
	 * Computes the Taylor series of the given function.
	 * @param input the input function
	 * @param variable the independent variable
	 * @param point the point of expansion
	 * @param order the order of expansion
	 * @return the Taylor series of the function
	 * @throws IllegalArgumentException if the input expression is an equation
	 */
	Expression taylorSeries(Expression input, String variable, Expression point, int order);
	
	/**
	 * Converts an expression to simple plain text.
	 * @param input the expression to convert
	 * @return a string representation of the expression
	 */
	String toPlainText(Expression input);
	
	/**
	 * Converts an expression to a LaTeX-compatible text.
	 * @param input the expression to convert
	 * @return a string in LaTeX language
	 */
	String toLatexText(Expression input);
	
	
	/**
	 * Aborts the current running operation (if there is one) on this framework instance.
	 * This method, which is thread-safe, has to be called from another thread,
	 * and causes the main thread to throw an {@link AbortedException}.
	 */
	void abort();
	
}
