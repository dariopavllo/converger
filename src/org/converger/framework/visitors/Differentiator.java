package org.converger.framework.visitors;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.converger.framework.Expression;
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
 * respect to the supplied variable, and returns its derivate.
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
		return v.equals(this.target) ? Constant.ONE : Constant.ZERO;
	}

	@Override
	public Expression visit(final Constant v) {
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
		// D f(g(x)) = f'(g(x)) * g'(x)
		return new NAryOperation(
			NAryOperator.PRODUCT,
			v.getFunction().accept(this, v.getArgument()), // f'(g(x))
			this.visit(v.getArgument()) // g'(x)
		);
	}


	/*
	 * Binary operators
	 */
	
	@Override
	public Expression visitDivision(final Expression o1, final Expression o2) {
		return new BinaryOperation(
			BinaryOperator.DIVISION,
			new NAryOperation(
				NAryOperator.ADDITION,
				new NAryOperation(
					NAryOperator.PRODUCT,
					visit(o1),
					o2
				),
				new NAryOperation(
					NAryOperator.PRODUCT,
					Constant.valueOf(-1),
					o1,
					visit(o2)
				)
			),
			new BinaryOperation(
				BinaryOperator.POWER,
				o2,
				Constant.valueOf(2)
			)
		);
	}

	@Override
	public Expression visitPower(final Expression o1, final Expression o2) {
		if (o2 instanceof Constant) {
			final Constant coefficient = (Constant) o2;
			return new NAryOperation(
					NAryOperator.PRODUCT,
					Constant.valueOf(coefficient.getValue()),
					new BinaryOperation(BinaryOperator.POWER, o1, Constant.valueOf(coefficient.getValue() - 1)),
					visit(o1)
				);
		}
		return new NAryOperation(
			NAryOperator.PRODUCT,
			new BinaryOperation(BinaryOperator.POWER, o1, o2),
			visit(
				new NAryOperation(
					NAryOperator.PRODUCT,
					new FunctionOperation(Function.LN, o1),
					o2
				)
			)
		);
	}
	
	/*
	 * N-ary operators
	 */
	
	@Override
	public Expression visitAddition(final List<Expression> operands) {
		//(f1 + f2 + ... + fn)' = f1' + f2' + ... + fn'
		final List<Expression> filtered = operands
			.stream()
			.map(x -> visit(x))
			.collect(Collectors.toList());
		
		return new NAryOperation(NAryOperator.ADDITION, filtered);
	}

	@Override
	public Expression visitProduct(final List<Expression> operands) {
		final List<Expression> addends = new ArrayList<>(operands.size());
		for (int i = 0; i < operands.size(); i++) {
			final List<Expression> terms = new ArrayList<>(operands.size());
			for (int j = 0; j < operands.size(); j++) {
				if (i == j) {
					terms.add(visit(operands.get(j)));
				} else {
					terms.add(operands.get(j));
				}
			}
			addends.add(new NAryOperation(NAryOperator.PRODUCT, terms));
		}
		return new NAryOperation(NAryOperator.ADDITION, addends);
	}
	
	/*
	 * Functions
	 */
	
	@Override
	public Expression visitSin(final Expression arg) {
		// D sin(x) = cos(x)
		return new FunctionOperation(Function.COS, arg);
	}
	
	@Override
	public Expression visitCos(final Expression arg) {
		// D cos(x) = -sin(x)
		return new NAryOperation(
			NAryOperator.PRODUCT,
			Constant.valueOf(-1),
			new FunctionOperation(Function.SIN, arg)
		);
	}
	
	@Override
	public Expression visitLn(final Expression arg) {
		// D ln(x) = 1/x
		return new BinaryOperation(
			BinaryOperator.DIVISION,
			Constant.ONE,
			arg
		);
	}
	
	@Override
	public Expression visitAbs(final Expression arg) {
		// D |x| = |x|/x
		return new BinaryOperation(
			BinaryOperator.DIVISION,
			new FunctionOperation(Function.ABS, arg),
			arg
		);
	}

}
