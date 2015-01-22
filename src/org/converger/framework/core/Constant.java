package org.converger.framework.core;

import org.converger.framework.Expression;

/**
 * Represents a numeric constant.
 * This is a leaf of the syntax tree.
 * @author Dario Pavllo
 */
public final class Constant implements Expression {

	/** Constant 0. */
	public static final Constant ZERO = Constant.valueOf(0);
	
	/** Constant 1. */
	public static final Constant ONE = Constant.valueOf(1);
	
	private final long value;
	
	/**
	 * Returns a constant with the supplied value.
	 * @param value an integral value
	 * @return a Constant associated with the given value
	 */
	public static Constant valueOf(final long value) {
		return new Constant(value);
	}
	
	private Constant(final long constantValue) {
		this.value = constantValue;
	}
	
	/**
	 * Returns the value of this constant.
	 * @return a long value
	 */
	public long getValue() {
		return this.value;
	}
	
	@Override
	public <X> X accept(final Expression.Visitor<X> v) {
		return v.visit(this);
	}
	
	@Override
	public String toString() {
		return Long.toString(this.value);
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof Constant) {
			final Constant o = (Constant) obj;
			return this.value == o.value;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Long.hashCode(this.value);
	}
	
	
}
