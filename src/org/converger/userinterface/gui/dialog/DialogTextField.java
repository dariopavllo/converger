package org.converger.userinterface.gui.dialog;

import java.awt.Font;

import javax.swing.JTextField;

import org.converger.userinterface.gui.GUIConstants;

/**
 * Extends a JTextField and implements the DialogComponent interface.
 * This component is used in the dialog.
 * @author Gabriele Graffieti
 *
 */
@SuppressWarnings("serial")
public class DialogTextField extends JTextField implements DialogComponent {

	/**
	 * Create the DialogTextField and set its lenght and its default font and text size.
	 */
	public DialogTextField() {
		super();
		this.setFont(new Font(GUIConstants.INPUT_FONT, Font.PLAIN, GUIConstants.INPUT_FONT_SIZE));
		this.setColumns(DialogConstants.TEXT_FIELD_WIDTH);
	}
	
	@Override
	public String getComponentValue() {
		return this.getText();
	}

}
