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
	CALCULUS("Calculus", CalculusItem.values()),
	/** Help voice on menu. */
	HELP("Help", HelpItem.values());
	
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
		 * Return the message of a menu item, the message is the way which the menu can communicate user selections.
		 * @return a string representing the message of the menu item.
		 */
		String getIconPath();
		
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
		NEW("New", "Nuovo File") {

			@Override
			public void clickEvent(final GUI gui) {
				// TODO Auto-generated method stub
				
			}
			
		}, 
		/** Open a new file. */
		OPEN("Open", "Apri File") {

			@Override
			public void clickEvent(final GUI gui) {
				// TODO Auto-generated method stub
				
			}
			
		}, 
		/** Save the current environment in a file. */
		SAVE("Save", "Salva File") {

			@Override
			public void clickEvent(final GUI gui) {
				// TODO Auto-generated method stub
				
			}
			
		},
		/** Exit from the application. */
		EXIT("Exit", "Exit") {

			@Override
			public void clickEvent(final GUI gui) {
				// TODO Auto-generated method stub
				
			}
			
		};

		private String name;
		private String iconPath;
		
		private FileItem(final String itemName, final String itemIconPath) {
			this.name = itemName;
			this.iconPath = itemIconPath;
		}
		
		@Override
		public String getName() {
			return this.name;
		}

		@Override
		public String getIconPath() {
			return this.iconPath;
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
		EDITEXP("Edit expression", "") {
			@Override
			public void clickEvent(final GUI gui) {
				this.executeFrameworkOperation(gui, FrameworkOperation.EDIT);
			}
		}, 
		/** Delete an expression. */
		DELETEEXP("Delete expression", "") {
			@Override
			public void clickEvent(final GUI gui) {
				Controller.getController().deleteExpression();
			}
		};

		private String name;
		private String iconPath;
		
		private EditItem(final String itemName, final String itemIconPath) {
			this.name = itemName;
			this.iconPath = itemIconPath;
		}
		
		@Override
		public String getName() {
			return this.name;
		}

		@Override
		public String getIconPath() {
			return this.iconPath;
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
		VARIABLESUB("Variable substitution", "") {
			@Override
			public void clickEvent(final GUI gui) {
				this.executeFrameworkOperation(gui, FrameworkOperation.SUBSTITUTE);
			}
		}, 
		
		/** simplify an expression. */
		SIMPLIFY("Simplify expression", "") {

			@Override
			public void clickEvent(final GUI gui) {
				this.executeFrameworkOperation(gui, FrameworkOperation.SIMPLIFY);
			}
			
		},
		/** Solve an equation. */
		SOLVE("Solve equation", "") {
			@Override
			public void clickEvent(final GUI gui) {
				this.executeFrameworkOperation(gui, FrameworkOperation.SOLVE);
			}
		},
		/** Evaluate an expression. */
		EVALUATE("Evaluate", "") {
			@Override
			public void clickEvent(final GUI gui) {
				this.executeFrameworkOperation(gui, FrameworkOperation.EVALUATE);
			}
		};

		private String name;
		private String iconPath;
		
		private SolveItem(final String itemName, final String itemIconPath) {
			this.name = itemName;
			this.iconPath = itemIconPath;
		}
		
		@Override
		public String getName() {
			return this.name;
		}

		@Override
		public String getIconPath() {
			return this.iconPath;
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
		DIFFERENTIATE("Differentiate", "") {
			@Override
			public void clickEvent(final GUI gui) {
				this.executeFrameworkOperation(gui, FrameworkOperation.DIFFERENTIATE);
				
			}
		}, 
		/** Calculates the definite integral of an expression between 2 points. */
		INTEGRATE("Integrate", "") {
			@Override
			public void clickEvent(final GUI gui) {
				this.executeFrameworkOperation(gui, FrameworkOperation.INTEGRATE);
			}
		},
		/** calculate the taylor series of an expression at the given point. */
		TAYLOR("Taylor series", "") {

			@Override
			public void clickEvent(final GUI gui) {
				this.executeFrameworkOperation(gui, FrameworkOperation.TAYLOR);
			}
			
		};

		private String name;
		private String iconPath;
		
		private CalculusItem(final String itemName, final String itemIconPath) {
			this.name = itemName;
			this.iconPath = itemIconPath;
		}
		
		@Override
		public String getName() {
			return this.name;
		}

		@Override
		public String getIconPath() {
			return this.iconPath;
		}
		
		@Override
		public abstract void clickEvent(final GUI gui);
	}
	
	/**
	 * Represents a collection of menu item placed in the help voice.
	 * @author Gabriele Graffieti
	 */
	public enum HelpItem implements MenuItem {
		/** Open the guide of the application. */
		GUIDE("Guide", "") {
			@Override
			public void clickEvent(final GUI gui) {
				// TODO Auto-generated method stub
				
			}
		}, 
		
		/** Open the about us page. */
		ABOUTUS("About us", "") {
			@Override
			public void clickEvent(final GUI gui) {
				// TODO Auto-generated method stub
				
			}
		};

		private String name;
		private String iconPath;
		
		private HelpItem(final String itemName, final String itemIconPath) {
			this.name = itemName;
			this.iconPath = itemIconPath;
		}
		
		@Override
		public String getName() {
			return this.name;
		}

		@Override
		public String getIconPath() {
			return this.iconPath;
		}
		
		@Override
		public abstract void clickEvent(final GUI gui);
		
	}
}
