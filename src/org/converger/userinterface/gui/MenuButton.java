package org.converger.userinterface.gui;

import org.converger.controller.Controller;
import org.converger.controller.FrameworkOperation;

/**
 * Represents all the application menu voices, with its names and items.
 * @author Gabriele Graffieti
 */
public enum MenuButton {
	/** File voice on menu. */
	FILE("File", FileItem.values()), 
	/** Edit voice on menu. */
	EDIT("Edit", EditItem.values()),
	/** Solve voice on menu, it contains equations functions. */
	SOLVE("Solve", SolveItem.values()),
	/** Calculus voice on menu it contains calculus functions. */
	CALCULUS("Calculus", CalculusItem.values());
	
	private final String name;
	private final MenuItem[] items;
	
	private MenuButton(final String btnName, final MenuItem... btnItems) {
		this.name = btnName;
		this.items = btnItems.clone();
	}
	/**
	 * Returns the name of the menu voice, which will be sgown on the gui.
	 * @return the name of the manu voice.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Returns an array of MenuItems, which represent all the sub-voices of a menu voice.
	 * @return an array sub-menu voices.
	 */
	public MenuItem[] getItems() {
		return this.items.clone();
	}
	
	/**
	 * Represent a single menu item, with its name and its message.
	 * @author Gabriele Graffieti
	 */
	interface MenuItem {
		/** 
		 * Return the name of the menu item, which will be shown on the gui.
		 * @return a string representing the name of the menu item.
		 */
		String getName();
		
		/**
		 * The method called when the item voice is clicked.
		 * @param gui the parent GUI of the menu item.
		 */
		void clickEvent(GUI gui);
		
		
	}
	
	/**
	 * Represents a collection of menu item placed in the file voice.
	 * @author Gabriele Graffieti
	 */
	public enum FileItem implements MenuItem {
		/** A new empty environment. */
		NEW("New") {

			@Override
			public void clickEvent(final GUI gui) {
				Controller.getController().newEnvironment();
			}
			
		}, 
		/** Open a new file. */
		OPEN("Open") {

			@Override
			public void clickEvent(final GUI gui) {
				Controller.getController().open();
			}
			
		}, 
		/** Save the current environment in a file. */
		SAVE("Save") {

			@Override
			public void clickEvent(final GUI gui) {
				Controller.getController().save();
			}
			
		},
		/** Exit from the application. */
		EXIT("Exit") {

			@Override
			public void clickEvent(final GUI gui) {
				Controller.getController().close();
			}
			
		};

		private String name;
		
		private FileItem(final String itemName) {
			this.name = itemName;
		}
		
		@Override
		public String getName() {
			return this.name;
		}
		
		@Override
		public abstract void clickEvent(final GUI gui);
		
	}
	
	/**
	 * Represents a collection of menu item placed in the edit voice.
	 * @author Gabriele Graffieti
	 */
	public enum EditItem implements FrameworkOperationMenuItem {
		
		/** Edit an expression. */
		EDITEXP("Edit expression") {
			@Override
			public void clickEvent(final GUI gui) {
				this.executeFrameworkOperation(gui, FrameworkOperation.EDIT);
			}
		}, 
		/** Delete an expression. */
		DELETEEXP("Delete expression") {
			@Override
			public void clickEvent(final GUI gui) {
				Controller.getController().deleteExpression();
			}
		};

		private String name;
		
		private EditItem(final String itemName) {
			this.name = itemName;
		}
		
		@Override
		public String getName() {
			return this.name;
		}
		
		@Override
		public abstract void clickEvent(final GUI gui);		
	}
	
	/**
	 * Represents a collection of menu item placed in the solve voice.
	 * @author Gabriele Graffieti
	 */
	public enum SolveItem implements FrameworkOperationMenuItem {
		/** Variable substitution. */
		VARIABLESUB("Variable substitution") {
			@Override
			public void clickEvent(final GUI gui) {
				this.executeFrameworkOperation(gui, FrameworkOperation.SUBSTITUTE);
			}
		}, 
		
		/** simplify an expression. */
		SIMPLIFY("Simplify expression") {

			@Override
			public void clickEvent(final GUI gui) {
				this.executeFrameworkOperation(gui, FrameworkOperation.SIMPLIFY);
			}
			
		},
		/** Solve an equation. */
		SOLVE("Solve equation") {
			@Override
			public void clickEvent(final GUI gui) {
				this.executeFrameworkOperation(gui, FrameworkOperation.SOLVE);
			}
		},
		/** Evaluate an expression. */
		EVALUATE("Evaluate") {
			@Override
			public void clickEvent(final GUI gui) {
				this.executeFrameworkOperation(gui, FrameworkOperation.EVALUATE);
			}
		};

		private String name;
		
		private SolveItem(final String itemName) {
			this.name = itemName;
		}
		
		@Override
		public String getName() {
			return this.name;
		}
		
		@Override
		public abstract void clickEvent(final GUI gui);		
	}
	
	/**
	 * Represents a collection of menu item placed in the calculus voice.
	 * @author Gabriele Graffieti
	 */
	public enum CalculusItem implements FrameworkOperationMenuItem {
		/** Find the derivative of an expression. */
		DIFFERENTIATE("Differentiate") {
			@Override
			public void clickEvent(final GUI gui) {
				this.executeFrameworkOperation(gui, FrameworkOperation.DIFFERENTIATE);
				
			}
		}, 
		/** Calculates the definite integral of an expression between 2 points. */
		INTEGRATE("Integrate") {
			@Override
			public void clickEvent(final GUI gui) {
				this.executeFrameworkOperation(gui, FrameworkOperation.INTEGRATE);
			}
		},
		/** calculate the taylor series of an expression at the given point. */
		TAYLOR("Taylor series") {

			@Override
			public void clickEvent(final GUI gui) {
				this.executeFrameworkOperation(gui, FrameworkOperation.TAYLOR);
			}
			
		};

		private String name;
		
		private CalculusItem(final String itemName) {
			this.name = itemName;
		}
		
		@Override
		public String getName() {
			return this.name;
		}
		
		@Override
		public abstract void clickEvent(final GUI gui);
	}
}
