package org.converger.framework.core;

import java.util.List;

import org.converger.framework.Expression;
import org.converger.framework.parser.ShuntingYardParser;
import org.converger.framework.parser.Token;
import org.converger.framework.parser.Tokenizer;
import org.converger.framework.parser.TreeBuilder;

/**
 * A static factory class that provides some
 * handful methods to build and manipulate expressions.
 * @author Dario Pavllo
 */
public final class ExpressionFactory {

	private ExpressionFactory() {
	}
	
	/**
	 * Builds a raw expression from the given string.
	 * @param str the input expression
	 * @return a syntax tree representing the expression
	 */
	public static Expression build(final String str) {
		final String[] parts = str.split("=");
		switch (parts.length) {
		case 1:
			//Single expression
			final Tokenizer tokenizer = new Tokenizer(parts[0]);
			final ShuntingYardParser parser = new ShuntingYardParser();
			
			parser.parse(tokenizer);
			final List<Token> tokens = parser.getOutputList();
			final TreeBuilder tb = new TreeBuilder(tokens);
			return tb.build();
			
		case 2:
			//Equation
			return new Equation(build(parts[0]), build(parts[1])); //Recursive
			
		default:
			throw new IllegalArgumentException("Invalid equation");
		}
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
	 * Subtracts an expression from another (e1 - e2).
	 * @param e1 the first expression
	 * @param e2 the second expression
	 * @return e1 - e2
	 */
	public static Expression subtract(final Expression e1, final Expression e2) {
		return new NAryOperation(
			NAryOperator.ADDITION,
			e1,
			ExpressionFactory.negate(e2)
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
