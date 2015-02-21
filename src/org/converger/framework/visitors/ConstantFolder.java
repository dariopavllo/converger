package org.converger.framework.visitors;

import java.util.ArrayList;
import java.util.List;

import org.converger.framework.Expression;
import org.converger.framework.core.BinaryOperation;
import org.converger.framework.core.Constant;
import org.converger.framework.core.ExpressionFactory;
import org.converger.framework.core.MathUtils;
import org.converger.framework.core.NAryOperation;
import org.converger.framework.core.NAryOperator;
import org.converger.framework.core.BinaryOperator;

/**
 * This visitor applies a constant folding operation to the input expression.
 * All the operations involving constants are evaluated until they become fully simplified.
 * @author Dario Pavllo
 */
public class ConstantFolder extends AbstractExpressionVisitor
	implements NAryOperator.Visitor<Expression>, BinaryOperator.Visitor<Expression> {
	
	@Override
	public Expression visit(final BinaryOperation v) {
		final BinaryOperation sv = (BinaryOperation) super.visit(v); //Simplified
		try {
			return sv.getOperator().accept(this, sv.getFirstOperand(), sv.getSecondOperand());
		} catch (final UnsupportedOperationException e) {
			return sv;
		}
	}
	
	@Override
	public Expression visit(final NAryOperation v) {
		final NAryOperation sv = (NAryOperation) super.visit(v); //Simplified
		try {
			return sv.getOperator().accept(this, sv.getOperands());
		} catch (final UnsupportedOperationException e) {
			return sv;
		}
	}
	
	/*------------------
	 * Binary operators
	 *-----------------*/
	
	/**
	 * Simplifies a rational number (a division node composed of two constants) using
	 * its greatest common divisor. For example, 10/100 becomes 1/10 and 6/4 becomes 3/2.
	 */
	@Override
	public Expression visitDivision(final Expression o1, final Expression o2) {
		Expression res1 = o1;
		Expression res2 = o2;
		
		if (o1 instanceof Constant && o2 instanceof Constant && !o2.equals(Constant.ZERO)) {
			final long numerator = ((Constant) o1).getValue();
			final long denominator = ((Constant) o2).getValue();
			final long gcd = MathUtils.gcd(numerator, denominator);
			res1 = Constant.valueOf(numerator / gcd);
			res2 = Constant.valueOf(denominator / gcd);
		}
		return new BinaryOperation(BinaryOperator.DIVISION, res1, res2);
	}

	/*-----------------
	 * N-ary operators
	 *-----------------*/
	
	/**
	 * Calculates the product of the constants contained in the current multiplication node.
	 * For example, 3*5*sin(x)*2 becomes 30*sin(x).
	 */
	@Override
	public Expression visitProduct(final List<Expression> operands) {
		//Calculates the product of all the constant terms in the node
		long constantTerm = 1;
		final List<Expression> factors = new ArrayList<>();
		for (final Expression o : operands) {
			if (o instanceof Constant) {
				final Constant c = (Constant) o;
				constantTerm *= c.getValue();
			} else {
				factors.add(o);
			}
		}
		
		if (constantTerm != 1 || factors.isEmpty()) { //NOPMD
			factors.add(Constant.valueOf(constantTerm));
		}
		
		return ExpressionFactory.implode(NAryOperator.PRODUCT, factors);
	}
	
	/**
	 * Calculates the sum of all the rational numbers contained in the addition node.
	 * A rational number can be either a quotient of two integer numbers (x/y) or
	 * a single integer number (a constant).
	 * e.g. 3/4 + 2/5 + 1 = 43/20
	 */
	@Override
	public Expression visitAddition(final List<Expression> operands) {
		//Calculates the sum of all the constant terms in the node
		final List<Expression> fractionalAddends = new ArrayList<>();
		final List<Expression> otherAddends = new ArrayList<>();
		for (final Expression o : operands) {
			if (MathUtils.isFractional(o)) {
				//Fractional number: x/y
				fractionalAddends.add(o);
			} else if (o instanceof Constant) {
				//Constant: it's converted to a fraction (x/1)
				final Constant c = (Constant) o;
				fractionalAddends.add(
					new BinaryOperation(BinaryOperator.DIVISION, c, Constant.ONE));
			} else {
				//Other nodes (discard)
				otherAddends.add(o);
			}
		}
		
		//Calculates the least common multiple of the denominator factors
		long lcm;
		try {
			lcm = fractionalAddends.stream()
			.map(e -> ((BinaryOperation) e).getSecondOperand())
			.mapToLong(e -> {
				final long val = ((Constant) e).getValue();
				if (val == 0) {
					throw new IllegalArgumentException();
				}
				return val;
			})
			.reduce(1, (x, y) -> MathUtils.lcm(x, y));
		} catch (final IllegalArgumentException e) {
			//The expression contains a division by 0: it is left unsimplified
			return new NAryOperation(NAryOperator.ADDITION, operands);
		}
		
		//Simplify everything
		final long sum = fractionalAddends.stream()
			.map(e -> (BinaryOperation) e)
			.mapToLong(e -> ((Constant) e.getFirstOperand()).getValue()
				* lcm / ((Constant) e.getSecondOperand()).getValue())
			.sum();
		
		//The term is added only if it's not equal to zero
		if (sum != 0) {
			otherAddends.add(ExpressionFactory.makeRational(sum, lcm));
		}
	
		return ExpressionFactory.implode(NAryOperator.ADDITION, otherAddends);
	}
	
	
}
