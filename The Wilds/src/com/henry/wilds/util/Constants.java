package com.henry.wilds.util;

import java.awt.Color;
import java.awt.Font;

/**
 * This class contains all the constants for the program
 * @author Samuel Henry
 * @since June 15, 2014
 * @version 1.0
 */
public class Constants {
	//*****************************************************************************************************************************
	//Window or General constants
	
	/**
	 * This constant is the starting width of the frame. It is overridden
	 * if the init file is made.
	 */
	public static int WIDTH = 800;
	
	/**
	 * This is the starting height of the frame. It is overridden
	 * if the init file is made.
	 */
	public static int HEIGHT = 600;
	
	/**
	 * This is the minimum width of the frame.
	 */
	public static int MIN_WIDTH = 640;
	
	/**
	 * Minimum height of the frame.
	 */
	public static int MIN_HEIGHT = 480;
	
	/**
	 * Whether or not the frame is maximized
	 */
	public static boolean MAXIMIZED = false;
	
	/**
	 * The background color of the frame
	 */
	public static Color BACKGROUND = new Color(50, 50, 50);
	
	/**
	 * The dark background color (color of the Info JPanel)
	 */
	public static Color DARK_BACKGROUND = BACKGROUND.darker().darker();
	
	/**
	 * The foreground or font color of the frame
	 */
	public static Color FONT_COLOR = new Color(0, 255, 0);
	
	/**
	 * The title of the frame
	 */
	public static String TITLE = "Wilds Dining Hall";
	
	/**
	 * The directory for images and icons
	 */
	public static String IMAGE_DIR = "/images/";
	
	/**
	 * The name of the init file
	 */
	public static String INIT_FILE_NAME = "wilds.ini";
	
	/**
	 * The amount the pasted items on the clipboard are offset
	 */
	public static int CLIPBOARD_OFFS = 30;
	
	/**
	 * The message in the JOptionPane for if the text entered into
	 * a JTextField contains invalid characters
	 */
	public static String INVALID_CHAR_SEQ_MSG = " cannot contain non-alphanumeric characters\n( /, ', \", =, (, [, etc...)";
	
	//*****************************************************************************************************************************
	//Table constants
	
	/**
	 * The default width of a table when it is created
	 */
	public static int TABLE_WIDTH = 100;
	
	/**
	 * The default height of a table when it is created
	 */
	public static int TABLE_HEIGHT = 50;
	
	/**
	 * The maximum width of a table
	 */
	public static int TABLE_MAX_WIDTH = 1000;
	
	/**
	 * The maximum height of a table
	 */
	public static int TABLE_MAX_HEIGHT = 1000;
	
	/**
	 * The speed of a table when arrow keys are used to move it
	 */
	public static int TABLE_SPEED = 3;
	
	/**
	 * The maximum speed of arrow key table movement
	 */
	public static int TABLE_MAX_SPEED = 10;
	
	/**
	 * The color of a table when it is selected
	 */
	public static Color TABLE_COLOR = new Color(150, 50, 50);
	
	/**
	 * The distance from a table's x position and y position
	 * to check if another table's x and y position are close to it.
	 * If it is close to it, the table's x and y position will be set
	 * to the other table's x and y position
	 */
	public static int SNAP_TO_CHECK = 15;
	
	//*****************************************************************************************************************************
	//UI constants
	
	/**
	 * The default dialog box width
	 */
	public static int STD_DIALOG_WIDTH = 300;
	
	/**
	 * The default dialog box height
	 */
	public static int STD_DIALOG_HEIGHT = 400;
	
	/**
	 * The default text field width
	 */
	public static int TEXT_FIELD_WIDTH = 130;
	
	/**
	 * The default title for a dialog box
	 */
	public static String DEFAULT_DIALOG_TITLE = "Message";
	
	/**
	 * The default width of a JSpinner
	 */
	public static int SPINNER_WIDTH = 75;
	
	//*****************************************************************************************************************************
	//ProjectView constants
	
	/**
	 * The maximum fps of the ProjectView
	 */
	public static int FRAME_CAP = 120;
	
	/**
	 * The starting ProjectView width
	 */
	public static int PROJ_VIEW_WIDTH = 1000;
	
	/**
	 * The starting ProjectView height
	 */
	public static int PROJ_VIEW_HEIGHT = 1000;
	
	/**
	 * The minimum ProjectView width
	 */
	public static int PROJ_VIEW_MIN_WIDTH = 800;
	
	/**
	 * The minimum ProjectView height
	 */
	public static int PROJ_VIEW_MIN_HEIGHT = 800;
	
	/**
	 * The maximum width of the ProjectView
	 */
	public static int PROJ_VIEW_MAX_WIDTH = 5000;
	
	/**
	 * The maximum height of the ProjectView
	 */
	public static int PROJ_VIEW_MAX_HEIGHT = 5000;
	
	/**
	 * The background color of the ProjectView
	 */
	public static Color PROJ_VIEW_BACK_COLOR = new Color(50, 150, 200);
	
	/**
	 * The font of the ProjectView
	 */
	public static Font PROJ_VIEW_FONT = new Font("Arial", Font.PLAIN, 12);
	
	/**
	 * Whether or not to show fps and such
	 */
	public static boolean SHOW_ADDITIONAL_INFO = false;
	
	/**
	 * The maximum zoom
	 */
	public static int MAX_ZOOM = 300;
	
	/**
	 * The minimum zoom
	 */
	public static int MIN_ZOOM = 30;
	
	/**
	 * The amount the zoom changes when the user zooms
	 * in or out.
	 */
	public static int ZOOM_AMT = 20;
	
	//*****************************************************************************************************************************
	//Info constants
	
	/**
	 * The minimum width of the info JPanel
	 */
	public static int INFO_MIN_WIDTH = 200;
	
	/**
	 * The minimum height of the info JPanel
	 */
	public static int INFO_MIN_HEIGHT = 400;
	
	//*****************************************************************************************************************************
	//Listener constants
	
	/**
	 * The total number of possible key combinations
	 */
	public static int TOTAL_KEY_COMBOS = 65538;
}