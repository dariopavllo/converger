package org.converger.framework.visitors;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;
import java.util.stream.Collectors;

import org.converger.framework.Expression;
import org.converger.framework.core.BinaryOperation;
import org.converger.framework.core.BinaryOperator;
import org.converger.framework.core.Constant;
import org.converger.framework.core.Equation;
import org.converger.framework.core.ExpressionFactory;
import org.converger.framework.core.FunctionOperation;
import org.converger.framework.core.NAryOperation;
import org.converger.framework.core.NAryOperator;
import org.converger.framework.core.Operator;
import org.converger.framework.core.Variable;

/**
 * This visitor implements an abstract printer,
 * a visitor which converts an expression to text.
 * @author Dario Pavllo
 */
public abstract class AbstractPrinter implements Expression.Visitor<String> {

	private final Stack<Optional<Operator>> parentStack;
	private final boolean parenthesizeDivision;
	private final String additionConcatenator;
	private final String subtractionConcatenator;
	
	/**
	 * Initializes this printer.
	 * @param divisionParentheses indicates whether the children of division
	 * operations should contain parentheses
	 * @param addConcatenator the concatenator of addition operations
	 * @param subConcatenator the concatenator of subtraction operations
	 */
	protected AbstractPrinter(final boolean divisionParentheses,
			final String addConcatenator, final String subConcatenator) {
		this.parenthesizeDivision = divisionParentheses;
		this.additionConcatenator = addConcatenator;
		this.subtractionConcatenator = subConcatenator;
		this.parentStack = new Stack<>();
		this.parentStack.push(Optional.empty());
	}
	
	/**
	 * Parenthesizes the given string.
	 * @param expression the string to parenthesize
	 * @return a string representing the parenthesized expression
	 */
	protected abstract String parenthesize(String expression);
	
	/**
	 * Specifies how to print a binary operation.
	 * @param v the binary operation
	 * @return a string representing the operation
	 */
	protected abstract String printBinary(BinaryOperation v);
	
	/**
	 * Specifies how to print an n-ary operation.
	 * @param v the n-ary operation
	 * @return a string representing the operation
	 */
	protected abstract String printNAry(NAryOperation v);
	
	/**
	 * Specifies how to print a function.
	 * @param v the function
	 * @return a string representing the function
	 */
	protected abstract String printFunction(FunctionOperation v);
	
	/**
	 * Specifies how to print a list of signed addends.
	 * @param addends the list of addends
	 * @return a string representing the operation
	 */
	private String printAddition(final List<Addend> addends) {
		final StringBuilder str = new StringBuilder();
		for (int i = 0; i < addends.size(); i++) {
			if (addends.get(i).isPositive() && i > 0) {
				str.append(" " + this.additionConcatenator + " "); //Plus sign
			} else if (!addends.get(i).isPositive()) {
				if (i > 0) {
					str.append(" " + this.subtractionConcatenator + " "); //Minus sign
				} else {
					str.append(this.subtractionConcatenator);
				}
			}
			str.append(this.visit(addends.get(i).getContent()));
		}
		return str.toString();		
	}
	
	/* Template method */
	private String parenthesizeIfNeeded(final String expression) {
		if (!this.parentStack.peek().isPresent()) {
			throw new IllegalStateException("No operator pushed");
		}
		final Operator current = this.parentStack.pop().get();
		final Optional<Operator> parent = this.parentStack.peek();
		
		//Reinserts the operator to restore the stack to its original state
		this.parentStack.push(Optional.of(current));
		
		//Checks the special case for division nodes
		if (parent.isPresent() && parent.get().equals(BinaryOperator.DIVISION)
				&& !this.parenthesizeDivision) {
			return expression;
		}
		
		//Parenthesizes the expression if needed (using the operator precedence)
		if (parent.isPresent() && parent.get().getPrecedence() >= current.getPrecedence()) {
			return this.parenthesize(expression);
		}
		
		//No parenthesization needed
		return expression;
	}
	
	@Override
	public String visit(final Variable v) {
		return v.getName();
	}

	@Override
	public String visit(final Constant v) {
		if (v.getValue() < 0) {
			return this.parenthesize(v.toString());
		} else {
			return v.toString();
		}
	}

	@Override
	public final String visit(final BinaryOperation v) {
		this.parentStack.push(Optional.of(v.getOperator()));
		//Visits the binary operation according to the subclass implementation
		String result = this.printBinary(v);
		//Special case for division nodes
		if (v.getOperator() == BinaryOperator.DIVISION && this.parenthesizeDivision
				|| v.getOperator() != BinaryOperator.DIVISION) {
			result = this.parenthesizeIfNeeded(result);
		}
		this.parentStack.pop();
		return result;
	}
	
	@Override
	public final String visit(final NAryOperation v) {
		this.parentStack.push(Optional.of(v.getOperator()));
		
		String result;
		if (v.getOperator() == NAryOperator.ADDITION) {
			//Special case for addition
			result = this.printAddition(v.getOperands().stream()
				.map(o -> new Addend(o))
				.collect(Collectors.toList()));
		} else {
			//Visits the n-ary operation according to the subclass implementation
			result = this.printNAry(v);
		}
		
		result = this.parenthesizeIfNeeded(result);
		this.parentStack.pop();
		return result;
	}
	
	@Override
	public final String visit(final FunctionOperation v) {
		this.parentStack.push(Optional.empty());
		final String result = this.printFunction(v);
		this.parentStack.pop();
		return result;
	}
	
	@Override
	public final String visit(final Equation v) {
		return this.visit(v.getFirstMember()) + " = " + this.visit(v.getSecondMember());
	}
	
	/**
	 * This class models an addend, storing its sign and its absolute value.
	 * The purpose of this class is to provide a way to print subtraction operators
	 * where possible, in order to produce human-friendly expressions.
	 */
	protected class Addend {
		
		private final boolean sign;
		private final Expression content;
		
		/**
		 * Instantiates this addend with the provided expression.
		 * The sign is automatically calculated.
		 * @param e the addend
		 */
		public Addend(final Expression e) {
			final Optional<Expression> positive = unNegate(e);
			if (positive.isPresent()) {
				this.sign = false;
				this.content = positive.get();
			} else {
				this.sign = true;
				this.content = e;
			}
		}
		
		/**
		 * Tells whether this addend has a positive sign.
		 * @return a boolean representing the sign of this addend
		 */
		public boolean isPositive() {
			return this.sign;
		}
		
		/**
		 * Returns the expression representing this addend.
		 * @return the expression representing this addend
		 */
		public Expression getContent() {
			return this.content;
		}
		
		/**
		 * Removes the negation (if present) from the given expression.
		 * @param e the expression to un-negate
		 * @return an empty Optional if the expression is already positive,
		 * the un-negated expression otherwise
		 */
		private Optional<Expression> unNegate(final Expression e) {
			//Checks for multiplications containing a negative term
			if (e instanceof NAryOperation) {
				final NAryOperation nOp = (NAryOperation) e;
				if (nOp.getOperator() == NAryOperator.PRODUCT
						&& nOp.getOperands().get(0) instanceof Constant) {
					final Constant c = (Constant) nOp.getOperands().get(0);
					
					//List of the operands, excluding the first term
					final List<Expression> operands = nOp.getOperands().subList(
							1, nOp.getOperands().size());
					
					if (c.equals(Constant.valueOf(-1))) {
						//The -1 term is removed
						return Optional.of(
							ExpressionFactory.implode(NAryOperator.PRODUCT, operands));
					}
					if (c.getValue() < 0) {
						//The minus sign is removed, but the term is preserved
						final List<Expression> output = new ArrayList<>();
						output.add(Constant.valueOf(-c.getValue()));
						output.addAll(operands);
						return Optional.of(
							ExpressionFactory.implode(NAryOperator.PRODUCT, output));
					}
				}
			}
			
			//Checks for negative constants
			if (e instanceof Constant) {
				final Constant c = (Constant) e;
				if (c.getValue() < 0) {
					return Optional.of(Constant.valueOf(-c.getValue()));
				}
			}
			
			//Checks for fractions
			if (e instanceof BinaryOperation) {
				final BinaryOperation bOp = (BinaryOperation) e;
				if (bOp.getOperator() == BinaryOperator.DIVISION) {
					//Recursive
					final Optional<Expression> numerator = this.unNegate(bOp.getFirstOperand());
					final Optional<Expression> denominator = this.unNegate(bOp.getSecondOperand());
					
					//If they have different signs
					if (numerator.isPresent() != denominator.isPresent()) {
						final Expression num = numerator.isPresent()
							? numerator.get()
							: bOp.getFirstOperand();
						final Expression denom = denominator.isPresent()
							? denominator.get()
							: bOp.getSecondOperand();
						//Strips the sign from the fraction
						return Optional.of(
							new BinaryOperation(BinaryOperator.DIVISION, num, denom));
					}
				}
			}
			return Optional.empty();
		}
		
	}

}
