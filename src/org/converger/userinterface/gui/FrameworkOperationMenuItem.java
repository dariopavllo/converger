package org.converger.userinterface.gui;

import java.util.List;
import java.util.NoSuchElementException;

import org.converger.controller.Controller;
import org.converger.controller.Field;
import org.converger.controller.FrameworkOperation;
import org.converger.controller.exception.NoElementSelectedException;
import org.converger.framework.SyntaxErrorException;
import org.converger.userinterface.gui.MenuButton.MenuItem;

/**
 * Represents a menu item which do a framework operation.
 * A framework operation is an operation that involve the cas framework.
 * All the framework operations are contained in the {@link FrameworkOperation} enum.
 * This interface contains only one method, executeFrameworkOperation, which it called by
 * every framework operation menu item at click.
 * @author Gabriele Graffieti
 *
 */
public interface FrameworkOperationMenuItem extends MenuItem {

	/**
	 * Execute the given framework operation. If the operation do not requires any field (no user intervention) 
	 * the operation is immediately executed.
	 * If the operation require user communication a dialog is shown.
	 * @param gui the gui where the dialog will be shown
	 * @param op the operation to be executed.
	 */
	default void executeFrameworkOperation(final GUI gui, final FrameworkOperation op) {
		try {
			int index = Controller.getController().getSelectedExpressionIndex();
			List<Field> fields = op.requestFields(index);
			if (fields.isEmpty()) {
				try {
					op.execute(index, fields);
				} catch (SyntaxErrorException | NoSuchElementException | IllegalArgumentException e) {
					gui.error(e.getMessage());
				}
			} else {
				gui.showDialog(op, fields, index);
			}
		} catch (NoElementSelectedException e) {
			gui.error(e.getMessage());
		}
	}
	
}
