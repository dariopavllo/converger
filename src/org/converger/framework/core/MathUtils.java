package org.converger.framework.core;

import org.converger.framework.Expression;

/**
 * An utility class which contains some recurring mathematical operations.
 * @author Dario Pavllo
 */
public final class MathUtils {

	private MathUtils() {
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
		if (exponent == 0) {
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
	 * Computes the greatest common divisor of two integers.
	 * @param a the first number
	 * @param b the second number
	 * @return the GCD of the two numbers
	 */
	public static long gcd(final long a, final long b) {
		return computeGcd(Math.abs(a), Math.abs(b));
	}
	
	private static long computeGcd(final long a, final long b) {
		//Euclid's algorithm
		return b == 0 ? a : computeGcd(b, a % b);
	}
	
	/**
	 * Computes the least common multiple of two integers.
	 * @param a the first number
	 * @param b the second number
	 * @return the LCM of the two numbers
	 */
	public static long lcm(final long a, final long b) {
		return Math.abs(a) / gcd(a, b) * Math.abs(b);
	}
	
	
	/**
	 * Tells whether the given expression is a fractional number,
	 * i.e., it is expressed as a/b (a, b are constants).
	 * @param exp the expression to test
	 * @return true if the expression is a fractional number, false otherwise
	 */
	public static boolean isFractional(final Expression exp) {
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
