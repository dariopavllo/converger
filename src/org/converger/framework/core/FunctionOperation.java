package org.converger.framework.core;

import org.converger.framework.Expression;

/**
 * Represents a function node.
 * @author Dario Pavllo
 */
public class FunctionOperation implements Expression {

	private final Function function;
	private final Expression argument;
	
	/**
	 * @param func the function definition
	 * @param arg the function argument
	 */
	public FunctionOperation(final Function func, final Expression arg) {
		this.function = func;
		this.argument = arg;
	}
	
	/**
	 * Returns the function definition associated with this operation.
	 * @return a function
	 */
	public Function getFunction() {
		return this.function;
	}
	
	/**
	 * Returns the argument of this function.
	 * @return an expression
	 */
	public Expression getArgument() {
		return this.argument;
	}
	
	@Override
	public <X> X accept(final Expression.Visitor<X> v) {
		return v.visit(this);
	}
	
	@Override
	public String toString() {
		return function.getName() + "(" + argument + ")";
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof FunctionOperation) {
			final FunctionOperation o = (FunctionOperation) obj;
			return this.function == o.function
				&& this.argument.equals(o.argument);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return this.function.hashCode()
			^ this.argument.hashCode();
	}
	
	
}
