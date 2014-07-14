package com.henry.wilds.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JPanel;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;

import com.henry.wilds.inner.Clipboard;
import com.henry.wilds.inner.Counselor;
import com.henry.wilds.inner.Table;
import com.henry.wilds.inner.Team;
import com.henry.wilds.util.Constants;
import com.henry.wilds.util.DragRect;
import com.henry.wilds.util.Images;
import com.henry.wilds.util.ProjectViewListener;
import com.henry.wilds.util.Time;
import com.henry.wilds.util.Util;

/**
 * This class contains the main view for the
 * dining hall. It contains all the tables and
 * counselor/team data.
 * @author Samuel Henry
 * @version 1.0
 * @since June 15, 2014
 */
public class ProjectView extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;
	
	public static enum Action {DRAG, SELECT, PARTNER};
	
	public static int TABLE_WIDTH = Constants.TABLE_WIDTH;
	public static int TABLE_HEIGHT = Constants.TABLE_HEIGHT;
	public static int TABLE_SPEED = Constants.TABLE_SPEED;
	public static int PROJ_VIEW_WIDTH = Constants.PROJ_VIEW_WIDTH;
	public static int PROJ_VIEW_HEIGHT = Constants.PROJ_VIEW_HEIGHT;
	public static BufferedImage TABLE_IMAGE = Images.TABLE_IMAGE;
	public static String TABLE_IMAGE_LOC = "Default";
	public static Color PROJ_VIEW_BACK_COLOR = Constants.PROJ_VIEW_BACK_COLOR;
	
	//**************************************************************************************
	
	public ArrayList<Table> selected = new ArrayList<Table>();
	public ArrayList<Counselor> counselors = new ArrayList<Counselor>();
	public ArrayList<Table> tables = new ArrayList<Table>();
	public ArrayList<Team> teams = new ArrayList<Team>();
	
	//For running the thread of this JPanel
	private Thread thread;
	private boolean running;
	public ProjectViewListener l = new ProjectViewListener(this);
	private int fps = 0;
	
	//Width and height
	private int width;
	private int height;
	private double zoom = 100;
	
	public Info info;
	public TablePopupMenu tableMenu;
	public Action act = Action.SELECT;
	public Clipboard clipboard;
	public DragRect dragRect;
	public int timeBeforeTableMove = 20;
	public int tableFrame = timeBeforeTableMove;
	
	public int blinkFrameStart = 2;
	public int blinkFrame = blinkFrameStart;
	public int blinkRestart = 8;
	public int blinkSize = 0;
	public int blinkMax = 8;
	
	/**
	 * Creates a ProjectView for moving/editing tables.
	 * @param frame The WildsUI main frame it is contained inside
	 * @param info The info JPanel that contains data for the selected table.
	 */
	public ProjectView(WildsUI frame, Info info) {
		this.info = info;
		this.clipboard = new Clipboard(frame, this);
		this.dragRect = new DragRect(this);
		
		tableMenu = new TablePopupMenu(frame, this);
		
		setSize(Constants.PROJ_VIEW_WIDTH, Constants.PROJ_VIEW_HEIGHT);
		setPreferredSize(new Dimension(Constants.PROJ_VIEW_WIDTH, Constants.PROJ_VIEW_HEIGHT));
		addMouseListener(l);
		addMouseMotionListener(l);
		addKeyListener(l);
		
		width = getWidth();
		height = getHeight();
		
		setBackground(new Color(30, 30, 30));
		
		sortLists();
		
		start();
	}
	
	/**
	 * Updates the contents of the ProjectView
	 */
	public void update() {
		width = getWidth();
		height = getHeight();
		
		if(l.isKeyDown(KeyEvent.VK_CONTROL) && l.isKeyDown(KeyEvent.VK_A)) {
			if(selected.size() != tables.size()) {
				selected.clear();
				
				for(int i = 0; i < tables.size(); i++) {
					selected.add(tables.get(i));
				}
			}
		}
		if(l.isKeyDown(KeyEvent.VK_ESCAPE)) {
			if(act == Action.PARTNER) {
				act = Action.SELECT;
			}
		}
		
		if(width != PROJ_VIEW_WIDTH || height != PROJ_VIEW_HEIGHT){
			setPreferredSize(new Dimension(PROJ_VIEW_WIDTH, PROJ_VIEW_HEIGHT));
		}
		
		if(dragRect.isDragging()) {
			dragRect.update();
		}
		
		if(l.isKeyDown(KeyEvent.VK_SHIFT)) {
			if(tableFrame <= timeBeforeTableMove) {
				tableFrame++;
			} else {
				for(int i = 0; i < selected.size(); i++) {
					moveTable(selected.get(i), 1);
				}
				tableFrame = 0;
			}
		} else {
			for(int i = 0; i < selected.size(); i++) {
				moveTable(selected.get(i), Constants.TABLE_SPEED);
			}
			if(tableFrame != timeBeforeTableMove) tableFrame = timeBeforeTableMove;
		}
		
		info.updatePos();
	}
	
	/**
	 * Key input for moving and editing tables.
	 * @param t The table to move
	 */
	public void moveTable(Table t, int speed) {
		if(!l.isKeyDown(KeyEvent.VK_CONTROL)) {
			if(l.isKeyDown(KeyEvent.VK_ESCAPE)) {
				selected.clear();
				info.update();
			} else if(l.isKeyDown(KeyEvent.VK_DELETE)) {
				for(int i = 0; i < selected.size(); i++) {
					tables.remove(selected.get(i));
				}
				selected.clear();
				info.update();
			}
			
			boolean up = l.isKeyDown(KeyEvent.VK_UP) || l.isKeyDown(KeyEvent.VK_W);
			boolean down = l.isKeyDown(KeyEvent.VK_DOWN) || l.isKeyDown(KeyEvent.VK_S);
			boolean right = l.isKeyDown(KeyEvent.VK_RIGHT) || l.isKeyDown(KeyEvent.VK_D);
			boolean left = l.isKeyDown(KeyEvent.VK_LEFT) || l.isKeyDown(KeyEvent.VK_A);
			
			if(!up && !down && !left && !right) {
				if(tableFrame != timeBeforeTableMove) tableFrame = timeBeforeTableMove;
			} else {
				if(!(up && down)) {
					if(up) {
						t.addY(-speed);
					} else if(down) {
						t.addY(speed);
					}
				}
				
				if(!(left && right)) {
					if(left) {
						t.addX(-speed);
					} else if(right) {
						t.addX(speed);
					}
				}
				clampTable(t);
			}
		}
	}
	
	/**
	 * Draws all the tables to the Project View
	 * @param g Graphics used for painting
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if(zoom != 100) {
			Graphics2D g2 = (Graphics2D) g;
			g2.scale(zoom / 100, zoom / 100);
		}
		
		g.setColor(PROJ_VIEW_BACK_COLOR);
		g.fillRect(0, 0, width, height);
		
		for(int i = 0; i < tables.size(); i++) {
			Table t = tables.get(i);
			
			t.render(g);
			if(selected.contains(t)) {
				g.setColor(Color.WHITE);
				g.drawRect(t.getX() - 2, t.getY() - 2, t.getWidth() + 4, t.getHeight() + 4);
			}
		}
		
		if(act == Action.PARTNER) {
			if(blinkFrame < blinkRestart) {
				blinkFrame++;
			} else {
				blinkSize++;
				blinkFrame = blinkFrameStart;
				if(blinkSize > blinkMax) {
					blinkSize = 0;
				}
			}
			
			if(selected.get(0).getPartner() != null) {
				Table t = selected.get(0).getPartner();
				
				g.setColor(new Color(0, 255, 0));
				g.drawRect(t.getX() - blinkSize, t.getY() - blinkSize, t.getWidth() + blinkSize * 2, t.getHeight() + blinkSize * 2);
			}
			
			for(int i = 0; i < tables.size(); i++) {
				Table t = tables.get(i);
				
				if(t.contains(l.getMouse())) {					
					g.setColor(new Color(50, 50, 50));
					
					g.drawRect(t.getX() - blinkSize, t.getY() - blinkSize, t.getWidth() + blinkSize * 2, t.getHeight() + blinkSize * 2);
					break;
				}
			}
		}
		
		if(dragRect.isDragging()) {
			g.setColor(Color.BLACK);
			g.drawRect(dragRect.x, dragRect.y, dragRect.width, dragRect.height);
		}
		
		if(Constants.SHOW_ADDITIONAL_INFO) {
			int x = ((JViewport) getParent()).getViewRect().x + 10;
			int y = ((JViewport) getParent()).getViewRect().y + 20;
			
			g.setColor(Color.BLACK);
			g.drawString("FPS: " + fps, x, y);
		}
		
		g.dispose();
	}
	
	/**
	 * Makes sure the table is still inside the ProjectView,
	 * and if not, if moves it inside.
	 * @param t The table to clamp
	 */
	public void clampTable(Table t) {
		if(t.getX() < 0) t.setX(0);
		else if(t.getX() + t.getWidth() > width) t.setX(width - t.getWidth());
		if(t.getY() < 0) t.setY(0);
		else if(t.getY() + t.getHeight() > height) t.setY(height - t.getHeight());
	}
	
	/**
	 * Adds a new table to the ProjectView
	 */
	public void addNewTable() {
		Point mouse = MouseInfo.getPointerInfo().getLocation();
		JViewport viewport = ((JViewport) getParent());
		SwingUtilities.convertPointFromScreen(mouse, viewport);
		Rectangle visible = viewport.getViewRect();
		Table toAdd = null;
		
		mouse.x += visible.x;
		mouse.y += visible.y;
		
		int x;
		int y;
		
		if(visible.contains(mouse)) {	
			x = mouse.x - Constants.TABLE_WIDTH / 2;
			y = mouse.y - Constants.TABLE_HEIGHT / 2;
			
			toAdd = new Table(null, x, y, Constants.TABLE_WIDTH, Constants.TABLE_HEIGHT);
		} else {
			x = visible.x + visible.width / 2 - Constants.TABLE_WIDTH / 2;
			y = visible.y + visible.height / 2 - Constants.TABLE_HEIGHT / 2;
			
			toAdd = new Table(null, x, y, Constants.TABLE_WIDTH, Constants.TABLE_HEIGHT);
		}
		
		if(toAdd != null) {
			tables.add(toAdd);
			selected.clear();
			selected.add(toAdd);
			info.update();
			
			System.out.println("Successfully added " + toAdd.toString());
		}
	}
	
	/**
	 * Copies the selected ArrayList to the Clipboard
	 */
	public void copy() {
		clipboard.copy(selected);
	}
	
	/**
	 * Cuts the selected ArrayList to the Clipboard
	 */
	public void cut() {
		clipboard.cut(selected);
	}
	
	/**
	 * Pastes the contents of the Clipboard onto the ProjectView
	 */
	public void paste() {
		clipboard.paste();
	}
	
	/**
	 * Sets the zoom to a specified number
	 * @param zoom New zoom
	 */
	public void setZoom(double zoom) {
		System.out.println("Setting zoom to " + zoom);
		
		this.zoom = zoom;
	}
	
	/**
	 * Gets the current zoom of the window
	 */
	public double getZoom() {
		return zoom;
	}
	
	/**
	 * Adds an amount to the zoom
	 */
	public void addZoom(double amt) {
		System.out.println("Adding " + amt + " to zoom");
		
		zoom += amt;
		
		if(zoom > Constants.MAX_ZOOM) {
			zoom = Constants.MAX_ZOOM;
		} else if(zoom < Constants.MIN_ZOOM) {
			zoom = Constants.MIN_ZOOM;
		}
	}
	
	/**
	 * Zooms in
	 */
	public void zoomIn() {
		System.out.println("Zooming in");
		
		zoom += Constants.ZOOM_AMT;
		
		if(zoom > Constants.MAX_ZOOM) {
			zoom = Constants.MAX_ZOOM;
		}
	}
	
	/**
	 * Zooms out
	 */
	public void zoomOut() {
		System.out.println("Zooming Out");
		
		zoom -= Constants.ZOOM_AMT;
		
		if(zoom < Constants.MIN_ZOOM) {
			zoom = Constants.MIN_ZOOM;
		}
	}
	
	/**
	 * Resets the zoom
	 */
	public void resetZoom() {
		System.out.println("Reseting zoom");
		
		zoom = 100;
	}
	
	/**
	 * The main loop for the thread that 
	 * updates and renders the ProjectView
	 */
	public void run() {
		running = true;
		
		int frames = 0;
		long frameCounter = 0;
		
		final double frameTime = 1.0 / Constants.FRAME_CAP;
		
		long lastTime = Time.getTime();
		double unprocessedTime = 0;		
		
		while(running) {
			boolean render = false;
			
			long startTime = Time.getTime();
			long passedTime = startTime - lastTime;
			lastTime = startTime;
			
			unprocessedTime += passedTime / (double) Time.SECOND;
			frameCounter += passedTime;
			
			while(unprocessedTime > frameTime) {
				render = true;
				
				unprocessedTime -= frameTime;
				update();
				
				if(frameCounter >= Time.SECOND) {
					fps = frames;
					frames = 0;
					frameCounter = 0;
				}
			}
			
			if(render) {
				repaint();
				frames++;
			} else {
				Util.sleep(1);
			}
		}
	}
	
	/**
	 * Starts the ProjectView thread
	 */
	public void start() {
		try {
			thread = new Thread(this, "Project View");
			thread.start();
			thread.setPriority(Thread.MAX_PRIORITY);
			
			System.out.println("Starting ProjectView thread");
		} catch(Exception e) {
			e.printStackTrace();
			System.err.println("Error creating thread for Project View JPanel");
			System.exit(1);
		}
	}
	
	/**
	 * Creates a new file, loading constants
	 */
	public static void createNewFile() {
		TABLE_WIDTH = Constants.TABLE_WIDTH;
		TABLE_HEIGHT = Constants.TABLE_HEIGHT;
		TABLE_SPEED = Constants.TABLE_SPEED;
		TABLE_IMAGE = Images.TABLE_IMAGE;
		PROJ_VIEW_HEIGHT = Constants.PROJ_VIEW_HEIGHT;
		PROJ_VIEW_WIDTH = Constants.PROJ_VIEW_WIDTH;
		PROJ_VIEW_BACK_COLOR = Constants.PROJ_VIEW_BACK_COLOR;
	}
	
	/**
	 * Searches for a team by its name in the team arraylist
	 * @param name The name to search by
	 * @return The team if found, null if not
	 */
	public Team getTeamByName(String name) {
		for(int i = 0; i < teams.size(); i++) {
			if(teams.get(i).getTeamName().equals(name)) {
				return teams.get(i);
			}
		}
		
		return null;
	}
	
	/**
	 * Gets the counselor based on his/her name
	 * @param name The name to search by
	 * @return The counselor if found, null if not
	 */
	public Counselor getCounsByName(String name) {
		for(int i = 0; i < counselors.size(); i++) {
			if(counselors.get(i).getName().equals(name)) {
				return counselors.get(i);
			}
		}
		
		return null;
	}
	
	/**
	 * Searches the tables arraylist for a table with the
	 * specified id. Returns it if found.
	 * @param id The id to search for
	 * @return The table if found, null if not
	 */
	public Table getTableById(String id) {
		for(int i = 0; i < tables.size(); i++) {
			if(tables.get(i).getId().equals(id)) {
				return tables.get(i);
			}
		}
		
		return null;
	}
	
	/**
	 * Sorts the counselor and team ArrayLists
	 */
	public void sortLists() {
		ArrayList<String> stringList = new ArrayList<String>();
		ArrayList<Counselor> newCounsList = new ArrayList<Counselor>();
		ArrayList<Team> newTeamList = new ArrayList<Team>();
		
		//Sort counselor ArrayList
		//************************************************************************
		System.out.println("Sorting counselor ArrayList.");
		
		for(int i = 0; i < counselors.size(); i++) {
			stringList.add(counselors.get(i).getName());
		}
		
		Collections.sort(stringList);
		
		for(int i = 0; i < stringList.size(); i++) {
			for(int x = 0; x < counselors.size(); x++) {
				if(counselors.get(x).getName().equals(stringList.get(i))) {
					newCounsList.add(counselors.get(x));
					break;
				}
			}
		}
		
		counselors.clear();
		for(int i = 0; i < newCounsList.size(); i++) {
			counselors.add(newCounsList.get(i));
		}
		
		//Sort team ArrayList
		//*************************************************************************
		stringList.clear();
		
		System.out.println("Sorting team ArrayList.");
		
		for(int i = 0; i < teams.size(); i++) {
			stringList.add(teams.get(i).getTeamName());
		}
		
		Collections.sort(stringList);
		
		for(int i = 0; i < stringList.size(); i++) {
			for(int x = 0; x < teams.size(); x++) {
				if(teams.get(x).getTeamName().equals(stringList.get(i))) {
					newTeamList.add(teams.get(x));
					break;
				}
			}
		}
		
		teams.clear();
		for(int i = 0; i < newTeamList.size(); i++) {
			teams.add(newTeamList.get(i));
		}
	}
	
	/**
	 * The big function of the program! Randomizes the counselors
	 * and sets them to their tables :)
	 */
	public void randomizeCounselors() {
		System.out.println("Randomizing counselors!");
		
		
	}
}