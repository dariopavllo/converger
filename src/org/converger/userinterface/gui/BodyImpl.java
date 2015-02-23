package org.converger.userinterface.gui;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.converger.controller.exception.NoElementSelectedException;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Creates the middle part of the gui, with the latex text visualizer.
 * @author Gabriele Graffieti
 */
public class BodyImpl implements Body {
	private final JPanel mainPanel;
	private final JPanel scrollPanel;
	private int panelSelected;
	private final List<JPanel> panelList = new ArrayList<>();
	private final List<Optional<String>> opList = new ArrayList<>();
	private final JScrollBar scrollBar;
	
	/**
	 * Create the body.
	 */
	public BodyImpl() {
		this.panelSelected = -1;
		
		this.mainPanel = new JPanel(new BorderLayout());
		
		scrollPanel = new JPanel();
		scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.Y_AXIS));
		scrollPanel.setBackground(GUIConstants.BACKGROUND_COLOR);

		final JScrollPane scroll = new JScrollPane(scrollPanel);
		//scroll.getVerticalScrollBar().addAdjustmentListener(e->e.getAdjustable().setValue(e.getAdjustable().getMaximum()));
		this.scrollBar = scroll.getVerticalScrollBar();
		
		this.mainPanel.add(scroll, BorderLayout.CENTER);
	}

	@Override
	public JPanel getMainPanel() {
		return this.mainPanel;
	}
	
	@Override
	public int getSelected() throws NoElementSelectedException {
		if (this.panelSelected >= 0) {
			return this.panelSelected;
		} else {
			throw new NoElementSelectedException("No expression selected");
		}
	}
	
	@Override
	public void drawNewExpression(final String latexExpression, final Optional<String> op) {
		this.newRow(this.createLatexPanel(latexExpression, this.panelList.size(), op), op);
		this.mainPanel.validate();
		this.scrollBar.setValue(scrollBar.getMaximum());
	}
	
	@Override
	public void editExpression(final int index, final String latexExpression) {
		this.createLatexPanel(latexExpression, index, Optional.empty());
		this.redraw();
	}
	
	@Override
	public void deleteExpression(final int index) {
		this.panelList.remove(index);
		this.opList.remove(index);
		this.setSelected(index - 1);
		this.redraw();
	}
	
	@Override
	public void deleteAll() {
		this.panelList.clear();
		this.opList.clear();
		this.setSelected(-1);
		this.redraw();
	}
	
	private JPanel createLatexPanel(final String latexString, final int index, final Optional<String> op) {
		final TeXFormula formula = new TeXFormula(latexString);
		final TeXIcon icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, 22);
		// now create an actual image of the rendered equation
		final BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2 = image.createGraphics();
		g2.setColor(new Color(1, 1, 1, 0));
		g2.fillRect(0, 0, icon.getIconWidth(), icon.getIconHeight());
		final JLabel jl = new JLabel();
		jl.setForeground(new Color(0, 0, 0));
		icon.paintIcon(jl, g2, 0, 0);
		// now create the panel which contains the latex rendering
		@SuppressWarnings("serial")
		final JPanel panel = new JPanel() {
			@Override
            public void paintComponent(final Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, null);
            }
        };
        if (index < this.panelList.size()) { // if it isn't a new equation
        	this.panelList.remove(index);
        	this.opList.remove(index);
        }
        this.panelList.add(index, panel);
        this.opList.add(index, op);
        panel.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mousePressed(final MouseEvent e) { // click on the panel
        		setSelected(panelList.indexOf(panel));
        	}
        });
        this.setSelected(panelList.indexOf(panel));
        panel.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        panel.setMinimumSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
		return panel;
	}
	
	private void newRow(final JPanel latexPanel, final Optional<String> op) {
		final JPanel rowPanel = new JPanel(new BorderLayout(GUIConstants.DEFAULT_BORDER, GUIConstants.DEFAULT_BORDER));
		rowPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		final JLabel rowNumberLabel = new JLabel("#" + Integer.toString(this.panelList.indexOf(latexPanel) + 1));
		rowNumberLabel.setPreferredSize(new Dimension(GUIConstants.ROW_BOX_WIDTH, 
				rowNumberLabel.getPreferredSize().height + (GUIConstants.DEFAULT_BORDER * 2))); // top and bottom border 
		rowPanel.add(rowNumberLabel, BorderLayout.WEST);
		rowPanel.add(latexPanel, BorderLayout.CENTER);
		if (op.isPresent()) {
			final JLabel opLabel = new JLabel(op.get());
			rowPanel.add(opLabel, BorderLayout.EAST);
			rowNumberLabel.setForeground(Color.BLUE);
		}
		rowPanel.setBackground(GUIConstants.BACKGROUND_COLOR);
		rowPanel.setBorder(new EmptyBorder(GUIConstants.DEFAULT_BORDER, GUIConstants.DEFAULT_BORDER,
				GUIConstants.DEFAULT_BORDER, GUIConstants.DEFAULT_BORDER));
		rowPanel.setMaximumSize(new Dimension(rowPanel.getMaximumSize().width, 
				latexPanel.getPreferredSize().height + (GUIConstants.DEFAULT_BORDER * 2)));
		rowPanel.setPreferredSize(new Dimension(rowPanel.getPreferredSize().width, 
				latexPanel.getPreferredSize().height + (GUIConstants.DEFAULT_BORDER * 2)));
        this.scrollPanel.add(rowPanel);
	}
	
	private void setSelected(final int index) {
		this.panelSelected = index;
		this.panelList.forEach(p->p.setBackground(GUIConstants.BACKGROUND_COLOR));
		if (index >= 0) {
			this.panelList.get(index).setBackground(GUIConstants.SELECTION_COLOR);
		}
	}
	
	private void redraw() {
		this.scrollPanel.removeAll();
		for (final JPanel p : this.panelList) {
			this.newRow(p, this.opList.get(this.panelList.indexOf(p)));
		}
		this.scrollPanel.revalidate();
		this.scrollPanel.repaint();
	}
}
