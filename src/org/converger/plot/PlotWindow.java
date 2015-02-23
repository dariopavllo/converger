package org.converger.plot;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.text.NumberFormat;
import java.util.function.DoubleFunction;

import javax.swing.JPanel;

/**
 * Represents a JPanel where a graph of a mathematical function will be plotted. 
 * @author Gabriele Graffieti
 * @author Dario Pavllo
 */
public class PlotWindow extends JPanel {

	private static final long serialVersionUID = 1779273767954189610L;

	private final NumberFormat labelFormat;
	private final Path2D path;
	private final DoubleFunction<Double> function;
	
	private Rectangle2D graphBounds;

	/**
	 * Constructs the panwl where the graph of the given function will be plotted.
	 * @param func the function to be plotted.
	 */
	public PlotWindow(final DoubleFunction<Double> func) {

		this.setScale(PlotConstants.INITIAL_SCALE, PlotConstants.INITIAL_SCALE);

		this.labelFormat = NumberFormat.getNumberInstance();
		this.labelFormat.setMaximumFractionDigits(2);

		this.path = new GeneralPath();
		this.function = func;
	}
	
	@Override
	public void paint(final Graphics plot) {
		final Graphics2D graph = (Graphics2D) plot;
		graph.setBackground(PlotConstants.BACKGROUND_COLOR);
		
		final AffineTransform newTrans = getTransform();

		// Enables antialiasing
		graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		// Clears the plot window
		graph.setColor(PlotConstants.BACKGROUND_COLOR);
		graph.fillRect(0, 0, (int) getBounds().getWidth(), (int) getBounds().getHeight());

		drawTicks(graph, newTrans);
		drawAxes(graph, newTrans);
		drawFunction(graph, newTrans);
	}
	
	/**
	 * Set the scale of the graph, horizontally and vertically.
	 * @param scaleX the horizontal scale.
	 * @param scaleY the vertical scale.
	 */
	public void setScale(final double scaleX, final double scaleY) {
		this.graphBounds = new Rectangle2D.Double(-1 * scaleX, -1 * scaleY, 2 * scaleX, 2 * scaleY);
	}

	/**
	 * Plot the graph into the window. 
	 * For every point in the interval calculates the value of the given function and plot the 
	 * track of the function. 
	 */
	public void plot() {
		this.path.reset();
		final double h = this.graphBounds.getWidth() / PlotConstants.SUBDIVISIONS;
		
		for (int i = 0; i <= PlotConstants.SUBDIVISIONS; i++) {
			final double x = this.graphBounds.getMinX() + h * i;
			final Point2D point = new Point2D.Double(x, this.function.apply(x));
			if (this.path.getCurrentPoint() == null) {
				this.path.moveTo(point.getX(), point.getY());
			} else {
				this.path.lineTo(point.getX(), point.getY());
			}
		}
	}
	
	private AffineTransform getTransform() {

		final AffineTransform transform = new AffineTransform(1, 0, 0, -1, 0,
				super.getParent().getHeight());

		transform.concatenate(AffineTransform.getScaleInstance(this.getBounds()
				.getWidth() / this.graphBounds.getWidth(), this.getBounds()
				.getHeight() / this.graphBounds.getHeight()));

		transform.concatenate(AffineTransform.getTranslateInstance(
				-this.graphBounds.getX(), -this.graphBounds.getY()));

		return transform;
	}

	private void drawFunction(final Graphics2D graph, final AffineTransform transform) {
		final AffineTransform originalTransform = graph.getTransform();
		
		// Sets the stroke to a fixed width, independent from the transform
		graph.setStroke(new Stroke() {
			@Override
			public Shape createStrokedShape(final Shape s) {
				final Stroke stroke = new BasicStroke(PlotConstants.STROKE_WIDTH);
				final Shape transformed = transform.createTransformedShape(s);
		        final Shape stroked = stroke.createStrokedShape(transformed);
				try {
					return transform.createInverse().createTransformedShape(stroked);
				} catch (final NoninvertibleTransformException e) {
					return stroke.createStrokedShape(s);
				}
		    }
		});
		
		graph.transform(transform);
		graph.setColor(PlotConstants.FUNCTION_COLOR);
		graph.draw(this.path);
		graph.setTransform(originalTransform);
	}

	private void drawAxes(final Graphics2D graph, final AffineTransform transform) {
		graph.setColor(PlotConstants.AXES_COLOR);

		final Point2D origin = transform.transform(new Point2D.Double(0, 0), null); // set the origin at the center of the window

		final Line2D xAxis = new Line2D.Double(getBounds().getMinX(),
				origin.getY(), getBounds().getMaxX(), origin.getY());

		final Line2D yAxis = new Line2D.Double(origin.getX(),
				getBounds().getMinY(), origin.getX(), getBounds().getMaxY());

		graph.draw(xAxis);
		graph.draw(yAxis);
	}

	private void drawTicks(final Graphics2D graph, final AffineTransform transform) {
		graph.setColor(PlotConstants.AXES_COLOR);

		final Point2D origin = transform.transform(new Point2D.Float(0, 0), null);
		final Point2D pt = new Point2D.Float();
		
		for (int i = 0; i <= PlotConstants.TICKS; i++) {
			if (i != PlotConstants.TICKS / 2) { // do not draw the origin thick
				final double x = this.graphBounds.getMinX() + i * this.graphBounds.getWidth() / PlotConstants.TICKS;
				
				pt.setLocation(x, 0);
				transform.transform(pt, pt);
				graph.drawLine((int) pt.getX(), (int) origin.getY() - PlotConstants.THICK_LENGTH,
						(int) pt.getX(), (int) origin.getY() + PlotConstants.THICK_LENGTH);
	
				final String label = this.labelFormat.format(x);
				graph.drawString(label, (float) pt.getX(),
						(float) origin.getY() - PlotConstants.THICK_PADDING);
			}
		}

		for (int i = 0; i <= PlotConstants.TICKS; i++) {
			if (i != PlotConstants.TICKS / 2) { // do not draw the origin thick
				final double y = this.graphBounds.getMinY() + i * this.graphBounds.getHeight() / PlotConstants.TICKS;
			
				pt.setLocation(0, y);
				transform.transform(pt, pt);
				graph.drawLine((int) origin.getX() - PlotConstants.THICK_LENGTH, (int) pt.getY(),
						(int) origin.getX() + PlotConstants.THICK_LENGTH, (int) pt.getY());
	
				final String label = this.labelFormat.format(y);
				graph.drawString(label, (float) origin.getX() + PlotConstants.THICK_PADDING,
						(float) pt.getY());
			}
		}
	}
}
