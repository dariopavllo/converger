package org.converger.framework;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.converger.framework.core.BinaryOperator;
import org.converger.framework.core.Function;
import org.converger.framework.core.NAryOperator;
import org.converger.framework.core.Operator;
import org.converger.framework.core.Variable;

/**
 * This class contains the framework's global data, such as
 * operator and function maps, and irrational constants.
 * @author Dario Pavllo
 */
public final class Environment {

	/** Represents the pi constant: 3.14159... */
	public static final Variable PI = new Variable("pi");
	
	/** Represents the Euler's constant (e): 2.71828... */
	public static final Variable E = new Variable("e");
	
	private static final Environment SINGLETON = new Environment();
	
	/** These maps contain the association between names and operators/functions */
	private final Map<String, Operator> operators;
	private final Map<String, Function> functions;
	
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
	}
	
	private void putOperator(final Operator o) {
		this.operators.put(o.getSymbol(), o);
	}
	
	private void putFunction(final Function o) {
		this.functions.put(o.getName(), o);
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
	
}
