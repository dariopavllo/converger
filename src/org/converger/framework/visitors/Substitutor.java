package org.converger.framework.visitors;

import java.util.HashMap;
import java.util.Map;

import org.converger.framework.Expression;
import org.converger.framework.core.Variable;

/**
 * This visitor implements a substitutor.
 * Each variable is substituted with the supplied subexpression.
 * @author Dario Pavllo
 */
public class Substitutor extends AbstractExpressionVisitor {

	private final Map<Variable, Expression> substitutionMap;
	
	/**
	 * Initializes this substitutor using the supplied map of variables-subexpressions.
	 * @param substitution the substitution map
	 */
	public Substitutor(final Map<Variable, Expression> substitution) {
		this.substitutionMap = new HashMap<>(substitution);
	}
	
	@Override
	public Expression visit(final Variable v) {
		if (this.substitutionMap.containsKey(v)) {
			return this.substitutionMap.get(v);
		}
		return v;
	}
	
}
