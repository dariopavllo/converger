package org.converger.plot;

/**
 * Represents the buttons in the plot window.
 * @author Gabriele Graffieti
 */
public enum PlotButton {
	/** Zoom out the graph. */
	ZOOM_OUT("") {

		@Override
		public void clickEvent(final GraphController controller) {
			controller.zoomOut();
		}
		
	},
	/** Zoom out the vertical axis. */
	VERTICAL_ZOOM_OUT("") {
		@Override
		public void clickEvent(final GraphController controller) {
			controller.zoomOutVertical();
		}
	},
	/** Zoom out the horizontal axis. */
	HORIZONTAL_ZOOM_OUT("") {
		@Override
		public void clickEvent(final GraphController controller) {
			controller.zoomOutHorizontal();
		}
	},
	/** Zoom in the graph. */
	ZOOM_IN("") {
		@Override
		public void clickEvent(final GraphController controller) {
			controller.zoomIn();
		}
	},
	/** Zoom in the vertical axis. */
	VERTICAL_ZOOM_IN("") {
		@Override
		public void clickEvent(final GraphController controller) {
			controller.zoomInVertical();
		}
	},
	/** Zoom in the horizontal axis. */
	HORIZONTAL_ZOOM_IN("") {
		@Override
		public void clickEvent(final GraphController controller) {
			controller.zoomInHorizontal();
		}
	};
	
	private final String iconPath;
	
	private PlotButton(final String iconP) {
		this.iconPath = iconP;
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