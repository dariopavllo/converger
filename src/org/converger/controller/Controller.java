package org.converger.controller;

import org.converger.userinterface.UserInterface;
import org.converger.userinterface.gui.GUI;
import org.converger.userinterface.utility.EObserver;
import org.converger.userinterface.utility.ESource;

/**
 * .
 * @author Gabriele Graffieti
 * */
public class Controller implements IController, EObserver<String> {

	private final UserInterface ui;
	
	/**
	 * .
	 */
	public Controller() {
		this.ui = new GUI("Converger", this);
		this.ui.show();
	}
	
	@Override
	public void update(final ESource<? extends String> s, final String message) {
		System.out.println(message);
	}
	
}
