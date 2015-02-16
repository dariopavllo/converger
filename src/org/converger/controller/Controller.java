package org.converger.controller;

import java.util.Set;

import org.converger.controller.exception.NoElementSelectedException;
import org.converger.framework.CasFramework;
import org.converger.framework.CasManager;
import org.converger.framework.Expression;
import org.converger.framework.SyntaxErrorException;
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
		this.framework = CasManager.getSingleton().createFramework();
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
		} catch (SyntaxErrorException e) {
			this.ui.error(e.getMessage());
		}
	}
	
	/* *********************************** DA ELIMINARE ********************************************************/ 
	
	public void deleteExpression() {
		try {
			final int exp = this.getSelectedExpression();
			this.currentEnvironment.delete(exp);
			this.ui.removeExpression(exp);
		} catch (NoElementSelectedException e) {
			this.ui.error(e.getMessage());
		}
	}
	
	public int getSelectedExpression() throws NoElementSelectedException {
		return this.ui.getSelectedExpression();
	}
	
	/**
	 * @param index the index of the expression
	 * @return The set of variables of the expression at the given index 
	 */
	public Set<String> getVariables(final int index) {
		return this.framework.enumerateVariables(this.currentEnvironment.getRecordList().get(index).getExpression());
	}
	
	@Override
	public void update(final ESource<? extends String> s, final String message) {
		System.out.println(message);
	}
	
	
	
}
