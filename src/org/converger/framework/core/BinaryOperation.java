package org.converger.framework.core;

import org.converger.framework.Expression;

/**
 * Represents a binary operation between two expressions.
 * @author Dario Pavllo
 */
public class BinaryOperation implements Expression {

	private final Expression firstOperand;
	private final Expression secondOperand;
	private final BinaryOperator operator;
	
	/**
	 * @param op the operator of this binary operation
	 * @param operand1 the first operand
	 * @param operand2 the second operand
	 */
	public BinaryOperation(final BinaryOperator op,
						   final Expression operand1,
						   final Expression operand2) {
		this.firstOperand = operand1;
		this.secondOperand = operand2;
		this.operator = op;
	}
	
	/**
	 * Returns the first operand of this operation.
	 * @return the first operand
	 */
	public Expression getFirstOperand() {
		return this.firstOperand;
	}
	
	/**
	 * Returns the second operand of this operation.
	 * @return the second operand
	 */
	public Expression getSecondOperand() {
		return this.secondOperand;
	}
	
	/**
	 * Returns the operator of this binary operation.
	 * @return a binary operator
	 */
	public BinaryOperator getOperator() {
		return this.operator;
	}
	
	@Override
	public <X> X accept(final Expression.Visitor<X> v) {
		return v.visit(this);
	}
	
	@Override
	public String toString() {
		return "(" + this.firstOperand + this.operator.getSymbol() + this.secondOperand + ")";
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof BinaryOperation) {
			final BinaryOperation o = (BinaryOperation) obj;
			return this.operator == o.operator
				&& this.firstOperand.equals(o.firstOperand)
				&& this.secondOperand.equals(o.secondOperand);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return this.firstOperand.hashCode()
			^ this.secondOperand.hashCode()
			^ this.operator.hashCode();
	}
	
}
