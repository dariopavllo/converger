package org.converger.userinterface.gui;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.converger.userinterface.gui.MenuButton.MenuItem;
import org.converger.userinterface.utility.EObserver;
import org.converger.userinterface.utility.ESource;

/** 
 * Builds a menu for the application gui.
 * @author Gabriele Graffieti
 */
public class Menu extends ESource<String> {
	
	private final JMenuBar menuBar = new JMenuBar();
	
	/**
	 * Constructs a new menu.
	 * @param obs the observer of the menu.
	 */
	public Menu(final EObserver<String> obs) {
		this.addEObserver(obs);
	
		for (final MenuButton b : MenuButton.values()) {
			final JMenu tmp = new JMenu(b.getName());
			this.menuBar.add(tmp);
			for (final MenuItem i : b.getItems()) {
				final JMenuItem tmp2 = new JMenuItem(i.getName());
				tmp2.addActionListener(e -> this.notifyEObservers(i.getMessage()));
				tmp.add(tmp2);
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
