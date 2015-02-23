package org.converger.userinterface.gui;

import java.awt.Color;

/**
 * A collections of constants generally used for setting gui property.
 * @author Gabriele Graffieti 
 */
public final class GUIConstants {
	
	/** the default margin between gui components. */
	public static final int DEFAULT_MARGIN = 5;
	/**the default border between gui components. */
	public static final int DEFAULT_BORDER = 10;
	/** the preferred height of the gui. */
	public static final int PREFERRED_WIDTH = 800;
	/** the preferred height of the gui. */
	public static final int PREFERRED_HEIGHT = 600;
	/** the input line font. */
	public static final String INPUT_FONT = "Tahoma";
	/** the input line font size. */
	public static final int INPUT_FONT_SIZE = 16;
	/** the default background color of the latex visualizer. */
	public static final Color BACKGROUND_COLOR = Color.WHITE;
	/** the default color for an expression selected. */
	public static final Color SELECTION_COLOR = new Color(201, 240, 240);
	/** the default width of the row number box. */
	public static final int ROW_BOX_WIDTH = 50;
	
	private GUIConstants() {
		
	}
}
