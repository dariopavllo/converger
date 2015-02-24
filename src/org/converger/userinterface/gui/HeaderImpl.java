package org.converger.userinterface.gui;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.Dimension;
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
		
		for (final HeaderButtons b : HeaderButtons.values()) {
			final JButton button  = new JButton();
			button.setIcon(new ImageIcon(HeaderImpl.class.getResource(b.getIconPath())));
			button.setToolTipText(b.getName());
			button.setPreferredSize(new Dimension(GUIConstants.HEADER_BUTTON_DIMENSION, GUIConstants.HEADER_BUTTON_DIMENSION));
			button.addActionListener(e -> b.clickEvent(gui));
			this.mainPanel.add(button);
		}
	}

	@Override
	public JPanel getMainPanel() {
		return this.mainPanel;
	}

}
