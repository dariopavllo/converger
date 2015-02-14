package org.converger.framework.test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
public class EnumerationTest {
	
	private final CasFramework cas = CasManager.getSingleton().createFramework();
	
	private void run(final String expression, final String... expected) {
		try {
			final Expression e = cas.parse(expression);
			final Set<String> set = new HashSet<>(Arrays.asList(expected));
			Assert.assertEquals(set, cas.enumerateVariables(e));
		} catch (SyntaxErrorException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	//CHECKSTYLE:OFF
	
	@Test
	public void testEnumeration() {
		this.run("x + y + z", "x", "y", "z");
		this.run("pi");
		this.run("pi + e");
		this.run("pi + e * x + 2", "x");
		this.run("sin(x) + x", "x");
		this.run("ln(x) + test + x*cos(y)", "x", "test", "y");
	}
	
	//CHECKSTYLE:ON
}
