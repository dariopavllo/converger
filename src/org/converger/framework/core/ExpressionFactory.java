package org.converger.framework.core;

import java.util.List;

import org.converger.framework.Expression;

/**
 * A static factory class that provides some
 * handful methods to build and manipulate expressions.
 * @author Dario Pavllo
 */
public final class ExpressionFactory {

	private ExpressionFactory() {
	}
	
	/**
	 * Negates an expression (multiplies it by -1).
	 * @param exp the expression to negate
	 * @return the negated expression
	 */
	public static Expression negate(final Expression exp) {
		return new NAryOperation(
			NAryOperator.PRODUCT,
			Constant.valueOf(-1),
			exp
		);
	}
	
	/**
	 * Builds a rational (fractional) number.
	 * If the denominator is equal to 1, only the numerator is returned.
	 * @param x the numerator
	 * @param y the denominator
	 * @return an expression representing the rational number x/y
	 */
	public static Expression makeRational(final long x, final long y) {
		return y == 1
			? Constant.valueOf(x)
			: new BinaryOperation(BinaryOperator.DIVISION,
				Constant.valueOf(x), Constant.valueOf(y));
	}
	
	/**
	 * Builds (safely) an n-ary operation using the supplied operator
	 * and list of operands.
	 * If the list is empty, the constant 0 is returned.
	 * If the list contains only one element, it is directly returned without any operation.
	 * @param operator an n-ary operator
	 * @param operands the list of operands
	 * @return an n-ary operation using the supplied arguments
	 */
	public static Expression implode(final NAryOperator operator,
			final List<Expression> operands) {
		switch (operands.size()) {
			case 0:
				return Constant.ZERO;
			case 1:
				return operands.get(0);
			default:
				return new NAryOperation(operator, operands);
		}
	}
}
