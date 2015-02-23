package org.converger.framework.visitors;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.converger.framework.Environment;
import org.converger.framework.Expression;
import org.converger.framework.core.BinaryOperation;
import org.converger.framework.core.Constant;
import org.converger.framework.core.Equation;
import org.converger.framework.core.FunctionOperation;
import org.converger.framework.core.NAryOperation;
import org.converger.framework.core.Variable;

/**
 * This visitor lists all the variables contained in the given expression.
 * @author Dario Pavllo
 */
public class VariableEnumerator implements Expression.Visitor<Void> {

	private final Set<String> variables;
	
	/**
	 * Initializes this enumerator.
	 */
	public VariableEnumerator() {
		this.variables = new HashSet<>();
	}
	
	/**
	 * Returns the list of found variables in this expression.
	 * @return a set of variables
	 */
	public Set<String> getVariables() {
		return new TreeSet<>(this.variables);
	}

	@Override
	public Void visit(final Variable v) {
		if (!Environment.getSingleton().hasConstant(v.getName())) {
			//If it's not a mathematical constant like e or pi, it's a variable
			variables.add(v.getName());
		}
		return null;
	}

	@Override
	public Void visit(final Constant v) {
		return null;
	}

	@Override
	public Void visit(final BinaryOperation v) {
		this.visit(v.getFirstOperand());
		this.visit(v.getSecondOperand());
		return null;
	}

	@Override
	public Void visit(final FunctionOperation v) {
		this.visit(v.getArgument());
		return null;
	}

	@Override
	public Void visit(final NAryOperation v) {
		v.getOperands().forEach(this::visit);
		return null;
	}
	
	@Override
	public Void visit(final Equation v) {
		this.visit(v.getFirstMember());
		this.visit(v.getSecondMember());
		return null;
	}
}
