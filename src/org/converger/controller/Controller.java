package org.converger.controller;

import org.converger.framework.CasFramework;
import org.converger.framework.Expression;
import org.converger.userinterface.UserInterface;
import org.converger.userinterface.gui.GUI;
import org.converger.userinterface.utility.EObserver;
import org.converger.userinterface.utility.ESource;

/**
 * .
 * @author Gabriele Graffieti
 * */
public class Controller implements IController, EObserver<String> {

	private static final Controller CONTROLLER = new Controller(); 
	
	private final UserInterface ui;
	private final CasFramework framework;
	private final Environment currentEnvironment;
	
	private Controller() {
		this.ui = new GUI("Converger", this);
		this.framework = CasFramework.getSingleton();
		this.currentEnvironment = new Environment();
	}
	
	public static Controller getController() {
		return CONTROLLER;
	}
	
	public void showUI() {
		this.ui.show();
	}
	
	public void addExpression(final String expression) {
		try {
			final Expression expr = this.framework.parse(expression);
			final String plainText = this.framework.toPlainText(expr);
			final String latexText = this.framework.toLatexText(expr);
			this.currentEnvironment.add(new Record(plainText, latexText, expr));
			this.ui.printExpression(latexText);
		} catch (IllegalArgumentException e) {
			this.ui.error(e.getMessage());
		}
	}
	
	@Override
	public void update(final ESource<? extends String> s, final String message) {
		System.out.println(message);
	}
	
	
	
}
