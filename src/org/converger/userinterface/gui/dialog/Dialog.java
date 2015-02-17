package org.converger.userinterface.gui.dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.converger.controller.Field;
import org.converger.controller.FrameworkOperation;
import org.converger.controller.SelectionField;

public class Dialog extends JDialog {
	
	private final Map<Field, DialogComponent> map = new HashMap<>();
	
	public Dialog(final JFrame frame, final FrameworkOperation operation, final List<Field> fields) {
		super(frame, true);
		this.setTitle(operation.getName());
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		// set dimension ??
		this.getContentPane().setLayout(new BorderLayout(20, 20));
		
		final JPanel contentPanel = new JPanel(new GridLayout(0, 2));
		this.getContentPane().add(contentPanel, BorderLayout.CENTER);
		fields.forEach(f -> {
			contentPanel.add(wrapperPanel(new DialogLabel(f.getName()), FlowLayout.RIGHT));
			map.put(f, createComponent(f));
			contentPanel.add(wrapperPanel(map.get(f), FlowLayout.LEFT));
		});
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
	}
	
	private static DialogComponent createComponent(final Field field) {
		switch (field.getType()) {
			case EXPRESSION : return new DialogTextField();
			case NUMERICAL : return new DialogSpinner();
			case SELECTION : return new DialogComboBox(((SelectionField) field).getAllowedValues());
			default : return null;
		}
	}
	
	private static JPanel wrapperPanel(final DialogComponent component, final int orientation) {
		final JPanel panel = new JPanel(new FlowLayout(orientation));
		panel.add((JComponent) component);
		return  panel;
	}
	
}
