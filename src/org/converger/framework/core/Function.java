package org.converger.framework.core;

/**
 * Represents a 1-argument function.
 * @author Dario Pavllo
 */
public enum Function {
	
	/** Sine function. */
	SIN("sin") {
		@Override
		public <X> X accept(final Visitor<X> v, final X arg) {
			return v.visitSin(arg);
		}
	},
	/** Cosine function. */
	COS("cos") {
		@Override
		public <X> X accept(final Visitor<X> v, final X arg) {
			return v.visitCos(arg);
		}
	},
	/** Natural logarithm (base e). */
	LN("ln") {
		@Override
		public <X> X accept(final Visitor<X> v, final X arg) {
			return v.visitLn(arg);
		}
	},
	/** Absolute value |x|. */
	ABS("abs") {
		@Override
		public <X> X accept(final Visitor<X> v, final X arg) {
			return v.visitAbs(arg);
		}
	},
	/** Square root. */
	SQRT("sqrt") {
		@Override
		public <X> X accept(final Visitor<X> v, final X arg) {
			return v.visitSqrt(arg);
		}
	};
	
	private final String name;
	
	private Function(final String functionName) {
		this.name = functionName;
	}
	
	/**
	 * Returns the name associated with this function.
	 * @return the function name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * This method is the entry points for classes implementing the
	 * visitor pattern of this object.
	 * @param <X> the argument type
	 * @param v a class which implements the visitor pattern
	 * @param arg the argument of this function
	 * @return an implementation-dependent object
	 */
	public abstract <X> X accept(Visitor<X> v, X arg);
	
	/**
	 * This interface contains all the possible actions which can be done
	 * on a function node, and it's used by classes implementing
	 * the visitor pattern.
	 * @param <X> the type of the operands
	 */
	public interface Visitor<X> { //NOPMD
		
		/**
		 * Default action on unimplemented methods.
		 * @param arg the function argument
		 * @return an implementation-dependent value
		 */
		default X visitDefaultFunction(final X arg) {
			throw new UnsupportedOperationException();
		}
		
		/**
		 * Action to do on the sine function.
		 * @param arg the function argument
		 * @return an implementation-dependent value
		 */
		default X visitSin(final X arg) {
			return this.visitDefaultFunction(arg);
		}
		
		/**
		 * Action to do on the cosine function.
		 * @param arg the function argument
		 * @return an implementation-dependent value
		 */
		default X visitCos(final X arg) {
			return this.visitDefaultFunction(arg);
		}
		
		/**
		 * Action to do on the natural logarithm function.
		 * @param arg the function argument
		 * @return an implementation-dependent value
		 */
		default X visitLn(final X arg) {
			return this.visitDefaultFunction(arg);
		}
		
		/**
		 * Action to do on the absolute value function.
		 * @param arg the function argument
		 * @return an implementation-dependent value
		 */
		default X visitAbs(final X arg) {
			return this.visitDefaultFunction(arg);
		}
		
		/**
		 * Action to do on the square root function.
		 * @param arg the function argument
		 * @return an implementation-dependent value
		 */
		default X visitSqrt(final X arg) {
			return this.visitDefaultFunction(arg);
		}
	}
}
