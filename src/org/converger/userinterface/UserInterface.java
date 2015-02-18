package org.converger.userinterface;

import java.util.List;
import java.util.Optional;

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
	 */
	void printExpression(String exp);
	
	/**
	 * Manage an error throws by the framework, for example an invalid expression or an invalid symbol.
	 * @param description the error description which will be shown on the user interface
	 */
	void error(String description); 
	
	/**
	 * Manage a yes/no question.
	 * @return the answer to the question, true is yes and false is no.
	 */
	boolean yesNoQuestion();
	
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
	Optional<String> save();
	
	/**
	 * Manage an open file request.
	 * It allows the user to select an expressions file and it will be opened in the user interface.
	 * @return the path of the selected file, or Optional.empty if no file is selected.
	 */
	Optional<String> open();
	
	/**
	 * Return an integer value which represent the index of the selected expression.
	 * @return the index of the selected expression.
	 * @throws NoElementSelectedException if no expression is selected.
	 */
	int getSelectedExpression() throws NoElementSelectedException;
	
	/**
	 * Manages the selection of a variable if the expression has 2 or more variables.
	 * @return the selected variable, or Optional.empty if no variable is selected.
	 */
	Optional<String> selectVariable();
	
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
}
