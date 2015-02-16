package org.converger.framework;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.converger.framework.core.BinaryOperator;
import org.converger.framework.core.Function;
import org.converger.framework.core.NAryOperator;
import org.converger.framework.core.Operator;
import org.converger.framework.core.SpecialConstant;

/**
 * This class contains the framework's global data, such as
 * operator and function maps, and irrational constants.
 * @author Dario Pavllo
 */
public final class Environment {
	
	private static final Environment SINGLETON = new Environment();
	
	/** These maps contain the association between names and operators/functions */
	private final Map<String, Operator> operators;
	private final Map<String, Function> functions;
	private final Map<String, SpecialConstant> specialConstants;
	
	/**
	 * Returns the unique instance of this class.
	 * @return an Environment singleton
	 */
	public static Environment getSingleton() {
		return Environment.SINGLETON;
	}
	
	private Environment() {
		this.operators = new HashMap<>();
		this.functions = new HashMap<>();
		this.specialConstants = new HashMap<>();
		
		//Inserts all the operators in the operator map
		for (final Operator o : BinaryOperator.values()) {
			this.putOperator(o);
		}
		for (final Operator o : NAryOperator.values()) {
			this.putOperator(o);
		}
		
		//Inserts all the functions in the function map
		for (final Function f : Function.values()) {
			this.putFunction(f);
		}
		
		//Inserts all the mathematical constants in the constant map
		for (final SpecialConstant c : SpecialConstant.values()) {
			this.putConstant(c);
		}
	}
	
	private void putOperator(final Operator o) {
		this.operators.put(o.getSymbol(), o);
	}
	
	private void putFunction(final Function f) {
		this.functions.put(f.getName(), f);
	}
	
	private void putConstant(final SpecialConstant c) {
		this.specialConstants.put(c.getName(), c);
	}
	
	/**
	 * Returns whether there exists an operator with the given symbol.
	 * @param o the operator symbol
	 * @return whether the operator exists
	 */
	public boolean hasOperator(final String o) {
		return this.operators.containsKey(o);
	}
	
	/**
	 * Returns the operator with the given name.
	 * @param o the operator symbol
	 * @return an Operator object
	 * @throws NoSuchElementException if the operator does not exist
	 */
	public Operator getOperator(final String o) {
		if (!this.hasOperator(o)) {
			throw new NoSuchElementException("Unknown operator");
		}
		return this.operators.get(o);
	}
	
	/**
	 * Returns whether there exists a function with the given name.
	 * @param f the function name
	 * @return whether the function exists
	 */
	public boolean hasFunction(final String f) {
		return this.functions.containsKey(f);
	}
	
	/**
	 * Returns the function with the given name.
	 * @param f the function name
	 * @return a Function object
	 * @throws NoSuchElementException if the function does not exist
	 */
	public Function getFunction(final String f) {
		if (!this.hasFunction(f)) {
			throw new NoSuchElementException("Unknown function");
		}
		return this.functions.get(f);
	}
	
	/**
	 * Returns whether there exists a mathematical constant with the given name.
	 * @param c the constant name
	 * @return whether the constant exists
	 */
	public boolean hasConstant(final String c) {
		return this.specialConstants.containsKey(c);
	}
	
	/**
	 * Returns the mathematical constant with the given name.
	 * @param c the constant name
	 * @return a SpecialConstant object
	 * @throws NoSuchElementException if the constant does not exist
	 */
	public SpecialConstant getConstant(final String c) {
		if (!this.hasConstant(c)) {
			throw new NoSuchElementException("Unknown mathematical constant");
		}
		return this.specialConstants.get(c);
	}
	
}
