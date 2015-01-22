package org.converger.framework;

import org.converger.framework.core.BinaryOperation;
import org.converger.framework.core.Constant;
import org.converger.framework.core.FunctionOperation;
import org.converger.framework.core.NAryOperation;
import org.converger.framework.core.Variable;

/**
 * This interface represents a mathematical expression,
 * which is a node of the syntax tree.
 * @author Dario Pavllo
 */
public interface Expression {

	/**
	 * Entry point for the visitor pattern.
	 * @param <X> input and return type
	 * @param v a class which implements the visitor pattern
	 * @return an implementation-dependent object
	 */
	<X> X accept(Visitor<X> v);
	
	/**
	 * This interface contains the possible actions that can be done on an expression.
	 * Each class must implement the visitor pattern to traverse the syntax tree.
	 */
	public interface Visitor<X> { //NOPMD

		/**
		 * Visits an expression.
		 * @param v the expression to visit
		 * @return an implementation-dependent value
		 */
		default X visit(final Expression v) {
			return v.accept(this);
		}
		
		/**
		 * Visits a variable (leaf node).
		 * @param v the variable to visit
		 * @return an implementation-dependent value
		 */
		X visit(Variable v);
		
		/** Visits a constant (leaf node).
		 * @param v the constant to visit
		 * @return an implementation-dependent value
		 */
		X visit(Constant v);
		
		/**
		 * Visits a binary operation (inner node).
		 * @param v the operation to visit
		 * @return an implementation-dependent value
		 */
		X visit(BinaryOperation v);
		
		/**
		 * Visits a function operation (inner node).
		 * @param v the function to visit
		 * @return an implementation-dependent value
		 */
		X visit(FunctionOperation v);
		
		/**
		 * Visits an n-ary operation (inner node).
		 * @param v the operation to visit
		 * @return an implementation-dependent value
		 */
		X visit(NAryOperation v);
		
	}
	
}
