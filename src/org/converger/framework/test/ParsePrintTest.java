package org.converger.framework.test;

import org.converger.framework.CasFramework;
import org.converger.framework.Expression;
import org.junit.Test;
import org.junit.Assert;

/**
 * Tests the consistency of the parser and the printer.
 * Each test expression is parsed, printed, and parsed again.
 * Afterwards, the parsed expressions are checked for equality.
 * @author Dario Pavllo
 */
public class ParsePrintTest {
	
	//CHECKSTYLE:OFF
	
	private void test(final String expression) {
		final Expression e = CasFramework.getSingleton().parse(expression);
		final String outStr = CasFramework.getSingleton().toPlainText(e);
		final Expression e2 = CasFramework.getSingleton().parse(outStr);
		Assert.assertEquals(e, e2);
	}
	
	@Test
	public void testEnumeration() {
		this.test("x + y + z");
		this.test("x + y - z");
		this.test("2x + 3y - 3x");
		this.test("sin(x) + cos(x)/2");
		this.test("ln(sin(x))*2cos(x) - x - y");
		this.test("1 + 2 + 3 / 2 - 1 ^ 3 ^ 4 ^ 5 ^ 5");
	}
	
	//CHECKSTYLE:ON
}
