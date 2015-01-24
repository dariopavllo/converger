package org.converger.framework.test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.converger.framework.CasFramework;
import org.converger.framework.Expression;
import org.junit.Test;
import org.junit.Assert;

/**
 * Automatic test for the parser and the evaluator.
 * @author Dario Pavllo
 */
public class EnumerationTest {
	
	//CHECKSTYLE:OFF
	
	private void test(final String expression, final String... expected) {
		final Expression e = CasFramework.getSingleton().parse(expression);
		final Set<String> set = new HashSet<>(Arrays.asList(expected));
		Assert.assertEquals(set, CasFramework.getSingleton().enumerateVariables(e));
	}
	
	@Test
	public void testEnumeration() {
		this.test("x + y + z", "x", "y", "z");
		this.test("pi");
		this.test("pi + e");
		this.test("pi + e * x + 2", "x");
		this.test("sin(x) + x", "x");
		this.test("ln(x) + test + x*cos(y)", "x", "test", "y");
	}
	
	//CHECKSTYLE:ON
}
