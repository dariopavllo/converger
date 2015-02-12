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
		final NAryOperation children = (NAryOperation) super.visit(v);
		final List<Expression> sorted = new ArrayList<>(v.getOperands().size());
		
		final List<Expression> constants = iterateAndAdd(Constant.class, children);
		//Sorts the constants by value
		constants.sort((x, y) -> (int) (((Constant) x).getValue() - ((Constant) y).getValue()));
		sorted.addAll(constants);
		
		final List<Expression> variables = iterateAndAdd(Variable.class, children);
		//Sorts the variables by name (lexicographically)
		variables.sort((x, y) -> ((Variable) x).getName().compareTo(((Variable) y).getName()));
		sorted.addAll(variables);
		
		//Adds the other operations in the following order
		sorted.addAll(iterateAndAdd(NAryOperation.class, children));
		sorted.addAll(iterateAndAdd(BinaryOperation.class, children));
		sorted.addAll(iterateAndAdd(FunctionOperation.class, children));
		
		return new NAryOperation(v.getOperator(), sorted);
	}
	
	private static List<Expression> iterateAndAdd(
			final Class<? extends Expression> operationClass,
			final NAryOperation operation) {
		
		//Gets all the operations having the specified type and returns them in a list
		final List<Expression> outputList = new ArrayList<>();
		for (final Expression child : operation.getOperands()) {
			if (operationClass.isAssignableFrom(child.getClass())) {
				outputList.add(child);
			}
		}
		return outputList;
	}
}
