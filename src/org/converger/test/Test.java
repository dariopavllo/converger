package org.converger.test;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.converger.controller.Controller;

/**
 * Test class for the application.
 * @author Gabriele Graffieti
 */
public final class Test {
	
	private Test() {
		
	}
	
	/**
	 * entry point.
	 * @param args arguments.
	 */
	public static void main(final String... args) {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		new Controller();
	}
}
