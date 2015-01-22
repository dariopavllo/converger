package org.converger.framework.visitors;

import java.util.ArrayList;
import java.util.List;

import org.converger.framework.Expression;
import org.converger.framework.core.BinaryOperation;
import org.converger.framework.core.Constant;
import org.converger.framework.core.FunctionOperation;
import org.converger.framework.core.NAryOperation;
import org.converger.framework.core.Variable;

/**
 * This visitor implements a specific type of simplifier which
 * compresses the syntax tree. An n-ary operation which has
 * some children of the same type is compressed to a single node.
 * For example, (a+b) + (c+d) + e becomes a + b + c + d + e.
 * @author Dario Pavllo
 */
public class TreeLeveler implements Expression.Visitor<Expression> {

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
		final List<Expression> leveled = new ArrayList<>();
		for (Expression child : v.getOperands()) {
			child = this.visit(child);
			if (child instanceof NAryOperation) {
				final NAryOperation op = (NAryOperation) child;
				if (op.getOperator() == v.getOperator()) {
					for (final Expression childOfChild : op.getOperands()) {
						leveled.add(childOfChild);
					}
				} else {
					leveled.add(child);
				}
			} else {
				leveled.add(child);
			}
		}
		return new NAryOperation(v.getOperator(), leveled);
	}

}
