package com.henry.wilds.ui.menu;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JMenuBar;
import javax.swing.KeyStroke;

import com.henry.wilds.ui.ProjectView;
import com.henry.wilds.ui.WildsUI;
import com.henry.wilds.util.Constants;
import com.henry.wilds.util.Images;

/**
 * The menu bar for the main window
 * @author Samuel Henry
 * @since June 20, 2014
 * @version 1.0
 */
public class MenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;
	
	//Menu accelerators
	public static String NEW_ACCEL = "control N"; //Ctrl+N
	public static String OPEN_ACCEL = "control O"; //Ctrl+O
	public static String SAVE_ACCEL = "control S"; //Ctrl+S
	public static String SAVE_AS_ACCEL = "control shift S"; //Ctrl+shift+S
	public static String EXIT_ACCEL = "control Q"; //Ctrl+Q
	public static String EDIT_COPY_ACCEL = "control C"; //Ctrl+C
	public static String EDIT_CUT_ACCEL = "control X"; //Ctrl+X
	public static String EDIT_PASTE_ACCEL = "control V"; //Ctrl+V
	public static String EDIT_COUNS_ACCEL = "control alt C"; //Ctrl+alt+C
	public static String EDIT_TEAM_ACCEL = "control alt T"; //Ctrl+alt+T
	public static String NEW_COUNS_ACCEL = "control shift C"; //Ctrl+shift+C
	public static String NEW_TEAM_ACCEL = "control shift T"; //Ctrl+shift+T
	public static String NEW_TABLE_ACCEL = "control shift N"; //Ctrl+shift+N
	public static String WIN_PREF_ACCEL = "control shift P"; //Ctrl+shift+P
	public static String WIN_GPREF_ACCEL = "control alt P"; //Ctrl+alt+P
	public static String WIN_ZOOM_RESET_ACCEL = "control R"; //Ctrl+R
	
	//Menu mnemonics
	private static char FILE_MN = 'F';
		private static char NEW_MN = 'N';
		private static char OPEN_MN = 'O';
		private static char SAVE_MN = 'S';
		private static char SAVE_AS_MN = 'A';
		private static char EXIT_MN = 'Q';
	private static char EDIT_MN = 'E';
		private static char EDIT_COPY_MN = 'C';
		private static char EDIT_CUT_MN = 'U';
		private static char EDIT_PASTE_MN = 'P';
		private static char EDIT_COUNS_MN = 'O';
		private static char EDIT_TEAM_MN = 'T';
	private static char ADD_MN = 'A';
		private static char ADD_COUNS_MN = 'C';
		private static char ADD_TEAM_MN = 'T';
		private static char ADD_TABLE_MN = 'E';
	private static char WINDOW_MN = 'W';
		private static char WIN_ZOOM_IN_MN = 'I';
		private static char WIN_ZOOM_OUT_MN = 'O';
		private static char WIN_ZOOM_RESET_MN = 'R';
		private static char WIN_PREF_MN = 'P';
		private static char WIN_GPREF_MN = 'G';
	
	private WildsUI frame;
	
	/**
	 * Creates the menu bar and adds it to the frame
	 * @param frame
	 */
	public MenuBar(WildsUI frame) {
		this.frame = frame;
		createMenu();
	}
	
	/**
	 * Actual code for creating all the Menus
	 * and MenuItems
	 */
	public void createMenu() {
		frame.setJMenuBar(this);

		// File menu **********************************************************
		Menu fileMenu = new Menu("File", FILE_MN, this);
		{
			MenuItem fileNew = new MenuItem("New Project...", NEW_MN, NEW_ACCEL, fileMenu);
			fileNew.setIcon(new ImageIcon(Images.NEW_ICON));
			fileNew.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frame.fileOps.newFile();
				}
			});
			
			MenuItem fileOpen = new MenuItem("Open...", OPEN_MN, OPEN_ACCEL, fileMenu);
			fileOpen.setIcon(new ImageIcon(Images.OPEN_ICON));
			fileOpen.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frame.fileOps.openFile();
				}
			});
			
			fileMenu.addSeparator();
			
			MenuItem fileSave = new MenuItem("Save", SAVE_MN, SAVE_ACCEL, fileMenu);
			fileSave.setIcon(new ImageIcon(Images.SAVE_ICON));
			fileSave.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frame.fileOps.saveChanges();
				}
			});
		
			MenuItem fileSaveAs = new MenuItem("Save As...", SAVE_AS_MN, SAVE_AS_ACCEL, fileMenu);
			fileSaveAs.setIcon(new ImageIcon(Images.SAVE_AS_ICON));
			fileSaveAs.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frame.fileOps.saveAs();
				}
			});
			
			fileMenu.addSeparator();
			
			MenuItem fileExit = new MenuItem("Exit", EXIT_MN, EXIT_ACCEL, fileMenu);
			fileExit.setIcon(new ImageIcon(Images.EXIT_ICON));
			fileExit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frame.quit();
				}
			});
		}
		// End file menu****************************************************

		// Edit menu *******************************************************
		Menu editMenu = new Menu("Edit", EDIT_MN, this);
		{
			
			MenuItem editCopy = new MenuItem("Copy", EDIT_COPY_MN, EDIT_COPY_ACCEL, editMenu);
			editCopy.setIcon(new ImageIcon(Images.COPY_ICON));
			editCopy.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frame.getContentPane().getSplit().getProjectView().copy();
				}
			});
			
			MenuItem editCut = new MenuItem("Cut", EDIT_CUT_MN, EDIT_CUT_ACCEL, editMenu);
			editCut.setIcon(new ImageIcon(Images.CUT_ICON));
			editCut.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frame.getContentPane().getSplit().getProjectView().cut();
				}
			});
			
			MenuItem editPaste = new MenuItem("Paste", EDIT_PASTE_MN, EDIT_PASTE_ACCEL, editMenu);
			editPaste.setIcon(new ImageIcon(Images.PASTE_ICON));
			editPaste.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frame.getContentPane().getSplit().getProjectView().paste();
				}
			});
			
			MenuItem editEditCouns = new MenuItem("Counselor...", EDIT_COUNS_MN, EDIT_COUNS_ACCEL, editMenu);
			editEditCouns.setIcon(new ImageIcon(Images.EDIT_COUNS_ICON));
			editEditCouns.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frame.editCouns.setVisible();
				}
			});

			MenuItem editEditTeam = new MenuItem("Team...", EDIT_TEAM_MN, EDIT_TEAM_ACCEL, editMenu);
			editEditTeam.setIcon(new ImageIcon(Images.EDIT_TEAM_ICON));
			editEditTeam.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frame.editTeam.setVisible();
				}
			});
		}
		// End edit menu *************************************************

		// Add menu ************************************************
		Menu addMenu = new Menu("Add", ADD_MN, this);
		{
			MenuItem addCouns = new MenuItem("Counselor...", ADD_COUNS_MN, NEW_COUNS_ACCEL, addMenu);
			addCouns.setIcon(new ImageIcon(Images.ADD_COUNS_ICON));
			addCouns.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frame.addCouns.setVisible();
				}
			});

			MenuItem addTeam = new MenuItem("Team...", ADD_TEAM_MN, NEW_TEAM_ACCEL, addMenu);
			addTeam.setIcon(new ImageIcon(Images.ADD_TEAM_ICON));
			addTeam.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frame.addTeam.setVisible();
				}
			});
			
			MenuItem addTable = new MenuItem("Table...", ADD_TABLE_MN, NEW_TABLE_ACCEL, addMenu);
			addTable.setIcon(new ImageIcon(Images.ADD_TABLE_ICON));
			addTable.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frame.getContentPane().getSplit().getProjectView().addNewTable();
				}
			});
		}
		// End add menu **************************************************
		Menu windowMenu = new Menu("Window", WINDOW_MN, this);
		{
			MenuItem windowZoomIn = new MenuItem("Zoom In", WIN_ZOOM_IN_MN, windowMenu);
			windowZoomIn.setIcon(new ImageIcon(Images.ZOOM_IN_ICON));
			windowZoomIn.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, Event.CTRL_MASK));
			windowZoomIn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ProjectView pv = frame.getProjView();
					
					pv.addZoom(Constants.ZOOM_AMT);
				}
			});
			
			MenuItem windowZoomOut = new MenuItem("Zoom Out", WIN_ZOOM_OUT_MN, windowMenu);
			windowZoomOut.setIcon(new ImageIcon(Images.ZOOM_OUT_ICON));
			windowZoomOut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, Event.CTRL_MASK));
			windowZoomOut.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ProjectView pv = frame.getProjView();
					
					pv.addZoom(-Constants.ZOOM_AMT);
				}
			});
			
			MenuItem windowZoomReset = new MenuItem("Reset Zoom", WIN_ZOOM_RESET_MN, WIN_ZOOM_RESET_ACCEL, windowMenu);
			windowZoomReset.setIcon(new ImageIcon(Images.ZOOM_RESET_ICON));
			windowZoomReset.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ProjectView pv = frame.getProjView();
					
					pv.resetZoom();
				}
			});
			
			MenuItem windowPreferences = new MenuItem("Preferences...", WIN_PREF_MN, WIN_PREF_ACCEL, windowMenu);
			windowPreferences.setIcon(new ImageIcon(Images.PREFS_ICON));
			windowPreferences.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frame.prefs.setVisible();
				}
			});
			
			MenuItem windowGlobalPreferences = new MenuItem("Global Preferences...", WIN_GPREF_MN, WIN_GPREF_ACCEL, windowMenu);
			windowGlobalPreferences.setIcon(new ImageIcon(Images.G_PREFS_ICON));
			windowGlobalPreferences.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frame.gPrefs.setVisible();
				}
			});
		}
	}
}