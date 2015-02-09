package org.converger.framework.visitors;

import java.util.stream.Collectors;

import org.converger.framework.core.BinaryOperation;
import org.converger.framework.core.FunctionOperation;
import org.converger.framework.core.NAryOperation;

/**
 * This visitor implements a basic printer,
 * which converts an expression to plain text.
 * @author Dario Pavllo
 */
public class BasicPrinter extends AbstractPrinter {
	
	/**
	 * Instantiates this printer.
	 */
	public BasicPrinter() {
		//Division parenthesization: enabled
		super(true, "+", "-");
	}
	
	@Override
	protected String parenthesize(final String expression) {
		return "(" + expression + ")";
	}

	@Override
	protected String printBinary(final BinaryOperation v) {
		return new StringBuilder()
			.append(this.visit(v.getFirstOperand()))
			.append(v.getOperator().getSymbol())
			.append(this.visit(v.getSecondOperand()))
			.toString();
	}

	@Override
	protected String printNAry(final NAryOperation v) {
		return v.getOperands()
			.stream()
			.map(x -> this.visit(x))
			.collect(
				Collectors.joining(v.getOperator().getSymbol())
			);
	}
	
	@Override
	protected String printFunction(final FunctionOperation v) {
		return v.getFunction().getName()
			+ this.parenthesize(this.visit(v.getArgument()));
	}

}
