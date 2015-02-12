package org.converger.framework.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.converger.framework.Expression;

/**
 * Represents an n-ary operation, that is an operation among
 * a variable number of arguments (two at least).
 * @author Dario Pavllo
 */
public class NAryOperation implements Expression {
	
	private final List<Expression> operands;
	private final NAryOperator operator;
	
	/**
	 * @param op the operator definition
	 * @param arguments the list of operands
	 */
	public NAryOperation(final NAryOperator op, final List<Expression> arguments) {
		//The operation should have at least two arguments
		if (arguments.size() < 2) { //NOPMD
			throw new IllegalArgumentException();
		}
		this.operands = new ArrayList<>(arguments);
		this.operator = op;
	}
	
	/**
	 * Varargs constructor.
	 * @param op the operation definition
	 * @param arguments an array of operands
	 */
	public NAryOperation(final NAryOperator op, final Expression... arguments) {
		this(op, Arrays.asList(arguments));
	}
	
	/**
	 * Returns the operator associated with this operation.
	 * @return an n-ary operator
	 */
	public NAryOperator getOperator() {
		return this.operator;
	}
	
	/**
	 * Returns the list of operands of this operation.
	 * @return a list of expressions
	 */
	public List<Expression> getOperands() {
		return Collections.unmodifiableList(this.operands);
	}
	
	@Override
	public <X> X accept(final Expression.Visitor<X> v) {
		return v.visit(this);
	}
	
	@Override
	public String toString() {
		return this.operands.stream()
			.map(x -> x.toString())
			.collect(Collectors.joining(this.operator.getSymbol(), "(", ")"));
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof NAryOperation) {
			final NAryOperation o = (NAryOperation) obj;
			return this.operator == o.operator
				&& this.operands.equals(o.operands);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return this.operator.hashCode()
			^ this.operands.hashCode();
	}

}
