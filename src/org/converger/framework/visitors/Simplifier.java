package org.converger.framework.visitors;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.converger.framework.Expression;
import org.converger.framework.core.BinaryOperation;
import org.converger.framework.core.BinaryOperator;
import org.converger.framework.core.Constant;
import org.converger.framework.core.FunctionOperation;
import org.converger.framework.core.NAryOperation;
import org.converger.framework.core.NAryOperator;
import org.converger.framework.core.Variable;

/**
 * This visitor simplifies algebraically the supplied expression.
 * It uses a series of algebraic rules and properties to transform
 * an expression to another equivalent expression with less complexity.
 * @author Dario Pavllo
 */
public class Simplifier implements
	Expression.Visitor<Expression> {

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
		//TODO singleton
		return v.getOperator().accept(new BinaryVisitor(),
				this.visit(v.getFirstOperand()),
				this.visit(v.getSecondOperand()));
	}
	
	@Override
	public Expression visit(final NAryOperation v) {
		final List<Expression> simplified = v.getOperands()
			.stream()
			.map(x -> visit(x))
			.collect(Collectors.toList());
		//TODO singleton
		return v.getOperator().accept(new NAryVisitor(), simplified);
	}
	
	@Override
	public Expression visit(final FunctionOperation v) {
		return new FunctionOperation(v.getFunction(), this.visit(v.getArgument()));
	}
	

	private class BinaryVisitor implements BinaryOperator.Visitor<Expression> {

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
		
	}
	
	private class NAryVisitor implements NAryOperator.Visitor<Expression> {
		
		@Override
		public Expression visitAddition(final List<Expression> operands) {
			int constantTerm = 0;
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

			final List<Expression> multiplicands = operands.stream()
				.filter(x -> !x.equals(Constant.ONE))
				.collect(Collectors.toList());
			
			switch (multiplicands.size()) {
				case 0:
					return Constant.ZERO;
				case 1:
					return multiplicands.get(0);
				default:
					return new NAryOperation(NAryOperator.PRODUCT, multiplicands);
			}
		}
		
	}
}
