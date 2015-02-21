package org.converger.controller;

/**
 * Represents a field, used by the user interface to communicate with the user. 
 * A field is the main interface that put in communication the user interface and the core
 * of the application.
 * @author Gabriele Graffieti
 *
 */
public interface Field {

	/**
	 * Returns the name of the field.
	 * @return the name of the field
	 */
	String getName();
	/**
	 * Returns the value of the field, set by the user.
	 * @return a string represents the value of the field set by the user
	 */
	String getValue();
	/**
	 * Set the value of the field.
	 * @param value a string represents user inserted value.
	 */
	void setValue(String value);
	/**
	 * Returns the type of the field, the types are in the Type enum.
	 * @return the type of the field.
	 */
	Type getType();
	/**
	 * Represents the set of types which a field can have.
	 * @author Gabriele Graffieti
	 *
	 */
	enum Type {
		/** A selectionField, a field where the user can select from a range of values. */
		SELECTION, 
		/** A numerical field, a fild where the user can choose from a range of numbers. */
		NUMERICAL, 
		/** An expression field, a field where the user can write an expression. */
		EXPRESSION;
	}
}
