package org.converger.framework.test;

import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import org.converger.framework.CasFramework;
import org.converger.framework.CasManager;
import org.converger.framework.Expression;
import org.converger.framework.SyntaxErrorException;
import org.junit.Test;
import org.junit.Assert;

/**
 * Automatic test for the numerical equation solver.
 * @author Dario Pavllo
 */
public class EquationTest {

	private static final double EPSILON = 1e-6;
	private static final int QUADRATIC_TEST_CASES = 1000;
	
	private final CasFramework cas = CasManager.getSingleton().createFramework();
	
	private void run(final String input, final double... expected) {
		try {
			final Expression e = cas.parse(input);
			final Set<Double> result = cas.solveNumerically(e);
			Assert.assertEquals(expected.length, result.size());
			int i = 0;
			for (final double val : result) {
				Assert.assertEquals(expected[i++], val, EquationTest.EPSILON);
			}
		} catch (SyntaxErrorException e) {
			Assert.fail(e.getMessage());
		}

	}
	
	//CHECKSTYLE:OFF
	
	@Test
	public void autoTestQuadratic() {
		//Tests some quadratic equations with random coefficients.
		
		final Random rnd = new Random(1); //Fixed seed
		final Iterator<Integer> stream = rnd.ints(-10, 10).iterator();
		for (int i = 0; i < QUADRATIC_TEST_CASES; i++) {
			int a = stream.next();
			if (a == 0) {
				//a must not be 0
				a = 1;
			}
			final int b = stream.next();
			final int c = stream.next();
			double x1 = (-b - Math.sqrt(b*b - 4*a*c)) / (2*a);
			double x2 = (-b + Math.sqrt(b*b - 4*a*c)) / (2*a);
			if (x2 < x1) {
				//The solutions must be sorted
				final double tmp = x1;
				x1 = x2;
				x2 = tmp;
			}
			final String equation = a + "x^2 + (" + b + ")x + (" + c + ") = 0";
			if (Double.isNaN(x1) && Double.isNaN(x2)) {
				this.run(equation);
			} else if (!Double.isNaN(x1) && !Double.isNaN(x2)) { //NOPMD
				this.run(equation, x1, x2);
			} else {
				this.run(equation, Double.isNaN(x1) ? x2 : x1);
			}
		}
	}
	
	@Test
	public void testEquations() {
		this.run("e^x - x = 5", -4.993216188, 1.936847407);
		this.run("x^3 + 2x^2 - 3x - 5 = 0", -2.377202853, -1.273890554, 1.651093408);
		this.run("sin(x) + x = cos(x)", 0.4566247045);
	}
	
	//CHECKSTYLE:ON
}
