package org.converger.framework.visitors;

import java.util.List;
import java.util.stream.Collectors;

import org.converger.framework.Expression;
import org.converger.framework.core.BinaryOperation;
import org.converger.framework.core.BinaryOperator;
import org.converger.framework.core.Constant;
import org.converger.framework.core.ExpressionFactory;
import org.converger.framework.core.Function;
import org.converger.framework.core.FunctionOperation;
import org.converger.framework.core.MathUtils;
import org.converger.framework.core.NAryOperation;
import org.converger.framework.core.NAryOperator;
import org.converger.framework.core.SpecialConstant;

/**
 * This visitor simplifies algebraically the supplied expression.
 * It uses a series of algebraic rules and properties to transform
 * an expression to another equivalent expression with less complexity.
 * @author Dario Pavllo
 */
public class AlgebraicSimplifier extends AbstractExpressionVisitor implements
	Expression.Visitor<Expression>,
	BinaryOperator.Visitor<Expression>,
	NAryOperator.Visitor<Expression>,
	Function.Visitor<Expression> {

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
			.map(x -> this.visit(x))
			.collect(Collectors.toList());
		
		return v.getOperator().accept(this, simplified);
	}
	
	@Override
	public Expression visit(final FunctionOperation v) {
		return v.getFunction().accept(this, this.visit(v.getArgument()));
	}
	
	/*------------------
	 * Binary operators
	 *-----------------*/
	
	@Override
	public Expression visitDivision(final Expression o1, final Expression o2) {
		// x/1 = x
		if (o2.equals(Constant.ONE)) {
			return o1;
		}
		
		// 0/x = 0 (if x != 0)
		if (o1.equals(Constant.ZERO) && !o2.equals(Constant.ZERO)) {
			return Constant.ZERO;
		}
		
		// x/x = 1 (if x != 0)
		if (o1.equals(o2) && !o2.equals(Constant.ZERO)) {
			return Constant.ONE;
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
		//1^x = 1
		if (o1.equals(Constant.ONE)) {
			return Constant.ONE;
		}
		
		//If the operands are constants, the result can be calculated
		if (o1 instanceof Constant && o2 instanceof Constant) {
			final long base = ((Constant) o1).getValue();
			final long exponent = ((Constant) o2).getValue();
			if (exponent >= 0) {
				final long result = MathUtils.integerPower(base, exponent);
				return Constant.valueOf(result);
			} else {
				//a^(-b) = 1/(a^b)
				final long result = MathUtils.integerPower(base, -exponent);
				return new BinaryOperation(
					BinaryOperator.DIVISION,
					Constant.ONE,
					Constant.valueOf(result)
				);
			}
		}
		return new BinaryOperation(BinaryOperator.POWER, o1, o2);
	}
	
	/*-----------------
	 * N-ary operators
	 *-----------------*/
	
	@Override
	public Expression visitAddition(final List<Expression> operands) {
		//Identity
		return new NAryOperation(NAryOperator.ADDITION, operands);
	}

	@Override
	public Expression visitProduct(final List<Expression> operands) {
		//Zero property: any term multiplied by zero is zero
		for (final Expression o : operands) {
			if (o.equals(Constant.ZERO)) {
				return Constant.ZERO;
			}
		}
		
		return new NAryOperation(NAryOperator.PRODUCT, operands);
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
	public Expression visitArcsin(final Expression arg) {
		if (arg.equals(Constant.ZERO)) {
			return Constant.ZERO;
		}
		return new FunctionOperation(Function.ARCSIN, arg);
	}
	
	@Override
	public Expression visitCos(final Expression arg) {
		if (arg.equals(Constant.ZERO)) {
			return Constant.ONE;
		}
		return new FunctionOperation(Function.COS, arg);
	}
	
	@Override
	public Expression visitArccos(final Expression arg) {
		if (arg.equals(Constant.ONE)) {
			return Constant.ZERO;
		}
		return new FunctionOperation(Function.ARCCOS, arg);
	}
	
	@Override
	public Expression visitTan(final Expression arg) {
		if (arg.equals(Constant.ZERO)) {
			return Constant.ZERO;
		}
		return new FunctionOperation(Function.TAN, arg);
	}
	
	@Override
	public Expression visitArctan(final Expression arg) {
		if (arg.equals(Constant.ZERO)) {
			return Constant.ZERO;
		}
		return new FunctionOperation(Function.ARCTAN, arg);
	}
	
	@Override
	public Expression visitLn(final Expression arg) {
		if (arg.equals(Constant.ONE)) {
			//ln(1) = 0
			return Constant.ZERO;
		}
		if (arg.equals(SpecialConstant.E.getAsVariable())) {
			//ln(e) = 1
			return Constant.ONE;
		}
		return new FunctionOperation(Function.LN, arg);
	}
	
	@Override
	public Expression visitAbs(final Expression arg) {
		if (arg instanceof Constant) {
			//If the argument is a constant, its absolute value can be calculated
			final Constant c = (Constant) arg;
			return c.getValue() >= 0 ? c : ExpressionFactory.negate(c);
		}
		return new FunctionOperation(Function.ABS, arg);
	}
	
	@Override
	public Expression visitSqrt(final Expression arg) {
		if (arg instanceof Constant) {
			//Tries to compute the integer square root
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
