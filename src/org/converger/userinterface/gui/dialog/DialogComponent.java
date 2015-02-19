package org.converger.userinterface.gui.dialog;

/**
 * Represents a generic dialog component. Every dialog component have to implement this interface.
 * @author Gabriele Graffieti
 *
 */
public interface DialogComponent {
	/**
	 * This method is called when you need to know the value of a component. 
	 * The component value is the value of a component after the user setting. 
	 * @return the value of the component in string format.
	 */
	String getComponentValue();
}
