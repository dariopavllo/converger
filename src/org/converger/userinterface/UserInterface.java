package org.converger.userinterface;

import java.util.List;
import java.util.Optional;
import java.util.function.DoubleFunction;

import org.converger.controller.Field;
import org.converger.controller.FrameworkOperation;
import org.converger.controller.exception.NoElementSelectedException;

/**
 * This interface represent the user interface of the CAS software. 
 * The controller can call only the functions of this interface. 
 * @author Gabriele Graffieti
 */
public interface UserInterface {
	
	/* METTERSI D'ACCORDO CON EH */
	
	/**
	 * Show the user interface on the screen.
	 */
	void show();
	
	/**
	 * Print in the user interface the given expression.
	 * @param exp the mathematical expression provided by the cas framework.
	 * @param op the operation which generated the expression to be added.
	 */
	void printExpression(String exp, Optional<String> op);
	
	/**
	 * Manage an error throws by the framework, for example an invalid expression or an invalid symbol.
	 * @param description the error description which will be shown on the user interface
	 */
	void error(String description); 
	
	/**
	 * Manage a yes/no question.
	 * @param message the message to be shown at the user
	 * @return the answer to the question, true is yes and false is no.
	 */
	boolean yesNoQuestion(String message);
	
	/**
	 * Manage the request of a dialog between the framework and the user when 
	 * a FrameworkOperation is called. 
	 * It requires the selected operation and a list of field.
	 * Every field in the list represent a single request form the framework to the user. 
	 * @param operation the selected operation.
	 * @param fields a list of field.
	 * @param index the index of the expression.
	 */
	void showDialog(FrameworkOperation operation, List<Field> fields, int index);
	
	/**
	 * Manage a save request when the file path is not specified.
	 * It allows the user to select a directory where the file will be saved.
	 * @return the path of the selected directory, or Optional.empty if no path is selected.
	 */
	Optional<String> saveDialog();
	
	/**
	 * Manage an open file request.
	 * It allows the user to select an expressions file and it will be opened in the user interface.
	 * @return the path of the selected file, or Optional.empty if no file is selected.
	 */
	Optional<String> openDialog();
	
	/**
	 * Return an integer value which represent the index of the selected expression.
	 * @return the index of the selected expression.
	 * @throws NoElementSelectedException if no expression is selected.
	 */
	int getSelectedExpression() throws NoElementSelectedException;
	
	/**
	 * Remove the selected expression from the user interface.
	 * @param index the index of the expression to be removed.
	 */
	void removeExpression(int index);
	
	/**
	 * Substitute the expression at the given index with the new expression passed.
	 * @param index the index of the expression to be edited.
	 * @param exp the new expression.
	 */
	void editExpression(int index, String exp);
	
	/**
	 * Removes all the expression from the user interface.
	 */
	void removeAll();
	
	/**
	 * Show the graph window.
	 * @param function the function to be plotted in the graph.
	 */
	void showGraph(DoubleFunction<Double> function);
}
