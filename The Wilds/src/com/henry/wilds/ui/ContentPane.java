package com.henry.wilds.ui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.henry.wilds.util.Constants;

/**
 * The main JPanel for the whole window
 * @author Samuel Henry
 * @since June 15, 2014
 * @version 1.0
 */
public class ContentPane extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private MainPane split;
	private Toolbar toolbar;
	
	/**
	 * Creates and adds a JPanel with all components
	 * in it to the main JFrame
	 * @param frame The main frame of the program
	 */
	public ContentPane(WildsUI frame) {
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setBackground(Constants.BACKGROUND);
		
		GridBagLayout layout = new GridBagLayout();
		layout.columnWidths = new int[] {20, 760, 20}; //Sum == WIDTH
		layout.rowHeights = new int[] {50, 500, 50}; //Sum == HEIGHT
		layout.columnWeights = new double[] {Double.MIN_VALUE, 1, Double.MIN_VALUE};
		layout.rowWeights = new double[] {Double.MIN_VALUE, 1, Double.MIN_VALUE};
		setLayout(layout);
		
		toolbar = new Toolbar(frame);
		GridBagConstraints toolbarConstraints = new GridBagConstraints();
		toolbarConstraints.fill = GridBagConstraints.BOTH;
		toolbarConstraints.gridx = 1;
		toolbarConstraints.gridy = 0;
		add(toolbar, toolbarConstraints);		
		
		split = new MainPane(frame);
		GridBagConstraints splitConstraints = new GridBagConstraints();
		splitConstraints.fill = GridBagConstraints.BOTH;
		splitConstraints.gridx = 1;
		splitConstraints.gridy = 1;
		add(split, splitConstraints);
	}
	
	/**
	 * Get the JSplitPane of the contentPane
	 * @return The JSplitPane
	 */
	public MainPane getSplit() {
		return split;
	}
	
	/**
	 * Sets the background color of everything in the content pane
	 * @param color The color to set the background to
	 */
	public void setBackgroundColor(Color color) {
		setBackground(color);
		
		split.setBackgroundColor(color);
		toolbar.setBackgroundColor(color);
	}
	
	/**
	 * Sets the font color of everything in the content pane
	 * @param color The color to set the font to
	 */
	public void setFontColor(Color color) {
		setForeground(color);
		
		split.setFontColor(color);
		toolbar.setFontColor(color);
	}
}