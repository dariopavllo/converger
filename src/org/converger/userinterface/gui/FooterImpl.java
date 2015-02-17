package org.converger.userinterface.gui;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;

import javax.swing.JButton;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.Font;

import javax.swing.JSeparator;

import org.converger.controller.Controller;

import java.awt.SystemColor;

/**
 * Creates the bottom part of the gui, with utility buttons and the line input.
 * @author Gabriele Graffieti
 */
public class FooterImpl implements Footer {
	private final JPanel mainPanel;
	private final JTextField inputLine;
	
	
	/**
	 * Create the footer.
	 */
	public FooterImpl() {
		this.mainPanel = new JPanel();
		this.mainPanel.setLayout(new BorderLayout(GUIConstants.DEFAULT_MARGIN, 
				GUIConstants.DEFAULT_MARGIN));
		this.mainPanel.setBorder(new EtchedBorder());
		final JPanel inputPanel = new JPanel();
		this.mainPanel.add(inputPanel, BorderLayout.NORTH);
		final GridBagLayout gbl = new GridBagLayout();
		gbl.columnWeights = new double[]{Double.MIN_VALUE, Double.MIN_VALUE, 1.0}; // the last column is full screen
		inputPanel.setLayout(gbl);
		
		
		final GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL; // components fill the cell in horizontal
		gbc.anchor = GridBagConstraints.WEST; 
		gbc.insets = new Insets(GUIConstants.DEFAULT_MARGIN, GUIConstants.DEFAULT_MARGIN, 
				GUIConstants.DEFAULT_MARGIN, GUIConstants.DEFAULT_MARGIN);
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		final JButton inputButton = new JButton(">");
		/* ************************************************** EVENTO OBSERVER ************************************************************************ */
		inputButton.addActionListener(e -> Controller.getController().addExpression(this.inputLine.getText()));
		inputPanel.add(inputButton, gbc);
		
		gbc.gridx++; // next row
		
		final JButton clearButton = new JButton("X");
		inputPanel.add(clearButton, gbc);
		clearButton.addActionListener(e -> this.inputLine.setText(""));
		clearButton.setEnabled(false);
		
		gbc.gridx++;
		
		this.inputLine = new JTextField();
		this.inputLine.setFont(new Font(GUIConstants.INPUT_FONT, Font.PLAIN, GUIConstants.INPUT_FONT_SIZE));
		inputPanel.add(inputLine, gbc);
		this.inputLine.setColumns(10);
		this.inputLine.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(final DocumentEvent arg0) {
				// not required
			}
			@Override
			public void insertUpdate(final DocumentEvent arg0) {
				clearButton.setEnabled(true);
				if (inputLine.getText().equals("")) {
					clearButton.setEnabled(false);
				}
				
			}
			@Override
			public void removeUpdate(final DocumentEvent arg0) {
				clearButton.setEnabled(true);
				if (inputLine.getText().equals("")) {
					clearButton.setEnabled(false);
				}
			}
		});
		
		final JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new FlowLayout(FlowLayout.LEFT, GUIConstants.DEFAULT_MARGIN, 
				GUIConstants.DEFAULT_MARGIN));
		this.mainPanel.add(btnPanel, BorderLayout.SOUTH);
		
		final JSeparator separator = new JSeparator();
		separator.setForeground(SystemColor.activeCaptionBorder);
		this.mainPanel.add(separator, BorderLayout.CENTER);
		
		final JButton[] utility = new JButton[UtilityButtons.values().length];
		
		for (final UtilityButtons b : UtilityButtons.values()) {
			utility[b.ordinal()] = new JButton(b.getName());
			utility[b.ordinal()].addActionListener(e -> {
				this.inputLine.setText(this.inputLine.getText() + b.getSymbol());
				this.inputLine.requestFocusInWindow();
			});
			btnPanel.add(utility[b.ordinal()]);
		}

	}
	
	@Override
	public JPanel getMainPanel() {
		return this.mainPanel;
	}
}
