package org.converger.framework.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import org.converger.framework.CasFramework;
import org.converger.framework.CasManager;
import org.converger.framework.Expression;
import org.converger.framework.SyntaxErrorException;
import org.converger.framework.algorithms.NumericalSolver;
import org.converger.framework.core.Constant;

/**
 * Console test class.
 * @author Dario Pavllo
 */
public final class ConsoleTest {

	private ConsoleTest() {
	}
	
	/**
	 * Entry point.
	 * @param args not used
	 */
	public static void main(final String... args) {
		final BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
		final CasFramework cas = CasManager.getSingleton().createFramework();
		try {
			final Expression exp = cas.parse(console.readLine());
			System.out.println(cas.toPlainText(exp));
			System.out.println(cas.toLatexText(exp));
			System.out.println("----Simplified----");
			final Expression simplified = cas.simplify(exp);
			System.out.println(cas.toPlainText(simplified));
			System.out.println(cas.toLatexText(simplified));
			System.out.println("----Derivative----");
			final Expression derivated = cas.differentiate(exp, "x");
			System.out.println(cas.toPlainText(derivated));
			System.out.println(cas.toLatexText(derivated));
			System.out.println("----Equation solution----");
			try {
				final NumericalSolver solver = new NumericalSolver(cas, exp);
				System.out.println("Solution = " + Arrays.toString(solver.solve().toArray()));
			} catch (IllegalArgumentException e) {
				System.err.println("Unsolvable");
			}
			System.out.println("----Taylor series----");
			final Expression taylor = cas.taylorSeries(exp, "x", Constant.ONE, 5);
			System.out.println(cas.toPlainText(taylor));
			System.out.println(cas.toLatexText(taylor));
		} catch (final IOException e) {
			//Should never happen
			System.err.println("I/O Exception");
		} catch (final SyntaxErrorException e) {
			System.err.println("Syntax error: " + e.getMessage());
		}
	}
}
