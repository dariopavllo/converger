package org.converger.userinterface.gui;

/**
 * An enum of buttons placed in the header section of the GUI.
 * Every button has its own event.
 * @author Gabriele Graffieti
 *
 */
public enum HeaderButtons {
	/** New file request. */
	NEW("Nuovo", "Nuovo"),
	/** Open file request. */
	OPEN("Apri", "Apri"), 
	/** Save the current file request. */
	SAVE("Salva", "Salva"), 
	/** Simplify a given expression. */
	SIMPLIFY("Semplifica", "Semplifica espressione"),
	/** Substitute variables in a given expression. */
	SUBSTITUTE("Sostituisci", "Sostituisci espressione"),
	/** Evaluate a given expression in a given point. */
	EVALUATE("Valuta", "Valuta espressione"),
	/** Derive a given expression. */
	DERIVE("Derivata", "Deriva espressione"),
	/** Plot a given expression. */
	PLOT("Grafica", "Grafica"),
	/** Help request. */
	HELP("Aiuto", "Aiuto");
	
	private final String name;
	private final String message;
	
	private HeaderButtons(final String btnName, final String btnMessage) {
		this.name = btnName;
		this.message = btnMessage;
	}
	
	/**
	 * Returns the name of the button given in string format.
	 * @return the name of the button
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Returns the message of the button in string format.
	 * @return the message of the button 
	 */
	public String getMessage() {
		return this.message;
	}
	
}
