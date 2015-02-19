package org.converger.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import org.converger.framework.CasFramework;
import org.converger.framework.Expression;
import org.converger.framework.SyntaxErrorException;

/**
 * Represents an operation which requires the framework intervention.
 * A framework operation's method requestFields return to the caller a list of Field which are used for communicate to
 * the user interface.
 * @author Gabriele Graffieti
 *
 */
public enum FrameworkOperation {
		
	/**
	 * Represents the editing of an expression.
	 */
	EDIT("Edit") {
		@Override
		public List<Field> requestFields(final int index) {
			final List<Field> listField = new ArrayList<>();
			final ExpressionField expField = new ExpressionField("Edit", "edit");
			expField.setValue(Controller.getController().getFramework().toPlainText(Controller.getController().getExpressionAt(index)));
			listField.add(expField);
			return listField;
		}

		@Override
		public void execute(final int index, final List<Field> fields) throws SyntaxErrorException {
			final String newExpression = fields.get(0).getValue(); // .get(0) because I know that the field list contains only one field
			final Expression exp = Controller.getController().getFramework().parse(newExpression);
			Controller.getController().editExpression(index, exp);
		}
	},
	
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
			Controller.getController().addExpression(simplifiedExpression, Optional.of(this.getName()));
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
				Controller.getController().addExpression(newExpression, Optional.of(this.getName()));
			}
		}
	},
	
	/**
	 * Represents the evaluation of a mathematical expression.
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
			final Map<String, Double> tmpMap = Collections.emptyMap(); // map used for check if given expressions not have variables.
			final CasFramework cas = Controller.getController().getFramework();
			for (final Field f : fields) {
				map.put(((ExpressionField) f).getMappedObject(), cas.evaluate(cas.parse(f.getValue()), tmpMap));
			}
			final Double result = cas.evaluate(exp, map);
			Controller.getController().addNumericalExpression(result, Optional.of(this.getName()));
		}
	},
	
	/**
	 * Represent the arithmetic resolution of an equation. The equation have to have only 
	 * one variable.
	 */
	SOLVE("Solve") {

		@Override
		public List<Field> requestFields(final int index) {
			return Collections.emptyList();
		}

		@Override
		public void execute(final int index, final List<Field> fields) {
			final Expression exp = Controller.getController().getExpressionAt(index);
			final Set<Double> res = Controller.getController().getFramework().solveNumerically(exp);
			res.forEach(d->Controller.getController().addNumericalExpression(d, Optional.of(this.getName())));
		}
		
	},
	
	/**
	 * Represents the derivative of a mathematical expression. Its derivative is calculated 
	 * algebraically, so the expression can be two or more variables. In this case the function calculate 
	 * the partial derivative in the selected variable.
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
			Controller.getController().addExpression(newExpression, Optional.of(this.getName()));
		}
	},
	
	/**
	 * Represent the definite integral of a function between 2 points.
	 */
	INTEGRATE("Integrate") {
		@Override
		public List<Field> requestFields(final int index) {
			final List<Field> listField = new ArrayList<>();
			listField.add(new ExpressionField("Lower bound", "lb"));
			listField.add(new ExpressionField("Upper bound", "ub"));
			return listField;
		}

		@Override
		public void execute(final int index, final List<Field> fields) throws SyntaxErrorException {
			final Expression exp = Controller.getController().getExpressionAt(index);
			final CasFramework cas = Controller.getController().getFramework();
			final Map<String, Double> tmpMap = Collections.emptyMap(); // map used for check if given expressions not have variables.
			double lb = 0;
			double ub = 0;
			for (final Field f : fields) {
				if (((ExpressionField) f).getMappedObject().equals("lb")) {
					lb = cas.evaluate(cas.parse(f.getValue()), tmpMap);
				} else {
					ub = cas.evaluate(cas.parse(f.getValue()), tmpMap);
				}
			}
			final double res = cas.integrateNumerically(exp, lb, ub);
			Controller.getController().addNumericalExpression(res, Optional.of(this.getName()));
		}
	},
	
	/**
	 * Represents the Taylor series of a function in one given point. 
	 */
	TAYLOR("Taylor Series") {

		@Override
		public List<Field> requestFields(final int index) {
			final List<Field> listField = new ArrayList<>();
			listField.add(new ExpressionField("Expansion point", "ep"));
			listField.add(new SelectionField("Select variable", Controller.getController().getVariables(index)));
			listField.add(new NumericalField("Order"));
			return listField;
		}

		@Override
		public void execute(final int index, final List<Field> fields) throws SyntaxErrorException {
			final Expression exp = Controller.getController().getExpressionAt(index);
			final CasFramework cas = Controller.getController().getFramework();
			String point = "";
			int order = 1;
			String variable = "";
			for (final Field f : fields) {
				switch (f.getType()) {
					case NUMERICAL: order = Integer.parseInt(f.getValue());
					break;
					case SELECTION: variable = f.getValue();
					break;
					case EXPRESSION: point = f.getValue();
					break;
					default:
				}
			}
			final Expression series = cas.taylorSeries(exp, variable, cas.parse(point), order);
			Controller.getController().addExpression(series, Optional.of(this.getName()));
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
	 * @param fields a list of fields, every field contains information, set by the user,
	 * for execute the operation.
	 * @throws SyntaxErrorException if a field value contains syntax errors.
	 * @throws NoSuchElementException in the evaluate operation, when a field value contains
	 * an expression with variables.
	 * @throws IllegalArgumentException in the integration or solve equation operation, when the 
	 * selected expression does not contain only one variable. 
	 */
	public abstract void execute(int index, List<Field> fields) throws SyntaxErrorException;
}
