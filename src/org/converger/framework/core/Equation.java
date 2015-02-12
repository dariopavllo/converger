package org.converger.framework.core;

import org.converger.framework.Expression;

/**
 * This class represents an equation.
 * @author Dario Pavllo
 */
public class Equation implements Expression {

	private final Expression firstMember;
	private final Expression secondMember;
	
	/**
	 * Instantiates an equation.
	 * @param left the left-hand side of the equation
	 * @param right the right-hand side of the equation
	 */
	public Equation(final Expression left, final Expression right) {
		this.firstMember = left;
		this.secondMember = right;
	}
	
	/**
	 * Returns the first member of this equation.
	 * @return the left-hand side of this equation
	 */
	public Expression getFirstMember() {
		return this.firstMember;
	}
	
	/**
	 * Returns the second member of this equation.
	 * @return the right-hand side of this equation
	 */
	public Expression getSecondMember() {
		return this.secondMember;
	}
	
	@Override
	public <X> X accept(final Expression.Visitor<X> v) {
		return v.visit(this);
	}
	
	@Override
	public String toString() {
		return this.firstMember + " = " + this.secondMember;
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof Equation) {
			final Equation o = (Equation) obj;
			return this.firstMember.equals(o.firstMember)
				&& this.secondMember.equals(o.secondMember);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return this.firstMember.hashCode()
			^ this.secondMember.hashCode();
	}
}
