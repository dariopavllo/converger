package org.converger.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents an operation which requires the framework intervention.
 * A framework operation's method requestFields return to the caller a list of Field which are used for communicate with
 * the user interface.
 * @author User
 *
 */
public enum FrameworkOperation {
	
	/**
	 * Represents the operation of modifying an expression. 
	 */
	MODIFY {

		@Override
		public List<Field> requestFields(final int index) {
			final List<Field> listField = new ArrayList<>();
			listField.add(new ExpressionField("New Expression", "expr"));
			return listField;
		}
	},
	
	/** 
	 * Represents an operation of mathematical expression simplification.  
	 */
	SIMPLIFY {
		
		@Override
		public List<Field> requestFields(final int index) {
			return Collections.emptyList();
		}
	}, 
	
	/**
	 * Represents an operation of variable substitution.  
	 */
	SUBSTITUTE {

		@Override
		public List<Field> requestFields(final int index) {
			final List<Field> listField = new ArrayList<>();
			for (final String v : Controller.getController().getVariables(index)) {
				listField.add(new ExpressionField("Substitute " + v, v));
			}
			return listField;
		}
	},
	
	/**
	 * Represents an operation of evaluation of a mathematical expression.
	 * An evaluation calculate the arithmetic value of an expression, so no variables are admitted.
	 */
	EVALUATE {
		
		@Override
		public List<Field> requestFields(final int index) {
			final List<Field> listField = new ArrayList<>();
			for (final String v : Controller.getController().getVariables(index)) {
				listField.add(new ExpressionField("Substitute " + v, v));
			}
			return listField;
		}
	},
	
	/**
	 * Represent the arithmetic resolution of an equation. The equation have to have only 
	 * one variable.
	 */
	SOLVE {

		@Override
		public List<Field> requestFields(final int index) {
			// TODO Auto-generated method stub
			return null;
		}
		
	},
	
	/**
	 * Represents the derivative of a mathematical expression. Its derivative is calculated 
	 * algebraically, so the expression can be two or more variables. In this case the function calculate 
	 * the partial derivative in one selected variable.
	 */
	DIFFERENTIATE {
		@Override
		public List<Field> requestFields(final int index) {
			final List<Field> listField = new ArrayList<>();
			listField.add(new SelectionField("Select Variable", Controller.getController().getVariables(index)));
			listField.add(new NumericalField("Order"));
			return listField;
		}
	},
	
	/**
	 * Represent the definite integral of a function between 2 points.
	 */
	INTEGRATE {
		@Override
		public List<Field> requestFields(final int index) {
			// TODO
			return null;
		}
	},
	
	/**
	 * Represents the Taylor series of a function in one given point. 
	 */
	TAYLOR {

		@Override
		public List<Field> requestFields(final int index) {
			// TODO Auto-generated method stub
			return null;
		}
		
	};
	
	/**
	 * Returns a list of fields used for communicate to the user interface.
	 * A framework operation can return an empty list if it don't requires user interaction. 
	 * @param index the index of the expression.
	 * @return a list of fields used for user-communication
	 */
	public abstract List<Field> requestFields(int index);
}
