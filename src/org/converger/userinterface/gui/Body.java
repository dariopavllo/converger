package org.converger.userinterface.gui;

import org.converger.controller.exception.NoElementSelectedException;

/**
 * Represent the center part of the GUI, where latex expressions are shown.
 * @author Gabriele Graffieti
 */
public interface Body extends GUIComponent {
	/** 
	 * @return the index of the selected expression. If no expression is selected throw a 
	 * NoElementSelectedException. 
	 * @throws NoElementSelectedException if no element is selected
	 */
	int getSelected() throws NoElementSelectedException;
	
	/**
	 * Draws a new expression to the screen. The expression is placed in the bottom of the expressions list.
	 * @param latexExpression the expression to draw in latex string format
	 */
	void drawNewExpression(final String latexExpression);
	
	/**
	 * Edits the expression at the given index and substitute it with the latex string given in input.
	 * @param index the index of the expression to be replaced.
	 * @param latexExpression the new expression.
	 */
	void editExpression(final int index, final String latexExpression);
	
	/**
	 * Deletes the expression at the given index.
	 * @param index the index of the expression to be removed.
	 */
	void deleteExpression(final int index);
	
	/**
	 * Deletes all the expressions.
	 */
	void deleteAll();
}
