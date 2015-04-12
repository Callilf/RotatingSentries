package com.callil.rotatingsentries.entityComponentSystem.components;

public class ShootingComponent extends Component {

	public enum ProjectileType {
		STANDARD;
	}
	
	/** number of generate projectile per seconds */
	private float frequency;
	
	/** time the last projectile was generate */
	private float lastGenerateTime;
	
	/**
	 * Constructor
	 * @param projectile the type of projectile
	 * @param frequency the frequency of generation per seconds
	 */
	public ShootingComponent(ProjectileType projectile, float frequency) {
		this.frequency = frequency;
		lastGenerateTime = 0;
	}

	/// GETTERS & SETTERS
	
	public float getFrequency() {
		return frequency;
	}

	public void setFrequency(float frequency) {
		this.frequency = frequency;
	}

	public float getLastGenerateTime() {
		return lastGenerateTime;
	}

	public void setLastGenerateTime(float lastGenerateTime) {
		this.lastGenerateTime = lastGenerateTime;
	}
}
