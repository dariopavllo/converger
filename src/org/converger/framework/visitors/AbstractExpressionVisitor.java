package org.converger.framework.visitors;

import java.util.List;
import java.util.stream.Collectors;

import org.converger.framework.Expression;
import org.converger.framework.core.BinaryOperation;
import org.converger.framework.core.Constant;
import org.converger.framework.core.FunctionOperation;
import org.converger.framework.core.NAryOperation;
import org.converger.framework.core.Variable;

/**
 * This abstract class is a template for visitors returning an expression.
 * The tree is traversed recursively.
 * @author Dario Pavllo
 */
public abstract class AbstractExpressionVisitor implements Expression.Visitor<Expression> {
	
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
		return new BinaryOperation(v.getOperator(),
			this.visit(v.getFirstOperand()),
			this.visit(v.getSecondOperand()));
	}

	@Override
	public Expression visit(final FunctionOperation v) {
		return new FunctionOperation(v.getFunction(), this.visit(v.getArgument()));
	}

	@Override
	public Expression visit(final NAryOperation v) {
		final List<Expression> visited = v.getOperands()
			.stream()
			.map(e -> this.visit(e))
			.collect(Collectors.toList());
		
		return new NAryOperation(v.getOperator(), visited);
	}
	
}
