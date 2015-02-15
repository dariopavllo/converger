package org.converger.userinterface.gui.dialog;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ErrorDialog {

	public ErrorDialog(JFrame frame, String errorMessage) {
		JOptionPane.showMessageDialog(frame,
			    errorMessage,
			    "Error",
			    JOptionPane.ERROR_MESSAGE);
	}	
}
