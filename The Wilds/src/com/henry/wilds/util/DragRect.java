package com.henry.wilds.util;

import java.awt.Point;
import java.awt.Rectangle;

import com.henry.wilds.inner.Table;
import com.henry.wilds.ui.ProjectView;

/**
 * This is the class for mouse dragging - it shows
 * as a Rectangle on the screen for selecting multiple
 * tables by dragging
 * @author Samuel Henry
 * @since July 1, 2014
 * @version 1.0
 */
public class DragRect extends Rectangle {
	private static final long serialVersionUID = 1L;
	
	private Point dragStart;
	private boolean dragging = false;
	private ProjectView pv;
	
	public DragRect(ProjectView pv) {
		this.pv = pv;
	}
	
	/**
	 * Sets the starting Point of the dragging,
	 * and sets the dragging boolean to true
	 * @param startPos The starting point of the dragging
	 */
	public void start(Point startPos) {
		dragStart = startPos;
		dragging = true;
	}
	
	/**
	 * Updates the rectangle based on a point
	 * @param mouse The Point to update by
	 */
	public void updateRect(Point mouse) {
		if(dragStart.x < mouse.x) {
			x = dragStart.x;
			width = mouse.x - dragStart.x;
		} else {
			x = mouse.x;
			width = dragStart.x - mouse.x;
		}
		
		if(dragStart.y < mouse.y) {
			y = dragStart.y;
			height = mouse.y - dragStart.y;
		} else {
			y = mouse.y;
			height = dragStart.y - mouse.y;
		}
	}
	
	/**
	 * Updates general things in the DragRect
	 */
	public void update() {
		for(int i = 0; i < pv.tables.size(); i++) {
			Table t = pv.tables.get(i);
			if(intersects(new Rectangle(t.getX(), t.getY(), t.getWidth(), t.getHeight()))) {
				if(!pv.selected.contains(t)) {
					pv.selected.add(t);
				}
			} else {
				if(pv.selected.contains(t)) {
					pv.selected.remove(t);
				}
			}
		}
	}
	
	/**
	 * Returns the dragging variable
	 * @return True if dragging, false otherwise
	 */
	public boolean isDragging() {
		return dragging;
	}
	
	/**
	 * Sets the mouse dragging to a boolean
	 * @param dragging New true/false value
	 */
	public void setDragging(boolean dragging) {
		this.dragging = dragging;
	}
	
	/**
	 * Stops the Rectangle from dragging, making its
	 * width and height 0
	 */
	public void stopDragging() {
		dragging = false;
		dragStart = null;
		width = 0;
		height = 0;
		pv.info.update();
	}

}