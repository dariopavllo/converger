package org.converger.controller;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;

import org.converger.controller.exception.NoElementSelectedException;
import org.converger.controller.utility.EObserver;
import org.converger.controller.utility.ESource;
import org.converger.controller.utility.KeyboardEvent;

/**
 * The observer of the keyboard.
 * @author Gabriele Graffieti
 *
 */
public class KeyboardObserver implements EObserver<KeyboardEvent> {

	@Override
	public void update(final ESource<? extends KeyboardEvent> s, final KeyboardEvent event) {
		if (event == KeyboardEvent.COPY) {
			boolean checker = true;
			String copy = "";
			try {
				copy = Controller.getController().getEnvironment().getRecordList()
						.get(Controller.getController().getSelectedExpressionIndex()).getPlainText();
			} catch (NoElementSelectedException e) { 
				checker = false;
			}
			if (checker) {
				final StringSelection selection = new StringSelection(copy);
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, selection);
			}
		}
	}
}
