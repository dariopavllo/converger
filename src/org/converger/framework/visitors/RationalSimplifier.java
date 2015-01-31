package org.converger.framework.visitors;

import java.util.ArrayList;
import java.util.List;

import org.converger.framework.Expression;
import org.converger.framework.MathUtils;
import org.converger.framework.core.BinaryOperation;
import org.converger.framework.core.NAryOperation;
import org.converger.framework.core.NAryOperator;
import org.converger.framework.core.BinaryOperator;

/**
 * This is a specific type of simplifier that reorders multiplication
 * and division nodes to satisfy some rational rules.<br />
 * (x/y)/z becomes x/(yz), x/(y/z) becomes (xz)/y,
 * x * (y/z) becomes (xy)/z.
 * @author Dario Pavllo
 */
public class RationalSimplifier extends AbstractExpressionVisitor
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
	
	@Override
	public Expression visitDivision(final Expression o1, final Expression o2) {
		// (a/b)/c = a/(b*c)
		if (o1 instanceof BinaryOperation) {
			final BinaryOperation b1 = (BinaryOperation) o1;
			if (b1.getOperator() == BinaryOperator.DIVISION) {
				// a/(b*c)
				return new BinaryOperation(
					BinaryOperator.DIVISION,
					b1.getFirstOperand(),
					new NAryOperation(
						NAryOperator.PRODUCT,
						b1.getSecondOperand(),
						o2
					)
				);
			}
		}
		// a/(b/c) = (a*c)/b
		if (o2 instanceof BinaryOperation) {
			final BinaryOperation b2 = (BinaryOperation) o2;
			if (b2.getOperator() == BinaryOperator.DIVISION) {
				// (a*c)/b
				return new BinaryOperation(
					BinaryOperator.DIVISION,
					new NAryOperation(
						NAryOperator.PRODUCT,
						o1,
						b2.getSecondOperand()
					),
					b2.getFirstOperand()
				);
			}
		}
		return new BinaryOperation(BinaryOperator.DIVISION, o1, o2);
	}

	/*-----------------
	 * N-ary operators
	 *-----------------*/
	
	@Override
	public Expression visitProduct(final List<Expression> operands) {
		// a*(x/y)*b*(z/w) = (a*x*b*z)/(y*w)
		final List<Expression> numerator = new ArrayList<>();
		final List<Expression> denominator = new ArrayList<>();
		for (final Expression operand : operands) {
			if (operand instanceof BinaryOperation) {
				final BinaryOperation bOp = (BinaryOperation) operand;
				if (bOp.getOperator() == BinaryOperator.DIVISION) {
					numerator.add(bOp.getFirstOperand());
					denominator.add(bOp.getSecondOperand());
				} else {
					numerator.add(operand);
				}
			} else {
				numerator.add(operand);
			}
		}
		if (!denominator.isEmpty()) {
			return new BinaryOperation(
				BinaryOperator.DIVISION,
				MathUtils.implode(NAryOperator.PRODUCT, numerator),
				MathUtils.implode(NAryOperator.PRODUCT, denominator)
			);
		}
		return new NAryOperation(NAryOperator.PRODUCT, operands);
	}
}
