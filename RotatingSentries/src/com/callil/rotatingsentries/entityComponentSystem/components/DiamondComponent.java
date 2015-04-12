/**
 * 
 */
package com.callil.rotatingsentries.entityComponentSystem.components;

/**
 * @author Callil
 * Defines that the enemies will spawn to go towards this entity.
 */
public class DiamondComponent extends Component {

	/** The period of generation. */
	private float frequency;
	/** The time at which the last enemy was generated. */
	private float lastGenerateTime;
	
	/**
	 * 
	 */
	public DiamondComponent(float frequency) {
		this.frequency = frequency;
	}

	/**
	 * @param name
	 */
	public DiamondComponent(String name) {
		super(name);
	}

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
