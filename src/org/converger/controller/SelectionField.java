package org.converger.controller;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a selection field, a field used by the user interface to allows the user choosing 
 * from a set of options.
 * @author Gabriele Graffieti
 *
 */
public class SelectionField implements Field {

	private final String name;
	private final Set<String> vars;
	private String value;
	
	/**
	 * Construct the selection field.
	 * @param fieldName the name of the selection field
	 * @param variables the set of options, in string format
	 */
	public SelectionField(final String fieldName, final Set<String> variables) {
		this.name = fieldName;
		this.vars = variables;
		this.value = "";
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public String getValue() {
		return this.value;
	}

	@Override
	public void setValue(final String newValue) {
		this.value = newValue;
	}

	@Override
	public Type getType() {
		return Field.Type.SELECTION;
	}
	
	/** @return An ArrayList of the options. */
	public Set<String> getAllowedValues() {
		return new HashSet<>(this.vars);
	}
}
