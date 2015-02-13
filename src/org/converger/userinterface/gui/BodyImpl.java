package org.converger.userinterface.gui;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import java.awt.GridLayout;

/**
 * Creates the middle part of the gui, with the latex text visualizer.
 * @author Gabriele Graffieti
 */
public class BodyImpl implements Body {

	private final JPanel mainPanel;
	
	/**
	 * Create the body.
	 */
	public BodyImpl() {
		this.mainPanel = new JPanel();
		this.mainPanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		final JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		this.mainPanel.add(textArea);
	}

	@Override
	public JPanel getMainPanel() {
		return this.mainPanel;
	}
}
