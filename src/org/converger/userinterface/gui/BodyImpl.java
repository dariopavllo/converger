package org.converger.userinterface.gui;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
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

/**
 * Creates the middle part of the gui, with the latex text visualizer.
 * @author Gabriele Graffieti
 */
public class BodyImpl implements Body {
	private final JPanel mainPanel;
	private final JPanel scrollPanel;
	private int panelSelected;
	private final List<JPanel> panelList = new ArrayList<>();
	
	/**
	 * Create the body.
	 */
	public BodyImpl() {
		this.panelSelected = -1;
		
		this.mainPanel = new JPanel(new BorderLayout());
		
		scrollPanel = new JPanel();
		scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.Y_AXIS));
		scrollPanel.setBackground(Color.WHITE);

		final JScrollPane scroll = new JScrollPane(scrollPanel);
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
	public void drawNewExpression(final String latexExpression) {
		this.newRow(this.createLatexPanel(latexExpression, this.panelList.size()));
		this.mainPanel.validate();
	}
	
	@Override
	public void editExpression(final int index, final String latexExpression) {
		this.createLatexPanel(latexExpression, index);
		this.redraw();
	}
	
	@Override
	public void deleteExpression(final int index) {
		this.panelList.remove(index);
		this.setSelected(-1);
		this.redraw();
	}
	
	@Override
	public void deleteAll() {
		this.panelList.clear();
		this.setSelected(-1);
		this.redraw();
	}
	
	private JPanel createLatexPanel(final String latexString, final int index) {
		final TeXFormula formula = new TeXFormula(latexString);
		final TeXIcon icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, 20);
		// now create an actual image of the rendered equation
		final BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2 = image.createGraphics();
		g2.setColor(new Color(1, 1, 1, 0));
		g2.fillRect(0, 0, icon.getIconWidth(), icon.getIconHeight());
		final JLabel jl = new JLabel();
		jl.setForeground(new Color(0, 0, 0));
		icon.paintIcon(jl, g2, 0, 0);
		
		@SuppressWarnings("serial")
		final JPanel panel = new JPanel() {
			@Override
            public void paintComponent(final Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, null);
            }
        };
        if (index < this.panelList.size()) {
        	this.panelList.remove(index);
        }
        this.panelList.add(index, panel);
        panel.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(final MouseEvent e) {
        		for (final JPanel p : panelList) {
        			p.setBackground(Color.WHITE);
        		}
        		panel.setBackground(Color.CYAN);
        		setSelected(panelList.indexOf(panel));
        		//System.out.println(getSelected());
        	}
        });
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        panel.setMinimumSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
		return panel;
	}
	
	private void newRow(final JPanel latexPanel) {
		final JPanel rowPanel = new JPanel(new BorderLayout());
		rowPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		final JLabel rowNumberLabel = new JLabel("#" + Integer.toString(this.panelList.indexOf(latexPanel) + 1));
		rowNumberLabel.setPreferredSize(new Dimension(50, rowNumberLabel.getPreferredSize().height + 20));
		rowPanel.add(rowNumberLabel, BorderLayout.WEST);
		rowPanel.add(latexPanel);
		rowPanel.setBackground(Color.WHITE);
		rowPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		rowPanel.setMaximumSize(new Dimension(rowPanel.getMaximumSize().width, latexPanel.getPreferredSize().height + 20));
		rowPanel.setPreferredSize(new Dimension(rowPanel.getPreferredSize().width, latexPanel.getPreferredSize().height + 20));
        this.scrollPanel.add(rowPanel);
	}
	
	private void setSelected(final int index) {
		this.panelSelected = index;
	}
	
	private void redraw() {
		this.scrollPanel.removeAll();
		for (final JPanel p : this.panelList) {
			this.newRow(p);
		}
	}
}
