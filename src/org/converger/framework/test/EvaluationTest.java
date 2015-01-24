package org.converger.framework.test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.converger.framework.CasFramework;
import org.converger.framework.Expression;
import org.junit.Test;
import org.junit.Assert;

/**
 * Automatic test for the parser and the evaluator.
 * @author Dario Pavllo
 */
public class EvaluationTest {

	private static final double EPSILON = 1e-9;
	
	private void test(final String input, final double expected) {
		this.test(input, expected, Collections.emptyMap());
	}
	
	private void test(final String input, final double expected,
			final Map<String, Double> map) {
		final Expression e = CasFramework.getSingleton().parse(input);
		final double result = CasFramework.getSingleton().evaluate(e, map);
		Assert.assertEquals(expected, result, EvaluationTest.EPSILON);
	}
	
	//CHECKSTYLE:OFF
	
	@Test
	public void testSimpleEvaluation() {
		this.test("3 + 2", 5);
		this.test("2 * 2", 4);
		this.test("3 * 5 + 2 * 3", 21);
		this.test("3 * (5 + 2) * 3", 63);
		this.test("3 * (5 + 2)^2 * 3", 441);
	}
	
	@Test
	public void testParametricEvaluation() {
		final Map<String, Double> values = new HashMap<>();
		values.put("x", 10.0);
		values.put("y", 5.0);
		values.put("z", 7.0);
		values.put("arg", 11.0);
		
		this.test("3 + x", 13, values);
		this.test("3 + x + y", 18, values);
		this.test("x + y * z", 45, values);
		this.test("x + 3 - 3^y", -230, values);
		this.test("arg * x + 1 * 2", 112, values);
		this.test("x + pi + e", 10 + Math.PI + Math.E, values);
	}
	
	//CHECKSTYLE:ON
}
