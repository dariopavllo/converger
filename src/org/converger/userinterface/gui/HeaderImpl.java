package org.converger.userinterface.gui;

import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.FlowLayout;

import javax.swing.border.EtchedBorder;

import org.converger.userinterface.utility.EObserver;
import org.converger.userinterface.utility.ESource;

/**
 * Creates the high part of the gui, with shortcut buttons for manage environment or expressions.
 * @author Gabriele Graffieti
 */
public class HeaderImpl extends ESource<String> implements Header {
	
	private final JPanel mainPanel;
	/**
	 * Construct the header.
	 * @param obs the observer of the header.
	 */
	public HeaderImpl(final EObserver<String> obs) {
		this.addEObserver(obs);
		
		this.mainPanel = new JPanel();
		this.mainPanel.setLayout(new FlowLayout(FlowLayout.LEFT, GUIConstants.getDefaultMargin(), 
				GUIConstants.getDefaultMargin()));
		this.mainPanel.setBorder(new EtchedBorder());
		
		final JButton[] buttons = new JButton[HeaderButtons.values().length];
		
		for (final HeaderButtons b : HeaderButtons.values()) {
			buttons[b.ordinal()] = new JButton(b.getName());
			/* ************************************************** EVENTO OBSERVER ************************************************************************ */
			buttons[b.ordinal()].addActionListener(e -> this.notifyEObservers(b.getMessage()));
			//buttons[b.ordinal()].addActionListener(e -> b.clickEvent(gui));
			this.mainPanel.add(buttons[b.ordinal()]);
		}
	}

	@Override
	public JPanel getMainPanel() {
		return this.mainPanel;
	}

}
