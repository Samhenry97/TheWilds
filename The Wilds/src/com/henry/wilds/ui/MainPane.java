package com.henry.wilds.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.InputMap;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import com.henry.wilds.util.Constants;

/**
 * This class is the JSplitPane that holds the actual finished view
 * of the dining hall with all the tables and counselors and such in it.
 * It contains a JScrollPane and JPanel for the view, and a JPanel for
 * the info of the table currently selected.
 * @author Samuel Henry
 * @version 1.0
 * @since June 15, 2014
 */
public class MainPane extends JSplitPane {
	private static final long serialVersionUID = 1L;
	
	private Info info;
	private ProjectView projView;
	
	/**
	 * Creates the main split pane that holds
	 * ProjectView and Info
	 * @param frame the frame that the main pane is in
	 */
	public MainPane(WildsUI frame) {
		super(JSplitPane.HORIZONTAL_SPLIT);
		setContinuousLayout(true);
		
		info = new Info();
		
		JScrollPane infoHolder = new JScrollPane(info);
		infoHolder.setMinimumSize(new Dimension(Constants.INFO_MIN_WIDTH + 20, Constants.INFO_MIN_HEIGHT));
		
		projView = new ProjectView(frame, info);
		info.setProjView(projView);
		
		JScrollPane projViewHolder = new JScrollPane(projView);
		projViewHolder.setMinimumSize(new Dimension(Constants.PROJ_VIEW_MIN_WIDTH, Constants.PROJ_VIEW_MIN_HEIGHT));
		
		//Disable arrow key scrolling in the scrollPane
		InputMap  actionMap = (InputMap) UIManager.getDefaults().get("ScrollPane.ancestorInputMap");
		actionMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), new AbstractAction() { //Down key scrolling
			public static final long serialVersionUID = 1L;
		    public void actionPerformed(ActionEvent e) {

		    }
		});
		actionMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), new AbstractAction() { //Up key scrolling
			public static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e) {
				
		    }
		});
		actionMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), new AbstractAction() { //Left key scrolling
			public static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e) {
				
		    }
		});
		actionMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), new AbstractAction() { //Right key scrolling
			public static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e) {
				
		    }
		});
		
		setLeftComponent(infoHolder);
		setRightComponent(projViewHolder);
	}
	
	/**
	 * Gets the ProjectView inside the MainPane
	 * @return projView in the MainPane
	 */
	public ProjectView getProjectView() {
		return projView;
	}
	
	/**
	 * Sets the background color of all components in main pane
	 * to a specified color
	 * @param color The color to change the background to
	 */
	public void setBackgroundColor(Color color) {
		info.setBackgroundColor(Constants.DARK_BACKGROUND);
	}
	
	/**
	 * Sets the font color of all components in the main pane
	 * to a specified color
	 * @param color The color to change the font to
	 */
	public void setFontColor(Color color) {
		info.setFontColor(color);
	}
}