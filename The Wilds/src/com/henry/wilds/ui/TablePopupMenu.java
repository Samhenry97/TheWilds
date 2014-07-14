package com.henry.wilds.ui;

import java.awt.Event;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;

import com.henry.wilds.inner.Counselor;
import com.henry.wilds.inner.Table;
import com.henry.wilds.inner.Team;
import com.henry.wilds.ui.menu.MenuBar;
import com.henry.wilds.ui.menu.MenuItem;
import com.henry.wilds.util.Images;

/**
 * The right click menu for the Project View
 * @author Samuel Henry
 * @since June 22, 2014
 * @version 1.0
 */
public class TablePopupMenu extends JPopupMenu {
	private static final long serialVersionUID = 1L;
	
	public ProjectView pv;
	private Table on;
	private MenuItem editTeam;
	private MenuItem editCounselor;
	private MenuItem cut;
	private MenuItem copy;
	private MenuItem paste;

	/**
	 * Creates a TablePopupMenu relative to the ProjectView
	 * @param frame For the dialogs that pop up
	 * @param pv The ProjectView contained inside
	 */
	public TablePopupMenu(final WildsUI frame, final ProjectView pv) {
		this.pv = pv;
		
		//Edit Menu
		//***********************************************************************************
		editCounselor = new MenuItem("Edit counselor...", MenuBar.EDIT_COUNS_ACCEL, this);
		editCounselor.setIcon(new ImageIcon(Images.EDIT_COUNS_ICON));
		editCounselor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(on.getCounselor() == null) {
					frame.addCouns.setVisible();
				} else {
					Counselor c = on.getCounselor();
					frame.editCouns.setVisible(c);
				}
			}
		});
		
		editTeam = new MenuItem("Edit team...", MenuBar.EDIT_TEAM_ACCEL, this);
		editTeam.setIcon(new ImageIcon(Images.EDIT_TEAM_ICON));
		editTeam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(on.getTeam() == null) {
					frame.addTeam.setVisible();
				} else {
					Team t = on.getTeam();
					frame.editTeam.setVisible(t);
				}
			}
		});
		
		addSeparator();
		
		//Clipboard Menu
		//***********************************************************************************
		
		cut = new MenuItem("Cut", MenuBar.EDIT_CUT_ACCEL, this);
		cut.setIcon(new ImageIcon(Images.CUT_ICON));
		cut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pv.cut();
			}
		});
		
		copy = new MenuItem("Copy", MenuBar.EDIT_COPY_ACCEL, this);
		copy.setIcon(new ImageIcon(Images.COPY_ICON));
		copy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pv.copy();
			}
		});
		
		paste = new MenuItem("Paste", MenuBar.EDIT_PASTE_ACCEL, this);
		paste.setIcon(new ImageIcon(Images.PASTE_ICON));
		paste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pv.paste();
			}
		});
		
		addSeparator();
		
		//Zoom Menu
		//***********************************************************************************
		
		MenuItem zoomIn = new MenuItem("Zoom In", this);
		zoomIn.setIcon(new ImageIcon(Images.ZOOM_IN_ICON));
		zoomIn.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, Event.CTRL_MASK));
		zoomIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pv.zoomIn();
			}
		});
		
		MenuItem zoomOut = new MenuItem("Zoom Out", this);
		zoomOut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, Event.CTRL_MASK));
		zoomOut.setIcon(new ImageIcon(Images.ZOOM_OUT_ICON));
		zoomOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pv.zoomOut();
			}
		});
		
		MenuItem resetZoom = new MenuItem("Reset Zoom", MenuBar.WIN_ZOOM_RESET_ACCEL, this);
		resetZoom.setIcon(new ImageIcon(Images.ZOOM_RESET_ICON));
		resetZoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pv.resetZoom();
			}
		});
		
		addSeparator();
		
		//Preferences Menu
		//***********************************************************************************
		MenuItem preferences = new MenuItem("Preferences...", this);
		preferences.setIcon(new ImageIcon(Images.PREFS_ICON));
		preferences.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.prefs.setVisible();
			}
		});
		
		MenuItem globalPreferences = new MenuItem("Global Preferences", this);
		globalPreferences.setIcon(new ImageIcon(Images.G_PREFS_ICON));
		globalPreferences.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.gPrefs.setVisible();
			}
		});
	}
	
	/**
	 * Shows the menu at the specified location,
	 * and enables options if the menu is on a table
	 * @param x X position of menu
	 * @param y Y position of menu
	 * @param onTable If the menu is currently on a table.
	 */
	public void showMenu(int x, int y, boolean onTable) {
		if(pv.clipboard.isEmpty()) paste.setEnabled(false);
		else paste.setEnabled(true);
		
		if(pv.selected.isEmpty()) {
			copy.setEnabled(false);
			cut.setEnabled(false);
		} else {
			copy.setEnabled(true);
			cut.setEnabled(true);
		}
		
		if(onTable) {
			editCounselor.setEnabled(true);
			editTeam.setEnabled(true);
			show(pv, x, y);
		} else {
			editCounselor.setEnabled(false);
			editTeam.setEnabled(false);
			show(pv, x, y);
		}
	}
	
	/**
	 * Shows the menu at the specified point,
	 * and enables options if the menu is on a table
	 * @param point The point to show the menu at
	 * @param onTable If hte menu is currently on a table
	 */
	public void showMenu(Point point, boolean onTable) {
		showMenu(point.x, point.y, onTable);
	}
	
	/**
	 * Sets the table the menu is currently on
	 * @param t The table to set
	 */
	public void setTableOn(Table t) {
		on = t;
	}
}
