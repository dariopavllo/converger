package org.converger.framework.visitors;

import java.util.List;
import java.util.stream.Collectors;

import org.converger.framework.core.BinaryOperation;
import org.converger.framework.core.BinaryOperator;
import org.converger.framework.core.FunctionOperation;
import org.converger.framework.core.NAryOperation;
import org.converger.framework.core.NAryOperator;
import org.converger.framework.core.SpecialConstant;
import org.converger.framework.core.Variable;
import org.converger.framework.core.Function;

/**
 * This visitor implements a LaTeX printer,
 * It returns a string in LaTeX language, which can be rendered subsequently.
 * @author Dario Pavllo
 */
public class LatexPrinter extends AbstractPrinter implements
	BinaryOperator.Visitor<String>,
	NAryOperator.Visitor<String>,
	Function.Visitor<String> {
	
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
		if (v.equals(SpecialConstant.PI.getAsVariable())) {
			//Prints "pi" in an aesthetically pleasing manner
			return "\\pi";
		}
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
		try {
			//Special syntax (if present)
			return v.getFunction().accept(this, this.visit(v.getArgument()));
		} catch (final UnsupportedOperationException e) {
			//Fallback
			return "\\mathrm{" + v.getFunction().getName() + "}"
					+ this.parenthesize(this.visit(v.getArgument()));
		}
		
	}

	@Override
	public String visitDivision(final String o1, final String o2) {
		return "\\dfrac{" + o1 + "}{" + o2 + "}";
	}
	
	@Override
	public String visitPower(final String o1, final String o2) {
		return "{" + o1 + "}^{" + o2 + "}";
	}

	@Override
	public String visitProduct(final List<String> operands) {
		return operands.stream().collect(Collectors.joining("{\\,}"));
	}
	
	/*-------------------
	 * Function overrides
	 *-------------------*/
	
	@Override
	public String visitSqrt(final String arg) {
		return "\\sqrt{ " + arg + "}";
	}
	
	@Override
	public String visitAbs(final String arg) {
		return "|" + arg + "|";
	}
	
}
