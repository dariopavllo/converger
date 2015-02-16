package org.converger.controller;

/**
 * Represents an expression field, a field used by the user interface to allows the user inserting 
 * an expression.
 * This field is used when the user have to insert a new value for an object.  
 * 
 * @author Gabriele Graffieti
 *
 */
public class ExpressionField implements Field {

	private final String name;
	private final String mappedObj;
	private String value;
	
	/**
	 * Constructs a new Expression field.
	 * @param fieldName the name of the expression field
	 * @param mappedObject the object which is replaced with the new value inserted by the user
	 */
	public ExpressionField(final String fieldName, final String mappedObject) {
		this.name = fieldName;
		this.mappedObj = mappedObject;
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getValue() {
		return this.value;
	}
	
	/** @return the object which its value will be replaced by the value from the expression field */
	public String getMappedObject() {
		return this.mappedObj;
	}

	@Override
	public void setValue(final String newValue) {
		this.value = newValue;
	}

	@Override
	public Type getType() {
		return Field.Type.EXPRESSION;
	}

}
