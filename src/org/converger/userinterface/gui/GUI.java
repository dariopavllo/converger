package org.converger.userinterface.gui;

import java.awt.BorderLayout;
import java.util.List;
import java.util.Optional;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.converger.controller.Field;
import org.converger.controller.FrameworkOperation;
import org.converger.controller.exception.NoElementSelectedException;
import org.converger.userinterface.UserInterface;
import org.converger.userinterface.gui.dialog.Dialog;
import org.converger.userinterface.gui.dialog.ErrorDialog;

/**
 * Represent a graphical user interface for the application.
 * This class implements the UserInterface interface, and provides all the required functions
 * for manage the graphic part of the application. 
 * This implementation is made with swing and includes many user-friendly facilities
 * like buttons for write mathematical expressions or for calculate and solve equations.
 * It also includes a LaTex representation of mathematical formulas.
 * @author Gabriele Graffieti
 */
public class GUI implements UserInterface {

	private final JFrame frame;
	private final Header header;
	private final Body body;
	private final Footer footer;
	
	/**
	 * Construct a new graphic user interface.
	 * @param name the name shown in the title bar.
	 */
	public GUI(final String name) {
		this.header = new HeaderImpl(this);
		this.body = new BodyImpl();
		this.footer = new FooterImpl();
		
		this.frame = new JFrame(name);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setSize(GUIConstants.PREFERRED_WIDTH, GUIConstants.PREFERRED_HEIGHT);
		this.buildGUI();
	}
	
	@Override
	public void show() {
		this.frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Full screen
		this.frame.setVisible(true);
		
	}

	@Override
	public void printExpression(final String exp) {
		this.body.drawNewExpression(exp);
	}

	@Override
	public void error(final String description) {
		new ErrorDialog(this.frame, description);
	}

	@Override
	public boolean yesNoQuestion() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Optional<String> save() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<String> open() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getSelectedExpression() throws NoElementSelectedException {
		return this.body.getSelected();
	}

	@Override
	public Optional<String> selectVariable() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void removeExpression(final int index) {
		this.body.deleteExpression(index);
	}
	
	@Override
	public void editExpression(final int index, final String newExpression) {
		this.body.editExpression(index, newExpression);
	}
	
	@Override
	public void showDialog(final FrameworkOperation operation, final List<Field> fields, final int index) {
		new Dialog(this.frame, operation, fields, index);
	}
	
	private void buildGUI() {
		this.frame.setJMenuBar(new Menu(this).getMenu());
		final JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout(GUIConstants.DEFAULT_MARGIN, 
				GUIConstants.DEFAULT_MARGIN));
		mainPanel.setBorder(new EmptyBorder(GUIConstants.DEFAULT_BORDER, GUIConstants.DEFAULT_BORDER, 
				GUIConstants.DEFAULT_BORDER, GUIConstants.DEFAULT_BORDER));
		
		mainPanel.add(this.header.getMainPanel(), BorderLayout.NORTH);
		mainPanel.add(this.body.getMainPanel(), BorderLayout.CENTER);
		mainPanel.add(this.footer.getMainPanel(), BorderLayout.SOUTH);
		
		this.frame.getContentPane().add(mainPanel);
	}
}
