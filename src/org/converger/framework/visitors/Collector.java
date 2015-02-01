package org.converger.framework.visitors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.converger.framework.Expression;
import org.converger.framework.MathUtils;
import org.converger.framework.core.BinaryOperation;
import org.converger.framework.core.Constant;
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
	
	/*------------------
	 * Binary operators
	 *-----------------*/
	
	private Map<Expression, List<Expression>> buildBasesMap(final List<Expression> operands) {
		final Map<Expression, List<Expression>> bases = new HashMap<>();
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
	
	/*private List<Expression> getFactorList(final Expression o) {
		if (o instanceof NAryOperation) {
			final NAryOperation op = (NAryOperation) o;
			//If the expression is a multiplication node, return its factors
			if (op.getOperator() == NAryOperator.PRODUCT) {
				return op.getOperands();
			}
		}
		//There's only a single factor, so a one-element list is returned
		final List<Expression> factorList = new ArrayList<>();
		factorList.add(o);
		return factorList;
	}
	
	@Override
	public Expression visitDivision(final Expression o1, final Expression o2) {
		final Map<Expression, List<Expression>> numeratorMap =
				this.buildBasesMap(this.getFactorList(o1));
		
		final Map<Expression, List<Expression>> denominatorMap =
				this.buildBasesMap(this.getFactorList(o2));
		
		//Merges the denominator terms with the numerator terms
		denominatorMap.forEach((base, exponents) -> {
			numeratorMap.putIfAbsent(base, new ArrayList<>());
			//The exponent is negated ( e.g. 1/x^2 = x^(-2) )
			exponents.forEach(e -> numeratorMap.get(base).add(MathUtils.negate(e)));
		});
		
		final List<Expression> result = new ArrayList<>();
		numeratorMap.forEach((base, exponents) -> {
			Expression e;
			if (exponents.size() == 1 && exponents.get(0).equals(Constant.ONE)) {
				//If the term has an exponent equal to one, the latter is removed
				e = base;
			} else {
				//The result's exponent is the sum of all exponents
				e = new BinaryOperation(
					BinaryOperator.POWER,
					base,
					MathUtils.implode(NAryOperator.ADDITION, exponents)
				);
			}
			result.add(e);
		});
		
		return MathUtils.implode(NAryOperator.PRODUCT, result);
	}*/

	/*-----------------
	 * N-ary operators
	 *-----------------*/
	
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
					MathUtils.implode(NAryOperator.ADDITION, exponents)
				);
			}
			result.add(e);
		});
		
		return MathUtils.implode(NAryOperator.PRODUCT, result);
	}
	
	/**
	 * Collects common terms under an addition node. For example, x + x becomes 2x,
	 * 2x + 3x becomes 5x. It also handles non-numeric coefficients
	 * ( e.g. x*sin(x) + y*sin(x) = (x + y)*sin(x) ).
	 * Note that, for complexity reasons, the current implementation does not handle
	 * multiplication nodes with more than two terms, and only the former is used as a coefficient.
	 */
	@Override
	public Expression visitAddition(final List<Expression> operands) {
		final Map<Expression, List<Expression>> terms = new HashMap<>();
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
				//The result's coefficient is the sum of all coefficients
				e = new NAryOperation(
					NAryOperator.PRODUCT,
					MathUtils.implode(NAryOperator.ADDITION, coefficients),
					term
				);
			}
			result.add(e);
		});
		
		return MathUtils.implode(NAryOperator.ADDITION, result);
	}
}
