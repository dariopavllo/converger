package org.converger.userinterface.gui.dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.converger.controller.Field;
import org.converger.controller.FrameworkOperation;
import org.converger.controller.SelectionField;

/**
 * A dialog form used by the application to communicate with the user.
 * A dialog requires a list of field, every field represents a single request from the 
 * application to the user. The dialog is built automatically according to the number and the type of fields 
 * passed to the constructor method.
 * @author Gabriele Graffieti
 */
@SuppressWarnings("serial")
public class Dialog extends JDialog {
	
	private final Map<Field, DialogComponent> map = new HashMap<>();
	
	/**
	 * Constructs dynamically the dialog, according to the type and the number of fields.
	 * @param parent the parent frame of the dialog
	 * @param operation the operation required by the user.
	 * @param fields a list of field used to construct the dialog
	 * @param index the index of the selected expression
	 */
	public Dialog(final JFrame parent, final FrameworkOperation operation, final List<Field> fields, final int index) {
		super(parent, true);
		this.setTitle(operation.getName() + " #" + (index + 1));
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.getContentPane().setLayout(new BorderLayout(DialogConstants.DEFAULT_BORDER, DialogConstants.DEFAULT_BORDER));
		
		final JPanel contentPanel = new JPanel(new GridLayout(0, 2));
		this.getContentPane().add(contentPanel, BorderLayout.CENTER);
		fields.forEach(f -> {
			contentPanel.add(wrapperPanel(new DialogLabel(f.getName()), FlowLayout.RIGHT));
			map.put(f, createComponent(f));
			contentPanel.add(wrapperPanel(map.get(f), FlowLayout.LEFT));
		});
		
		final JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		this.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
		
		final JButton okButton = new JButton("Ok");
		okButton.addActionListener(e -> {
			map.forEach((f, c) -> f.setValue(c.getComponentValue()));
			try {
				operation.execute(index, fields);
				this.dispose();
			} catch (Exception ex) {
				new ErrorDialog(parent, ex.getMessage());
			}
		});
		buttonsPanel.add(okButton);
		
		final JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(e->this.dispose());
		buttonsPanel.add(cancelButton);
		
		this.setResizable(false);
		this.pack();
		this.setLocationRelativeTo(parent);
		this.setVisible(true);
	}
	
	private static DialogComponent createComponent(final Field field) {
		switch (field.getType()) {
			case EXPRESSION : return new DialogTextField(field.getValue());
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