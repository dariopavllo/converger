package org.converger.framework.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.converger.framework.CasFramework;
import org.converger.framework.Expression;

/**
 * Console test class.
 * @author Dario Pavllo
 */
public final class Test {

	private Test() {
	}
	
	/**
	 * Entry point.
	 * @param args not used
	 */
	public static void main(final String... args) {
		final BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
		final CasFramework cas = CasFramework.getSingleton();
		try {
			final Expression exp = cas.parse(console.readLine());
			System.out.println(cas.toPlainText(exp));
			System.out.println(cas.toLatexText(exp));
			System.out.println("----Simplified----");
			final Expression simplified = cas.simplify(exp);
			System.out.println(cas.toPlainText(simplified));
			System.out.println(cas.toLatexText(simplified));
			System.out.println("----Derivate----");
			final Expression derivated = cas.differentiate(exp, "x");
			System.out.println(cas.toPlainText(derivated));
			System.out.println(cas.toLatexText(derivated));
		} catch (final IOException e) {
			//Should never happen
			System.err.println("I/O Exception");
		}
	}
}
