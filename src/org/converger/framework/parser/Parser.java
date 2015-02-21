package org.converger.framework.parser;

import java.util.List;

/**
 * This interface defines a parser, which accepts a list of generic (string) tokens
 * and produces a list of strongly typed tokens.
 * The parser's behavior is implementation-dependent, but it's allowed to reorder
 * the tokens to supply them to the following stage.
 * @author Dario Pavllo
 */
public interface Parser {

	/**
	 * Parses the given list of string tokens.
	 * @param input an iterable which contains string tokens
	 * @throws IllegalArgumentException if the expression contains errors
	 */
	void parse(Iterable<String> input);
	
	/**
	 * Returns the process' result.
	 * @return a list of typed tokens
	 */
	List<Token> getOutputList();
	
}
