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
 * Sorts the operands of n-ary operators.
 * @author Dario Pavllo
 */
public class TreeSorter extends AbstractExpressionVisitor {

	@Override
	public Expression visit(final NAryOperation v) {
		final List<Expression> sorted = new ArrayList<>(v.getOperands().size());
		for (final Expression child : v.getOperands()) {
			if (child instanceof Constant) {
				sorted.add(this.visit(child));
			}
		}
		for (final Expression child : v.getOperands()) {
			if (child instanceof Variable) {
				sorted.add(this.visit(child));
			}
		}
		for (final Expression child : v.getOperands()) {
			if (child instanceof NAryOperation) {
				sorted.add(this.visit(child));
			}
		}
		for (final Expression child : v.getOperands()) {
			if (child instanceof BinaryOperation) {
				sorted.add(this.visit(child));
			}
		}
		for (final Expression child : v.getOperands()) {
			if (child instanceof FunctionOperation) {
				sorted.add(this.visit(child));
			}
		}
		
		return new NAryOperation(v.getOperator(), sorted);
	}

}
