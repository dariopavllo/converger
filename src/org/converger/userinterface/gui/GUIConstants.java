package org.converger.userinterface.gui;

/**
 * A collections of constants generally used for setting gui property.
 * @author Gabriele Graffieti 
 */
public final class GUIConstants {
	
	private static final int DEFAULT_MARGIN = 5;
	private static final int DEFAULT_BORDER = 10;
	private static final int PREFERRED_WIDTH = 800;
	private static final int PREFERRED_HEIGHT = 600;
	private static final String INPUT_FONT = "Tahoma";
	private static final int INPUT_FONT_SIZE = 16;
	
	private GUIConstants() {
		
	}
	
	/** @return the default margin for gui components. */
	public static int getDefaultMargin() {
		return DEFAULT_MARGIN;
	}
	
	/** @return the default border for gui components. */
	public static int getDefaultBorder() {
		return DEFAULT_BORDER;
	}
	
	/** @return the preferred height of the gui */
	public static int getPreferredHeight() {
		return PREFERRED_HEIGHT;
	}
	
	/** @return the preferred width of the gui */
	public static int getPreferredWidth() {
		return PREFERRED_WIDTH;
	}
	
	/** @return the input line font size. */
	public static int getInputFontSize() {
		return INPUT_FONT_SIZE;
	}
	
	/** @return the input line font. */
	public static String getInputFont() {
		return INPUT_FONT;
	}

}
