package org.converger.framework.visitors;

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
 * This visitor implements a LaTeX printer.
 * It returns a string in LaTeX language with can be rendered subsequently.
 * @author Dario Pavllo
 */
public class LatexPrinter implements
	Expression.Visitor<String>,
	BinaryOperator.Visitor<String>, 
	NAryOperator.Visitor<String> {

	
	@Override
	public String visit(final Variable v) {
		return v.getName();
	}

	@Override
	public String visit(final Constant v) {
		return v.toString();
	}

	@Override
	public String visit(final BinaryOperation v) {
		final String o1 = this.visit(v.getFirstOperand());
		final String o2 = this.visit(v.getSecondOperand());

		try {
			//There is a special syntax for this operator
			return v.getOperator().accept(this, o1, o2);
		} catch (final UnsupportedOperationException e) {
			//Fallback syntax
			final String output = new StringBuilder()
				.append("\\left(")
				.append(this.visit(v.getFirstOperand()))
				.append(v.getOperator().getSymbol())
				.append(this.visit(v.getSecondOperand()))
				.append("\\right)")
				.toString();
			return output;
		}
		
	}
	
	@Override
	public String visit(final NAryOperation v) {
		final List<String> output = v.getOperands()
				.stream()
				.map(x -> this.visit(x))
				.collect(Collectors.toList());
		return v.getOperator().accept(this, output);
		/*final String output = v.getOperands()
			.stream()
			.map(x -> this.visit(x))
			.collect(
				Collectors.joining(v.getOperator().getSymbol())
			);
		if (this.firstLevel) {
			this.firstLevel = false;
			return output;
		} else {
			return "\\left(" + output + "\\right)";
		}*/
	}
	
	@Override
	public String visit(final FunctionOperation v) {
		return v.getFunction().getName() + "\\left(" + this.visit(v.getArgument()) + "\\right)";
	}

	@Override
	public String visitDivision(final String o1, final String o2) {
		//firstLevel = true;
		return "\\frac{" + o1 + "}{" + o2 + "}";
	}
	
	@Override
	public String visitPower(final String o1, final String o2) {
		return o1 + "^" + o2;
	}
	
	
	@Override
	public String visitAddition(final List<String> operands) {
		return operands.stream().collect(Collectors.joining(" + ", "\\left(", "\\right)"));
	}

	@Override
	public String visitProduct(final List<String> operands) {
		return operands.stream().collect(Collectors.joining("{\\cdot}"));
	}
	
	
}
