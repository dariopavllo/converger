package org.converger.framework.algorithms;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.converger.framework.CasFramework;
import org.converger.framework.Expression;

/**
 * This class represents a numerical integrator, which
 * calculates approximately the definite integral of a function.
 * @author Dario Pavllo
 * @author Gabriele Graffieti
 */
public class NumericalIntegrator {

	/** The infinitesimal increment h. A lower value means more precision, but also more time */
	private static final double H = 1e-4;
	
	private final Expression function;
	private final CasFramework cas;
	private final String variable;
	
	/**
	 * Initializes this integrator.
	 * @param framework the framework instance
	 * @param f the function to integrate
	 */
	public NumericalIntegrator(final CasFramework framework, final Expression f) {
		this.cas = framework;
		
		//The function has to be in one variable
		final Set<String> variables = this.cas.enumerateVariables(f);
		if (variables.size() != 1) { //NOPMD
			throw new IllegalArgumentException("The input function should have only one variable");
		}
		this.variable = variables.iterator().next();
		this.function = framework.simplify(f);
	}
	
	/**
	 * Integrates the function contained in this object using the trapezoidal rule.
	 * @param lowerBound the lower bound of the integral
	 * @param upperBound the upper bound of the integral
	 * @return the approximate definite integral of the function
	 */
	public double integrate(final double lowerBound, final double upperBound) {
		//The number of subdivisions is rounded to a whole number
		final int subdivisions = (int) ((upperBound - lowerBound) / H);
		
		//Calculates the effective increment
		final double increment = (upperBound - lowerBound) / subdivisions;
		
		//Integrates
		double integral = 0;
		final Map<String, Double> values = new HashMap<>();
		for (int i = 0; i < subdivisions; i++) {
			//Trapezoidal rule
			final double a = lowerBound + i * increment;
			final double b = lowerBound + (i + 1) * increment;
			
			values.put(this.variable, a);
			final double fA = this.cas.evaluate(this.function, values);
			values.put(this.variable, b);
			final double fB = this.cas.evaluate(this.function, values);
			
			integral += fA + fB;
		}
		
		integral *= increment / 2;
		return integral;
	}
}
