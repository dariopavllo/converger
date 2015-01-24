package org.converger.framework;

import java.util.List;

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
	 * If the list contains only one element, it is directly returned without the operation.
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
