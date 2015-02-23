package org.converger.plot;

/**
 * A controller which manage the zoom over a graph.
 * @author Gabriele Graffieti
 *
 */
public class GraphController {

	private double verticalScale;
	private double horizontalScale;
	private final PlotWindow graph;
	
	/**
	 * Builds the controller of the given plot window.
	 * @param pw the plot window to be controlled by this controller.
	 */
	public GraphController(final PlotWindow pw) {
		this.verticalScale = PlotConstants.INITIAL_SCALE;
		this.horizontalScale = PlotConstants.INITIAL_SCALE;
		this.graph = pw;
	}
	
	/**
	 * Zoom out the given plot window.
	 */
	public void zoomOut() {
		this.horizontalScale *= 2;
		this.verticalScale *= 2;
		this.replot();
	}
	
	/**
	 * Zoom out the vertical axis of the given plotWindow.
	 */
	public void zoomOutVertical() {
		this.verticalScale *= 2;
		this.replot();
	}
	
	/**
	 * Zoom out the horizontal axis of the given plotWindow.
	 */
	public void zoomOutHorizontal() {
		this.horizontalScale *= 2;
		this.replot();
	}
	
	/**
	 * Zoom in the given plot window.
	 */
	public void zoomIn() {
		this.horizontalScale /= 2;
		this.verticalScale /= 2;
		this.replot();
	}
	
	/**
	 * Zoom in the vertical axis of the given plotWindow.
	 */
	public void zoomInVertical() {
		this.verticalScale /= 2;
		this.replot();
	}
	
	/**
	 * Zoom in the horizontal axis of the given plotWindow.
	 */
	public void zoomInHorizontal() {
		this.horizontalScale /= 2;
		this.replot();
	}
	
	
	private void replot() {
		this.graph.setScale(this.horizontalScale, this.verticalScale);
		this.graph.plot();
		this.graph.repaint();
		this.graph.revalidate();
	}
	
}
