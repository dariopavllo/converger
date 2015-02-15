package org.converger.controller;

/**
 * Represents a numerical field, a field used by the user interface to allows the user choosing 
 * from positive numbers.
 * @author Gabriele Graffieti
 *
 */
public class NumericalField implements Field {

	private final String name;
	private String value;
	
	/**
	 * Construct the numerical field.
	 * @param fieldName the name of the numerical field
	 */
	public NumericalField(final String fieldName) {
		this.name = fieldName;
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getValue() {
		return this.value;
	}

	/**
	 * @param newValue It must be a string representation of a positive number
	 */
	@Override
	public void setValue(final String newValue) {
		this.value = newValue;
		
	}

	@Override
	public Type getType() {
		return Field.Type.NUMERICAL;
	}

}
