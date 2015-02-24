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
public class DialogTextField extends JTextField implements DialogComponent {

	private static final long serialVersionUID = -2975833711880471257L;

	/**
	 * Create the DialogTextField and set its lenght and its default font and text size.
	 */
	public DialogTextField() {
		super();
		this.setFont(new Font(GUIConstants.INPUT_FONT, Font.PLAIN, GUIConstants.INPUT_FONT_SIZE));
		this.setColumns(DialogConstants.TEXT_FIELD_WIDTH);
	}
	
	/**
	 * Create a DialogTextField width a text set.
	 * @param text the text to be set into the text field.
	 */
	public DialogTextField(final String text) {
		this();
		this.setText(text);
	}
	
	@Override
	public String getComponentValue() {
		return this.getText();
	}

}
