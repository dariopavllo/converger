package org.converger.userinterface.gui.dialog;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Represents an error dialog, which communicate at the used one error.
 * This class shows a JOptionPane with an error message.
 * @author Gabriele Graffieti
 *
 */
public class ErrorDialog {

	/**
	 * Constructs a new Error dialog with a custom message.
	 * In fact this constructor builds a new JOptionPane with a custom message.
	 * @param frame the parent frame of the dialog.
	 * @param errorMessage the message showed to the screen.
	 */
	public ErrorDialog(final JFrame frame, final String errorMessage) {
		JOptionPane.showMessageDialog(frame,
			    errorMessage,
			    "Error",
			    JOptionPane.ERROR_MESSAGE);
	}	
}
