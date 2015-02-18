package org.converger.controller;

import java.util.Set;

import org.converger.controller.exception.NoElementSelectedException;
import org.converger.framework.CasFramework;
import org.converger.framework.CasManager;
import org.converger.framework.Expression;
import org.converger.framework.SyntaxErrorException;
import org.converger.userinterface.UserInterface;
import org.converger.userinterface.gui.GUI;

/**
 * The controller of the application. 
 * It coordinates the user interface and the framework, offers functions 
 * which are called by the user interface to get framework functionalities.
 * Briefly the controller filters the gui requests and interacts with the framework.
 * The controller is a singleton, and for get the controller instance you can call the 
 * getController method.
 * @author Gabriele Graffieti
 * */
public final class Controller {

	private static final Controller SINGLETON = new Controller(); 
	
	private final UserInterface ui;
	private final CasFramework framework;
	private final Environment currentEnvironment;
	
	private Controller() {
		this.ui = new GUI("Converger");
		this.framework = CasManager.getSingleton().createFramework();
		this.currentEnvironment = new Environment();
	}
	
	/**
	 * Return the instance of the controller.
	 * @return the instance of the controller.
	 */
	public static Controller getController() {
		return SINGLETON;
	}
	
	/**
	 * Returns the framework of the application.
	 * @return the framework of the application.
	 */
	public CasFramework getFramework() {
		return this.framework;
	}
	
	/**
	 * Get the selected expression to the user interface.
	 * @return the index of the selected expression.
	 * @throws NoElementSelectedException if no expression is selected.
	 */
	public int getSelectedExpressionIndex() throws NoElementSelectedException {
		return this.ui.getSelectedExpression();
	}
	
	/**
	 * Returns the expression at the given index.
	 * @param index the index of the required expression
	 * @return the expression at the given index.
	 */
	public Expression getExpressionAt(final int index) {
		return this.currentEnvironment.getRecordList().get(index).getExpression();
	}
	
	/**
	 * Show the user interface to the screen.
	 */
	public void showUI() {
		this.ui.show();
	}
	
	/**
	 * Parse a string to an expression, add it to the current environment and print it in the user interface.
	 * @param expression a string representation of a mathematical expression.
	 */
	public void addExpression(final String expression) {
		try {
			final Expression exp = this.framework.parse(expression);
			this.addExpression(exp);
		} catch (SyntaxErrorException e) {
			this.ui.error(e.getMessage());
		}
	}
	
	/**
	 * Add a new expression to the current environment and prints it to the user interface.
	 * @param exp expression to be added.
	 */
	public void addExpression(final Expression exp) {
		final String latexText = this.framework.toLatexText(exp);
		this.currentEnvironment.add(this.getRecordFromExpression(exp));
		this.ui.printExpression(latexText);
	}
	
	/**
	 * Add a numerical expression to the current environment. A numerical expression is a real number 
	 * given in decimal representation (for example 1.125).
	 * @param number the real number to be added to the current environment
	 */
	public void addNumericalExpression(final Double number) {
		try {
			final Expression exp = this.framework.parse(Double.toString(number));
			this.currentEnvironment.add(new Record(Double.toString(number), Double.toString(number), exp));
			this.ui.printExpression(Double.toString(number));
		} catch (SyntaxErrorException e) {
			this.ui.error(e.getMessage());
		}
	}
	
	/**
	 * Substitute the expression at the given index with the new expression passed, and 
	 * notify it at the user interface.
	 * @param index the index of the expression to be edited
	 * @param newExpression the new expression.
	 */
	public void editExpression(final int index, final Expression newExpression)  {
		this.currentEnvironment.modifyExpression(index, this.getRecordFromExpression(newExpression));
		this.ui.editExpression(index, this.framework.toLatexText(newExpression));
	}
	
	/**
	 * Delete an expression from the current environment and sequentially from the user interface.
	 */
	public void deleteExpression() {
		try {
			final int exp = this.getSelectedExpressionIndex();
			this.currentEnvironment.delete(exp);
			this.ui.removeExpression(exp);
		} catch (NoElementSelectedException e) {
			this.ui.error(e.getMessage());
		}
	}
	/**
	 * Returns the set of variables of the expression in the given index.
	 * @param index the index of the expression
	 * @return The set of variables of the expression at the given index 
	 */
	public Set<String> getVariables(final int index) {
		return this.framework.enumerateVariables(this.currentEnvironment.getRecordList().get(index).getExpression());
	}
	
	private Record getRecordFromExpression(final Expression exp) {
		return new Record(this.framework.toPlainText(exp), this.framework.toLatexText(exp), exp);
	}
}
