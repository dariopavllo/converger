package org.converger.framework.algorithms;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.converger.framework.CasFramework;
import org.converger.framework.Expression;
import org.converger.framework.core.BinaryOperation;
import org.converger.framework.core.BinaryOperator;
import org.converger.framework.core.Equation;
import org.converger.framework.core.ExpressionFactory;
import org.converger.framework.core.NAryOperation;
import org.converger.framework.core.NAryOperator;
import org.converger.framework.core.Variable;

/**
 * This class approximates the solutions of an equation using Newton's method.
 * @author Dario Pavllo
 * @author Gabriele Graffieti
 */
public class NumericalSolver {

	/** The starting point for Newton's algorithm (should be difficult to guess). */
	private static final double STARTING_POINT = 0.5632371895425321;
	/** The stop condition (error between subsequent iterations). */
	private static final double MAX_ERROR = 1e-9;
	/** The minimum error for a solution to be allowed (on maximum iteration count). */
	private static final double MIN_ERROR = 1e-5;
	/** The maximum iteration count. */
	private static final int MAX_ITERATIONS = 100;
	/** The number of error increments before divergence is declared */
	private static final int MAX_DIVERGENCE_COUNT = 20;
	/** The maximum range of a solution [-x, x], to prevent floating-point errors */
	private static final double MAX_RANGE = 1e10;
	
	private final CasFramework cas;
	private final String variable;
	private Expression function;
	private Expression derivative;
	private Map<String, Double> values;

	/**
	 * Initializes this numerical solver.
	 * @param framework the framework instance
	 * @param e the equation to solve
	 */
	public NumericalSolver(final CasFramework framework, final Expression e) {
		if (!(e instanceof Equation)) {
			throw new IllegalArgumentException("The input expression is not an equation");
		}
		
		this.cas = framework;
		
		//The equation has to be in one variable
		final Set<String> variables = this.cas.enumerateVariables(e);
		if (variables.size() != 1) { //NOPMD
			throw new IllegalArgumentException("The input equation should have only one variable");
		}
		this.variable = variables.iterator().next();
		
		//Setups the algorithm
		this.setup((Equation) e);
	}
	
	private void setup(final Equation equation) {
		//Given the equation f = g, we need to find the roots of f - g = 0
		this.function = this.cas.simplify(new NAryOperation(
			NAryOperator.ADDITION,
			equation.getFirstMember(),
			ExpressionFactory.negate(equation.getSecondMember())
		));
		
		this.derivative = this.cas.differentiate(this.function, this.variable);
	}
	
	/**
	 * Adds the current solution to the solution list and transforms the
	 * function in order to find the next root.
	 * @param value the found approximate root
	 * @param index the incremental index of the current solution
	 */
	private void addSolution(final double value, final int index) {
		final String varName = "_" + index; //Solution variable name
		this.values.put(varName, value);
		
		/* Eliminates the found root from the function.
		 * This is achieved by dividing the function by (x - x0), where x0 is the root. */
		this.function = new BinaryOperation(
			BinaryOperator.DIVISION,
			this.function,
			new NAryOperation(
				NAryOperator.ADDITION,
				new Variable(this.variable),
				ExpressionFactory.negate(new Variable(varName))
			)
		);
		
		this.derivative = this.cas.differentiate(this.function, this.variable);
	}
	
	/**
	 * Solves numerically the equation contained in this object.
	 * @return a set of real solutions
	 */
	public Set<Double> solve() {
		this.values = new HashMap<>();
		int divergenceCount = 0;
		boolean diverged = false;
		int currentSolutionIndex = 0;
		
		while (!diverged) {
			//Setups the initial condition for the current solution
			double x0 = NumericalSolver.STARTING_POINT;
			double prevError = Double.POSITIVE_INFINITY; //Previous error
			
			for (int i = 0; i < NumericalSolver.MAX_ITERATIONS; i++) {
				values.put(this.variable, x0);
				
				//Newton-Raphson iteration: x1 = x0 - f(x0)/f'(x0)
				final double x1 = x0 - this.cas.evaluate(this.function, this.values)
					/ this.cas.evaluate(this.derivative, this.values);
				
				//If the current iteration yields NaN, the algorithm has obviously diverged
				if (Double.isNaN(x1) || Double.isInfinite(x1)) {
					diverged = true;
					break;
				}
				
				//Calculates the error of the current iteration
				final double error = Math.abs(x1 - x0);
				if (error < NumericalSolver.MAX_ERROR) {
					prevError = error; //For the minimum error check
					break;
				}
				if (error > prevError) {
					//The function is diverging
					divergenceCount++;
					if (divergenceCount == NumericalSolver.MAX_DIVERGENCE_COUNT) {
						diverged = true;
						break;
					}
				}
				x0 = x1;
				prevError = error;
			}
			if (!diverged) {
				if (Math.abs(x0) <= NumericalSolver.MAX_RANGE
					&& prevError < NumericalSolver.MIN_ERROR) {
					//Adds the solution
					this.addSolution(x0, currentSolutionIndex++);
				} else {
					//The solution is too big
					diverged = true;
				}
				
			}
		}
		this.values.remove(this.variable); //Removes the temporary variable x0
		return new TreeSet<>(this.values.values());
	}
}
