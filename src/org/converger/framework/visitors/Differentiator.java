package org.converger.framework.visitors;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.converger.framework.Expression;
import org.converger.framework.MathUtils;
import org.converger.framework.core.BinaryOperation;
import org.converger.framework.core.BinaryOperator;
import org.converger.framework.core.Constant;
import org.converger.framework.core.Function;
import org.converger.framework.core.FunctionOperation;
import org.converger.framework.core.NAryOperation;
import org.converger.framework.core.NAryOperator;
import org.converger.framework.core.Variable;

/**
 * This visitor differentiates the visited function with
 * respect to the supplied variable, and returns its derivative.
 * The output expression must be simplified afterwards.
 * @author Dario Pavllo
 */
public class Differentiator implements
		Expression.Visitor<Expression>,
		BinaryOperator.Visitor<Expression>,
		NAryOperator.Visitor<Expression>,
		Function.Visitor<Expression> {

	private final Variable target;
	
	/**
	 * @param targetVariable the independent variable
	 */
	public Differentiator(final Variable targetVariable) {
		this.target = targetVariable;
	}
	
	@Override
	public Expression visit(final Variable v) {
		//Derivative of a variable: 1 if it's the target variable, 0 otherwise
		return v.equals(this.target) ? Constant.ONE : Constant.ZERO;
	}

	@Override
	public Expression visit(final Constant v) {
		//The derivative of an additive constant is always 0
		return Constant.ZERO;
	}

	@Override
	public Expression visit(final BinaryOperation v) {
		return v.getOperator().accept(this,
				v.getFirstOperand(), v.getSecondOperand());
	}
	
	@Override
	public Expression visit(final NAryOperation v) {
		return v.getOperator().accept(this, v.getOperands());
	}
	
	@Override
	public Expression visit(final FunctionOperation v) {
		//Derivative of function composition: [f(g(x))]' = f'(g(x)) * g'(x)
		return new NAryOperation(
			NAryOperator.PRODUCT,
			v.getFunction().accept(this, v.getArgument()), // f'(g(x))
			this.visit(v.getArgument()) // g'(x)
		);
	}


	/*------------------
	 * Binary operators
	 *-----------------*/
	
	@Override
	public Expression visitDivision(final Expression f, final Expression g) {
		//Derivative of division: [f(x)/g(x)]' = (f'(x)*g(x) - f(x)*g'(x))/g^2(x)
		return new BinaryOperation(
			BinaryOperator.DIVISION,
			new NAryOperation(
				NAryOperator.ADDITION,
				new NAryOperation(
					NAryOperator.PRODUCT,
					this.visit(f),
					g
				),
				new NAryOperation(
					NAryOperator.PRODUCT,
					Constant.valueOf(-1),
					f,
					this.visit(g)
				)
			),
			new BinaryOperation(
				BinaryOperator.POWER,
				g,
				Constant.valueOf(2)
			)
		);
	}

	@Override
	public Expression visitPower(final Expression f, final Expression g) {
		//If g is a constant: [f(x)^g]' = g*f(x)^(g-1)
		if (g instanceof Constant) {
			final Constant coefficient = (Constant) g;
			return new NAryOperation(
					NAryOperator.PRODUCT,
					Constant.valueOf(coefficient.getValue()),
					new BinaryOperation(
						BinaryOperator.POWER,
						f,
						Constant.valueOf(coefficient.getValue() - 1)
					),
					this.visit(f)
				);
		}
		//General case: [f(x)^g(x)]' = f(x)^g(x) * [ g(x) * ln(f(x)) ]'
		return new NAryOperation(
			NAryOperator.PRODUCT,
			new BinaryOperation(BinaryOperator.POWER, f, g),
			this.visit(
				new NAryOperation(
					NAryOperator.PRODUCT,
					new FunctionOperation(Function.LN, f),
					g
				)
			)
		);
	}
	
	/*-----------------
	 * N-ary operators
	 *-----------------*/
	
	@Override
	public Expression visitAddition(final List<Expression> operands) {
		//Derivative of addition: (f1 + f2 + ... + fn)' = f1' + f2' + ... + fn'
		final List<Expression> filtered = operands
			.stream()
			.map(x -> this.visit(x))
			.collect(Collectors.toList());
		
		return new NAryOperation(NAryOperator.ADDITION, filtered);
	}

	@Override
	public Expression visitProduct(final List<Expression> operands) {
		/* Derivative of product (generalized to any number of factors):
		 * (f1 * f2 * ... * fn)' = (f1' * f2 * ... * fn) + (f1 * f2' * ... * fn)
		 * + ... + (f1 * f2 * ... * fn')
		 */
		final List<Expression> addends = new ArrayList<>(operands.size());
		for (int i = 0; i < operands.size(); i++) {
			final List<Expression> factors = new ArrayList<>(operands.size());
			for (int j = 0; j < operands.size(); j++) {
				if (i == j) {
					factors.add(this.visit(operands.get(j)));
				} else {
					factors.add(operands.get(j));
				}
			}
			addends.add(new NAryOperation(NAryOperator.PRODUCT, factors));
		}
		return new NAryOperation(NAryOperator.ADDITION, addends);
	}
	
	/*-----------
	 * Functions
	 *-----------*/
	
	@Override
	public Expression visitSin(final Expression arg) {
		//Derivative of sin(x) = cos(x)
		return new FunctionOperation(Function.COS, arg);
	}
	
	@Override
	public Expression visitCos(final Expression arg) {
		//Derivative of cos(x) = -sin(x)
		return MathUtils.negate(new FunctionOperation(Function.SIN, arg));
	}
	
	@Override
	public Expression visitLn(final Expression arg) {
		//Derivative of ln(x) = 1/x
		return new BinaryOperation(
			BinaryOperator.DIVISION,
			Constant.ONE,
			arg
		);
	}
	
	@Override
	public Expression visitAbs(final Expression arg) {
		//Derivative of |x| = |x|/x
		return new BinaryOperation(
			BinaryOperator.DIVISION,
			new FunctionOperation(Function.ABS, arg),
			arg
		);
	}
	
	@Override
	public Expression visitSqrt(final Expression arg) {
		//Derivative of sqrt(x) = 1/(2sqrt(x))
		return new BinaryOperation(
			BinaryOperator.DIVISION,
			Constant.ONE,
			new NAryOperation(
				NAryOperator.PRODUCT,
				Constant.valueOf(2),
				new FunctionOperation(Function.SQRT, arg)
			)
		);
	}

}
