package org.converger.userinterface.gui;


/**
 * An enumeration of buttons placed in the header section of the GUI.
 * Every button has its own event.
 * @author Gabriele Graffieti
 *
 */
public enum HeaderButtons {
	/** New file request. */
	NEW("Nuovo", "Nuovo") {

		@Override
		public void clickEvent(final GUI gui) {
			MenuButton.FileItem.NEW.clickEvent(gui);
		}
	},
	/** Open file request. */
	OPEN("Apri", "Apri") {

		@Override
		public void clickEvent(final GUI gui) {
			MenuButton.FileItem.OPEN.clickEvent(gui);
		}
	}, 
	/** Save the current file request. */
	SAVE("Salva", "Salva") {

		@Override
		public void clickEvent(final GUI gui) {
			MenuButton.FileItem.SAVE.clickEvent(gui);
		}
	}, 
	
	/** Delete the selected expression. */
	DELETE("Delete", "Delete") {

		@Override
		public void clickEvent(final GUI gui) {
			MenuButton.EditItem.DELETEEXP.clickEvent(gui);
		}
	},
	
	/** Simplify a given expression. */
	SIMPLIFY("Semplifica", "Semplifica espressione") {

		@Override
		public void clickEvent(final GUI gui) {
			MenuButton.SolveItem.SIMPLIFY.clickEvent(gui);
		}
	},
	/** Substitute variables in a given expression. */
	SUBSTITUTE("Sostituisci", "Sostituisci espressione") {

		@Override
		public void clickEvent(final GUI gui) {
			MenuButton.SolveItem.VARIABLESUB.clickEvent(gui);
		}
	},
	/** Evaluate a given expression in a given point. */
	EVALUATE("Valuta", "Valuta espressione") {

		@Override
		public void clickEvent(final GUI gui) {
			MenuButton.SolveItem.EVALUATE.clickEvent(gui);
		}
	},
	/** Derive a given expression. */
	DIFFERENTIATE("Derivata", "Deriva") {

		@Override
		public void clickEvent(final GUI gui) {
			MenuButton.CalculusItem.DIFFERENTIATE.clickEvent(gui);
		}
	},
	/** Plot a given expression. */
	PLOT("Grafica", "Grafica") {

		@Override
		public void clickEvent(final GUI gui) {
			// TODO Auto-generated method stub
			
		}
	},
	/** Help request. */
	HELP("Aiuto", "Aiuto") {

		@Override
		public void clickEvent(final GUI gui) {
			// TODO Auto-generated method stub
			
		}
	};
	
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
	
	/**
	 * The method called when the button is clicked.
	 * @param gui the parent GUI of the button.
	 */
	public abstract void clickEvent(GUI gui);
	
}
