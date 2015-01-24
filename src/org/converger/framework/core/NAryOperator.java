package org.converger.framework.core;

import java.util.List;

/**
 * Represents an n-ary operator, namely, an operator which accepts a variable
 * number of arguments. It can be useful for commutative and associative
 * operators such as addition and product.
 * @author Dario Pavllo
 */
public enum NAryOperator implements Operator {

	/** Addition operator. */
	ADDITION("+", 1) {
		@Override
		public <X> X accept(final Visitor<X> v, final List<X> operands) {
			return v.visitAddition(operands);
		}
	},
	/** Product operator. */
	PRODUCT("*", 2) {
		@Override
		public <X> X accept(final Visitor<X> v, final List<X> operands) {
			return v.visitProduct(operands);
		}
	};
	
	private final String symbol;
	private final int precedence;
	
	private NAryOperator(final String operatorSymbol, final int precedenceValue) {
		this.symbol = operatorSymbol;
		this.precedence = precedenceValue;
	}
	
	@Override
	public String getSymbol() {
		return this.symbol;
	}
	
	@Override
	public int getPrecedence() {
		return this.precedence;
	}
	
	/**
	 * This method is the entry points for classes implementing the
	 * visitor pattern of this object.
	 * @param <X> the type of the operands
	 * @param v a class which implements the visitor pattern
	 * @param operands the list of operands of this operator
	 * @return an implementation-dependent object
	 */
	public abstract <X> X accept(Visitor<X> v, List<X> operands);
	
	/**
	 * This interface contains the possible actions which can be done
	 * on an n-ary operator, and it's used by classes implementing
	 * the visitor pattern.
	 * @param <X> the type of the operands
	 */
	public interface Visitor<X> { //NOPMD
		
		/**
		 * Default action on unimplemented methods.
		 * @param operands the list of operands
		 * @return an implementation-dependent value
		 */
		default X visitDefaultNAryOperator(final List<X> operands) {
			throw new UnsupportedOperationException();
		}
		
		/**
		 * Action to do on addition operations.
		 * @param operands the list of operands
		 * @return an implementation-dependent value
		 */
		default X visitAddition(final List<X> operands) {
			return this.visitDefaultNAryOperator(operands);
		}
		
		/**
		 * Action to do on product operations.
		 * @param operands the list of operands
		 * @return an implementation-dependent value
		 */
		default X visitProduct(final List<X> operands) {
			return this.visitDefaultNAryOperator(operands);
		}
	}
}
