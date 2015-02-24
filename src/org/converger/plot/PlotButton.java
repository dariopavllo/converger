package org.converger.plot;

/**
 * Represents the buttons in the plot window.
 * @author Gabriele Graffieti
 */
public enum PlotButton {
	/** Zoom out the graph. */
	ZOOM_OUT("zoom out", "/org/converger/resources/icons/plot/zoomOut.png") {

		@Override
		public void clickEvent(final GraphController controller) {
			controller.zoomOut();
		}
		
	},
	/** Zoom out the vertical axis. */
	VERTICAL_ZOOM_OUT("zoom out vertically", "/org/converger/resources/icons/plot/zoomOutV.png") {
		@Override
		public void clickEvent(final GraphController controller) {
			controller.zoomOutVertical();
		}
	},
	/** Zoom out the horizontal axis. */
	HORIZONTAL_ZOOM_OUT("zoom out horizontally", "/org/converger/resources/icons/plot/zoomOutH.png") {
		@Override
		public void clickEvent(final GraphController controller) {
			controller.zoomOutHorizontal();
		}
	},
	/** Zoom in the graph. */
	ZOOM_IN("zoom in", "/org/converger/resources/icons/plot/zoomIn.png") {
		@Override
		public void clickEvent(final GraphController controller) {
			controller.zoomIn();
		}
	},
	/** Zoom in the vertical axis. */
	VERTICAL_ZOOM_IN("zoom in vertically", "/org/converger/resources/icons/plot/zoomInV.png") {
		@Override
		public void clickEvent(final GraphController controller) {
			controller.zoomInVertical();
		}
	},
	/** Zoom in the horizontal axis. */
	HORIZONTAL_ZOOM_IN("zoom in horizontally", "/org/converger/resources/icons/plot/zoomInH.png") {
		@Override
		public void clickEvent(final GraphController controller) {
			controller.zoomInHorizontal();
		}
	};
	
	private final String name;
	private final String iconPath;
	
	private PlotButton(final String buttonName, final String iconP) {
		this.name = buttonName;
		this.iconPath = iconP;
		
	}
	
	/**
	 * Return the name of the button.
	 * @return the name of the button
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Returns the icon path of the button.
	 * @return the icon path of the button
	 */
	public String getIconPath() {
		return this.iconPath;
	}
	
	/**
	 * The method called when a button is pressed.
	 * @param controller the {@GraphController} of the current plot window.
	 */
	public abstract void clickEvent(GraphController controller);
}