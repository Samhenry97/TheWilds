package com.henry.wilds.inner;


/**
 * The main class for the Counselor
 * @author Samuel henry
 * @since June 15, 2014
 * @version 1.0
 */
public class Counselor {
	
	private String name;
	private String cabinName;
	private Team team;
	private Counselor partner;
	
	/**
	 * Creates an empty counselor
	 */
	public Counselor() {
		this("", "", null);
	}
	
	/**
	 * Creates a counselor with a specified name, cabin name, and Team
	 * @param name Counselor's name
	 * @param cabinName Counselor's cabin name
	 * @param team Counselor's team
	 */
	public Counselor(String name, String cabinName, Team team) {
		this(name, cabinName, team, null);
	}
	
	/**
	 * Creates a counselor with a specified name, cabin name, Team,
	 * and partner
	 * @param name Counselors' name
	 * @param cabinName Counselor's cabin name
	 * @param team Counselor's team
	 * @param partner Counselor's partner
	 */
	public Counselor(String name, String cabinName, Team team, Counselor partner) {
		this.name = name;
		this.team = team;
		this.partner = partner;
		this.cabinName = cabinName;
	}
	
	/**
	 * Gets the counselor's name
	 * @return The name of the counselor
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the counselor's name
	 * @param name The new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the counselor's cabin name
	 * @return The cabin name
	 */
	public String getCabinName() {
		return cabinName;
	}

	/**
	 * Sets the counselor's cabin name
	 * @param cabinName The new cabin name
	 */
	public void setCabinName(String cabinName) {
		this.cabinName = cabinName;
	}

	/**
	 * Gets the counselor's team
	 * @return The team
	 */
	public Team getTeam() {
		return team;
	}

	/**
	 * Sets the counselor's team
	 * @param team The new team
	 */
	public void setTeam(Team team) {
		this.team = team;
	}

	/**
	 * Gets the counselor's partner
	 * @return Counselor's partner
	 */
	public Counselor getPartner() {
		return partner;
	}

	/**
	 * Sets the counselor's partner
	 * @param partner The new partner
	 */
	public void setPartner(Counselor partner) {
		this.partner = partner;
	}
	
	/**
	 * The overridden Object method toString
	 * @return The counselor's name
	 */
	@Override
	public String toString() {
		return name;
	}
	
	/**
	 * Calls the Object class's <code>toString()</code> method
	 * @return Return value of Object's <code>toString()</code>
	 */
	public String superToString() {
		return super.toString();
	}
}