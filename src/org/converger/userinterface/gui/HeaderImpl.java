package org.converger.userinterface.gui;

import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.FlowLayout;

import javax.swing.border.EtchedBorder;

/**
 * Creates the high part of the gui, with shortcut buttons for manage environment or expressions.
 * @author Gabriele Graffieti
 */
public class HeaderImpl implements Header {
	
	private final JPanel mainPanel;
	/**
	 * Construct the header.
	 * @param gui the parent gui of the header
	 */
	public HeaderImpl(final GUI gui) {
		this.mainPanel = new JPanel();
		this.mainPanel.setLayout(new FlowLayout(FlowLayout.LEFT, GUIConstants.DEFAULT_MARGIN, 
				GUIConstants.DEFAULT_MARGIN));
		this.mainPanel.setBorder(new EtchedBorder());
		
		final JButton[] buttons = new JButton[HeaderButtons.values().length];
		
		for (final HeaderButtons b : HeaderButtons.values()) {
			buttons[b.ordinal()] = new JButton(b.getName());
			buttons[b.ordinal()].addActionListener(e -> b.clickEvent(gui));
			this.mainPanel.add(buttons[b.ordinal()]);
		}
	}

	@Override
	public JPanel getMainPanel() {
		return this.mainPanel;
	}

}
