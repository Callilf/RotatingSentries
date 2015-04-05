/**
 * 
 */
package com.callil.rotatingsentries;

/**
 * @author Callil
 *
 */
public class GameSingleton {
	
	/** The instance of the singleton. */
	private static GameSingleton instance;

	/** The total time elapsed. */
	private float totalTime;
	
	
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
}
