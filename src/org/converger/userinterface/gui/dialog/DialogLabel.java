package org.converger.userinterface.gui.dialog;

import java.awt.Font;

import javax.swing.JLabel;

import org.converger.userinterface.gui.GUIConstants;

/**
 * Extends a JLabel and implements the DialogComponent interface.
 * This component is used in the dialog.
 * The getCoponentValue method throws an UnsopportedOperationException
 * @author Gabriele Graffieti
 */
@SuppressWarnings("serial")
public class DialogLabel extends JLabel implements DialogComponent {

	/**
	 * Constructs a new DalogLabel with the given text.
	 * @param text the text of the label
	 */
	public DialogLabel(final String text) {
		super(text);
		this.setFont(new Font(this.getFont().getFontName(), Font.PLAIN, GUIConstants.INPUT_FONT_SIZE));
	}
	
	@Override
	public String getComponentValue() {
		throw new UnsupportedOperationException();
	}

}
