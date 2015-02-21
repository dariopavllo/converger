package org.converger.framework.parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class splits a string into tokens.
 * The input string is expected to be a mathematical expression.
 * @author Dario Pavllo
 */
public class Tokenizer implements Iterable<String> {
	
	private final String input;
	private final List<String> tokens;
	
	/**
	 * @param inputExpression the input expression
	 */
	public Tokenizer(final String inputExpression) {
		this.input = inputExpression;
		this.tokens = new ArrayList<>();
		this.tokenize();
	}
	
	@Override
	public Iterator<String> iterator() {
		return this.tokens.iterator();
	}
	
	private void tokenize() {
		final StringBuilder currentToken = new StringBuilder();
		TokenType currentType = TokenType.NONE;
		
		for (int i = 0; i < this.input.length(); i++) {
			final char c = this.input.charAt(i);
			final TokenType newType = this.getCharacterType(c);
			if (currentType == TokenType.SYMBOL || //Symbol: 1 character
				newType != currentType) {
				//If the type has changed, push the token
				if (currentType != TokenType.NONE) {
					this.tokens.add(currentToken.toString());
				}
				//Reset the current token
				currentToken.setLength(0);
				currentType = newType;
			}
			currentToken.append(c);
		}
		//Push the last token
		if (currentType != TokenType.NONE) {
			this.tokens.add(currentToken.toString());
		}
		
	}
	
	private TokenType getCharacterType(final char c) {
		if (Character.isAlphabetic(c) || c == '\'') {
			return TokenType.WORD;
		} else if (Character.isDigit(c) || c == '.') {
			return TokenType.NUMBER;
		} else if (Character.isWhitespace(c)) {
			return TokenType.NONE;
		} else {
			return TokenType.SYMBOL;
		}
	}
	
	private enum TokenType {
		NONE, SYMBOL, WORD, NUMBER;
	}
}
