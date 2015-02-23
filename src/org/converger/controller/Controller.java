package org.converger.controller; // NOPMD

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.converger.controller.exception.NoElementSelectedException;
import org.converger.controller.utility.EObserver;
import org.converger.controller.utility.ESource;
import org.converger.controller.utility.KeyboardEvent;
import org.converger.framework.CasFramework;
import org.converger.framework.CasManager;
import org.converger.framework.Expression;
import org.converger.framework.SyntaxErrorException;
import org.converger.userinterface.UserInterface;
import org.converger.userinterface.gui.GUI;

/**
 * The controller of the application. 
 * It coordinates the user interface and the framework, offers functions 
 * which are called by the user interface to get framework functionalities.
 * Briefly the controller filters the gui requests and interacts with the framework.
 * The controller is a singleton, and for get the controller instance you can call the 
 * getController method.
 * @author Gabriele Graffieti
 * */
public final class Controller implements EObserver<KeyboardEvent> {

	private static final Controller SINGLETON = new Controller(); 
	private static final int DECIMALS = 7;
	
	private final UserInterface ui;
	private final CasFramework framework;
	private final Environment currentEnvironment;
	
	private Controller() {
		this.ui = new GUI("Converger", this);
		this.framework = CasManager.getSingleton().createFramework();
		this.currentEnvironment = new Environment();
	}
	
	/**
	 * Return the instance of the controller.
	 * @return the instance of the controller.
	 */
	public static Controller getController() {
		return SINGLETON;
	}
	
	/**
	 * Returns the framework of the application.
	 * @return the framework of the application.
	 */
	public CasFramework getFramework() {
		return this.framework;
	}
	
	/**
	 * Get the selected expression to the user interface.
	 * @return the index of the selected expression.
	 * @throws NoElementSelectedException if no expression is selected.
	 */
	public int getSelectedExpressionIndex() throws NoElementSelectedException {
		return this.ui.getSelectedExpression();
	}
	
	/**
	 * Returns the expression at the given index.
	 * @param index the index of the required expression
	 * @return the expression at the given index.
	 */
	public Expression getExpressionAt(final int index) {
		return this.currentEnvironment.getRecordList().get(index).getExpression();
	}
	
	/**
	 * Returns the current environment.
	 * @return the current environment
	 */
	public Environment getEnvironment() {
		return this.currentEnvironment;
	}
	
	/**
	 * Show the user interface to the screen.
	 */
	public void showUI() {
		this.ui.show();
	}
	
	/**
	 * Parse a string to an expression, add it to the current environment and print it in the user interface.
	 * This method is used when a new expression is inserted manually by the user
	 * @param expression a string representation of a mathematical expression.
	 */
	public void addExpression(final String expression) {
		try {
			final Expression exp = this.framework.parse(expression);
			this.addExpression(exp, Optional.empty());
		} catch (SyntaxErrorException e) {
			this.ui.error(e.getMessage());
		}
	}
	
	/**
	 * Add a new expression to the current environment and prints it to the user interface.
	 * @param exp expression to be added.
	 * @param op the operation which generated the numerical value
	 */
	public void addExpression(final Expression exp, final Optional<String> op) {
		final String latexText = this.framework.toLatexText(exp);
		this.currentEnvironment.add(this.getRecordFromExpression(exp, op));
		this.ui.printExpression(latexText, op);
	}
	
	/**
	 * Add a numerical expression to the current environment. A numerical expression is a real number 
	 * given in decimal representation (for example 1.125).
	 * @param number the real number to be added to the current environment
	 * @param op the operation which generated the numerical value
	 */
	public void addNumericalExpression(final Double number, final Optional<String> op) {
		try {
			final Expression exp = this.framework.parse(Double.toString(number));
			String num;
			if (number.isNaN()) {
				num = "Indeterminate";
			} else if (number.isInfinite()) {
				num = Double.toString(number);
			} else {
				num = new BigDecimal(String.valueOf(number)).setScale(DECIMALS, BigDecimal.ROUND_HALF_UP).toPlainString();
			}
			
			this.currentEnvironment.add(new Record(num, num, exp, op));
			this.ui.printExpression(num, op);
		} catch (SyntaxErrorException e) {
			this.ui.error(e.getMessage());
		}
	}
	
	/**
	 * Substitute the expression at the given index with the new expression passed, and 
	 * notify it at the user interface.
	 * @param index the index of the expression to be edited
	 * @param newExpression the new expression.
	 */
	public void editExpression(final int index, final Expression newExpression)  {
		this.currentEnvironment.modifyExpression(index, this.getRecordFromExpression(newExpression, Optional.empty())); // Optional.empty because if I edit an expression this expression will lost its original meaning
		this.ui.editExpression(index, this.framework.toLatexText(newExpression));
	}
	
	/**
	 * Delete an expression from the current environment and sequentially from the user interface.
	 */
	public void deleteExpression() {
		try {
			final int expIndex = this.getSelectedExpressionIndex();
			this.currentEnvironment.delete(expIndex);
			this.ui.removeExpression(expIndex);
		} catch (NoElementSelectedException e) {
			this.ui.error(e.getMessage());
		}
	}

	
	@Override
	public void update(final ESource<? extends KeyboardEvent> s, final KeyboardEvent event) {
		if (event == KeyboardEvent.COPY) {
			boolean checker = true;
			String copy = "";
			try {
				copy = this.currentEnvironment.getRecordList().get(this.getSelectedExpressionIndex()).getPlainText();
			} catch (NoElementSelectedException e) { 
				checker = false;
			}
			if (checker) {
				final StringSelection selection = new StringSelection(copy);
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, selection);
			}
		}
	}
	
	/**
	 * Open a new empty environment.
	 */
	public void newEnvironment() {
		if (this.currentEnvironment.isModified() && this.ui.yesNoQuestion("Save changes to the current environment?")) {
			this.save();
		}
		this.currentEnvironment.reset();
		this.ui.removeAll();
	}
	
	/**
	 * Save the current environment. If no path is specified open a save dialog with the 
	 * user interface to allow the user to choose a path.
	 */
	public void save() {
		if (this.currentEnvironment.isModified()) { // I save only if the correntEnvironment has modifications
			if (this.currentEnvironment.getFilePath().isPresent()) {
				this.saveAction();
			} else {
				final Optional<String> path = this.ui.saveDialog();
				if (path.isPresent()) {
					this.currentEnvironment.setFilePath(path.get());
					this.saveAction();
				}
			}
		}
	}
	
	/**
	 * Open a file chosen by the user. If a file is corrupted or contains invalid expressions this function shows an
	 * error message to the user interface. 
	 * If the user wants to open a file but the current file is not saved, a yes/no dialog appear, for save the current
	 * environment and after open the new file.
	 */
	public void open() {
		if (this.currentEnvironment.isModified() && this.ui.yesNoQuestion("Save changes to the current environment?")) {
			this.save();
		}
		final Optional<String> path = this.ui.openDialog();
		if (path.isPresent()) {
			this.currentEnvironment.reset();
			this.ui.removeAll();
			try {
				final FileReader fr = new FileReader(path.get());
				final BufferedReader r = new BufferedReader(fr);
				for (String line = r.readLine(); line != null; line = r.readLine()) { // for every line
					final String[] vett = line.split("\t");
					if (vett.length == 2) {  // NOPMD
						this.currentEnvironment.add(new Record(vett[0], vett[1], this.framework.parse(vett[0]), Optional.empty()));
					} else if (vett.length == 3) {  // NOPMD
						this.currentEnvironment.add(new Record(vett[0], vett[1], this.framework.parse(vett[0]), Optional.of(vett[2])));
					} else {
						r.close();
						throw new IOException();
					}
				}
				r.close();
				this.currentEnvironment.setFilePath(path.get());
				this.currentEnvironment.getRecordList().forEach(rec->ui.printExpression(rec.getLatexText(), rec.getOperation()));
				this.currentEnvironment.setEdited(false);
			} catch (IOException | SyntaxErrorException e) {
				this.ui.error("File corrupted");
				this.currentEnvironment.reset();
			}
		}
	}
	
	/**
	 * Close the application.
	 * If the are no saved modifications asks to the user if its want to save.
	 */
	public void close() {
		if (this.currentEnvironment.isModified() && this.ui.yesNoQuestion("Save changes to the current environment?")) {
			this.save();
		}
		System.exit(0);
	}
	
	/**
	 * Plot the graph of the selected expression, if its has only one variable.
	 */
	public void plot() {
		try {
			final Expression exp = this.getExpressionAt(this.getSelectedExpressionIndex());
			final Set<String> vars = this.framework.enumerateVariables(exp);
			if (vars.size() > 1) { //NOPMD
				throw new IllegalArgumentException("The expression has too many variables");
			}
			final Map<String, Double> map = new HashMap<>();
			this.ui.showGraph(x-> {
				if (vars.size() == 1) { //NOPMD  only one variable
					vars.forEach(v->map.put(v, x));
				}
				return this.framework.evaluate(exp, map);
			});
			
		} catch (NoElementSelectedException | IllegalArgumentException e) {
			this.ui.error(e.getMessage());
		}
	}
	
	private void saveAction() {
		if (this.currentEnvironment.getFilePath().isPresent()) { // avoid errors
			try {
				final FileWriter fw = new FileWriter(this.currentEnvironment.getFilePath().get());
				final BufferedWriter w = new BufferedWriter(fw);
				for (final Record r : this.currentEnvironment.getRecordList()) {
					w.write(r.getPlainText());
					w.write("\t");
					w.write(r.getLatexText());
					if (r.getOperation().isPresent()) {
						w.write("\t");
						w.write(r.getOperation().get());
					}
					w.newLine();
				}
				w.close();
				this.currentEnvironment.setEdited(false);
			} catch (IOException e) {
				this.ui.error(e.getMessage());
			}
		}
	}
	
	private Record getRecordFromExpression(final Expression exp, final Optional<String> op) {
		return new Record(this.framework.toPlainText(exp), this.framework.toLatexText(exp), exp, op);
	}
}
