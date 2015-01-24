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
import org.converger.framework.core.SpecialConstant;
import org.converger.framework.core.Variable;

/**
 * This visitor simplifies algebraically the supplied expression.
 * It uses a series of algebraic rules and properties to transform
 * an expression to another equivalent expression with less complexity.
 * @author Dario Pavllo
 */
public class Simplifier implements
	Expression.Visitor<Expression>,
	BinaryOperator.Visitor<Expression>,
	NAryOperator.Visitor<Expression>,
	Function.Visitor<Expression> {

	@Override
	public Expression visit(final Variable v) {
		return v;
	}

	@Override
	public Expression visit(final Constant v) {
		return v;
	}

	@Override
	public Expression visit(final BinaryOperation v) {
		return v.getOperator().accept(this,
				this.visit(v.getFirstOperand()),
				this.visit(v.getSecondOperand()));
	}
	
	@Override
	public Expression visit(final NAryOperation v) {
		final List<Expression> simplified = v.getOperands()
			.stream()
			.map(x -> visit(x))
			.collect(Collectors.toList());
		return v.getOperator().accept(this, simplified);
	}
	
	@Override
	public Expression visit(final FunctionOperation v) {
		return v.getFunction().accept(this, this.visit(v.getArgument()));
	}
	
	/*-----------
	 * Operators
	 *-----------*/
	
	@Override
	public Expression visitDivision(final Expression o1, final Expression o2) {
		// x/1 = x
		if (o2.equals(Constant.ONE)) {
			return o1;
		}
		// 0/x = 0 (for each x != 0)
		if (o1.equals(Constant.ZERO) && !o2.equals(Constant.ZERO)) {
			return Constant.ZERO;
		}
		return new BinaryOperation(BinaryOperator.DIVISION, o1, o2);
	}

	@Override
	public Expression visitPower(final Expression o1, final Expression o2) {
		//x^0 = 1
		if (o2.equals(Constant.ZERO)) {
			return Constant.ONE;
		}
		//x^1 = x
		if (o2.equals(Constant.ONE)) {
			return visit(o1);
		}
		return new BinaryOperation(BinaryOperator.POWER, o1, o2);
	}
	
	
	@Override
	public Expression visitAddition(final List<Expression> operands) {
		long constantTerm = 0;
		final List<Expression> addends = new ArrayList<>();
		for (final Expression o : operands) {
			if (o instanceof Constant) {
				final Constant c = (Constant) o;
				constantTerm += c.getValue();
			} else {
				addends.add(o);
			}
		}
		if (constantTerm != 0) {
			addends.add(Constant.valueOf(constantTerm));
		}
		
		switch (addends.size()) {
			case 0:
				return Constant.ZERO;
			case 1:
				return addends.get(0);
			default:
				return new NAryOperation(NAryOperator.ADDITION, addends);
		}
	}

	@Override
	public Expression visitProduct(final List<Expression> operands) {
		for (final Expression o : operands) {
			if (o.equals(Constant.ZERO)) {
				return Constant.ZERO;
			}
		}

		/*final List<Expression> multiplicands = operands.stream()
			.filter(x -> !x.equals(Constant.ONE))
			.collect(Collectors.toList());*/
		
		long constantTerm = 1;
		final List<Expression> multiplicands = new ArrayList<>();
		for (final Expression o : operands) {
			if (o instanceof Constant) {
				final Constant c = (Constant) o;
				constantTerm *= c.getValue();
			} else {
				multiplicands.add(o);
			}
		}
		
		if (constantTerm != 1) { //NOPMD
			multiplicands.add(Constant.valueOf(constantTerm));
		}
		
		switch (multiplicands.size()) {
			case 0:
				return Constant.ZERO;
			case 1:
				return multiplicands.get(0);
			default:
				return new NAryOperation(NAryOperator.PRODUCT, multiplicands);
		}
	}
	
	/*------------
	 * Functions
	 *------------*/
	
	@Override
	public Expression visitSin(final Expression arg) {
		if (arg.equals(Constant.ZERO)) {
			return Constant.ZERO;
		}
		return new FunctionOperation(Function.SIN, arg);
	}
	
	@Override
	public Expression visitCos(final Expression arg) {
		if (arg.equals(Constant.ZERO)) {
			return Constant.ONE;
		}
		return new FunctionOperation(Function.COS, arg);
	}
	
	@Override
	public Expression visitLn(final Expression arg) {
		if (arg.equals(SpecialConstant.E.getAsVariable())) {
			return Constant.ONE;
		}
		return new FunctionOperation(Function.LN, arg);
	}
	
	@Override
	public Expression visitAbs(final Expression arg) {
		if (arg instanceof Constant) {
			//If the argument is a constant, the absolute value can be calculated
			final Constant c = (Constant) arg;
			return c.getValue() >= 0 ? c : MathUtils.negate(c);
		}
		return new FunctionOperation(Function.ABS, arg);
	}
	
	@Override
	public Expression visitSqrt(final Expression arg) {
		if (arg instanceof Constant) {
			//Tries to compute the square root
			final Constant c = (Constant) arg;
			final long squareRoot = (long) Math.sqrt(c.getValue());
			if (squareRoot * squareRoot == c.getValue()) {
				//Eureka!
				return Constant.valueOf(squareRoot);
			}
		}
		return new FunctionOperation(Function.SQRT, arg);
	}
		
}
