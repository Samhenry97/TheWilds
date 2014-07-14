package com.henry.wilds.util;

import java.awt.image.BufferedImage;

/**
 * This class contains all the images and icons for the program
 * @author Samuel Henry
 * @version 1.0
 * @since July 6, 2014
 */
public class Images {

	//Images
	public static BufferedImage ICON;
	public static BufferedImage ADD_COUNS_ICON;
	public static BufferedImage ADD_TEAM_ICON;
	public static BufferedImage ADD_COUNS;
	public static BufferedImage ADD_TEAM;
	public static BufferedImage PREFS_ICON;
	public static BufferedImage G_PREFS_ICON;
	public static BufferedImage SAVE_ICON;
	public static BufferedImage OPEN_ICON;
	public static BufferedImage NEW_ICON;
	public static BufferedImage EDIT_COUNS_ICON;
	public static BufferedImage EDIT_TEAM_ICON;
	public static BufferedImage EDIT_COUNS;
	public static BufferedImage EDIT_TEAM;
	public static BufferedImage EXIT_ICON;
	public static BufferedImage ADD_TABLE_ICON;
	public static BufferedImage SAVE_AS_ICON;
	public static BufferedImage ZOOM_IN_ICON;
	public static BufferedImage ZOOM_OUT_ICON;
	public static BufferedImage ZOOM_RESET_ICON;
	public static BufferedImage COPY_ICON;
	public static BufferedImage CUT_ICON;
	public static BufferedImage PASTE_ICON;
	public static BufferedImage RANDOMIZE;
	
	public static BufferedImage TABLE_IMAGE;
	
	/**
	 * Loads all the images in the program
	 */
	public static void loadImages() {
		System.out.println("Loading images");
		
		//Icons
		ICON = Util.getImage("icons/favicon.png");
		ADD_COUNS_ICON = Util.getImage("icons/add_couns_icon.png");
		ADD_TEAM_ICON = Util.getImage("icons/add_team_icon.png");
		PREFS_ICON = Util.getImage("icons/prefs.png");
		G_PREFS_ICON = Util.getImage("icons/gprefs.png");
		SAVE_ICON = Util.getImage("icons/save.png");
		OPEN_ICON = Util.getImage("icons/open.png");
		NEW_ICON = Util.getImage("icons/new.png");
		EDIT_COUNS_ICON = Util.getImage("icons/edit_couns_icon.png");
		EDIT_TEAM_ICON = Util.getImage("icons/edit_team_icon.png");
		EXIT_ICON = Util.getImage("icons/exit.png");
		ADD_TABLE_ICON = Util.getImage("icons/add_table.png");
		SAVE_AS_ICON = Util.getImage("icons/save_as.png");
		ZOOM_IN_ICON = Util.getImage("icons/zoom_in.png");
		ZOOM_OUT_ICON = Util.getImage("icons/zoom_out.png");
		ZOOM_RESET_ICON = Util.getImage("icons/reset_zoom.png");
		CUT_ICON = Util.getImage("icons/cut.png");
		COPY_ICON = Util.getImage("icons/copy.png");
		PASTE_ICON = Util.getImage("icons/paste.png");
		RANDOMIZE = Util.getImage("icons/shuffle.png");
		
		//General
		ADD_COUNS = Util.getImage("general/add_couns.png");
		ADD_TEAM = Util.getImage("general/add_team.png");
		EDIT_COUNS = Util.getImage("general/edit_couns.png");
		EDIT_TEAM= Util.getImage("general/edit_team.png");
		TABLE_IMAGE = Util.getImage("general/table.png");
		
		System.out.println("Done loading images\n");
	}

}