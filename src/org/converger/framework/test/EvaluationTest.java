package org.converger.framework.test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.converger.framework.CasFramework;
import org.converger.framework.CasManager;
import org.converger.framework.Expression;
import org.converger.framework.SyntaxErrorException;
import org.junit.Test;
import org.junit.Assert;

/**
 * Automatic test for the parser and the evaluator.
 * @author Dario Pavllo
 */
public class EvaluationTest {

	private static final double EPSILON = 1e-9;
	private final CasFramework cas = CasManager.getSingleton().createFramework();
	
	private void run(final String input, final double expected) {
		this.run(input, expected, Collections.emptyMap());
	}
	
	private void run(final String input, final double expected,
			final Map<String, Double> map) {
		try {
			final Expression e = cas.parse(input);
			final double result = cas.evaluate(e, map);
			Assert.assertEquals(expected, result, EvaluationTest.EPSILON);			
		} catch (SyntaxErrorException e) {
			Assert.fail(e.getMessage());
		}

	}
	
	//CHECKSTYLE:OFF
	
	@Test
	public void testSimpleEvaluation() {
		this.run("3 + 2", 5);
		this.run("2 * 2", 4);
		this.run("3 * 5 + 2 * 3", 21);
		this.run("3 * (5 + 2) * 3", 63);
		this.run("3 * (5 + 2)^2 * 3", 441);
		this.run("3*2+2*5-1*7*2*3+7-(18/3)*100/5", -139);
		this.run("(7-2)*7*3 + 4+4+6 * 5^3*3/5", 563);
		this.run("2^3^2", 512);
		this.run("2^(3^2)", 512);
		this.run("(2^3)^2", 64);
	}
	
	@Test
	public void testParametricEvaluation() {
		final Map<String, Double> values = new HashMap<>();
		values.put("x", 10.0);
		values.put("y", 5.0);
		values.put("z", 7.0);
		values.put("arg", 11.0);
		
		this.run("3 + x", 13, values);
		this.run("3 + x + y", 18, values);
		this.run("x + y * z", 45, values);
		this.run("x + 3 - 3^y", -230, values);
		this.run("arg * x + 1 * 2", 112, values);
		this.run("x + pi + e", 10 + Math.PI + Math.E, values);
	}
	
	@Test
	public void testImplicitMultiplication() {
		final Map<String, Double> values = new HashMap<>();
		values.put("x", 10.0);
		
		this.run("x^2 + 2x + 1", 121, values);
		this.run("x^2 - 2x + 1 + 3cos(0)", 84, values);
		this.run("5ln(e)", 5, values);
		this.run("8ln(e^e)/(4e)", 2, values);
		this.run("5x ln(e)", 50, values);
	}
	//CHECKSTYLE:ON
}
