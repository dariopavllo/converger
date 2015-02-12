package org.converger.framework.visitors;

import java.util.ArrayList;
import java.util.List;

import org.converger.framework.Expression;
import org.converger.framework.core.NAryOperation;

/**
 * This visitor implements a specific type of simplifier which
 * compresses the syntax tree. An n-ary operation which has
 * some children of the same type is compressed to a single node.
 * For example, (a+b) + (c+d) + e becomes a + b + c + d + e.
 * @author Dario Pavllo
 */
public class TreeLeveler extends AbstractExpressionVisitor {

	@Override
	public Expression visit(final NAryOperation v) {
		final List<Expression> leveled = new ArrayList<>();
		for (Expression child : v.getOperands()) {
			child = this.visit(child);
			if (child instanceof NAryOperation) {
				final NAryOperation op = (NAryOperation) child;
				if (op.getOperator() == v.getOperator()) {
					for (final Expression childOfChild : op.getOperands()) {
						//Merges its children with the parent node
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
