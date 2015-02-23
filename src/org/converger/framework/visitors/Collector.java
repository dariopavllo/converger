package org.converger.framework.visitors;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.converger.framework.Expression;
import org.converger.framework.core.BinaryOperation;
import org.converger.framework.core.Constant;
import org.converger.framework.core.ExpressionFactory;
import org.converger.framework.core.NAryOperation;
import org.converger.framework.core.NAryOperator;
import org.converger.framework.core.BinaryOperator;

/**
 * This visitor applies some algebraic properties to an expression by
 * collecting like terms where possible, and returns a simplified version of it.
 * @author Dario Pavllo
 */
public class Collector extends AbstractExpressionVisitor
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

	/*-----------------
	 * N-ary operators
	 *-----------------*/
	
	/*
	 * Builds a map of exponentiation operations, using the base as the key and the
	 * exponent as the value. Nodes sharing the same base are added in the same record,
	 * appending the exponent to the respective list.
	 */
	private Map<Expression, List<Expression>> buildBasesMap(final List<Expression> operands) {
		//LinkedHashMap is used here to preserve insertion order
		final Map<Expression, List<Expression>> bases = new LinkedHashMap<>();
		for (final Expression child : operands) {
			//By default, the node has implicitly an exponent equal to 1
			Expression base = child;
			Expression exponent = Constant.ONE;
			if (child instanceof BinaryOperation) {
				final BinaryOperation op = (BinaryOperation) child;
				if (op.getOperator() == BinaryOperator.POWER) {
					//If it's a power operation, redefine the base and the exponent
					base = op.getFirstOperand();
					exponent = op.getSecondOperand();
				}
			}
			bases.putIfAbsent(base, new ArrayList<>());
			bases.get(base).add(exponent);
		}
		return bases;
	}
	
	/**
	 * This method collects power operations that share the same base (under a product operation).
	 * For example: x^2 * x^3 becomes x^5
	 * This also applies to more complex bases, like: sin(x)^2 + sin(x)^x = sin(x)^(2 + x),
	 */
	@Override
	public Expression visitProduct(final List<Expression> operands) {
		final Map<Expression, List<Expression>> bases = this.buildBasesMap(operands);
		
		final List<Expression> result = new ArrayList<>();
		bases.forEach((base, exponents) -> {
			Expression e;
			if (exponents.size() == 1 && exponents.get(0).equals(Constant.ONE)) {
				//If the term has an exponent equal to one, the latter is removed
				e = base;
			} else {
				//The result's exponent is the sum of all exponents
				e = new BinaryOperation(
					BinaryOperator.POWER,
					base,
					ExpressionFactory.implode(NAryOperator.ADDITION, exponents)
				);
			}
			result.add(e);
		});
		
		return ExpressionFactory.implode(NAryOperator.PRODUCT, result);
	}
	
	/**
	 * Collects like terms under an addition node. For example, x + x becomes 2x,
	 * 2x + 3x becomes 5x. It also handles non-numeric coefficients
	 * ( e.g. x*sin(x) + y*sin(x) = (x + y)*sin(x) ).
	 * Note that, for complexity reasons, the current implementation does not handle
	 * multiplication nodes with more than two terms, and only the first is used as a coefficient.
	 */
	@Override
	public Expression visitAddition(final List<Expression> operands) {
		//LinkedHashMap is used here to preserve insertion order
		final Map<Expression, List<Expression>> terms = new LinkedHashMap<>();
		for (final Expression child : operands) {
			//By default, the node has implicitly a coefficient equal to one
			Expression term = child;
			Expression coefficient = Constant.ONE;
			if (child instanceof NAryOperation) {
				final NAryOperation op = (NAryOperation) child;
				//For complexity reasons, only product nodes with two terms are reduced
				if (op.getOperator() == NAryOperator.PRODUCT && op.getOperands().size() == 2) {
					coefficient = op.getOperands().get(0); //First factor
					term = op.getOperands().get(1); //Second factor
				}
			}
			terms.putIfAbsent(term, new ArrayList<>());
			terms.get(term).add(coefficient);
			
		}
		
		final List<Expression> result = new ArrayList<>();
		terms.forEach((term, coefficients) -> {
			Expression e;
			if (coefficients.size() == 1 && coefficients.get(0).equals(Constant.ONE)) {
				//If the term has a coefficient equal to one, the latter is removed
				e = term;
			} else {
				//The result's coefficient is the sum of all the coefficients
				e = new NAryOperation(
					NAryOperator.PRODUCT,
					ExpressionFactory.implode(NAryOperator.ADDITION, coefficients),
					term
				);
			}
			result.add(e);
		});
		
		return ExpressionFactory.implode(NAryOperator.ADDITION, result);
	}
}
