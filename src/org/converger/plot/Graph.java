package org.converger.plot;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.function.DoubleFunction;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.converger.userinterface.gui.GUIConstants;

/**
 * Represents the window of the expression graph.
 * It has the {@link PlotWindow} and buttons to zoom over the graph.
 * @author Gabriele Graffieti
 *
 */
public class Graph {

	private final JFrame frame;
	
	/**
	 * Constructs the graph window.
	 * @param function the function to be plotted.
	 */
	public Graph(final DoubleFunction<Double> function) {
		this.frame = new JFrame("Plot window");
		this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.frame.setSize(GUIConstants.PREFERRED_WIDTH, GUIConstants.PREFERRED_HEIGHT);
		
		final PlotWindow pw = new PlotWindow(function);
		final GraphController controller = new GraphController(pw);
		
		final JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(new EmptyBorder(GUIConstants.DEFAULT_BORDER, GUIConstants.DEFAULT_BORDER, 
				GUIConstants.DEFAULT_BORDER, GUIConstants.DEFAULT_BORDER));
		
		
		final JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		for (final PlotButton b : PlotButton.values()) {
			final JButton button = new JButton();
			button.setIcon(new ImageIcon(Graph.class.getResource(b.getIconPath())));
			button.setToolTipText(b.getName());
			button.addActionListener(e->b.clickEvent(controller));
			buttonsPanel.add(button);
		}
		
		mainPanel.add(buttonsPanel, BorderLayout.NORTH);
		mainPanel.add(pw, BorderLayout.CENTER);
		this.frame.getContentPane().add(mainPanel);
		pw.plot();
		
	}
	
	/**
	 * Shows the graph window.
	 */
	public void show() {
		this.frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Full screen
		this.frame.setVisible(true);
	}
	
}
