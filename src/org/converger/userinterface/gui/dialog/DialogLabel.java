package org.converger.userinterface.gui.dialog;

import javax.swing.JLabel;

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
	}
	
	@Override
	public String getComponentValue() {
		throw new UnsupportedOperationException();
	}

}
