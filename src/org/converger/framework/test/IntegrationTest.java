package org.converger.framework.test;

import org.converger.framework.CasFramework;
import org.converger.framework.CasManager;
import org.converger.framework.Expression;
import org.converger.framework.SyntaxErrorException;
import org.junit.Test;
import org.junit.Assert;

/**
 * Automatic test for the integrator.
 * @author Dario Pavllo
 */
public class IntegrationTest {

	private static final double EPSILON = 1e-6;
	
	private final CasFramework cas = CasManager.getSingleton().createFramework();
	
	private void run(final String input, final double lowerBound,
		final double upperBound, final double expected) {
		try {
			final Expression e = cas.parse(input);
			final double result = cas.integrateNumerically(e, lowerBound, upperBound);
			Assert.assertEquals(expected, result, IntegrationTest.EPSILON);
			
		} catch (SyntaxErrorException e) {
			Assert.fail(e.getMessage());
		}

	}
	
	//CHECKSTYLE:OFF
	
	@Test
	public void testDefiniteIntegrals() {
		//Odd functions
		this.run("sin(x)", -5, 5, 0);
		this.run("x", -1, 1, 0);
		this.run("x^3", -3, 3, 0);
		this.run("sin(x)^3", -5, 5, 0);
		this.run("tan(x)", -1, 1, 0);
		this.run("x*e^(-x^2)", -3, 3, 0);
		
		//Random functions
		this.run("sin(x)", 0, Math.PI, 2);
		this.run("1/x", 1, 2, Math.log(2));
		this.run("x^2", 0, 5, 125/3.0);
		this.run("x*e^(-x^2)", 0, 1, 0.5 - 1/(2*Math.E));
	}
	
	//CHECKSTYLE:ON
}
