package org.converger.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.converger.framework.CasFramework;
import org.converger.framework.Expression;
import org.converger.framework.SyntaxErrorException;

/**
 * Represents an operation which requires the framework intervention.
 * A framework operation's method requestFields return to the caller a list of Field which are used for communicate with
 * the user interface.
 * @author User
 *
 */
public enum FrameworkOperation {
		
	/** 
	 * Represents an operation of mathematical expression simplification.  
	 */
	SIMPLIFY("Simplify") {
		
		@Override
		public List<Field> requestFields(final int index) {
			return Collections.emptyList();
		}

		@Override
		public void execute(final int index, final List<Field> fields) {
			final Expression exp = Controller.getController().getExpressionAt(index);
			final Expression simplifiedExpression = Controller.getController().getFramework().simplify(exp);
			Controller.getController().addExpression(simplifiedExpression);
		}
	},
	
	/**
	 * Represents an operation of variable substitution.  
	 */
	SUBSTITUTE("Substitute") {

		@Override
		public List<Field> requestFields(final int index) {
			final List<Field> listField = new ArrayList<>();
			for (final String v : Controller.getController().getVariables(index)) {
				listField.add(new ExpressionField("Substitute " + v, v));
			}
			return listField;
		}

		@Override
		public void execute(final int index, final List<Field> fields) throws SyntaxErrorException {
			final Expression exp = Controller.getController().getExpressionAt(index);
			final Map<String, Expression> map = new HashMap<>();
			final CasFramework cas = Controller.getController().getFramework();
			for (final Field f : fields) {
				if (!f.getValue().isEmpty()) {
					map.put(((ExpressionField) f).getMappedObject(), cas.parse(f.getValue()));
				}
			}
			if (!map.isEmpty()) {
				final Expression newExpression = cas.substitute(exp, map);
				Controller.getController().addExpression(newExpression);
			}
		}
	},
	
	/**
	 * Represents an operation of evaluation of a mathematical expression.
	 * An evaluation calculate the arithmetic value of an expression, so no variables are admitted.
	 */
	EVALUATE("Evaluate") {
		
		@Override
		public List<Field> requestFields(final int index) {
			final List<Field> listField = new ArrayList<>();
			for (final String v : Controller.getController().getVariables(index)) {
				listField.add(new ExpressionField("Substitute " + v, v));
			}
			return listField;
		}

		@Override
		public void execute(final int index, final List<Field> fields) throws SyntaxErrorException {
			final Expression exp = Controller.getController().getExpressionAt(index);
			final Map<String, Double> map = new HashMap<>();
			final Map<String, Double> tmpMap = Collections.emptyMap();
			final CasFramework cas = Controller.getController().getFramework();
			try {
				for (final Field f : fields) {
					map.put(((ExpressionField) f).getMappedObject(), cas.evaluate(cas.parse(f.getValue()), tmpMap));
				}
				final Double result = cas.evaluate(exp, map);
				Controller.getController().addNumericalExpression(result);
			} catch (NoSuchElementException e) { // NOPMD
				throw e;
			}
		}
	},
	
	/**
	 * Represent the arithmetic resolution of an equation. The equation have to have only 
	 * one variable.
	 */
	SOLVE("Solve") {

		@Override
		public List<Field> requestFields(final int index) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void execute(final int index, final List<Field> fields) {
			// TODO Auto-generated method stub
			
		}
		
	},
	
	/**
	 * Represents the derivative of a mathematical expression. Its derivative is calculated 
	 * algebraically, so the expression can be two or more variables. In this case the function calculate 
	 * the partial derivative in one selected variable.
	 */
	DIFFERENTIATE("Differentiate") {
		@Override
		public List<Field> requestFields(final int index) {
			final List<Field> listField = new ArrayList<>();
			listField.add(new SelectionField("Select Variable", Controller.getController().getVariables(index)));
			listField.add(new NumericalField("Order"));
			return listField;
		}

		@Override
		public void execute(final int index, final List<Field> fields) {
			final Expression exp = Controller.getController().getExpressionAt(index);
			int order = 1; 
			String variable = "";
			for (final Field f : fields) { // I have only two field in the list, one selection field and one numerical field
				if (f.getType() == Field.Type.NUMERICAL) {
					order = Integer.parseInt(f.getValue());
				} else {
					variable = f.getValue();
				}
			}
			Expression newExpression = Controller.getController().getFramework().differentiate(exp, variable); // fist order
			for (int i = 1; i < order; i++) { 
				newExpression = Controller.getController().getFramework().differentiate(newExpression, variable);
			}
			Controller.getController().addExpression(newExpression);
		}
	},
	
	/**
	 * Represent the definite integral of a function between 2 points.
	 */
	INTEGRATE("Integrate") {
		@Override
		public List<Field> requestFields(final int index) {
			// TODO
			return null;
		}

		@Override
		public void execute(final int index, final List<Field> fields) {
			// TODO Auto-generated method stub
			
		}
	},
	
	/**
	 * Represents the Taylor series of a function in one given point. 
	 */
	TAYLOR("Taylor Series") {

		@Override
		public List<Field> requestFields(final int index) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void execute(final int index, final List<Field> fields) {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	private final String name;
	
	private FrameworkOperation(final String opName) {
		this.name = opName;
	}
	
	/** @return the name of the FrameworkOperation. */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Returns a list of fields used for communicate to the user interface.
	 * A framework operation can return an empty list if it don't requires user interaction. 
	 * @param index the index of the expression.
	 * @return a list of fields used for user-communication
	 */
	public abstract List<Field> requestFields(int index);
	
	/**
	 * Execute the framework operation.
	 * @param index the index of the selected expression 
	 * @param fields a list of fields, every field's value was set by the user.
	 * @throws SyntaxErrorException if a field value contains syntax errors.
	 * @throws NoSuchElementException in the evaluate operation, when a field value contains
	 * an expression with at least one variable.
	 * @throws IllegalArgumentException in the integration or solve equation operation, when the 
	 * selected expression does not contain only one variable. 
	 */
	public abstract void execute(int index, List<Field> fields) throws SyntaxErrorException;
}
