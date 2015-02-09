package org.converger.framework.visitors;

import java.util.List;
import java.util.stream.Collectors;

import org.converger.framework.core.BinaryOperation;
import org.converger.framework.core.BinaryOperator;
import org.converger.framework.core.FunctionOperation;
import org.converger.framework.core.NAryOperation;
import org.converger.framework.core.NAryOperator;
import org.converger.framework.core.Variable;

/**
 * This visitor implements a basic printer,
 * which converts an expression to plain text.
 * @author Dario Pavllo
 */
public class LatexPrinter extends AbstractPrinter
	implements BinaryOperator.Visitor<String>, NAryOperator.Visitor<String> {
	
	/**
	 * Instantiates this printer.
	 */
	public LatexPrinter() {
		//Division parenthesization: disabled (not needed on fractions)
		super(false, "\\;+\\;", "\\;-\\;");
	}
	
	@Override
	protected String parenthesize(final String expression) {
		return "\\left(" + expression + "\\right)";
	}
	
	@Override
	public String visit(final Variable v) {
		return "\\mathit{" + super.visit(v) + "}";
	}

	@Override
	protected String printBinary(final BinaryOperation v) {
		
		final String o1 = this.visit(v.getFirstOperand());
		final String o2 = this.visit(v.getSecondOperand());
		String result;
		try {
			//If there's a special syntax for this operator...
			result = v.getOperator().accept(this, o1, o2);
		} catch (final UnsupportedOperationException e) {
			//Fallback syntax
			result = new StringBuilder()
				.append(this.visit(v.getFirstOperand()))
				.append(v.getOperator().getSymbol())
				.append(this.visit(v.getSecondOperand()))
				.toString();
		}
		return result;
	}

	@Override
	protected String printNAry(final NAryOperation v) {
		final List<String> output = v.getOperands()
				.stream()
				.map(x -> this.visit(x))
				.collect(Collectors.toList());
		return v.getOperator().accept(this, output);
	}
	
	@Override
	protected String printFunction(final FunctionOperation v) {
		return "\\mathrm{" + v.getFunction().getName() + "}"
				+ this.parenthesize(this.visit(v.getArgument()));
	}

	@Override
	public String visitDivision(final String o1, final String o2) {
		return "\\frac{" + o1 + "}{" + o2 + "}";
	}

	@Override
	public String visitProduct(final List<String> operands) {
		return operands.stream().collect(Collectors.joining("{\\,}"));
	}
	
}
