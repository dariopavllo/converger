package org.converger.plot;

import java.awt.Color;

/**
 * A collections of constants generally used for setting graph property.
 * @author Gabriele Graffieti 
 */
public final class PlotConstants {
	/** The initial scale of the graph. */
	public static final double INITIAL_SCALE = 5.0;
	/** The length of a graph thick. */
	public static final int THICK_LENGTH = 5;
	/** The padding between a thick and the number. */
	public static final int THICK_PADDING = 10;
	/** the background color of the graph window. */
	public static final Color BACKGROUND_COLOR = Color.WHITE;
	/** The color of the function plotted. */
	public static final Color FUNCTION_COLOR = Color.RED;
	/** The color of the axes. */
	public static final Color AXES_COLOR = Color.BLACK;
	/** The width of the function line. */
	public static final float STROKE_WIDTH = 2.0f;
	/** The number of thicks in an axis. */
	public static final int TICKS = 10;
	/** The number of subdivision where the function is evaluated. */
	public static final int SUBDIVISIONS = 300;
	
	private PlotConstants() {
		
	}
}
