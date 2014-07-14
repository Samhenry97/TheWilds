package com.henry.wilds.inner;

import java.awt.Graphics;
import java.awt.Point;

import com.henry.wilds.ui.ProjectView;
import com.henry.wilds.util.Constants;

/**
 * This is the main class for a Table in the program.
 * It holds the x, y, width, and height, along with the
 * Counselor and Team of the table.
 * @author Samuel Henry
 * @since June 1, 2014
 * @version 1.0
 */
public class Table {

	private Counselor counselor;
	private Team team;
	private Table partner;
	private String id;
	private boolean fixed = false;
	
	private int x;
	private int y;
	private int width;
	private int height;
	
	/**
	 * Creates a table at 0, 0, with the constant table width and height
	 */
	public Table() {
		this(null);
	}
	
	/**
	 * Creates a table at 0, 0, with the constant width and height
	 * along with a counselor
	 * @param counselor The counselor at the table
	 */
	public Table(Counselor counselor) {
		this(counselor, 0, 0, Constants.TABLE_WIDTH, Constants.TABLE_HEIGHT);
	}
	
	/**
	 * Creates a table at a specified x, y, width, and height, along
	 * with a counselor.
	 * @param counselor The counselor at the table.
	 * @param x X position of the table.
	 * @param y Y position of the table.
	 * @param width Width of the table.
	 * @param height Height of the table.
	 */
	public Table(Counselor counselor, int x, int y, int width, int height) {
		this(counselor, null, x, y, width, height);
	}
	
	/**
	 * Creates a table at a specified x, y, width, and height, along
	 * with a counselor and team.
	 * @param counselor Counselor at the table
	 * @param team Team of the table.
	 * @param x X position of the table.
	 * @param y Y position of the table.
	 * @param width Width of the table.
	 * @param height Height of the table.
	 */
	public Table(Counselor counselor, Team team, int x, int y, int width,	int height) {
		this.counselor = counselor;
		this.team = team;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	/**
	 * Draws the table with an image or a color,
	 * including its name and such
	 * @param g Graphics used for drawing
	 */
	public void render(Graphics g) {
		if(ProjectView.TABLE_IMAGE != null) {
			g.drawImage(ProjectView.TABLE_IMAGE, x, y, width, height, null);
		} else {
			g.fillRect(x, y, width, height);
		}
		if(team != null) {
			g.setColor(team.getColor());
			g.fillRect(x + width - 10, y, 10, 10);
		} else {
			g.setColor(Constants.TABLE_COLOR);
		}
		if(counselor != null) {
			g.drawString(counselor.getName(), x + 10, y + height / 2 - g.getFont().getSize() / 2);
			if(counselor.getCabinName() != null) {
				g.drawString(counselor.getCabinName(), x + 10, y + height / 2 + g.getFont().getSize() / 2);
			}
		}
	}
	
	/**
	 * Finds if a point is inside the table (usually
	 * used for mouse collision detection)
	 * @param p Point to check
	 * @return True if point is inside, false otherwise
	 */
	public boolean contains(Point p) {
		return x < p.x && x + width > p.x && y < p.y && y + height > p.y;
	}

	/**
	 * Gets the table's counselor
	 * @return Table's counselor
	 */
	public Counselor getCounselor() {
		return counselor;
	}
	
	/**
	 * Sets the table's counselor
	 * @param counselor Counselor to add to table
	 */
	public void setCounselor(Counselor counselor) {
		this.counselor = counselor;
	}

	/**
	 * Gets the table's x position
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets the table's x position
	 * @param x New x position for table.
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * Adds a certain amount to the x position
	 * @param amount Amount to add to x position
	 */
	public void addX(int amount) {
		this.x += amount;
	}

	/**
	 * Gets the table's y position
	 * @return Table's y position
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the table's y position
	 * @param y New y position for table
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Adds a specified amount to table's y position
	 * @param amount Amount to add to table's y position
	 */
	public void addY(int amount) {
		this.y += amount;
	}

	/**
	 * Gets the table's width
	 * @return Table's width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Sets the table's width
	 * @param width New width for table
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Gets the table's height
	 * @return The height of the table
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Sets the table's height
	 * @param height New height for the table.
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * Gets the table's team
	 * @return the Team of the table
	 */
	public Team getTeam() {
		return team;
	}

	/**
	 * Sets the table's team
	 * @param team New team for table
	 */
	public void setTeam(Team team) {
		this.team = team;
	}
	
	/**
	 * Gets the table's partner table
	 * @return The table's partner table
	 */
	public Table getPartner() {
		return partner;
	}
	
	/**
	 * Sets the table's partner table
	 * @param table The new partner table
	 */
	public void setPartner(Table table) {
		partner = table;
	}
	
	/**
	 * Gets the table's id
	 * @return Table's id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the table's id
	 * @param id The new id
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * If the table is fixed
	 * @return The boolean variable fixed
	 */
	public boolean isFixed() {
		return fixed;
	}
	
	/**
	 * Sets if the table is fixed or not
	 * @param fixed The new value for fixed
	 */
	public void setFixed(boolean fixed) {
		this.fixed = fixed;
	}
}