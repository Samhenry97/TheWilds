package com.henry.wilds.inner;

import java.awt.Color;

/**
 * This is the main class for the Team object in
 * the program - contains a team name and color
 * @author Samuel Henry
 * @since June 15, 2014
 * @version 1.0
 */
public class Team {
	
	private String teamName;
	private Color color;
	
	/**
	 * Creates a blank team
	 */
	public Team() {
		this(null);
	}
	
	/**
	 * Creates a team with a name, and a default color
	 * @param name The name of the team
	 */
	public Team(String name) {
		this(name, Color.BLACK);
	}
	
	/**
	 * Creates a team with a name and color
	 * @param name Team's name
	 * @param color Team's color
	 */
	public Team(String name, Color color) {
		this.teamName = name;
		this.color = color;
	}
	
	/**
	 * Gets the team's name
	 * @return Team's name
	 */
	public String getTeamName() {
		return teamName;
	}
	
	/**
	 * Sets the team's name
	 * @param teamName The new name
	 */
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	/**
	 * Gets the team's color
	 * @return
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Sets the team's color
	 * @param color The new color
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	
	/**
	 * Overrides the Object toString method. This
	 * is primarily used for the JComboBox
	 * @return The team name
	 */
	@Override
	public String toString() {
		return teamName;
	}
	
	/**
	 * Calls the Object class's <code>toString()</code> method
	 * @return Return value of Object's <code>toString()</code>
	 */
	public String superToString() {
		return super.toString();
	}
}