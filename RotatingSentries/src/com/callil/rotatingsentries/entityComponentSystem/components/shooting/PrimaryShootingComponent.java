package com.callil.rotatingsentries.entityComponentSystem.components.shooting;

import org.andengine.entity.sprite.Sprite;


public class PrimaryShootingComponent extends AbstractPrimaryAttackComponent {
	
	/** The period of spawn of projectiles. */
	private float periodOfSpawn;
	
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
		this.periodOfSpawn = frequency;
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
	
	public float getPeriodOfSpawn() {
		return periodOfSpawn;
	}

	public void setPeriodOfSpawn(float spawnPeriod) {
		this.periodOfSpawn = Math.max(spawnPeriod, 0.1f);
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
