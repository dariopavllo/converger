package org.converger.framework.core;

import org.converger.framework.Expression;

/**
 * This class implements a named variable.
 * @author Dario Pavllo
 */
public class Variable implements Expression {

	private final String name;
	
	/**
	 * @param variableName the variable name
	 */
	public Variable(final String variableName) {
		this.name = variableName;
	}
	
	/**
	 * Returns this variable's name.
	 * @return variable name
	 */
	public String getName() {
		return this.name;
	}

	@Override
	public <X> X accept(final Expression.Visitor<X> v) {
		return v.visit(this);
	}
	
	@Override
	public String toString() {
		return this.getName();
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof Variable) {
			final Variable o = (Variable) obj;
			return this.name.equals(o.name);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return this.name.hashCode();
	}
	
}
