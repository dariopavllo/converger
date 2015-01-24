package org.converger.framework.core;

/**
 * Represents a binary operator: an operator which accepts two operands.
 * @author Dario Pavllo
 */
public enum BinaryOperator implements Operator {

	/** Division operator. */
	DIVISION("/", 2) {
		@Override
		public <X> X accept(final Visitor<X> v, final X o1, final X o2) {
			return v.visitDivision(o1, o2);
		}
	},
	/** Power (exponentiation) operator. */
	POWER("^", 3) {
		@Override
		public <X> X accept(final Visitor<X> v, final X o1, final X o2) {
			return v.visitPower(o1, o2);
		}
	},
	/** Subtraction operator (only for parsing purposes). */
	SUBTRACTION("-", 1) {
		@Override
		public <X> X accept(final Visitor<X> v, final X o1, final X o2) {
			/* Subtraction is used only for parsing purposes,
			 * and it gets converted to another construct subsequently. */
			throw new UnsupportedOperationException();
		}
	};
	
	private final String symbol;
	private final int precedence;
	
	private BinaryOperator(final String opSymbol, final int opPrecedence) {
		this.symbol = opSymbol;
		this.precedence = opPrecedence;
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
	 * @param o1 the first operand
	 * @param o2 the second operand
	 * @return an implementation-dependent object
	 */
	public abstract <X> X accept(Visitor<X> v, X o1, X o2);
	
	/**
	 * This interface contains the possible actions which can be done
	 * on a binary operator, and it's used by classes implementing
	 * the visitor pattern.
	 * @param <X> the type of the operands
	 */
	public interface Visitor<X> { //NOPMD
		
		/**
		 * Default action on unimplemented methods.
		 * @param o1 the first operand
		 * @param o2 the second operand
		 * @return an implementation-dependent value
		 */
		default X visitDefaultBinaryOperator(final X o1, final X o2) {
			throw new UnsupportedOperationException();
		}
		
		/**
		 * Action to do on division operations.
		 * @param o1 the first operand
		 * @param o2 the second operand
		 * @return an implementation-dependent value
		 */
		default X visitDivision(final X o1, final X o2) {
			return this.visitDefaultBinaryOperator(o1, o2);
		}
		
		/**
		 * Action to do on exponentiation.
		 * @param o1 the first operand
		 * @param o2 the second operand
		 * @return an implementation-dependent value
		 */
		default X visitPower(final X o1, final X o2) {
			return this.visitDefaultBinaryOperator(o1, o2);
		}
	}

}
