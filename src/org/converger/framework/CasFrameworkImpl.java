package org.converger.framework;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.converger.framework.core.SpecialConstant;
import org.converger.framework.core.Variable;
import org.converger.framework.parser.ShuntingYardParser;
import org.converger.framework.parser.Token;
import org.converger.framework.parser.Tokenizer;
import org.converger.framework.parser.TreeBuilder;
import org.converger.framework.visitors.BasicPrinter;
import org.converger.framework.visitors.Differentiator;
import org.converger.framework.visitors.Evaluator;
import org.converger.framework.visitors.LatexPrinter;
import org.converger.framework.visitors.RationalSimplifier;
import org.converger.framework.visitors.Simplifier;
import org.converger.framework.visitors.TreeLeveler;
import org.converger.framework.visitors.TreeSorter;
import org.converger.framework.visitors.VariableEnumerator;

/**
 * Actual implementation of the CAS framework.
 * @author Dario Pavllo
 */
public final class CasFrameworkImpl implements CasFramework {

	private static final CasFramework SINGLETON = new CasFrameworkImpl();
	
	private CasFrameworkImpl() {
	}
	
	/**
	 * Returns the unique instance of this class.
	 * @return a CasFramework singleton
	 */
	public static CasFramework getSingleton() {
		return CasFrameworkImpl.SINGLETON;
	}
	
	@Override
	public Expression parse(final String input) {
		final Tokenizer tokenizer = new Tokenizer(input);
		final ShuntingYardParser parser = new ShuntingYardParser();
		try {
			parser.parse(tokenizer);
			final List<Token> result = parser.getOutputList();
			final TreeBuilder tb = new TreeBuilder(result);
			return tb.build();
		} catch (final IllegalArgumentException e) {
			//Rethrow
			throw new IllegalArgumentException(e.getMessage(), e);
		} /*catch (final Exception e) {
			//Unexpected error
			throw new IllegalArgumentException("Unexpected syntax error", e);
		}*/
	}

	@Override
	public Expression simplify(final Expression input) {
		Expression current = input;
		Expression previous;
		
		int steps = 0;
		//Iterative simplification: the process is repeated until the tree no longer changes
		do {
			previous = current;
			current = new Simplifier().visit(current);
			current = new TreeLeveler().visit(current);
			current = new RationalSimplifier().visit(current);
			current = new TreeSorter().visit(current);
			steps++;
		} while (!previous.equals(current));
		System.out.println("Simplified in " + steps + " steps.");
		return current;
	}

	@Override
	public Expression substitute(final Expression input,
			final Map<String, Expression> subexpressions) {
		// TODO Auto-generated method stub
		return null;
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
		
		return new Evaluator(finalMap).visit(input);
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

}
