package com.henry.wilds.inner;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.henry.wilds.ui.ProjectView;
import com.henry.wilds.ui.WildsUI;
import com.henry.wilds.util.Constants;

/**
 * This is the class for the clipboard for copying and
 * pasting tables to the room
 * @author Samuel Henry
 * @since July 1, 2014
 * @version 1.0
 */
public class Clipboard {
	
	private ArrayList<Object> clipboard = new ArrayList<Object>();
	private WildsUI frame;
	private ProjectView pv;
	
	/**
	 * Creates the clipboard
	 * @param frame The main window to create inside
	 */
	public Clipboard(WildsUI frame, ProjectView pv) {
		this.frame = frame;
		this.pv = pv;
	}
	
	/**
	 * Copies the selected data to the clipboard
	 * @param toCopy The ArrayList to copy to the clipboard
	 */
	public void copy(ArrayList<Table> toCopy) {
		if(!clipboard.isEmpty()) clipboard.clear();
		
		for(int i = 0; i < toCopy.size(); i++) {
			clipboard.add(toCopy.get(i));
		}
		
		System.out.println("Copying");
	}
	
	/**
	 * Cuts the selected data to the clipboard
	 * @param toCut The ArrayList to cut to the clipboard
	 */
	public void cut(ArrayList<Table> toCut) {
		if(!clipboard.isEmpty()) clipboard.clear();
		
		for(int i = 0; i < toCut.size(); i++) {
			Table t = toCut.get(i);
			
			clipboard.add(t);
			
			pv.tables.remove(t);
		}
		
		System.out.println("Cutting");
	}
	
	/**
	 * Pastes the contents of the clipboard onto the ProjectView
	 */
	public void paste() {
		if(!clipboard.isEmpty()) {
			pv.selected.clear();
			
			for(int i = 0; i < clipboard.size(); i++) {
				Object o = clipboard.get(i);
				
				if(o instanceof Table) {
					Table t = (Table) o;
					Table newTable = new Table(null, t.getTeam(), t.getX() + Constants.CLIPBOARD_OFFS, t.getY() + Constants.CLIPBOARD_OFFS, t.getWidth(), t.getHeight());
					
					pv.tables.add(newTable);
					pv.selected.add(newTable);
				}
			}
		} else {
			JOptionPane.showMessageDialog(frame, "Nothing is currently copied to the clipboard :/");
		}
		
		System.out.println("Pasting");
	}
	
	/**
	 * Clears the data in the clipboard
	 */
	public void clear() {
		clipboard.clear();
	}
	
	public boolean isEmpty() {
		return clipboard.isEmpty();
	}

}