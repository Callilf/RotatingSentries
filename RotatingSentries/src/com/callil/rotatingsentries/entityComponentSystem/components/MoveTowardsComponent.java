/**
 * 
 */
package com.callil.rotatingsentries.entityComponentSystem.components;

import org.andengine.entity.sprite.Sprite;

import android.util.Log;


/**
 * @author Callil
 * Defines that the entity will move either straight or towards the center of a Sprite target.
 */
public class MoveTowardsComponent extends Component {

	/** The speed of movements. */
	private float speed;
	
	
	
	/** The target of the movement. */
	private Sprite target;
	
	/** The x direction of the movement. */
	private float directionX;
	/** The y direction of the movement. */
	private float directionY;
	
	/**
	 * Constructor.
	 * At least one of initialDirectionX or initialDIrectionY must be equal to 1.
	 * @param speed the speed of movement
	 * @param initialDirectionX the initial direction X between 0.0f and 1.0f.
	 * @param initialDirectionY the initial direction Y between 0.0f and 1.0f.
	 */
	public MoveTowardsComponent(float speed, float initialDirectionX, float initialDirectionY) {
		this.speed = speed;
		this.directionX = initialDirectionX;
		this.directionY = initialDirectionY;
	}
	
	
	
	/**
	 * Constructor.
	 * @param speed the speed of movement
	 * @param target The target of the movement
	 */
	public MoveTowardsComponent(float speed, Sprite target) {
		this.speed = speed;
		this.target = target;
	}

	
	
	//Getters & Setters
	
	/**
	 * @return the speed
	 */
	public float getSpeed() {
		return speed;
	}

	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(float speed) {
		this.speed = speed;
	}

	/**
	 * @return the directionX
	 */
	public float getDirectionX() {
		return directionX;
	}

	/**
	 * @param directionX the directionX to set
	 */
	public void setDirectionX(float directionX) {
		this.directionX = directionX;
	}

	/**
	 * @return the directionY
	 */
	public float getDirectionY() {
		return directionY;
	}

	/**
	 * @param directionY the directionY to set
	 */
	public void setDirectionY(float directionY) {
		this.directionY = directionY;
	}

	/**
	 * @return the target
	 */
	public Sprite getTarget() {
		return target;
	}

	/**
	 * @param target the target to set
	 */
	public void setTarget(Sprite target) {
		this.target = target;
	}

}
