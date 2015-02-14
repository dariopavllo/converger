package org.converger.userinterface.gui;

import javax.swing.JPanel;


/**
 * Represent any GUI component such footer or header. 
 * @author Gabriele Graffieti 
 */
public interface GUIComponent {
	/**
	 * @return the main panel of the component
	 */
	JPanel getMainPanel();
}
