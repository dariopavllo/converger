package org.converger.userinterface.gui.dialog;

import java.util.Set;

import javax.swing.JComboBox;

/**
 * Extends a JComboBox of Strings and implements the DialogComponent interface.
 * This component is used in the dialog.
 * @author Gabriele Graffieti
 */
public class DialogComboBox extends JComboBox<String> implements DialogComponent {
	
	private static final long serialVersionUID = -3907762002981643058L;

	/**
	 * Create the DialogComboBox and add to it the set of String passed.
	 * @param values the set of values of the DialogComboBox.
	 */
	public DialogComboBox(final Set<String> values) {
		super();
		values.forEach(s->this.addItem(s));
	}
	
	@Override
	public String getComponentValue() {
		if (this.getItemCount() > 0) {
			return this.getSelectedItem().toString();
		} else {
			return "";
		}
	}

}
