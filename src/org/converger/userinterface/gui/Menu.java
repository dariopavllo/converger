package org.converger.userinterface.gui;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.converger.userinterface.gui.MenuButton.MenuItem;

/** 
 * Builds a menu for the application gui.
 * @author Gabriele Graffieti
 */
public class Menu {
	
	private final JMenuBar menuBar = new JMenuBar();
	
	/**
	 * Constructs a new menu.
	 * @param gui the gui related to this menu.
	 */
	public Menu(final GUI gui) {
		for (final MenuButton b : MenuButton.values()) {
			final JMenu menuButton = new JMenu(b.getName());
			this.menuBar.add(menuButton);
			for (final MenuItem i : b.getItems()) {
				final JMenuItem menuItem = new JMenuItem(i.getName());
				menuItem.addActionListener(e -> i.clickEvent(gui));
				menuButton.add(menuItem);
			}
		}
	
	}
	
	/**
	 * Returns the menu.
	 * @return a fully built menu.
	 */
	public JMenuBar getMenu() {
		return this.menuBar;
	}
}
