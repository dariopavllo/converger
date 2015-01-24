package org.converger.framework.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.converger.framework.Environment;
import org.converger.framework.Expression;
import org.converger.framework.MathUtils;
import org.converger.framework.core.BinaryOperation;
import org.converger.framework.core.BinaryOperator;
import org.converger.framework.core.Constant;
import org.converger.framework.core.Function;
import org.converger.framework.core.FunctionOperation;
import org.converger.framework.core.NAryOperation;
import org.converger.framework.core.NAryOperator;
import org.converger.framework.core.Operator;
import org.converger.framework.core.Variable;

/**
 * This class builds a syntax tree from a list
 * of pre-parsed tokens in reverse polish notation.
 * @author Dario Pavllo
 */
public class TreeBuilder {

	private final List<Token> tokenList;
	private final Stack<Expression> stack;
	
	/**
	 * @param tokens a list of tokens in postfix (RPN) notation
	 */
	public TreeBuilder(final List<Token> tokens) {
		this.tokenList = new ArrayList<>(tokens);
		this.stack = new Stack<>();
	}
	
	/**
	 * Builds a syntax tree from the given token list.
	 * @return the generated tree
	 */
	public Expression build() {
		for (final Token t : this.tokenList) {
			switch (t.getType()) {
				case OPERATOR:
					this.pushOperator(t);
					break;
				case FUNCTION:
					this.pushFunction(t);
					break;
				case NUMBER:
					final int value = Integer.parseInt(t.getContent());
					this.stack.push(Constant.valueOf(value));
					break;
				case VARIABLE:
					this.stack.push(new Variable(t.getContent()));
					break;
				default:
					//Unexpected error (should never happen)
					throw new IllegalStateException("Internal error (unexpected token)");
			}
		}
		
		return stack.pop();
	}
	
	private void pushOperator(final Token t) {
		final Expression o2 = this.stack.pop(); //Second operand
		final Expression o1 = this.stack.pop(); //First operand
		
		final Operator o = Environment.getSingleton().getOperator(t.getContent());
		Expression result;
		if (o instanceof BinaryOperator) {
			//Binary operator
			final BinaryOperator binOp = (BinaryOperator) o;
			if (binOp == BinaryOperator.SUBTRACTION) {
				//Special case: the subtraction is transformed into x + (-1)*y
				result = new NAryOperation(
					NAryOperator.ADDITION,
					o1,
					MathUtils.negate(o2)
				);
			} else {
				//Normal case
				result = new BinaryOperation(binOp, o1, o2);
			}
			
		} else {
			//N-ary operator
			final NAryOperator nAryOp = (NAryOperator) o;
			result = new NAryOperation(nAryOp, o1, o2);
		}
		
		this.stack.push(result);
	}
	
	private void pushFunction(final Token t) {
		final Expression argument = this.stack.pop();
		
		final Function f = Environment.getSingleton().getFunction(t.getContent());
		this.stack.push(new FunctionOperation(f, argument));
	}
	
}
