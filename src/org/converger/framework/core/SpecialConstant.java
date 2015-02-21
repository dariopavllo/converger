package org.converger.framework.core;


/**
 * Represents a mathematical constant: a number
 * that is "significantly interesting in some way".
 * @author Dario Pavllo
 */
public enum SpecialConstant {

	/** Represents the pi constant: 3.14159... */
	PI("pi", Math.PI),
	/** Represents Euler's constant (e): 2.71828... */
	E("e", Math.E);
	
	private final String name;
	private final double value;
	private final Variable variable;
	
	private SpecialConstant(final String constantName, final double realValue) {
		this.name = constantName;
		this.value = realValue;
		this.variable = new Variable(constantName);
	}
	
	/**
	 * Returns this constant's name.
	 * @return this constant's name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Returns this constant's value.
	 * @return a real value
	 */
	public double getValue() {
		return this.value;
	}
	
	/**
	 * Returns this constant as a symbolic variable.
	 * @return the variable associated with this constant
	 */
	public Variable getAsVariable() {
		return this.variable;
	}
	
}
