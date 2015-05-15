package com.callil.rotatingsentries.entityComponentSystem.components;

public class ShootingComponent extends Component {

	public enum ProjectileType {
		STANDARD;
	}
	
	/** number of generate projectile per seconds */
	private float frequency;
	
	/** time the last projectile was generate */
	private float lastGenerateTime;
	
	/** The list of frames to play for the shoot animation. If null, no animation. */
	private int[] shootAnimFrames;
	/** The list of durations of each frame of the shoot animation. */
	private long[] shootAnimDurations;
	
	/**
	 * Constructor
	 * @param projectile the type of projectile
	 * @param frequency the frequency of generation per seconds
	 */
	public ShootingComponent(ProjectileType projectile, float frequency) {
		this.frequency = frequency;
		lastGenerateTime = 0;
	}
	
	/**
	 * Constructor
	 * @param projectile the type of projectile
	 * @param frequency the frequency of generation per seconds
	 */
	public ShootingComponent(ProjectileType projectile, float frequency, int[] shootAnimFrames, long[] shootAnimDurations) {
		this(projectile, frequency);
		this.shootAnimFrames = shootAnimFrames;
		this.shootAnimDurations = shootAnimDurations;
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

	public int[] getShootAnimFrames() {
		return shootAnimFrames;
	}

	public void setShootAnimFrames(int[] shootAnimFrames) {
		this.shootAnimFrames = shootAnimFrames;
	}

	public long[] getShootAnimDurations() {
		return shootAnimDurations;
	}

	public void setShootAnimDurations(long[] shootAnimDurations) {
		this.shootAnimDurations = shootAnimDurations;
	}
}
