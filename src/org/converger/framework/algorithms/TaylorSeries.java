package org.converger.framework.algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.converger.framework.CasFramework;
import org.converger.framework.Expression;
import org.converger.framework.core.BinaryOperation;
import org.converger.framework.core.BinaryOperator;
import org.converger.framework.core.Constant;
import org.converger.framework.core.Equation;
import org.converger.framework.core.ExpressionFactory;
import org.converger.framework.core.NAryOperation;
import org.converger.framework.core.NAryOperator;
import org.converger.framework.core.Variable;

/**
 * This class expands a function to a polynomial
 * by using Taylor's theorem.
 * @author Dario Pavllo
 * @author Gabriele Graffieti
 */
public class TaylorSeries {
	
	private final Expression function;
	private final CasFramework cas;
	
	/**
	 * Instantiates this class.
	 * @param framework the framework instance
	 * @param f the function to convert to its Taylor series
	 */
	public TaylorSeries(final CasFramework framework, final Expression f) {
		this.cas = framework;
		if (f instanceof Equation) {
			throw new IllegalArgumentException("Cannot expand an equation");
		}
		this.function = framework.simplify(f);
	}
	
	/**
	 * Expands the function contained in this object to its Taylor series.
	 * @param variable the independent variable
	 * @param point the point of expansion
	 * @param order the order of expansion
	 * @return the Taylor series of the function
	 */
	public Expression expand(final String variable, final Expression point, final int order) {
		final List<Expression> terms = new ArrayList<>(order + 1);
		long factorial = 1;
		final Map<String, Expression> values = new HashMap<>();
		values.put(variable, point);
		Expression f = this.function;
		terms.add(this.cas.substitute(f, values)); //Constant term
		
		for (int i = 1; i <= order; i++) {
			factorial *= i;
			f = this.cas.differentiate(f, variable);
			final Expression term = new BinaryOperation(
				BinaryOperator.DIVISION,
				new NAryOperation(
					NAryOperator.PRODUCT,
					this.cas.substitute(f, values),
					new BinaryOperation(
						BinaryOperator.POWER,
						ExpressionFactory.subtract(new Variable(variable), point),
						Constant.valueOf(i)
					)
				),
				Constant.valueOf(factorial)
			);
			terms.add(term);
		}
		
		return this.cas.simplify(ExpressionFactory.implode(NAryOperator.ADDITION, terms));
	}
}
