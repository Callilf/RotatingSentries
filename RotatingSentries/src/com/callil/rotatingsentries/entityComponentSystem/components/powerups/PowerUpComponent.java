/**
 * 
 */
package com.callil.rotatingsentries.entityComponentSystem.components.powerups;

import com.callil.rotatingsentries.entityComponentSystem.components.Component;
import com.callil.rotatingsentries.singleton.GameSingleton;

/**
 * @author Callil
 * Defines that the entity can be picked up by shooting it, and disappears after some time.
 */
public class PowerUpComponent extends Component {

	/** The number of seconds before it disappears. */
	private float timeToLive;
	
	/** The time at which it spawned. */
	private float spawnTime;
	
	
	/**
	 * Constructor.
	 * @param timeToLive the number of seconds before the power up disappears
	 */
	public PowerUpComponent(float timeToLive) {
		this.timeToLive = timeToLive;
		this.spawnTime = GameSingleton.getInstance().getTotalTime();
	}


	
	
	
	
	public float getTimeToLive() {
		return timeToLive;
	}


	public void setTimeToLive(float timeToLive) {
		this.timeToLive = timeToLive;
	}


	public float getSpawnTime() {
		return spawnTime;
	}


	public void setSpawnTime(float spawnTime) {
		this.spawnTime = spawnTime;
	}

}
