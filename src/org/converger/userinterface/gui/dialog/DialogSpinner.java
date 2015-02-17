package org.converger.userinterface.gui.dialog;

import java.awt.Dimension;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
 * Extends a JSpinner and implements the DialogComponent interface.
 * This spinner represent a range of positive integers, from 1 to infinite.
 * The user can choose one of these values.
 * This component is used in the dialog.
 * @author Gabriele Graffieti
 *
 */
@SuppressWarnings("serial")
public class DialogSpinner extends JSpinner implements DialogComponent {

	/**
	 * Create the DialogSpinner, set its property and its dimension.
	 */
	public DialogSpinner() {
		super();
		final SpinnerNumberModel spinnerModel = new SpinnerNumberModel();
		spinnerModel.setMinimum(1); // only positive numbers
		spinnerModel.setValue(1); // 1 is the lower bound
		this.setModel(spinnerModel);
		this.setPreferredSize(new Dimension(DialogConstants.SPINNER_WIDTH, this.getPreferredSize().height));
	}
	
	@Override
	public String getComponentValue() {
		return this.getValue().toString();
	}
}
