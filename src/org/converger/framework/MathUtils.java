package org.converger.framework;

import java.util.List;

import org.converger.framework.core.BinaryOperation;
import org.converger.framework.core.BinaryOperator;
import org.converger.framework.core.Constant;
import org.converger.framework.core.NAryOperation;
import org.converger.framework.core.NAryOperator;

/**
 * Utility class which contains some useful methods
 * for recurring actions.
 * @author Dario Pavllo
 */
public final class MathUtils {

	private MathUtils() {
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
	 * Build (safely) an n-ary operation using the supplied operator
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
	
	/**
	 * Calculates the integer power of a natural number (a^b) using the
	 * exponentiation-by-squaring algorithm, in O(log(b)).
	 * @param base the number to exponentiate
	 * @param exponent the exponent
	 * @return the operation result
	 */
	public static long integerPower(final long base, final long exponent) {
		//Base cases
		if (base == 0) {
			return 1;
		}
		if (exponent == 1) { //NOPMD
			return base;
		}
		
		//Exponentiation by squaring
		if (exponent % 2 == 0) { //If the exponent is even
			return integerPower(base * base, exponent / 2);
		} else {
			return base * integerPower(base * base, (exponent - 1) / 2);
		}
	}
	
	
	/**
	 * Tells whether the given expression is a rational number,
	 * i.e., can be expressed as a/b (a, b are constants).
	 * @param exp the expression to test
	 * @return true if the expression is a natural number, false otherwise
	 */
	public static boolean isRational(final Expression exp) {
		if (exp instanceof BinaryOperation) {
			final BinaryOperation op = (BinaryOperation) exp;
			if (op.getOperator() == BinaryOperator.DIVISION) {
				return op.getFirstOperand() instanceof Constant
					&& op.getSecondOperand() instanceof Constant;
			}
		}
		return false;
	}
	
}
