package com.henry.wilds.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.henry.wilds.inner.FileOps;
import com.henry.wilds.ui.dialog.AddCounselor;
import com.henry.wilds.ui.dialog.AddTeam;
import com.henry.wilds.ui.dialog.EditCounselor;
import com.henry.wilds.ui.dialog.EditTeam;
import com.henry.wilds.ui.dialog.GlobalPreferences;
import com.henry.wilds.ui.dialog.Preferences;
import com.henry.wilds.ui.menu.MenuBar;
import com.henry.wilds.util.Constants;
import com.henry.wilds.util.Images;
import com.henry.wilds.util.Util;

/**
 * This is the class for the main window of the
 * whole program. It contains all the dialogs,
 * and the user interface.
 * @author Samuel Henry
 * @since June 15, 2014
 * @version 1.0
 */
public class WildsUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private ContentPane contentPane;
	
	public AddCounselor addCouns;
	public AddTeam addTeam;
	public EditCounselor editCouns;
	public EditTeam editTeam;
	public Preferences prefs;
	public GlobalPreferences gPrefs;
	
	public FileOps fileOps;

	/**
	 * Creates the actual WildsUI frame and all of its components
	 */
	public WildsUI() {
		Util.setLookAndFeelToSystem();
		
		Images.loadImages();
		
		loadData();
		
		createWindow();
		
		fileOps.loadFile();
	}
	
	/**
	 * Loads the init file, and loads the last
	 * project edited, if there is one
	 */
	public void loadData() {
		fileOps = new FileOps(this);
		fileOps.init();
	}

	/**
	 * Creates the actual window and components
	 */
	public void createWindow() {
		System.out.println("Creating GUI.");
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		setSize(Constants.WIDTH, Constants.HEIGHT);
		setLocationRelativeTo(null);
		if(Constants.MAXIMIZED) setExtendedState(getExtendedState() | MAXIMIZED_BOTH);
		
		setMinimumSize(new Dimension(Constants.MIN_WIDTH, Constants.MIN_HEIGHT));
		setTitle(Constants.TITLE);
		setIconImage(Images.ICON);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				quit();
			}
		});
		
		addWindowStateListener(new WindowStateListener() {
			public void windowStateChanged(WindowEvent e) {
				int state = e.getNewState();
				if(state == NORMAL) Constants.MAXIMIZED = false;
				else if((state & MAXIMIZED_BOTH) == MAXIMIZED_BOTH) Constants.MAXIMIZED = true;
			}
		});
		
		System.out.println("Loading content pane.");
		contentPane = new ContentPane(this);
		System.out.println("Setting content pane.");
		setContentPane(contentPane);
		
		System.out.println("Creating dialog boxes.");
		createDialogs();
		
		System.out.println("Creating menu bar.");
		createMenu();
	}
	
	/**
	 * Creates the menu bar of the window
	 */
	public void createMenu() {
		new MenuBar(this);
	}
	
	/**
	 * Creates the pop-up boxes used in the program
	 */
	public void createDialogs() {
		addCouns = new AddCounselor(this, getProjView());
		addTeam = new AddTeam(this, getProjView());
		editCouns = new EditCounselor(this, getProjView());
		editTeam = new EditTeam(this, getProjView());
		prefs = new Preferences(this, getProjView());
		gPrefs = new GlobalPreferences(this, getProjView());
	}
	
	/**
	 * Quits the program, saving if asked
	 */
	public void quit() {
		int save = JOptionPane.showConfirmDialog(this, "Do you want to save the changes to this project?", "Close", JOptionPane.YES_NO_CANCEL_OPTION);
		
		switch(save) {
			case 0 : boolean exit = fileOps.saveChanges();
					  if(exit) break;
					  else return;
			case 1 : dispose(); break;
			case 2 : return;
			case -1 : return;
			default : System.out.println("WHUH-OH!! Mistake in closing window!");
		}
		
		fileOps.saveInitFile();
		
		System.exit(0);
	}
	
	/**
	 * Gets the main contentPane of the window
	 * @return The contentPane
	 */
	public ContentPane getContentPane() {
		return contentPane;
	}
	
	/**
	 * Gets the Project View of the window
	 * @return The ProjectView
	 */
	public ProjectView getProjView() {
		return contentPane.getSplit().getProjectView();
	}
	
	/**
	 * Sets the background of everything in the program
	 */
	public void setBackgroundColor(Color color) {
		System.out.println("Setting background to rgb(" + color.getRed() + ", " + color.getGreen() + ", " + color.getBlue() + ")");
		
		contentPane.setBackgroundColor(color);
		
		addCouns.setBackgroundColor(color);
		addTeam.setBackgroundColor(color);
		editCouns.setBackgroundColor(color);
		editTeam.setBackgroundColor(color);
		prefs.setBackgroundColor(color);
		gPrefs.setBackgroundColor(color);
	}
	
	/**
	 * Sets the font color of everything in the program
	 * @param color
	 */
	public void setFontColor(Color color) {
		System.out.println("Setting foreground to rgb(" + color.getRed() + ", " + color.getGreen() + ", " + color.getBlue() + ")");
		
		contentPane.setFontColor(color);
		
		addCouns.setFontColor(color);
		addTeam.setFontColor(color);
		editCouns.setFontColor(color);
		editTeam.setFontColor(color);
		prefs.setFontColor(color);
		gPrefs.setFontColor(color);
	}
}