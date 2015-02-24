package org.converger.userinterface.gui;

/**
 * An enum of utility buttons which help the user by providing writing facilities.
 * @author Gabriele Graffieti
 *
 */
public enum UtilityButtons {
	/** a left parenthesis. */
	LPARENTHESIS("(", "(", "/org/converger/resources/icons/footer/left_par.png"),
	/** a right parenthesis. */
	RPARENTHESIS(")", ")", "/org/converger/resources/icons/footer/right_p.png"),
	/** the addition sing (+). */
	PLUS("+", "plus", "/org/converger/resources/icons/footer/plus.png"),
	/** the subtraction sign (-). */
	MINUS("-", "minus", "/org/converger/resources/icons/footer/minus.png"),
	/** the multiplication sign (*). */
	MUL("*", "multiply", "/org/converger/resources/icons/footer/mul.png"),
	/** the division sign (/). */
	DIV("/", "divide", "/org/converger/resources/icons/footer/div.png"),
	/** the equals sign (=). */
	EQUAL("=", "equals", "/org/converger/resources/icons/footer/equal.png"),
	/** Square the expression. */
	SQUARE("^2", "square", "/org/converger/resources/icons/footer/square.png"),
	/** Raise the expression to the nth power. */
	POW("^", "pow", "/org/converger/resources/icons/footer/pow.png"),
	/** the square root symbol. */
	SQRT("sqrt(", "square root", "/org/converger/resources/icons/footer/squareroot.png"),
	/** the absolute value symbol. */
	ABS("abs(", "absolute value", "/org/converger/resources/icons/footer/abs.png"),
	/** Facility for writing an exponential expression. */
	EXP("e^(", "exp", "/org/converger/resources/icons/footer/exp.png"),
	/** the natural logarithm symbol. */
	LN("ln(", "natural logarithm", "/org/converger/resources/icons/footer/log.png"),
	/** the sine symbol. */
	SIN("sin(", "sine", "/org/converger/resources/icons/footer/sine.png"),
	/** the cosine symbol. */
	COS("cos(", "cosine", "/org/converger/resources/icons/footer/cosine.png"),
	/** the tangent symbol. */
	TAN("tan(", "tangent", "/org/converger/resources/icons/footer/tangent.png"),
	/** the arcsine symbol. */
	ASIN("asin(", "arcsine", "/org/converger/resources/icons/footer/arcsine.png"),
	/** the arccosine symbol. */
	ACOS("acos(", "arccosine", "/org/converger/resources/icons/footer/arccosine.png"),
	/** the arctangent symbol. */
	ATAN("atan(", "arctangent", "/org/converger/resources/icons/footer/arctan.png"),
	/** The mathematical constant Pi. */
	PI("pi", "pi", "/org/converger/resources/icons/footer/pi.png"),
	/** The mathematical constant E. */
	E("e", "e", "/org/converger/resources/icons/footer/e.png");
	
	
	private final String symbol;
	private final String name;
	private final String path;
	
	private UtilityButtons(final String exprSymbol, final String exprName, final String iconPath) {
		this.symbol = exprSymbol;
		this.name = exprName;
		this.path = iconPath;
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
	
	/**
	 * Return the path of the button's icon.
	 * @return the path of the button's icon
	 */
	public String getIconPath() {
		return this.path;
	}
}


