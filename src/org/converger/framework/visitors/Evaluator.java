package org.converger.framework.visitors;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.converger.framework.Expression;
import org.converger.framework.core.BinaryOperation;
import org.converger.framework.core.BinaryOperator;
import org.converger.framework.core.Constant;
import org.converger.framework.core.Equation;
import org.converger.framework.core.Function;
import org.converger.framework.core.FunctionOperation;
import org.converger.framework.core.NAryOperation;
import org.converger.framework.core.NAryOperator;
import org.converger.framework.core.Variable;

/**
 * This visitor evaluates a function at a point
 * using a supplied map of variables-values.
 * The expression is evaluated numerically, not algebraically,
 * and the result is a real value.
 * Throws {@link java.util.NoSuchElementException} if a variable with no mapped value is found.
 * @author Dario Pavllo
 */
public class Evaluator implements
	Expression.Visitor<Double>,
	BinaryOperator.Visitor<Double>,
	NAryOperator.Visitor<Double>,
	Function.Visitor<Double> {
	
	private final Map<Variable, Double> values;
	
	/**
	 * @param valueMap a variable-value map
	 */
	public Evaluator(final Map<Variable, Double> valueMap) {
		//No defensive copy (for performance reasons)
		this.values = valueMap;
	}
	
	@Override
	public Double visit(final Variable v) {
		if (!this.values.containsKey(v)) {
			throw new NoSuchElementException("No value set for variable " + v.getName());
		}
		return this.values.get(v);
	}

	@Override
	public Double visit(final Constant v) {
		return (double) v.getValue();
	}

	@Override
	public Double visit(final BinaryOperation v) {
		return v.getOperator().accept(this,
				this.visit(v.getFirstOperand()),
				this.visit(v.getSecondOperand()));
	}
	
	@Override
	public Double visit(final NAryOperation v) {
		final List<Double> operands = v.getOperands()
			.stream()
			.map(x -> this.visit(x))
			.collect(Collectors.toList());
		
		return v.getOperator().accept(this, operands);
	}
	
	@Override
	public Double visit(final FunctionOperation v) {
		return v.getFunction().accept(this,
				this.visit(v.getArgument()));
	}
	
	@Override
	public Double visit(final Equation v) {
		throw new UnsupportedOperationException("Cannot evaluate an equation");
	}

	/*------------------
	 * Binary operators
	 *-----------------*/
	
	@Override
	public Double visitDivision(final Double o1, final Double o2) {
		return o1 / o2;
	}

	@Override
	public Double visitPower(final Double o1, final Double o2) {
		return Math.pow(o1, o2);
	}
		
	/*-----------------
	 * N-ary operators
	 *-----------------*/
		
	@Override
	public Double visitAddition(final List<Double> operands) {
		return operands.stream().reduce(0.0, Double::sum);
	}

	@Override
	public Double visitProduct(final List<Double> operands) {
		return operands.stream().reduce(1.0, (x, y) -> x * y);
	}
	
	/*-----------
	 * Functions
	 *-----------*/
	
	@Override
	public Double visitSin(final Double arg) {
		return Math.sin(arg);
	}
	
	@Override
	public Double visitArcsin(final Double arg) {
		return Math.asin(arg);
	}
	
	@Override
	public Double visitCos(final Double arg) {
		return Math.cos(arg);
	}
	
	@Override
	public Double visitArccos(final Double arg) {
		return Math.acos(arg);
	}
	
	@Override
	public Double visitTan(final Double arg) {
		return Math.tan(arg);
	}
	
	@Override
	public Double visitArctan(final Double arg) {
		return Math.atan(arg);
	}
	
	@Override
	public Double visitLn(final Double arg) {
		return Math.log(arg);
	}
	
	@Override
	public Double visitAbs(final Double arg) {
		return Math.abs(arg);
	}
	
	@Override
	public Double visitSqrt(final Double arg) {
		return Math.sqrt(arg);
	}
}
