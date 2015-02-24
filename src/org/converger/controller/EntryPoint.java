package org.converger.controller;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Entry point of the application.
 * @author Gabriele Graffieti
 */
public final class EntryPoint {

	private EntryPoint() {
		
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
			System.out.println("errore visualizzazione gui");
		}
		
		Controller.getController().showUI();
	}
	
}
