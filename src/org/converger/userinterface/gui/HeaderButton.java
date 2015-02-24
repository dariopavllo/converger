package org.converger.userinterface.gui;

import org.converger.controller.Controller;


/**
 * An enumeration of buttons placed in the header section of the GUI.
 * Every button has its own event.
 * @author Gabriele Graffieti
 *
 */
public enum HeaderButton {
	/** New file request. */
	NEW("New", "/org/converger/resources/icons/header/new.png") {

		@Override
		public void clickEvent(final GUI gui) {
			MenuButton.FileItem.NEW.clickEvent(gui);
		}
	},
	/** Open file request. */
	OPEN("Open", "/org/converger/resources/icons/header/open.png") {

		@Override
		public void clickEvent(final GUI gui) {
			MenuButton.FileItem.OPEN.clickEvent(gui);
		}
	}, 
	/** Save the current file request. */
	SAVE("Save", "/org/converger/resources/icons/header/save.png") {

		@Override
		public void clickEvent(final GUI gui) {
			MenuButton.FileItem.SAVE.clickEvent(gui);
		}
	}, 
	
	/** Delete the selected expression. */
	DELETE("Delete", "/org/converger/resources/icons/header/delete.png") {

		@Override
		public void clickEvent(final GUI gui) {
			MenuButton.EditItem.DELETEEXP.clickEvent(gui);
		}
	},
	
	/** Simplify a given expression. */
	SIMPLIFY("Simplify", "/org/converger/resources/icons/header/simplify.png") {

		@Override
		public void clickEvent(final GUI gui) {
			MenuButton.SolveItem.SIMPLIFY.clickEvent(gui);
		}
	},
	/** Substitute variables in a given expression. */
	SUBSTITUTE("Substitute", "/org/converger/resources/icons/header/substitute.png") {

		@Override
		public void clickEvent(final GUI gui) {
			MenuButton.SolveItem.VARIABLESUB.clickEvent(gui);
		}
	},
	/** Evaluate a given expression in a given point. */
	EVALUATE("Evaluate", "/org/converger/resources/icons/header/evaluate.png") {

		@Override
		public void clickEvent(final GUI gui) {
			MenuButton.SolveItem.EVALUATE.clickEvent(gui);
		}
	},
	/** Derive a given expression. */
	DIFFERENTIATE("Differentiate", "/org/converger/resources/icons/header/differentiate.png") {

		@Override
		public void clickEvent(final GUI gui) {
			MenuButton.CalculusItem.DIFFERENTIATE.clickEvent(gui);
		}
	},
	/** Plot a given expression. */
	PLOT("Plot", "/org/converger/resources/icons/header/plot.png") {

		@Override
		public void clickEvent(final GUI gui) {
			Controller.getController().plot();
		}
	};
	
	private final String name;
	private final String path;
	
	private HeaderButton(final String btnName, final String btnIconPath) {
		this.name = btnName;
		this.path = btnIconPath;
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
	public String getIconPath() {
		return this.path;
	}
	
	/**
	 * The method called when the button is clicked.
	 * @param gui the parent GUI of the button.
	 */
	public abstract void clickEvent(GUI gui);
	
}
