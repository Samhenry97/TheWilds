package com.henry.wilds.util;

import static com.henry.wilds.ui.ProjectView.Action.DRAG;
import static com.henry.wilds.ui.ProjectView.Action.PARTNER;
import static com.henry.wilds.ui.ProjectView.Action.SELECT;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JViewport;

import com.henry.wilds.inner.Table;
import com.henry.wilds.ui.ProjectView;

/**
 * This class listens for input inside the Project View.
 * It listens for keys, mouse clicks, and mouse motion.
 * @author Samuel Henry
 * @since June 20, 2014
 * @version 1.0
 */
public class ProjectViewListener implements KeyListener, MouseListener, MouseMotionListener {
	private Point lastMouse = null;	
	private ProjectView pv;
	
	/**
	 * Creates the listener inside the ProjectView
	 * @param pv the ProjectView
	 */
	public ProjectViewListener(ProjectView pv) {
		this.pv = pv;
	}
	
	//All the keys that are currently pressed
	private static boolean keys[] = new boolean[Constants.TOTAL_KEY_COMBOS];
	
	
	/**
	 * Resets the specified key
	 */
	public static void resetKey(int keyCode) {
		keys[keyCode] = false;
	}
	
	/**
	 * Resets all keys that are pressed
	 */
	public static void resetAll() {
		for(int i = 0; i < Constants.TOTAL_KEY_COMBOS; i++) {
			keys[i] = false;
		}
	}
	
	/**
	 * Return true if the key (specified by keycode) is down,
	 * false if not
	 * @param keyCode The key to be checking
	 * @return true if key(keyCode) is down, false if not
	 */
	public boolean isKeyDown(int keyCode) {
		return keys[keyCode];
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {		
		if(e.getButton() == 3) {
			Point mouse = new Point(e.getX(), e.getY());
			Point mouseZoomed = new Point((int) (e.getX() / (pv.getZoom() / 100)), (int) (e.getY() / (pv.getZoom() / 100)));
			
			if(pv.selected.isEmpty()) {
				boolean shown = false;
				for(int i = 0; i < pv.tables.size(); i++) {
					Table t = pv.tables.get(i);
					if(t.contains(mouseZoomed)) {
						pv.selected.clear();
						pv.selected.add(t);
						pv.tableMenu.setTableOn(t);
						pv.tableMenu.showMenu(mouse, true);
						shown = true;
						break;
					}
				}
				if(!shown) {
					pv.selected.clear();
					pv.tableMenu.showMenu(mouse, false);
				}
			} else if(pv.selected.size() >= 1) {
				boolean shown = false;
				
				for(int i = 0; i < pv.selected.size(); i++) {
					Table t = pv.selected.get(i);
					
					if(t.contains(mouseZoomed)) {
						pv.tableMenu.setTableOn(t);
						if(pv.selected.size() == 1) {
							pv.tableMenu.showMenu(mouse, true);
						} else {
							pv.tableMenu.showMenu(mouse, false);
						}
						shown = true;
						break;
					}
				}
				
				if(!shown) {
					for(int i = 0; i < pv.tables.size(); i++) {
						Table t = pv.tables.get(i);
						if(t.contains(mouseZoomed)) {
							pv.selected.clear();
							pv.selected.add(t);
							pv.tableMenu.setTableOn(t);
							pv.tableMenu.showMenu(mouse, true);
							shown = true;
							break;
						}
					}
				}
				
				if(!shown) {
					pv.selected.clear();
					pv.tableMenu.showMenu(mouse, false);
				}
			}
			
			pv.info.update();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if(lastMouse != null) {
			lastMouse.x = (int) (e.getX() / (pv.getZoom() / 100));
			lastMouse.y = (int) (e.getY() / (pv.getZoom() / 100));
		} else {
			lastMouse = new Point((int) (e.getX() / (pv.getZoom() / 100)), (int) (e.getY() / (pv.getZoom() / 100)));
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {		
		if(e.getButton() == 1) {
			if(!pv.hasFocus()) pv.requestFocus();
			
			if(pv.act == SELECT) {
				if(isKeyDown(KeyEvent.VK_CONTROL)) {
					for(int i = 0; i < pv.tables.size(); i++) {
						Table t = pv.tables.get(i);
						if(t.contains(lastMouse)) {
							if(!pv.selected.contains(t)) {
								pv.selected.add(t);
							} else {
								pv.selected.remove(t);
							}
						}
					}
				} else if(pv.selected.isEmpty()) {
					for(int i = 0; i < pv.tables.size(); i++) {
						Table t = pv.tables.get(i);
						if(t.contains(lastMouse)) {
							pv.selected.add(t);
							break;
						}
					}
				} else {
					boolean unselect = true;
					for(int i = 0; i < pv.selected.size(); i++) {
						Table t = pv.selected.get(i);
						if(t.contains(lastMouse)) {
							unselect = false;
							break;
						}
					}
					if(unselect) {
						pv.selected.clear();
						for(int i = 0; i < pv.tables.size(); i++) {
							Table t = pv.tables.get(i);
							if(t.contains(lastMouse)) {
								pv.selected.add(t);
								break;
							}
						}
					}
				}
				
				pv.info.update();
			} else if(pv.act == PARTNER) {
				for(int i = 0; i < pv.tables.size(); i++) {
					Table t = pv.tables.get(i);
					
					if(t.contains(lastMouse) && !pv.selected.contains(t)) {
						pv.selected.get(0).setPartner(t);
						if(t.getPartner() != null) {
							t.getPartner().setPartner(null);
						}
						t.setPartner(pv.selected.get(0));
						System.out.println(pv.selected.get(0) + " partner = " + t);
						pv.act = SELECT;
						break;
					}
				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(pv.act != SELECT && pv.act != PARTNER) {
			pv.act = SELECT;
			pv.dragRect.stopDragging();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {			
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if(e.getKeyChar() == 'n') {
			if(pv.act == SELECT) {
				if(pv.selected.size() == 1) {
					int pos = pv.tables.indexOf(pv.selected.get(0));
					if(pos == pv.tables.size() - 1) pos = 0;
					else pos++;
					pv.selected.set(0, pv.tables.get(pos));
					pv.info.update();
				}
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Point mouse = new Point((int) (e.getX() / (pv.getZoom() / 100)), (int) (e.getY() / (pv.getZoom() / 100)));
		
		if(pv.act == SELECT) {
			if(!isKeyDown(KeyEvent.VK_CONTROL)) {
				
				int dx = mouse.x - lastMouse.x;
				int dy = mouse.y - lastMouse.y;
				
				if(pv.selected.size() > 1) {					
					for(int i = 0; i < pv.selected.size(); i++) {
						Table t = pv.selected.get(i);
						t.addX(dx);
						t.addY(dy);
						pv.clampTable(t);
					}
				} else if(pv.selected.size() == 1 && isKeyDown(KeyEvent.VK_SHIFT)) {
					Table sel = pv.selected.get(0);
					
					if(!sel.contains(mouse)) {
						sel.setX(mouse.x - sel.getWidth() / 2);
						sel.setY(mouse.y - sel.getHeight() / 2);
					}
					
					int onX = sel.getX();
					int onY = sel.getY();
					
					Rectangle snapTo = ((JViewport) pv.getParent()).getViewRect();
					
					boolean xSet = false;
					boolean ySet = false;
					
					for(int i = 0; i < pv.tables.size(); i++) {
						Table t = pv.tables.get(i);
						if(t != sel && snapTo.intersects(new Rectangle(t.getX(), t.getY(), t.getWidth(), t.getHeight()))) {
							int lowX = t.getX() - Constants.SNAP_TO_CHECK;
							int highX = t.getX() + Constants.SNAP_TO_CHECK;
							int lowY = t.getY() - Constants.SNAP_TO_CHECK;
							int highY = t.getY() + Constants.SNAP_TO_CHECK;
							
							if(!xSet && lowX < onX && onX < highX) {
								sel.setX(t.getX());
								xSet = true;
							}
								
							
							if(!ySet && lowY < onY && onY < highY) {
								sel.setY(t.getY());
								ySet = true;
							}
							
							if(xSet && ySet) break;
						}
					}
					
					if(!xSet) sel.addX(dx);
					if(!ySet) sel.addY(dy);
					
					pv.clampTable(sel);
					pv.info.updatePos();
				} else if(pv.selected.size() == 1 && !isKeyDown(KeyEvent.VK_SHIFT)) {
					Table sel = pv.selected.get(0);
					
					if(!sel.contains(mouse)) {
						sel.setX(mouse.x - sel.getWidth() / 2);
						sel.setY(mouse.y - sel.getHeight() / 2);
					}
					
					sel.addX(dx);
					sel.addY(dy);
					pv.clampTable(sel);
					pv.info.updatePos();
				} else if(pv.selected.isEmpty()) {
					if(!isKeyDown(KeyEvent.VK_SHIFT) && !pv.dragRect.isDragging()) {
						pv.act = DRAG;
						pv.dragRect.start(mouse);
					}
				}
				
				lastMouse = mouse;
			}
		} else if(pv.act == DRAG) {
			pv.dragRect.updateRect(mouse);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		lastMouse.x = (int) (e.getX() / (pv.getZoom() / 100));
		lastMouse.y = (int) (e.getY() / (pv.getZoom() / 100));
	}
	
	/**
	 * Gets the current mouse position
	 */
	public Point getMouse() {
		return lastMouse;
	}
}