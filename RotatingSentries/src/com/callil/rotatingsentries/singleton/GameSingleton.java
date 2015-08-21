/**
 * 
 */
package com.callil.rotatingsentries.singleton;

import java.util.HashSet;
import java.util.Set;

import com.callil.rotatingsentries.enums.PowerUpTypeEnum;

/**
 * @author Callil
 *
 */
public class GameSingleton {
	
	/** The instance of the singleton. */
	private static GameSingleton instance;

	/** The total time elapsed. */
	private float totalTime;
	
	// Area touch related attributes
	/** If the user is touching during this frame. */
	public boolean isTouchingArea;
	public float areaTouchX;
	public float areaTouchY;
	
	/** Set of known power ups. */
	private Set<PowerUpTypeEnum> knownPowerUps = new HashSet<PowerUpTypeEnum>();
	
	/**
	 * Constructor.
	 */
	private GameSingleton() {}

	/** Get the singleton instance. */
	public static synchronized GameSingleton getInstance() {
		if (instance == null) {
			instance = new GameSingleton();
		}
		return instance;
	}
	
	/** Reinit for the start of a new game. */
	public void reinit() {
		knownPowerUps.clear();
		totalTime = 0;
	}

	
	// Getters & Setters
	
	/**
	 * @return the totalTime
	 */
	public float getTotalTime() {
		return totalTime;
	}

	/**
	 * @param totalTime the totalTime to set
	 */
	public void setTotalTime(float totalTime) {
		this.totalTime = totalTime;
	}

	public Set<PowerUpTypeEnum> getKnownPowerUps() {
		return knownPowerUps;
	}

	public void setKnownPowerUps(Set<PowerUpTypeEnum> knownPowerUps) {
		this.knownPowerUps = knownPowerUps;
	}
}
