package org.converger.controller;

import java.util.Optional;

import org.converger.framework.Expression;

/**
 * Represents a single mathematical expression, in plain text, LaTex and Expression format.
 * The expression can be modified.
 * @author Gabriele Graffieti
 */
public class Record {
	
	private final String plainText;
	private final String latexText;
	private final Expression expression;
	private final Optional<String> operation;
	
	/**
	 * Construct a new mathematical expression from the given values.
	 * @param expText the new plain text of the mathematical expression
	 * @param expLatex the new latex text of the mathematical expression
	 * @param exp an Expression representing the new mathematical expression
	 * @param op the operation which generates the record.
	 */
	public Record(final String expText, final String expLatex, final Expression exp, final Optional<String> op) {
		this.plainText = expText;
		this.latexText = expLatex;
		this.expression = exp;
		this.operation = op;
	}

	/** @return the plain text of the selected mathematical expression */
	public String getPlainText() {
		return this.plainText;
	}

	/** @return the latex of the selected mathematical expression */
	public String getLatexText() {
		return this.latexText;
	}

	/** @return the Expression format of the selected mathematical expression */
	public Expression getExpression() {
		return this.expression;
	}
	
	/**
	 * Return the operation which generated this record. 
	 * If the record was added manually by the user, it returns an Optional.empty 
	 * @return the operation which generated this record.
	 */
	public Optional<String> getOperation() {
		return this.operation;
	}
}
