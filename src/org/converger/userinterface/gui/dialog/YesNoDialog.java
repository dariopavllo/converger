package org.converger.userinterface.gui.dialog;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Represents a yes no dialog implementation.
 * The user can choose between 2 options, and the result will be turned in a boolean value
 * @author User
 *
 */
public class YesNoDialog {
	
	private final JFrame parentFrame;
	private final String msg;

	/**
	 * Create the dialog.
	 * @param parent the parent frame of the dialog.
	 * @param message the message to be shown
	 */
	public YesNoDialog(final JFrame parent, final String message) {
		this.parentFrame = parent;
		this.msg = message;
	}
	
	/**
	 * Return the choice of the user as boolean value. 
	 * @return a boolean representing the choice of the user, true is yes and false is no.
	 */
	public boolean response() {
		final int i = JOptionPane.showConfirmDialog(parentFrame, 
				msg, 
				"Warning", 
				JOptionPane.YES_NO_OPTION, 
				JOptionPane.WARNING_MESSAGE);
		return i == JOptionPane.OK_OPTION;
	}
}
