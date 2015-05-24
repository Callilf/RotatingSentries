package com.callil.rotatingsentries.entityComponentSystem.components.shooting;

import org.andengine.entity.sprite.Sprite;


public class PrimaryShootingComponent extends AbstractPrimaryAttackComponent {

	public enum ProjectileType {
		STANDARD,
		PIERCING;
	}
	
	/** number of generate projectile per seconds */
	private float frequency;
	
	/** time the last projectile was generate */
	private float lastGenerateTime;
	
	/** Type of the projectile */
	private ProjectileType projectileType;
	
	/** The list of frames to play for the shoot animation. If null, no animation. */
	private int[] shootAnimFrames;
	/** The list of durations of each frame of the shoot animation. */
	private long[] shootAnimDurations;
	
	/**
	 * Constructor
	 * @param projectileType the type of projectile
	 * @param frequency the frequency of generation per seconds
	 */
	public PrimaryShootingComponent(Sprite icon, ProjectileType projectileType, float frequency) {
		super(icon);
		this.frequency = frequency;
		this.projectileType = projectileType;
		lastGenerateTime = 0;
	}
	
	/**
	 * Constructor
	 * @param projectileType the type of projectile
	 * @param frequency the frequency of generation per seconds
	 */
	public PrimaryShootingComponent(Sprite icon, ProjectileType projectileType, float frequency, int[] shootAnimFrames, long[] shootAnimDurations) {
		this(icon, projectileType, frequency);
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

	public ProjectileType getProjectileType() {
		return projectileType;
	}

	public void setProjectileType(ProjectileType projectileType) {
		this.projectileType = projectileType;
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
