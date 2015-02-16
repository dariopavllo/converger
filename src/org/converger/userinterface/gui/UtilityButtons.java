package org.converger.userinterface.gui;

/**
 * An enum of utility buttons which help the user by providing writing facilities.
 * @author Gabriele Graffieti
 *
 */
public enum UtilityButtons {
	/** a left parenthesis. */
	LPARENTHESIS("(", "("),
	/** a right parenthesis. */
	RPARENTHESIS(")", ")"),
	/** the addition sing (+). */
	PLUS("+", "plus"),
	/** the subtraction sign (-). */
	MINUS("-", "minus"),
	/** the multiplication sign (*). */
	MUL("*", "multiply"),
	/** the division sign (/). */
	DIV("/", "divide"),
	/** the equals sign (=). */
	EQUAL("=", "equals"),
	/** Square the expression. */
	SQUARE("^2", "square"),
	/** Raise the expression to the nth power. */
	POW("^", "pow"),
	/** the square root symbol. */
	SQRT("sqrt(", "square root"),
	/** Facility a nth root. */
	NTHROOT("^(1/", "nth root"),
	/** the absolute value symbol. */
	ABS("abs(", "absolute value"),
	/** Facility for writing an exponential expression. */
	EXP("e^(", "exp"),
	/** the natural logarithm symbol. */
	LN("ln(", "natural logarithm"),
	/** the sine symbol. */
	SIN("sin(", "sine"),
	/** the cosine symbol. */
	COS("cos(", "cosine"),
	/** the tangent symbol. */
	TAN("tan(", "tangent"),
	/** the arcsine symbol. */
	ASIN("asin(", "arcsine"),
	/** the arccosine symbol. */
	ACOS("acos(", "arccosine"),
	/** the arctangent symbol. */
	ATAN("atan(", "arctangent"),
	/** The mathematical constant Pi. */
	PI("pi", "pi"),
	/** The mathematical constant E. */
	E("e", "e");
	
	
	private final String symbol;
	private final String name;
	
	private UtilityButtons(final String exprSymbol, final String exprName) {
		this.symbol = exprSymbol;
		this.name = exprName;
	}
	
	/**
	 * Returns the symbol of the selected button in string format.
	 * @return the symbol of the selected button.
	 * 
	 * */
	public String getSymbol() {
		return this.symbol;
	}
	/**
	 * Return the name of the selected button. This name is given in string format and it represent the name of the selected operation.
	 * @return the name of the mathematical operation selected.
	 * */
	public String getName() {
		return this.name;
	}
}


