/**
 * 
 */
package com.callil.rotatingsentries.entityComponentSystem.components;

import org.andengine.entity.sprite.Sprite;

/**
 * @author Callil
 * Defines that this entity is a robber. This component is used to manage the workflow of a robber.
 */
public class EnemyRobberComponent extends Component {

	public static final int STATE_ARRIVING = 0;
	public static final int STATE_WALKING = 1;
	public static final int STATE_ATTACKING = 2;
	
	/**
	 * The state of the robber.
	 */
	private int state;
	
	/**
	 * The speed of the robber when walking.
	 */
	private float speed;
	/**
	 * The target towards which the robber will move.
	 */
	private Sprite target;
	
	
	
	/**
	 * Constructor.
	 */
	public EnemyRobberComponent(float speed, Sprite target) {
		this(0, speed, target);
	}
	
	/**
	 * Constructor.
	 */
	public EnemyRobberComponent(int state, float speed, Sprite target) {
		this.state = state;
		this.speed = speed;
		this.target = target;
	}

	
	
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public Sprite getTarget() {
		return target;
	}

	public void setTarget(Sprite target) {
		this.target = target;
	}


}
