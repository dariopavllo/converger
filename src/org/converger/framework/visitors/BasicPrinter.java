package org.converger.framework.visitors;

import java.util.stream.Collectors;

import org.converger.framework.Expression;
import org.converger.framework.core.BinaryOperation;
import org.converger.framework.core.Constant;
import org.converger.framework.core.FunctionOperation;
import org.converger.framework.core.NAryOperation;
import org.converger.framework.core.Variable;

/**
 * This visitor implements a basic printer,
 * which converts an expression to plain text.
 * @author Dario Pavllo
 */
public class BasicPrinter implements Expression.Visitor<String> {

	@Override
	public String visit(final Variable v) {
		return v.getName();
	}

	@Override
	public String visit(final Constant v) {
		if (v.getValue() < 0) {
			return "(" + v.toString() + ")";
		} else {
			return v.toString();
		}
	}

	@Override
	public String visit(final BinaryOperation v) {
		return new StringBuilder()
			.append('(')
			.append(this.visit(v.getFirstOperand()))
			.append(v.getOperator().getSymbol())
			.append(this.visit(v.getSecondOperand()))
			.append(')')
			.toString();
	}
	
	@Override
	public String visit(final NAryOperation v) {
		return v.getOperands()
			.stream()
			.map(x -> this.visit(x))
			.collect(
				Collectors.joining(v.getOperator().getSymbol(), "(", ")")
			);
	}
	
	@Override
	public String visit(final FunctionOperation v) {
		return v.getFunction().getName() + "(" + this.visit(v.getArgument()) + ")";
	}

}
