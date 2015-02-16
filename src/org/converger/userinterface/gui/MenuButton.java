package org.converger.userinterface.gui;

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
	}
	
	public enum FileItem implements MenuItem {
		NEW("New", "Nuovo File") {

			@Override
			public void clickEvent(GUI gui) {
				// TODO Auto-generated method stub
				
			}
			
		}, 
		OPEN("Open", "Apri File") {

			@Override
			public void clickEvent(GUI gui) {
				// TODO Auto-generated method stub
				
			}
			
		}, 
		SAVE("Save", "Salva File") {

			@Override
			public void clickEvent(GUI gui) {
				// TODO Auto-generated method stub
				
			}
			
		},
		EXIT("Exit", "Exit") {

			@Override
			public void clickEvent(GUI gui) {
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
		
		public abstract void clickEvent(GUI gui);
		
	}
	
	public enum EditItem implements MenuItem {
		EDITEXP("Edit expression", "Modifica expr"), 
		DELETEEXP("Delete expression", "Cancella expr");

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
		
	}
	
	public enum SolveItem implements MenuItem {
		VARIABLESUB("Variable substitution", "Sostituisci variabile"), 
		SOLVE("Solve equation", "risolvi equazione"),
		APPROXIMATE("Approximate", "Approssima");

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
		
	}
	
	public enum CalculusItem implements MenuItem {
		DERIVE("Differenziate", "Deriva"), 
		DEFINT("Integrate", "Integrale definito");

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
		
	}
	
	public enum HelpItem implements MenuItem {
		GUIDE("Guide", "Guida"), 
		ABOUTUS("About us", "Su di noi, nemmeno una nuvolaa");

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
		
	}
}
