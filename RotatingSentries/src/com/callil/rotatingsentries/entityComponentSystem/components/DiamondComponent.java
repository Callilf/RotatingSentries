/**
 * 
 */
package com.callil.rotatingsentries.entityComponentSystem.components;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.text.Text;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * @author Callil
 * Defines that the enemies will spawn to go towards this entity.
 */
public class DiamondComponent extends Component {

	/** The period of generation. */
	private float frequency;
	/** The time at which the last enemy was generated. */
	private float lastGenerateTime;
	
	/** The current number of hp. */
	private int life;
	/** The max number of hp (full health). */
	private int lifeMax;
	
	private Text lifeText;
	
	/** The rectangle representing the life bar. */
	private Rectangle lifeBar;
	/** The rectangle representing the max life bar. */
	private Rectangle lifeBarMax;
	
	/**
	 * Constructor.
	 */
	public DiamondComponent(float frequency, int life, Rectangle currlifeBar, Rectangle maxLifeBar) {
		setLifeBar(currlifeBar);		
		setLifeBarMax(maxLifeBar);
		
		setFrequency(frequency);
		setLife(life);
		setLifeMax(life);
	}

	/**
	 * @param name
	 */
	public DiamondComponent(String name) {
		super(name);
	}
	
	/**
	 * Deal damages to the diamond.
	 * @param damage the amount of damages. If negative, it will heal the diamond.
	 */
	public void takeDamage(int damage) {
		this.life = Math.max(0, this.life - damage);
		
		//If healing, increase the max health
		if (damage < 0 && this.life > this.lifeMax) {
			this.lifeMax = this.life;
		}
		
		if (this.lifeText != null) {
			this.lifeText.setText(String.valueOf(this.life));
		}
		if (lifeBar != null) {
			lifeBar.setWidth( (lifeBarMax.getWidth() * life) / this.lifeMax);
		}
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
	}

	public Text getLifeText() {
		return lifeText;
	}

	public void setLifeText(Text lifeText) {
		this.lifeText = lifeText;
	}

	/**
	 * @return the lifeBar
	 */
	public Rectangle getLifeBar() {
		return lifeBar;
	}

	/**
	 * @param lifeBar the lifeBar to set
	 */
	public void setLifeBar(Rectangle lifeBar) {
		this.lifeBar = lifeBar;
	}

	public Rectangle getLifeBarMax() {
		return lifeBarMax;
	}

	public void setLifeBarMax(Rectangle lifeBarMax) {
		this.lifeBarMax = lifeBarMax;
	}

	public int getLifeMax() {
		return lifeMax;
	}

	public void setLifeMax(int lifeMax) {
		this.lifeMax = lifeMax;
	}

	
}
