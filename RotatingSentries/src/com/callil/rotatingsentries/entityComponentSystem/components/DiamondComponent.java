/**
 * 
 */
package com.callil.rotatingsentries.entityComponentSystem.components;

import org.andengine.entity.text.Text;

/**
 * @author Callil
 * Defines that the enemies will spawn to go towards this entity.
 */
public class DiamondComponent extends Component {

	/** The period of generation. */
	private float frequency;
	/** The time at which the last enemy was generated. */
	private float lastGenerateTime;
	
	/** The number of hp. */
	private int life;
	private Text lifeText;
	
	/**
	 * 
	 */
	public DiamondComponent(float frequency, int life, Text lifeText) {
		lifeText.setZIndex(50);
		setFrequency(frequency);
		setLifeText(lifeText);
		setLife(life);
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

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
		if (this.lifeText != null) {
			this.lifeText.setText(String.valueOf(this.life));
		}
	}

	public Text getLifeText() {
		return lifeText;
	}

	public void setLifeText(Text lifeText) {
		this.lifeText = lifeText;
	}

}
