/**
 * 
 */
package com.callil.rotatingsentries.enums;

/**
 * @author Callil
 * 
 */
public enum SpriteAnimationEnum {
	
	SENTRY_STANDARD_SHOOT(new int[]{2,3,4,5,6,7,8}, 75),
	SENTRY_ELECTRIC_ATTACK(new int[]{0,1,2,3,4,5,6}, 75),
	
	ENEMY_ROBBER_WALK(new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13}, 100),
	ENEMY_ROBBER_ATTACK(new int[]{12,14,15,16,17,18}, 100),
	ENEMY_ROBBER_ATTACK_RECOVER(new int[]{19,17,14,12}, 100),
	ENEMY_ROBBER_CLIMBING(new int[]{20,21,22,23}, 100),
	ENEMY_ROBBER_ROPE(new int[]{0,1,2,3,4,5,6,7,8,9,8,7,6,10,11,12}, 75),
	
	MINE_BLINK(new int[]{0,1}, 500),
	MINE_BLINK_FAST(new int[]{0,1}, 100),
	
	EXPLOSION(new int[]{0,1,2,3,4}, 75);

	/** The frames to display. */
	private int[] frames;
	
	/** The frame durations. */
	private long[] frameDurations;
	
	/**
	 * Constructor
	 * @param frames the array of frames to display
	 * @param oneFrameDuration the duration of each frame
	 */
	private SpriteAnimationEnum(int[] frames, long oneFrameDuration) {
		this.setFrames(frames);
		this.frameDurations = new long[frames.length];
		for (int i=0 ; i<this.frameDurations.length ; i++) {
			this.frameDurations[i] = oneFrameDuration;
		}
	}
	
	
	/**
	 * Constructor
	 * @param frames the array of frames to display
	 * @param durations the array of frame duration
	 */
	private SpriteAnimationEnum(int[] frames, long[] durations) {
		this.setFrames(frames);
		this.frameDurations = durations;
	}
	
	
	
	
	

	/**
	 * @return the frames
	 */
	public int[] getFrames() {
		return frames;
	}

	/**
	 * @param frames the frames to set
	 */
	public void setFrames(int[] frames) {
		this.frames = frames;
	}

	/**
	 * @return the frameDurations
	 */
	public long[] getFrameDurations() {
		return frameDurations;
	}


	/**
	 * @param frameDurations the frameDurations to set
	 */
	public void setFrameDurations(long[] frameDurations) {
		this.frameDurations = frameDurations;
	}
	
}
