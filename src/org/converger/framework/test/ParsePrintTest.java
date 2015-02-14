package org.converger.framework.test;

import org.converger.framework.CasFramework;
import org.converger.framework.CasManager;
import org.converger.framework.Expression;
import org.converger.framework.SyntaxErrorException;
import org.junit.Test;
import org.junit.Assert;

/**
 * Tests the consistency of the parser and the printer.
 * Each test expression is parsed, printed, and parsed again.
 * Afterwards, the parsed expressions are checked for equality.
 * @author Dario Pavllo
 */
public class ParsePrintTest {
	
	private final CasFramework cas = CasManager.getSingleton().createFramework();
	
	private void run(final String expression) {
		try {
			final Expression e = cas.parse(expression);
			final String outStr = cas.toPlainText(e);
			final Expression e2 = cas.parse(outStr);
			Assert.assertEquals(e, e2);
		} catch (SyntaxErrorException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	//CHECKSTYLE:OFF
	
	@Test
	public void testEnumeration() {
		this.run("x + y + z");
		this.run("x + y - z");
		this.run("2x + 3y - 3x");
		this.run("sin(x) + cos(x)/2");
		this.run("ln(sin(x))*2cos(x) - x - y");
		this.run("1 + 2 + 3 / 2 - 1 ^ 3 ^ 4 ^ 5 ^ 5");
	}
	
	//CHECKSTYLE:ON
}
