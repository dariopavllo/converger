package org.converger.framework.core;


import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.converger.framework.AbortedException;
import org.converger.framework.CasFramework;
import org.converger.framework.Expression;
import org.converger.framework.SyntaxErrorException;
import org.converger.framework.algorithms.NumericalSolver;
import org.converger.framework.visitors.BasicPrinter;
import org.converger.framework.visitors.Collector;
import org.converger.framework.visitors.ConstantFolder;
import org.converger.framework.visitors.Differentiator;
import org.converger.framework.visitors.Evaluator;
import org.converger.framework.visitors.LatexPrinter;
import org.converger.framework.visitors.RationalSimplifier;
import org.converger.framework.visitors.AlgebraicSimplifier;
import org.converger.framework.visitors.Substitutor;
import org.converger.framework.visitors.TreeLeveler;
import org.converger.framework.visitors.TreeSorter;
import org.converger.framework.visitors.VariableEnumerator;

/**
 * Actual implementation of the Converger framework.
 * @author Dario Pavllo
 */
public final class CasFrameworkImpl implements CasFramework {

	private volatile boolean aborted = false; //NOPMD
	
	private void interruptionCheck() {
		if (this.aborted) {
			aborted = false;
			throw new AbortedException();
		}
	}
	
	@Override
	public Expression parse(final String input) throws SyntaxErrorException {
		try {
			final Expression result = ExpressionFactory.build(input);
			
			//The tree is leveled in one pass to compress redundant operations
			return new TreeLeveler().visit(result);
			
		} catch (final IllegalArgumentException e) {
			//Rethrow
			throw new SyntaxErrorException(e.getMessage(), input, e);
			
		} catch (final EmptyStackException e) {
			//Unexpected error
			throw new SyntaxErrorException("Syntax error", input, e);
		}
	}

	@Override
	public Expression simplify(final Expression input) {
		Expression current = input;
		Expression previous;
		
		//Iterative simplification: the process is repeated until the tree no longer changes
		do {
			previous = current;
			current = new TreeLeveler().visit(current);
			current = new AlgebraicSimplifier().visit(current);
			current = new RationalSimplifier().visit(current);
			current = new Collector().visit(current);
			current = new ConstantFolder().visit(current);
			current = new TreeSorter().visit(current);
			this.interruptionCheck();
		} while (!previous.equals(current));
		
		return current;
	}

	@Override
	public Expression substitute(final Expression input,
			final Map<String, Expression> subexpressions) {
		final Map<Variable, Expression> finalMap = new HashMap<>();
		subexpressions.forEach((x, y) -> finalMap.put(new Variable(x), y));
		
		return new Substitutor(finalMap).visit(input);
	}

	@Override
	public Expression differentiate(final Expression input, final String variable) {
		final Differentiator d = new Differentiator(new Variable(variable));
		final Expression result = d.visit(this.simplify(input));
		return this.simplify(result);
	}

	@Override
	public double evaluate(final Expression input, final Map<String, Double> values) {
		final Map<Variable, Double> finalMap = new HashMap<>();
		values.forEach((x, y) -> finalMap.put(new Variable(x), y));
		
		//Special constants
		for (final SpecialConstant c : SpecialConstant.values()) {
			finalMap.put(c.getAsVariable(), c.getValue());
		}
		this.interruptionCheck();
		return new Evaluator(finalMap).visit(input);
	}
	
	@Override
	public Set<Double> solveNumerically(final Expression input) {
		final NumericalSolver solver = new NumericalSolver(this, input);
		return solver.solve();
	}

	@Override
	public String toPlainText(final Expression input) {
		return new BasicPrinter().visit(input);
	}

	@Override
	public String toLatexText(final Expression input) {
		return new LatexPrinter().visit(input);
	}

	@Override
	public Set<String> enumerateVariables(final Expression input) {
		final VariableEnumerator enumerator = new VariableEnumerator();
		enumerator.visit(input);
		return enumerator.getVariables();
	}

	@Override
	public void abort() {
		this.aborted = true;
	}

}
